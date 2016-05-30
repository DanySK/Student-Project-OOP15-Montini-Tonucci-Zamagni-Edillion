package view;

import java.util.List;
import java.util.Map;

import controller.StageLoopImp;
import model.entities.Entity;
import model.entities.StatType;
import model.skills.Skill;
/**
 * Interface for the main GUI of the game, the CombatGUI
 */
public interface CombatGUI {
    
    /**
     * Initializes the CombatGUI, adding all the needed component
     * @param controllerReference 
     * @param monsterList list of the monsters of the current stage
     * @param heroName name of the current hero
     * @param heroStats statistics of the current hero
     * @param heroSkills skills usable by the current hero
     */
    void initialize(StageLoopImp controllerReference, List<Entity> monsterList, String heroName, 
            Map<StatType, Integer> heroStats, List<Skill> heroSkills);
    
    /**
     * Shows the message of victory and sends the player back to StageSelectionGUI
     */
    void victory();
    
    /**
     * Shows the message of defeat and sends the player back to StageSelectionGUI
     */
    void defeat();
    
    /**
     * Generates the hero statistics panel
     * @param heroName name of the current hero
     * @param heroStats statistics of the current hero
     */
    void generateHeroPanel(String heroName, Map<StatType, Integer> heroStats);
    
    /**
     * Enables or disables the skills
     * @param state true or false
     */
    void enableButtons(boolean state);
    
    /**
     * Generates the enemies statistics panel
     * @param monsterList list of the monsters of the current stage
     */
    void generateEnemiesPanel(List<Entity> monsterList);
    
    /**
     * Refreshes the battle log with the least recent attack
     * @param attacker the entity who attacks
     * @param target the entity being attacked
     * @param skill the skill used for the attack
     * @param damage the damage of the attack
     */
    void refreshCombatLog(String attacker, String target, String skill, int damage);
    
    /**
     * Refreshes the count of the turn
     * @param time
     */
    void refreshCount(int time);
}
