package model.entities;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import model.skills.Skill;


public class BasicEntity implements Entity {

    public final static int MINLEVEL = 1;
    public final static int MAXLEVEL = 20;
    public final static int STANDARD_SPEED = 5;
    public final static int STANDARD_HP = 100;

    private final String name;
    private int hp = BasicEntity.STANDARD_HP;
    private int level = BasicEntity.MINLEVEL;
    private int speed = BasicEntity.STANDARD_SPEED; //attacchi al secondo (un attacco ogni 60/speed secondi)
    private List<Skill> skillList;

    /**
     * @param name entity's name
     * @param hp entity's hitpoint
     * @param skillList entity's skillset
     */
    private BasicEntity(final String name, final int hp, final int level, final int speed, final List<Skill> skillList) throws IllegalArgumentException {
        if (name == null) {
            throw new IllegalArgumentException("Insert a name not null");
        }
        if (level > BasicEntity.MAXLEVEL || level < BasicEntity.MINLEVEL) {
            throw new IllegalArgumentException("Level outside the allowed range: " + level + " (" + BasicEntity.MINLEVEL + " - " + BasicEntity.MAXLEVEL + ")");
        }
        if (speed < 1) {
            throw new IllegalArgumentException("Speed parameter must be higher than 0 (your speed: " + speed + ")");
        }
        this.name = name;
        this.hp = hp;
        this.level = level;
        this.speed = speed;
        this.skillList = new ArrayList<Skill>(skillList);
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public int getHp() {
        return this.hp;
    }

    @Override
    public void setHp(final int hp) {
        this.hp = hp;
    }

    @Override
    public void increaseHp(final int hp) {
        this.hp += hp;
    }

    @Override
    public void decreaseHp(final int hp) {
        if (this.hp > hp) {
            this.hp -= hp;
        } else {
            this.hp = 0;
        }
    }

    @Override
    public int getLevel() {
        return level;
    }

    @Override
    public void setLevel(final int level) {
        this.level = level;
    }

    @Override
    public int getSpeed() {
        return speed;
    }

    @Override
    public void setSpeed(final int speed) {
        this.speed = speed;
    }

    @Override
    public List<Skill> getSkillList() {
        return this.skillList;
    }

    public List<Skill> getAllowedSkillList() {
        return this.skillList.stream().filter(s -> s.getRequiredLevel() <= this.level).collect(Collectors.toList());
    }

    @Override
    public Skill getSkill(final int index) {
        return this.skillList.get(index);
    }

    @Override
    public void setSkillList(final List<Skill> skillList) {
        this.skillList = skillList;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        return sb.append("Name: ").append(this.name)
                 .append("\nStats: \tHP: ").append(this.hp)
                 .append("\n\tSpeed: ").append(this.speed)
                 .append("\n\tLevel: ").append(this.level)
                 .append("\n\tSkills: ").append(this.getAllowedSkillList().toString())
                 .toString();
    }


    /**
     * Builder class revisited to make it reusable over inheritance.
     * Source: http://stackoverflow.com/questions/17164375/subclassing-a-java-builder-class
     *
     * @param <T> Builder of a class that extends this one
     */
    @SuppressWarnings("unchecked")
    public static class  Builder<T extends Builder<? extends T>> {
        private String name;
        private int hp = BasicEntity.STANDARD_HP;
        private int level = BasicEntity.MINLEVEL;
        private int speed = BasicEntity.STANDARD_SPEED;
        private List<Skill> skillList = Role.FMAGE.getSkillList();;

        public T name(final String name) {
            this.name = name;
            return (T) this;
        }

        public T hp(final int hp) {
            this.hp = hp;
            return (T) this;
        }

        public T level(final int level) {
            this.level = level;
            return (T) this;
        }

        public T speed(final int speed) {
            this.speed = speed;
            return (T) this;
        }

        public T skillArr(final Skill[] skillArr) {
            this.skillList = new ArrayList<Skill>(Arrays.asList(skillArr));
            return (T) this;
        }

        public T skillList(final List<Skill> skillList) {
            this.skillList = skillList;
            return (T) this;
        }

        public BasicEntity build() throws IllegalArgumentException {
            return new BasicEntity(this);
        }
    }

    protected BasicEntity(final Builder<?> builder) {
        this(builder.name, builder.hp, builder.level, builder.speed, builder.skillList);
    }
}
