package model.items;


import model.entities.StatType;
/**
 * Interface for potion and equipment.
 */
public interface Item {

    String getName();
    
    int getPrice();
    
    int getEffectiveness();
    
    StatType getStatTypeInfluence();
    
}
