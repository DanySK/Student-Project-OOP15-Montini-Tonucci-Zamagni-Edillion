package model.skills;

public enum FMageSkills implements Skill {
    
    TACKLE("Tackle", 5, 1),
    PHOENIX("Phoenix", 9, 1),
    PYROBALL("Pyroball", 15, 5);
    
    
    private final String name;
    private int damage;
    private int requiredLevel;
    
    private FMageSkills(final String name, final int damage, final int requiredLevel) {
        this.name = name;
        this.damage = damage;
        this.requiredLevel = requiredLevel;
    }
    
    /**
     * @return returns the name with the first letter capitalized only
     *          (this.name() returns the full capitalized name, we don't like it)
     */
    @Override
    public int getDamage() {
        return this.damage;
    }

    @Override
    public int getRequiredLevel() {
        return this.requiredLevel;
    }
    
    @Override
    public String toString() {
        return this.name;
    }
    
}