package controller;

import model.entities.Role;
import model.stages.StageData;

public interface GameEngine {


    void gioca();
    
    /**
     * view gives to controller the new hero built (e.g. from a gui panel)
     * @param hero
     */
    void buildHero(String name, Role role);
    
    void stageLoad(StageData data);
}
