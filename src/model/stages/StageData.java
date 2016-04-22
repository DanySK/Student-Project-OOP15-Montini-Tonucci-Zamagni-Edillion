package model.stages;

import java.util.*;
import java.util.stream.Collectors;

import model.entities.Entity;
import model.entities.MonsterFactory;
import model.entities.MonsterTemplates;
import model.entities.StatType;
import model.entities.BasicEntity.StatTime;

public enum StageData implements Stage {
    TUTORIAL("Tutorial", MonsterTemplates.PEASANT),
    FIRSTMISSION("First mission", MonsterTemplates.GOBLIN),
    THECAVE("The Cave", MonsterTemplates.UOMOGATTO),
    UNFAIR("Unfair", MonsterTemplates.PEASANT, MonsterTemplates.PEASANT);


    private final static float EXP_HP_MOD = 1.5F;
    private final String name;
    private final int reward;
    private final MonsterTemplates[] enemies;
    private StageState state;

    StageData(final String name, final MonsterTemplates... enemyList) {
        this.name = name;
        this.enemies = enemyList;
        this.reward = this.calculateReward();
        this.state = StageState.LOCKED;
    }

    @Override
    public String getName() {
        return name;
    }
    
    @Override
    public int getReward() {
        return reward;
    }

    @Override
    public List<Entity> getEnemyList() {
        MonsterFactory factory = new MonsterFactory();
        return Arrays.asList(this.enemies).stream().map(e -> factory.createMonster(e))
                .collect(Collectors.toList());
    }
    
    @Override
    public StageState getState() {
        return this.state;
    }

    @Override
    public void setState(StageState state) {
        this.state = state;
    }
    
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        return sb.append("Name: ").append(this.name).append("\nEnemies: ").append(this.getEnemyList()).append("\nReward: ")
                .append(this.reward).append("exp").toString();
    }

    /**
     * Little algo that calculates a fair reward for each stage.
     * 
     * @return the reward value.
     */
    private int calculateReward() {
        return this.getEnemyList().stream().mapToInt(e -> Math.round((e.getStat(StatType.HP, StatTime.CURRENT) * EXP_HP_MOD * e.getStat(StatType.LEVEL, StatTime.CURRENT)))).sum();
    }
}