package model.stages;

import java.util.*;

import model.entities.Entity;

public class StageImpl implements Stage {

    private final String name;
    private final List<Entity> enemyList;
    private final int reward;

    public StageImpl(final String name, final int reward, final List<Entity> enemyList) {
        this.name = name;
        this.reward = reward;
        this.enemyList = enemyList;
    }

    @Override
    public String getName() {
        return name;
    }

    /**
     * @return the reward
     */
    @Override
    public int getReward() {
        return reward;
    }

    @Override
    public List<Entity> getEnemyList() {
        return enemyList;
    }

    @Override
    public boolean isCleared() { // if there are enemies with more than 0 hp, return !true (= false)
        return !this.enemyList.stream().anyMatch(x -> x.getHp() > 0);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        return sb.append("Name: ").append(this.name)
                 .append("\nEnemies: ").append(this.enemyList)
                 .append("\nReward: ").append(this.reward).append("exp")
                 .toString();
    }

}