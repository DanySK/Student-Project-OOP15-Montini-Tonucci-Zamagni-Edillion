package model.skills;

public enum Skills {
    //                        damage - level - mod
    PUNCH("Punch",               3,     1,      0, SkillType.BASIC),
    KICK("Kick",                 5,     1,      1, SkillType.MELEE),
    DHIT("Double Hit",           7,     2,      1, SkillType.MELEE),
    SLAM("Slam",                 5,     2,      1, SkillType.MELEE),
    FLARE("Flare",              10,     2,      3, SkillType.FIRESPELL),
    PYROBALL("Pyroball",        16,     3,      5, SkillType.FIRESPELL),
    CPUNCH("Comet Punch",       30,     4,      6, SkillType.MELEE),
    ;
    
    private final String name;
    private final int damage;
    private final int requiredLevel;
    private final int modifier;
    private final SkillType[] assign;
    
    private Skills(String name, int damage, int requiredLevel, int modifier, SkillType... assign) {
        this.name = name;
        this.damage = damage;
        this.requiredLevel = requiredLevel;
        this.modifier = modifier;
        this.assign = assign;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @return the damage
     */
    public int getDamage() {
        return damage;
    }

    /**
     * @return the requiredLevel
     */
    public int getRequiredLevel() {
        return requiredLevel;
    }

    /**
     * @return the modifier
     */
    public int getModifier() {
        return modifier;
    }

    /**
     * @return the assign list, who can use that skill
     */
    public SkillType[] getAssign() {
        return assign;
    }
    
    public String toString() {
        StringBuilder str = new StringBuilder();
        return str.append("\n" + this.name)
                  .append("\tDamage: ")
                  .append(this.damage)
                  .append("\tModifier: ")
                  .append(this.modifier)
                  .append("\tnRequired Level: ")
                  .append(this.requiredLevel)
                  .append("\tAssigned to: ")
                  .append(this.getAssign())
                  .append("\n")
                  .toString();
    }
    
}
