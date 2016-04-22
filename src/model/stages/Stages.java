package model.stages;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import model.entities.BasicEntity.StatTime;
import model.entities.Entity;
import model.entities.StatType;

public class Stages {
     
    private Stages() {
    }

    public static boolean isCleared(List<Entity> enemyList) { // if there are enemies with more than
        // 0 hp, return !true (= false)
        return !enemyList.stream().anyMatch(m -> m.getStat(StatType.HP, StatTime.CURRENT) > 0);
    }
    
    public static void setStagesData(Map<StageData, StageState> stageMap) {
        stageMap.keySet().stream().forEach(k -> k.setState(stageMap.get(k)));
    }
    
    public static Map<StageData, StageState> generateStagesData() {
        Map<StageData, StageState> stageMap = new HashMap<>();
        Arrays.asList(StageData.values()).stream().forEach(s -> stageMap.put(s, s.getState()));
        return stageMap; 
    }
    
}