package data;
import model.*;
import model.skills.*;

public enum BasicMonster {
    GOBLIN(new EntityImpl("Gobelino", 15, BasicSkills.values())), 
    ENRICOPAPI(new EntityImpl("Uomogatto", 25, BasicSkills.values()));

    private Entity type;
    
    BasicMonster(Entity type) {
        this.type = type;
    }
    
    public Entity getType() {
        return this.type;
    }
 
}