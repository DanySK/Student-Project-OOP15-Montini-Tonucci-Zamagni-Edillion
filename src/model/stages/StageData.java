package model.stages;

import java.util.*;
import java.util.stream.Collectors;

import model.entities.Entity;
import model.entities.MonsterFactory;
import model.entities.MonsterTemplates;

public enum StageData implements Stage {
    TUTORIAL("Tutorial", MonsterTemplates.PEASANT),
    FIRSTMISSION("First mission", MonsterTemplates.GOBLIN),
    THECAVE("The Cave", MonsterTemplates.UOMOGATTO),
    UNFAIR("Unfair", MonsterTemplates.PEASANT, MonsterTemplates.PEASANT);


    private final static float EXP_HP_MOD = 1.5F;
    private final String name;
    private final List<Entity> enemyList;
    private final int reward;

    StageData(final String name, final MonsterTemplates... enemyList) {
        this.name = name;
        MonsterFactory factory = new MonsterFactory();
        this.enemyList = Arrays.asList(enemyList).stream().map(e -> factory.createMonster(e))
                .collect(Collectors.toList());
        this.reward = this.calculateReward();
    }

    public static boolean isCleared(List<Entity> enemyList) { // if there are enemies with more than
                                                              // 0 hp, return !true (= false)
        return !enemyList.stream().anyMatch(m -> m.getHp() > 0);
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
        return new ArrayList<Entity>(enemyList);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        return sb.append("Name: ").append(this.name).append("\nEnemies: ").append(this.enemyList).append("\nReward: ")
                .append(this.reward).append("exp").toString();
    }

    /**
     * Little algo that calculates a fair reward for each stage.
     * 
     * @return the reward value.
     */
    private int calculateReward() {
        return this.enemyList.stream().mapToInt(e -> Math.round((e.getHp() * EXP_HP_MOD * e.getLevel()))).sum();
    }

}