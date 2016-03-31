package model.skills;

/**
 * Skill interface.
 */
public interface Skill {

    /**
     * @return the name
     */
    String getName();

    /**
     * @return the damage
     */
    int getDamage();

    /**
     * @return the requiredLevel
     */
    int getRequiredLevel();

    /**
     * @return the modifier
     */
    int getModifier();

    /**
     * @return the requiredLevel
     */
    int getMana();

    /**
     * @return the assign list, who can use that skill
     */
    SkillType[] getAssign();

}