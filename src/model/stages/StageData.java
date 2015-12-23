package model.stages;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import model.entities.BasicMonster;
import model.entities.Entity;
import model.entities.MonsterFactory;

public class StageData {

    private static StageData SINGLETON = null;
    private static List<Stage> stages = new ArrayList<>();
    private static int index;

    /**
     * Adding stages data
     */
    static { //Se lento/occupa troppa memoria andrà caricato progressivamente da file/database
        MonsterFactory factory = new MonsterFactory();
        List<Entity> mList = new ArrayList<>();
        mList.add(factory.createMonster(BasicMonster.GOBLIN));
        mList.add(factory.createMonster(BasicMonster.GOBLIN));

        stages.add(new StageImpl("Stage 1", 45, new ArrayList<Entity>(mList)));
        stages.add(new StageImpl("Stage 2", 123, new ArrayList<Entity>(Collections.singletonList(factory.createMonster(BasicMonster.GOBLIN)))));
        stages.add(new StageImpl("Stage 3", 111, new ArrayList<Entity>(Collections.singletonList(factory.createMonster(BasicMonster.UOMOGATTO)))));
        stages.add(new StageImpl("Stage 4", 243, new ArrayList<Entity>()));
    }

    private StageData() { };

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