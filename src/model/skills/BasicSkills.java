package model.skills;

public enum BasicSkills implements Skill {
    
    TACKLE("Tackle", 5), 
    PUNCH("Punch", 7),
    KICK("Kick", 10);
    
    private final String name;
    private int damage;
    
    private BasicSkills(final String name, final int damage) {
        this.name = name;
        this.damage = damage;
    }
    
    /**
     * @return returns the name with the first letter capitalized only
     *          (this.name() returns the full capitalized name, we don't like it)
     */
    public String getName() {
        return this.name;       
    }

    public int getDamage() {
        return damage;
    }
    
}
