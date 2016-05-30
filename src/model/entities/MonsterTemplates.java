package model.entities;

import model.skills.SkillType;

/**
 * Monster templates data.
 */
public enum MonsterTemplates {
    // name, hp, speed, level, mana, manaRegen, assign
    PEASANT("Angry Peasant", 5, 2, 1, 0, 0, SkillType.BASIC),
    RAT("Huge Rat", 15, 2, 1, 0, 0, SkillType.BASIC),
    GOBLIN("Goblin", 25, 3, 1, 20, 2, SkillType.GOBLIN, SkillType.BASIC), 
    COBOLD("Cobold", 35, 3, 4, 30, 2, SkillType.ORC, SkillType.BASIC),
    MRSKELTAL("Mr. Skeltal", 50, 5, 5, 100, 5, SkillType.BASIC, SkillType.SKULL),
    DRAGON("Fire Mage", 80, 4 ,5, 15, 100, SkillType.FIRESPELL);
    

    private final String name;
    private final int hp;
    private final int speed;
    private final int level;
    private final int mana;
    private final int manaRegen;
    private final SkillType[] assign;

    /**
     * 
     * @param name monster's name
     * @param hp monster's hp
     * @param speed monster's speed
     * @param level monster's level
     * @param assign monster's skilltypes allowed
     */
    MonsterTemplates(final String name, final int hp, final int speed, final int level, final int mana, final int manaRegen, final SkillType... assign) {
        this.name = name;
        this.hp = hp;
        this.speed = speed;
        this.level = level;
        this.assign = assign;
        this.mana = mana;
        this.manaRegen = manaRegen;
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
     * @return the mana
     */
    public int getMana() {
        return mana;
    }

    /**
     * @return the manaRegen
     */
    public int getManaRegen() {
        return manaRegen;
    }

    /**
     * @return the assigned skills' type
     */
    public SkillType[] getAssign() {
        return assign;
    }
}