package model.entities;

import java.util.List;

import model.skills.Skill;
import model.skills.SkillType;

/**
 * Role's data.
 */
public enum Role {

    WARRIOR("Warrior", SkillType.BASIC, SkillType.MELEE),
    ARCHER("Archer", SkillType.BASIC, SkillType.DISTANCE),
    BLACKMAGE("Black Mage", SkillType.BASIC, SkillType.FIRESPELL),
    REDMAGE("Red Mage", SkillType.FIRESPELL, SkillType.WHITESPELL);

    private final String name;
    private SkillType[] type;

    Role(final String name, final SkillType... type) {
        this.name = name;
        this.type = type;
    }

    /**
     * 
     * @return role's skilltype array
     */
    public SkillType[] getSkillType() {
        return this.type;
    }

    /**
     * 
     * @return role's skill list
     */
    public List<Skill> getSkillList() {
        return SkillType.getSkillList(this.type);
    }

    /**
     * 
     * @return role's name
     */
    public String getName() {
        return this.name;
    }

}