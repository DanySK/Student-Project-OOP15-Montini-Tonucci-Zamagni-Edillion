package model.stages;

import java.util.List;

import model.entities.Entity;

/**
 * Stage's interface with public methods.
 */
public interface Stage {

    /**
     * stage's name.
     * @return the name as String
     */
    String getName();

    /**
     * 
     * @return stage's exp reward after it  gets completed.
     */
    int getReward();

    /**
     * 
     * @return stage's gold reward after it  gets completed.
     */
    int getGoldReward();

    /**
     * Rebuilds the enemies list by new monster instances from the factory.
     * @return a new list with new enemies
     */
    List<Entity> getEnemyList();

    /**
     * stage's stage.
     * @return the state as StageState
     */
    StageState getState();

    /**
     * set a state.
     * @param state new StageState
     */
    void setState(StageState state);

    /**
     * get the next stage on the array.
     * @return next stage
     */
    Stage getNext();

}
