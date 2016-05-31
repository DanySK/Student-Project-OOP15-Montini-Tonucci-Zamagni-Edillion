package controller;

import model.entities.Hero;
import model.items.ItemUsable;
import model.skills.Skill;
import model.stages.StageData;

public interface StageLoop{
    
    /**
     * sets parameters for the fight and starts combatGUI
     * @param stage
     * @param hero
     */
    void load(StageData stage, Hero hero);

    /**
     * view tells controller an attacker wants to attack a target.
     * @param skill
     * @param monsterId
     */
    void attack(Skill skill, int monsterId);
    

    /**
     * view tells controller hero wants to use an item on a target.
     * @param item
     * @param targetId
     */
    void useItem(ItemUsable item, int targetId);    

}
