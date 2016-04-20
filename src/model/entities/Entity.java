package model.entities;

import java.util.List;

import model.skills.Skill;

/**
 * Interface for monsters and heroes.
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
     * @param hp
     *            set hitpoints value
     */
    void setHp(final int hp);

    /**
     * 
     * @param hp
     *            increase hitpoints value
     */
    void increaseHp(int hp);

    /**
     * 
     * @param hp
     *            decrease hitpoints value
     */
    void decreaseHp(int hp);

    /**
     * @return the level
     */
    int getLevel();

    /**
     * Sets entity's level.
     * 
     * @param level
     *            new level to be set
     */
    void setLevel(int level);

    /**
     * @return entity's speed
     */
    int getSpeed();

    /**
     * Sets entity's speed.
     * 
     * @param speed
     *            new speed to be set
     */
    void setSpeed(int speed);
    
    /**
     * 
     * @return entity's mana 
     */
    int getMana();
    
    /**
     * Sets entity's mana.
     * 
     * @param level
     *            new mana to be set
     */
    void setMana(int mana);

    /**
     * 
     * @return entity's manaregen
     */
    int getManaRegen();
    
    /**
     * Sets entity's manaRegen.
     * 
     * @param manaRegen
     *            new manaRegen to be set
     */
    void setManaRegen(int manaRegen);

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