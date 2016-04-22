package model.entities;

import java.util.EnumMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import model.skills.Skill;
import model.skills.SkillType;

/**
 * The basic entity that build monsters and gets extended by Heroes.
 */
public class BasicEntity implements Entity {

    /**
     * Minimum level allowed.
     */
    public final static int MIN_LEVEL = 1;

    /**
     * Maximum level allowed.
     */
    public final static int MAX_LEVEL = 20;

    /**
     * Minimum speed allowed.
     */
    public final static int MIN_SPEED = 1;

    /**
     * Standard speed value (assigned if speed is not explicit on builder).
     */
    public final static int STANDARD_SPEED = 5;

    /**
     * Standard hitpoint value (assigned if hp is not explicit on builder).
     */
    public final static int STANDARD_HP = 100;

    /**
     * Standard mana value (assigned if mana is not explicit on builder).
     */
    public static final int STANDARD_MANA = 50;

    /**
     * Standard mana regen value (assigned if mana is not explicit on builder).
     */
    public static final int STANDARD_MANAREGEN = 5;

    private final String name;
    protected EnumMap<StatType, Integer> globalStats = new EnumMap<>(StatType.class);
    private EnumMap<StatType, Integer> currStats = new EnumMap<>(StatType.class);
    private List<Skill> skillList;

    /**
     * @param name
     *            entity's name
     * @param hp
     *            entity's hitpoint
     * @param skillList
     *            entity's skillset
     */
    private BasicEntity(final String name, Optional<Integer> hp, Optional<Integer> level, Optional<Integer> speed,
            Optional<Integer> mana, Optional<Integer> manaRegen, final SkillType[] types)
                    throws IllegalArgumentException {
        //TODO Controllo associato all'enum, con uno stream/ciclo unico
        if (name == null) {
            throw new IllegalArgumentException("Insert a name not null");
        }
        if (!hp.isPresent()) {
            hp = Optional.of(BasicEntity.STANDARD_HP);
        }
        if (!level.isPresent()) {
            level = Optional.of(BasicEntity.MIN_LEVEL);
        }
        if (!speed.isPresent()) {
            speed = Optional.of(BasicEntity.MIN_SPEED);
        }
        if (level.isPresent()
                && (level.get().intValue() > BasicEntity.MAX_LEVEL || level.get().intValue() < BasicEntity.MIN_LEVEL)) {
            throw new IllegalArgumentException("Level outside the allowed range: " + level + " ("
                    + BasicEntity.MIN_LEVEL + " - " + BasicEntity.MAX_LEVEL + ")");
        }
        if (speed.isPresent() && (speed.get().intValue() < BasicEntity.MIN_SPEED)) {
            throw new IllegalArgumentException("Speed parameter must be higher than 0 (your speed: " + speed + ")");
        }
        if (!mana.isPresent()) {
            mana = Optional.of(BasicEntity.STANDARD_MANA);
        }
        if (!manaRegen.isPresent()) {
            manaRegen = Optional.of(BasicEntity.STANDARD_MANAREGEN);
        }

        this.name = name;
        globalStats.put(StatType.HP, hp.get());
        globalStats.put(StatType.LEVEL, level.get());
        globalStats.put(StatType.SPEED, speed.get());
        globalStats.put(StatType.MANA, mana.get());
        globalStats.put(StatType.MANAREGEN, manaRegen.get());
        this.skillList = SkillType.getSkillList(types);
        this.copyStats();
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public int getStat(StatType statType, StatTime time) {
     return (time.equals(StatTime.CURRENT) ? currStats : globalStats).get(statType);
    }

    @Override
    public int setStat(StatType statType, int value, StatTime time, ActionType action) {
        Optional<Integer> newValue = Optional.empty();
        if (action.equals(ActionType.SET)) {
            newValue = Optional.of(value);
        } else {
            int oldValue = this.getStat(statType, time);
            if (action.equals(ActionType.INCREASE)) {
                newValue = Optional.of(oldValue + value);
            } else if (action.equals(ActionType.DECREASE)) {
                newValue = Optional.of(oldValue - value);
            }
        }
        return (time.equals(StatTime.CURRENT) ? currStats : globalStats).replace(statType, newValue.get()).intValue();
    }
    
    @Override
    public void copyStats() {
        this.currStats.clear();
        this.currStats.putAll(this.globalStats);
    }

    @Override
    public List<Skill> getSkillList() {
        return this.skillList;
    }

    @Override
    public List<Skill> getAllowedSkillList() {
        return this.skillList.stream()
                .filter(s -> s.getRequiredLevel() <= this.getStat(StatType.LEVEL, StatTime.GLOBAL))
                .collect(Collectors.toList());
    }

    @Override
    public Skill getSkill(final int index) {
        return this.skillList.get(index);
    }

    public String toString(StatTime time) {
        final StringBuilder sb = new StringBuilder();
        return sb.append("Name: ").append(this.name)
                .append("\nStats: \tHP: ").append(this.getStat(StatType.HP, time))
                .append("\n\tSpeed: ").append(this.getStat(StatType.SPEED, time))
                .append("\n\tLevel: ").append(this.getStat(StatType.LEVEL, time))
                .append("\n\tMana: ").append(this.getStat(StatType.MANA, time))
                .append("\n\tMana Regen: ").append(this.getStat(StatType.MANAREGEN, time))
                .append("\n\tSkills: ").append(this.getAllowedSkillList().toString()).toString();
    }

    /**
     * Builder class revisited to make it reusable over inheritance. Source:
     * http://stackoverflow.com/questions/17164375/subclassing-a-java-builder-class
     *
     * @param <T>
     *            Builder of a class that extends this one
     */
    @SuppressWarnings("unchecked")
    public static class Builder<T extends Builder<? extends T>> {
        private String name;
        private Optional<Integer> hp = Optional.empty();
        private Optional<Integer> level = Optional.empty();
        private Optional<Integer> speed = Optional.empty();
        private Optional<Integer> mana = Optional.empty();
        private Optional<Integer> manaRegen = Optional.empty();
        private SkillType[] types;

        /**
         * Adds a name to the builder instance.
         * 
         * @param name
         *            entity's name
         * @return builder instance
         */
        public T name(final String name) {
            this.name = name;
            return (T) this;
        }

        /**
         * Adds an hp value to the builder instance.
         * 
         * @param hp
         *            entity's hp
         * @return builder instance
         */
        public T hp(final int hp) {
            this.hp = Optional.ofNullable(hp);
            return (T) this;
        }

        /**
         * Adds a level value to the builder instance.
         * 
         * @param level
         *            entity's level
         * @return builder instance
         */
        public T level(final int level) {
            this.level = Optional.ofNullable(level);
            return (T) this;
        }

        /**
         * Adds a speed value to the builder instance.
         * 
         * @param speed
         *            entity's speed
         * @return builder instance
         */
        public T speed(final int speed) {
            this.speed = Optional.ofNullable(speed);
            return (T) this;
        }

        /**
         * Adds a mana value to the builder instance.
         * 
         * @param mana
         *            entity's mana
         * @return builder instance
         */
        public T mana(final int mana) {
            this.mana = Optional.ofNullable(mana);
            return (T) this;
        }

        /**
         * Adds a mana regen value to the builder instance.
         * 
         * @param manaRegen
         *            entity's manaRegen
         * @return builder instance
         */
        public T manaRegen(final int manaRegen) {
            this.manaRegen = Optional.ofNullable(manaRegen);
            return (T) this;
        }

        /**
         * Adds multiple skilletypes to the builder instance.
         * 
         * @param types
         *            entity's skilltypes
         * @return builder instance
         */
        public T skillType(final SkillType... types) {
            this.types = types;
            return (T) this;
        }

        /**
         * Builds the Entity.
         * 
         * @return the new entity to be built.
         * @throws IllegalArgumentException
         */
        public BasicEntity build() {
            return new BasicEntity(this);
        }
    }

    /**
     * Builds the entity by calling the private constructor.
     * 
     * @param builder
     *            data structure where constructor can get data
     */
    protected BasicEntity(final Builder<?> builder) {
        this(builder.name, builder.hp, builder.level, builder.speed, builder.mana, builder.manaRegen, builder.types);
    }

    public enum StatTime {
        GLOBAL, CURRENT;
    }

    public enum ActionType {
        SET, DECREASE, INCREASE;
    }
}
