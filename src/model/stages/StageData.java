package model.stages;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

import model.entities.Entity;
import model.entities.MonsterFactory;
import model.entities.MonsterTemplates;
import model.entities.StatType;
import model.entities.BasicEntity.StatTime;
import model.entities.RandomName;

/**
 * All stages data.
 */
public enum StageData implements Stage {
    TUTORIAL("Tutorial", MonsterTemplates.PEASANT),
    FIRSTMISSION("First mission", MonsterTemplates.RAT),
    THECAVE("The Cave", MonsterTemplates.GOBLIN),
    UNFAIR("Unfair", MonsterTemplates.GOBLIN, MonsterTemplates.PEASANT),
    THECASTLE1("The Castle (part 1)", MonsterTemplates.MRSKELTAL),
    THECASTLE2("The Castle (part 2)", MonsterTemplates.MRSKELTAL, MonsterTemplates.RAT),
    MARSH("Marsh", MonsterTemplates.RAT, MonsterTemplates.GOBLIN, MonsterTemplates.COBOLD),
    RUIN("Ruin", MonsterTemplates.PEASANT, MonsterTemplates.MRSKELTAL, MonsterTemplates.COBOLD),
    DRAGONQUEST("Dragon Quest", MonsterTemplates.DRAGON),
    PLATOON("Platoon", MonsterTemplates.MRSKELTAL, MonsterTemplates.COBOLD, MonsterTemplates.RAT),
    DRAGONTWIN("Twin Dragons", MonsterTemplates.DRAGON, MonsterTemplates.DRAGON),
    DRAGONTRIO("Dragon Trio", MonsterTemplates.DRAGON, MonsterTemplates.DRAGON, MonsterTemplates.DRAGON);
    

    private final float EXP_HP_MOD = 1.5F;
    private final String name;
    private final int reward;
    private final int goldReward;
    private final MonsterTemplates[] enemies;
    private Optional<List<Entity>> actualEnemyList = Optional.empty();
    private StageState state;

    StageData(final String name, final MonsterTemplates... enemyList) {
        this.name = name;
        this.enemies = enemyList;
        this.reward = this.calculateReward();
        this.goldReward = Math.round(this.calculateReward() / 2);
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
    public int getGoldReward() {
        return goldReward;
    }

    @Override
    public List<Entity> getEnemyList() {
        if (!this.actualEnemyList.isPresent()) {
            this.restoreEnemyList();
        }
        return this.actualEnemyList.get();
    }

    @Override
    public void restoreEnemyList() {
        List<RandomName> namePool = new ArrayList<>(Arrays.asList(RandomName.values()));
        Random rnd = new Random();
        MonsterFactory factory = new MonsterFactory();
        this.actualEnemyList = Optional.of(Arrays.asList(this.enemies).stream().map(e -> {
            String suffix = " " + namePool.remove(rnd.nextInt(namePool.size())).toString();
            return factory.createMonster(e, suffix);
        }).collect(Collectors.toList()));
    }

    @Override
    public StageState getState() {
        return this.state;
    }

    @Override
    public void setState(final StageState state) {
        this.state = state;
    }

    @Override
    public Stage getNext() throws IllegalStateException {
        if (this.ordinal() == StageData.values().length - 1) {
            throw new IllegalStateException("Last stage reached!");
        }
        return StageData.values()[this.ordinal() + 1];
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        return sb.append("Name: ").append(this.name).append("\nEnemies: ").append(this.getEnemyList())
                .append("\nReward: ").append(this.reward).append("exp").toString();
    }

    /**
     * Little algo that calculates a fair reward for each stage.
     * 
     * @return the reward value.
     */
    private int calculateReward() {
        return this.ordinal() * 10 + 
                this.getEnemyList().stream()
                .mapToInt(e -> e.getStat(StatType.HP, StatTime.GLOBAL)).sum() * 3;
    }

}