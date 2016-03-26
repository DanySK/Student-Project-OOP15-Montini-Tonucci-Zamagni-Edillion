package model.stages;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import model.entities.MonsterTemplates;
import model.entities.Entity;
import model.entities.MonsterFactory;

public class StageData {

    private static StageData SINGLETON = null;
    private static List<Stage> stages = new ArrayList<>();
    private static int index;

    /**
     * Adding stages data
     */
    private StageData() { 
        MonsterFactory factory = new MonsterFactory();
        List<Entity> mList = new ArrayList<>();
        mList.add(factory.createMonster(MonsterTemplates.GOBLIN));
        mList.add(factory.createMonster(MonsterTemplates.GOBLIN));

        stages.add(new StageImpl("Stage 1", 45, new ArrayList<Entity>(mList)));
        stages.add(new StageImpl("Stage 2", 123, new ArrayList<Entity>(Collections.singletonList(factory.createMonster(MonsterTemplates.GOBLIN)))));
        stages.add(new StageImpl("Stage 3", 111, new ArrayList<Entity>(Collections.singletonList(factory.createMonster(MonsterTemplates.UOMOGATTO)))));
        stages.add(new StageImpl("Stage 4", 243, new ArrayList<Entity>()));        
    };

    public static StageData getData() {
        if (SINGLETON == null) {
            synchronized (StageData.class) {
                if (SINGLETON == null) {
                    SINGLETON = new StageData();
                    index = 0;
                }
            }
        }
        return SINGLETON;
    }

    public Stage getNext() {
        return stages.get(index++);
    }

    public Stage getStage(final int index) {
        return stages.get(index);
    }

}