package controller;


import model.entities.Role;
import model.stages.StageData;

public interface GameEngine {

    /**
     * call from the view initially
     * @param name of save
     */
    void beginPlay(String save);
    
    /**
     * call from the view
     * tell the view saves
     */
    void continues();
    
    /**
     * view gives to controller the new hero built (e.g. from a gui panel)
     * @param name hero
     * @param role
     */
    void buildHero(String name, Role role);
    
    /**
     * @param data is the stage that will load
     */
    void stageLoad(StageData data);
    
}
