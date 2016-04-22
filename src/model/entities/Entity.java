package model.entities;

import java.util.List;

import model.entities.BasicEntity.ActionType;
import model.entities.BasicEntity.StatTime;
import model.skills.Skill;

/**
 * Interface for monsters and heroes.
 */
public interface Entity {

    int getStat(StatType statType, StatTime time);
    
    int setStat(StatType statType, int value, StatTime time, ActionType action);
    
    void copyStats();

    String getName();
    
    /**
     * 
     * @return returns the skill's list
     */
    List<Skill> getSkillList();

    /**
     * 
     * @return returns only allowed skills (level capped)
     */
    List<Skill> getAllowedSkillList();

    /**
     * @param index
     *            gets that skill
     * @return returns a skill data
     */
    Skill getSkill(int index);

}