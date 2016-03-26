package model.entities;

import model.skills.SkillType;

public enum MonsterTemplates {
    PEASANT("Angry Peasant", 5, 2, 1, SkillType.BASIC),
    GOBLIN("Gobelino", 25, 3, 1, SkillType.GOBLIN, SkillType.BASIC), 
    UOMOGATTO("UomoGatto", 25, 3, 3, SkillType.ORC, SkillType.BASIC),
    FIREMAGE("Fire Mage", 80, 4 ,5, SkillType.FIRESPELL);

    private final String name;
    private final int hp;
    private final int speed;
    private final int level;
    private final SkillType[] assign;
    
    private MonsterTemplates(final String name, final int hp, final int speed, final int level, final SkillType... assign) {
        this.name = name;
        this.hp = hp;
        this.speed = speed;
        this.level = level;
        this.assign = assign;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @return the hp
     */
    public int getHp() {
        return hp;
    }

    /**
     * @return the speed
     */
    public int getSpeed() {
        return speed;
    }

    /**
     * @return the level
     */
    public int getLevel() {
        return level;
    }

    /**
     * @return the assign
     */
    public SkillType[] getAssign() {
        return assign;
    }
    
}