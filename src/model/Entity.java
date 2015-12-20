package model;

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

    /**
     * 
     * @return returns the skills set
     */
    List<Skill> getSkillList();
    
    /**
     * 
     * @return returns a skill data
     */
    Skill getSkill(int index);
}