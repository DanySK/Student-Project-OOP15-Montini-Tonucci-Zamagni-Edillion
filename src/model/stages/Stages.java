package model.stages;

import java.util.Arrays;
import java.util.EnumMap;
import java.util.List;

import model.entities.BasicEntity.StatTime;
import model.entities.Entity;
import model.entities.StatType;

/**
 * Support class for stages.
 *
 */
public final class Stages {

    private Stages() {
    }

    /**
     * returns true if the stage is cleared/every enemy is dead.
     * @param enemyList enemy list of a stage
     * @return true or false
     */
    public static boolean isCleared(final List<Entity> enemyList) { // if there are enemies with more than
        // 0 hp, return !true (= false)
        return !enemyList.stream().anyMatch(m -> m.getStat(StatType.HP, StatTime.CURRENT) > 0);
    }

    /**
     * sets all stage's state from a map, load uti.
     * @param stageMap a map generated from the other method
     */
    public static void setStagesData(final EnumMap<StageData, StageState> stageMap) {
        stageMap.keySet().stream().forEach(k -> k.setState(stageMap.get(k)));
    }

    /**
     * generates a map with every stage and its stage, save uti.
     * @return the map stage-state
     */
    public static EnumMap<StageData, StageState> generateStagesData() {
        EnumMap<StageData, StageState> stageMap = new EnumMap<>(StageData.class);
        Arrays.asList(StageData.values()).stream().forEach(s -> stageMap.put(s, s.getState()));
        return stageMap; 
    }

}