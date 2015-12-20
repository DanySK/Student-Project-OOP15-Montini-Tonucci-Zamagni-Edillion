package model;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;

import model.skills.Skill;


public class EntityImpl implements Entity {

    private final String name;
    private int hp;
    final private List<Skill> skillList;
    

    /**
     * @param name entity's name
     * @param hp entity's hitpoint
     * @param skillList entity's skillset
     */
    public EntityImpl(final String name, final int hp, Skill[] skillArr) {
        this.name = name;
        this.hp = hp;
        this.skillList = new ArrayList<Skill>(Arrays.asList(skillArr));
    }
    
    public EntityImpl(final String name, final int hp, List<Skill> skillList) {
        this.name = name;
        this.hp = hp;
        this.skillList = new ArrayList<Skill>(skillList);
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public int getHp() {
        return this.hp;
    }

    @Override
    public void setHp(final int hp) {
        this.hp = hp;
    }

    @Override
    public void increaseHp(final int hp) {
        this.hp += hp;
    }

    @Override
    public void decreaseHp(final int hp) {
        if (this.hp > hp) {
            this.hp -= hp;
        } else {
            this.hp = 0;
        }
    }

    @Override
    public List<Skill> getSkillList() {
        return this.skillList;
    }

    @Override
    public Skill getSkill(int index) {
        return this.skillList.get(index);
    }

}
