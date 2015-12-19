package model;

import java.util.*;

public abstract class AbstractEntity implements Entity {
    
    protected String name;
    protected int hp;
    protected Set<Skill> skillSet;
    
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    
    public int getHp() {
        return hp;
    }
    
    //the setHp could be useless, we will see...
    public void setHp(int hp) {
        this.hp = hp;
    }
    
    public void increaseHp(int hp) {
        this.hp = this.hp+hp;
    }
    
    public void decreaseHp(int hp) {
        this.hp = this.hp-hp;
    }
    
    public Set<Skill> getSkillSet() {
        return skillSet;
    }
    
    
    
    
}
