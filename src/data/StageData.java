package data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import model.*;

public class StageData {

    private static StageData SINGLETON = null;
    private static List<Stage> stages = new ArrayList<>();
    private static int index;
    
    /**
     * Adding stages data
     */
    static {
        MonsterFactory factory = new MonsterFactory();
        stages.add(new StageImpl("Stage 1", new ArrayList<Entity>(Collections.singletonList(factory.createMonster(BasicMonster.GOBLIN)))));
        stages.add(new StageImpl("Stage 2", new ArrayList<Entity>(Collections.singletonList(factory.createMonster(BasicMonster.GOBLIN)))));
        stages.add(new StageImpl("Stage 1", new ArrayList<Entity>(Collections.singletonList(factory.createMonster(BasicMonster.ENRICOPAPI)))));
        stages.add(new StageImpl("Stage 3", new ArrayList<Entity>()));
    }

    private StageData() {};

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


}