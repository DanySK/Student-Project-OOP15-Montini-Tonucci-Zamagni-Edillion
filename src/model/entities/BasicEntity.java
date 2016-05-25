package model.entities;

import java.io.Serializable;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.stream.Collectors;

import model.skills.Skill;
import model.skills.SkillType;

/**
 * The basic entity that build monsters and gets extended by Heroes.
 */
public class BasicEntity implements Entity, Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -6177994680160567847L;
    private final String name;
    protected final EnumMap<StatType, Integer> globalStats = new EnumMap<>(StatType.class);
    private final Map<StatType, Integer> currStats = new EnumMap<>(StatType.class);
    private final List<Skill> skillList;

    /**
     * @param name
     *            entity's name
     * @param hp
     *            entity's hitpoint
     * @param skillList
     *            entity's skillset
     */
    private BasicEntity(final String name, final Optional<Integer> hp, final Optional<Integer> level,
            final Optional<Integer> speed, final Optional<Integer> mana, final Optional<Integer> manaRegen,
            final SkillType[] types) throws IllegalArgumentException {

        if (name == null) {
            throw new IllegalArgumentException("Insert a name not null");
        }
        this.name = name;
        globalStats.put(StatType.HP, StatType.HP.check(hp));
        globalStats.put(StatType.LEVEL, StatType.LEVEL.check(level));
        globalStats.put(StatType.SPEED, StatType.SPEED.check(speed));
        globalStats.put(StatType.MANA, StatType.MANA.check(mana));
        globalStats.put(StatType.MANAREGEN, StatType.MANAREGEN.check(manaRegen));

        this.skillList = SkillType.getSkillList(types);
        this.copyStats();
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public int getStat(final StatType statType, final StatTime time) {
        return (time.equals(StatTime.CURRENT) ? currStats : globalStats).get(statType);
    }

    @Override
    public int setStat(final StatType statType, final int value, final StatTime time, ActionType action) {
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

        try { // Negative HP set to 0
            statType.check(newValue);
        } catch (IllegalArgumentException e) {
            if (statType.equals(StatType.HP) && newValue.get() <= 0) {
                newValue = Optional.of(0);
                action = ActionType.SET;
            } else {
                throw e;
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
    public Map<StatType, Integer> getStatMap(final StatTime time) {
        return (time.equals(StatTime.CURRENT) ? currStats : globalStats);
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


    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        BasicEntity other = (BasicEntity) obj;

        for (Entry<StatType, Integer> e : this.currStats.entrySet()) {
            if (other.getStat(e.getKey(), StatTime.CURRENT) != e.getValue()) {
                return false;
            }
        }

        for (Entry<StatType, Integer> e : this.globalStats.entrySet()) {
            if (other.getStat(e.getKey(), StatTime.GLOBAL) != e.getValue()) {
                return false;
            }
        }


        if (name == null) {
            if (other.name != null) {
                return false;
            }
        } else if (!name.equals(other.name)) {
            return false;

        }

        if (skillList == null) {
            if (other.skillList != null) {
                return false;
            }
        } else if (!skillList.equals(other.skillList)) {
            return false;
        }

        return true;
    }

    @Override
    public String toString(final StatTime time) {
        final StringBuilder sb = new StringBuilder();
        return sb.append("Name: ").append(this.name).append("\nStats: \tHP: ").append(this.getStat(StatType.HP, time))
                .append("\n\tSpeed: ").append(this.getStat(StatType.SPEED, time)).append("\n\tLevel: ")
                .append(this.getStat(StatType.LEVEL, time)).append("\n\tMana: ")
                .append(this.getStat(StatType.MANA, time)).append("\n\tMana Regen: ")
                .append(this.getStat(StatType.MANAREGEN, time)).append("\n\tSkills: ")
                .append(this.getAllowedSkillList().toString()).toString();
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
