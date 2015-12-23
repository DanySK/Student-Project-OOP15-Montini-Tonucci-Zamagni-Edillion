package model.entities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import model.skills.Skill;
import model.skills.BasicSkills;
import model.skills.FMageSkills;

public enum Role {
    WARRIOR(BasicSkills.values()),
    FMAGE(FMageSkills.values());

    private List<Skill> skillList;

    private Role(Skill[] skillList) {
        this.skillList = new ArrayList<Skill>(Arrays.asList((skillList)));
    }

    public List<Skill> getSkillList() {
        return skillList;
    }
}
