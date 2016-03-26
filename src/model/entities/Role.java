package model.entities;

import java.util.List;

import model.skills.Skills;
import model.skills.SkillType;
import model.skills.SkillProvider;

public enum Role {
    
    WARRIOR("Warrior", SkillType.BASIC, SkillType.MELEE),
    ARCHER("Archer"),
    FIREMAGE("Fire Mage", SkillType.BASIC, SkillType.FIRESPELL),
    WHITEMAGE("White Mage");

    private final String name;
    private SkillType[] type;

    private Role(String name, SkillType... type) {
        this.name = name;
        this.type = type;
    }

    public SkillType[] getSkillType() {
        return this.type;
    }
    
    public List<Skills> getSkillList() {
        final SkillProvider provider = SkillProvider.get();
        return provider.getSkillList(this.type);
    }

    public String getName() {
        return this.name;
    }

}