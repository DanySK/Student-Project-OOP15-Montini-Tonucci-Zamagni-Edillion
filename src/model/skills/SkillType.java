package model.skills;

public enum SkillType {
    //Heroes
    BASIC, MELEE, FIRESPELL, WHITESPELL, DISTANCE, 
    
    //Monsters
    SKULL, ORC, GOBLIN;
    
    public String toString() {
        return this.name();
    }
}
