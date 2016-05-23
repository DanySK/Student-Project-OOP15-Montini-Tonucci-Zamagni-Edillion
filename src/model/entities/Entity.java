package model.entities;

import java.util.List;
import java.util.Map;

import model.entities.BasicEntity.ActionType;
import model.entities.BasicEntity.StatTime;
import model.skills.Skill;

/**
 * Interface for monsters and heroes.
 */
public interface Entity {

    /**
     * Get a stat value, found with some enums.
     * @param statType the stats you want the value
     * @param time choose between current stats or global stats
     * @return stat value
     */
    int getStat(StatType statType, StatTime time);

    /**
     * Set a single stat's value.
     * @param statType the stats you want to set the value
     * @param value value modifier
     * @param time choose between current stats or global stats
     * @param action set, decrease or increase
     * @return the new value
     */
    int setStat(StatType statType, int value, StatTime time, ActionType action);

    /**
     * Override current stats with global stats.
     */
    void copyStats();

    /**
     * Get entity's name.
     * @return the name
     */
    String getName();

    /**
     * get the map with all stats.
     * @param time current or global
     * @return the map stat-value
     */
    Map<StatType, Integer> getStatMap(StatTime time);

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
    
    /**
     * new tostring time-based.
     * @param time StatTime
     * @return string version
     */
    String toString(final StatTime time);

}