package model;

import java.util.*;

public abstract class AbstractStage {

    protected int id;
    protected String name;
    protected List<Entity> enemyList;
    //reward
    
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public List<Entity> getEnemyList() {
        return enemyList;
    }
    
    
    
}
