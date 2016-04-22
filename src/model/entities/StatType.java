package model.entities;

public enum StatType {
    HP("HitPoints"), LEVEL("Level"), SPEED("Speed"), MANA("Mana"), MANAREGEN("Mana Regen"), EXP("Experience");
    
    private final String name;

    /**
     * @param name
     */
    private StatType(String name) {
        this.name = name;
    }
    
    @Override
    public String toString() {
        return this.name();
    }
    
}