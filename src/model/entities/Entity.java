package model.entities;

import java.util.List;

import model.skills.Skill;

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
    List<Skill> getSkillList();
    
    /**
     * 
     * set the skill list.
     */
    void setSkillList(List<Skill> skillList);

    /**
     * 
     * @return returns only allowed skills (level capped) 
     */
    List<Skill> getAllowedSkillList();

    /**
     * 
     * @return returns a skill data
     */
    Skill getSkill(int index);
}