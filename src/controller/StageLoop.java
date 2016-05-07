package controller;

import model.entities.Hero;
import model.skills.Skill;
import model.stages.StageData;

public interface StageLoop{
    
    /**
     * Allows the agent to play his action.
     */
    void load(StageData stage, Hero hero);

    /**
     * view tells controller an attacker wants to attack a target.
     * @param mossa
     * @param monsterId
     */
    void attack(Skill mossa, int monsterId);


}
