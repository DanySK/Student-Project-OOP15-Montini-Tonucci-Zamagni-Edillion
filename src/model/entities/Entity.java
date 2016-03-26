package model.entities;

import java.util.List;

import model.skills.Skills;

    /**
     * 
     */
public interface Entity {

    /**
     * 
     * @return return the name of the Entity
     */
    String getName();

    /**
     * 
     * @return hitpoints status
     */
    int getHp();

    /**
     * 
     * @param hp set hitpoints value
     */
    void setHp(final int hp);

    /**
     * 
     * @param hp increase hitpoints value
     */
    void increaseHp(int hp);

    /**
     * 
     * @param hp decrease hitpoints value
     */
    void decreaseHp(int hp);

    int getLevel();
    
    void setLevel(int level); 
    
    int getSpeed();

    void setSpeed(int speed);

    /**
     * 
     * @return returns the skill's list
     */
    List<Skills> getSkillList();
    
    /**
     * 
     * @return returns only allowed skills (level capped) 
     */
    List<Skills> getAllowedSkillList();

    /**
     * 
     * @return returns a skill data
     */
    Skills getSkill(int index);
    
    /**
     * 
     */

    void setSkillList(List<Skills> skillList);
    
}