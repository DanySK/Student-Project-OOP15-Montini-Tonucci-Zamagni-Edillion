package model.items;


/**
 * Interface for potion and equipment.
 */
public interface Item {

    String getName();
    
    int getPrice();
    
    int getEffectiveness();
    
    StatType getStatTypeInfluence();
    

    public enum StatType {
        HP, MANA, SPEED;
    }
}
