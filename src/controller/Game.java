package controller;

import java.util.Optional;

import model.entities.Hero;
import model.entities.Role;
import model.stages.StageData;
import model.stages.StageState;

public class Game implements GameEngine{
    
    public static final int NUM_STAGE = 4;
    private static Optional<Game> singleton = Optional.empty();
    private Hero hero;
    public boolean win;
    
    
    /**
     * @return the SINGLETON instance of the class.
     */
    public static synchronized GameEngine getInstance() {
        if (!singleton.isPresent()) {
            singleton = Optional.of(new Game());
        }
        return singleton.get();
    }
    
    
    public Game() {
        
        //new StartGUI();
        
        
    }

    @Override
    public void gioca() {
        if (hero.getName() == "") { //CONTROLLA L'ESISTENAZA DI UN SALVATAGGIO PER CREARE O NO L'EROE (non c'è, crealo)
            
            //new CreateHeroGUI();
            
        } else {
           StageData.TUTORIAL.setState(StageState.UNLOCKED);
        
           //new SelezionaStageGUI(); 
        }
    }


    @Override
    public void buildHero(String name, Role role) {
        
        hero = new Hero.Builder().name(name).hp(20).level(1).speed(5).role(role).build();
        gioca();
    }


    @Override
    public void stageLoad(StageData data) {
        
        StageLoop stage = new StageLoopImp();
        

        stage.load(data, hero);
        
    } 
}