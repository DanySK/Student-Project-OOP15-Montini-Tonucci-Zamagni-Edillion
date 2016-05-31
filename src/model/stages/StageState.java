package model.stages;

/**
 * the state a stage can assume.
 */
public enum StageState {
    /**
     * The stage is locked, player can't fight on it.
     */
    LOCKED, 

    /**
     * The stage is unlocked, player can fight on it but it never won it.
     */
    UNLOCKED,

    /**
     * The stage is unlocked, player can fight on it and it won it at least once.
     */
    DONE;
}