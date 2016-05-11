package model.stages;

import java.util.List;

import model.entities.Entity;

public interface Stage {
    
    String getName();

    /**
     * 
     * @return stage's reward after it  gets completed.
     */
    int getReward();

    /**
     * Rebuilds the enemies list by new monster instances from the factory.
     * @return a new list with new enemies
     */
    List<Entity> getEnemyList();
    
    StageState getState();
    
    void setState(StageState state);
    
    Stage getNext();

}
