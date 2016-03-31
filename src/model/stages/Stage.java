package model.stages;

import java.util.List;

import model.entities.Entity;

public interface Stage {

    String getName();

    int getReward();

    List<Entity> getEnemyList();

}
