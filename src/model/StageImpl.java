package model;

import java.util.*;

public class StageImpl implements Stage {

    private String name;
    private List<Entity> enemyList;
    //reward

    public StageImpl(String name, List<Entity> enemyList) {
        this.name = name;
        this.enemyList = enemyList;
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