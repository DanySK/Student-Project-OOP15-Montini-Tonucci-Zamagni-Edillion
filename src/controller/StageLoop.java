package controller;

import model.entities.Entity;
import model.entities.Hero;

public interface StageLoop{
    
    /**
     * Allows the agent to play his action.
     */
    boolean play(int stage, Hero hero);

    /**
     * view tells controller an attacker wants to attack a target.
     * @param attacker
     * @param target
     * @param skillId
     */
    void attack(Entity attacker, Entity target, int skillId);

    /**
     * view tells controller which stage it has to load.
     * @param index
     */
    void loadStage(int index);

}
