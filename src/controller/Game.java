package controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import model.entities.Hero;
import model.entities.Role;
import model.stages.StageData;
import model.stages.StageState;
import model.stages.Stages;
import view.HeroCreationGUI;
import view.LoadSaveGUIImpl;
import view.StageSelectionGUI;
import view.StartGUI;

public class Game implements GameEngine{

    public static final String FOLDER_PATH = System.getProperty("user.home") 
        + System.getProperty("file.separator") + "Edillion";
    private static Optional<Game> singleton = Optional.empty();
    private Hero NULL;
    private Hero hero = NULL;
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
        
        if (!(new File(FOLDER_PATH).isDirectory()) ) {
            new File(FOLDER_PATH).mkdir();
        }
        
        new StartGUI("Menù principale");
    }
    
    
    @Override
    public void continues() {
        File dir = new File(FOLDER_PATH);
        String[] existingSave = dir.list();
        
        if ( existingSave.length != 0 ) {
            new LoadSaveGUIImpl("Menù principale", existingSave);
        } else {
            new HeroCreationGUI("Hero Creation");
        }
    }

    @Override
    public void beginPlay(String saveName) {
        
        if ( saveName != "" ){ 
            loadSave(saveName);
        } else {
            if(StageData.TUTORIAL.getState() == StageState.LOCKED) { 
                StageData.TUTORIAL.setState(StageState.UNLOCKED);
            }
        }
        new StageSelectionGUI("Selezione dello stage da affrontare");
    }

    @Override
    public void buildHero(String name, Role role) {
        
        hero = new Hero.Builder().name(name).hp(20).level(1).speed(5).role(role).build();
        beginPlay("");
    }


    @Override
    public void stageLoad(StageData data) {
        
        StageLoop stage = new StageLoopImp();
        stage.load(data, hero);
    }
    
    /**
     * Charge and set the save
     * 
     * @param fileSelect
     *            name of the file you want to upload
     */
    @SuppressWarnings("unchecked")
    private void loadSave(String fileSelect) {
        
        List<Object> saveList = null;
        FileInputStream fileInputStream = null;
        Iterator<Object> iterSave = null;
        ObjectInputStream objectInputStream = null;
        EnumMap<StageData, StageState> mapStage = null;
        
        try {
            fileInputStream = new FileInputStream(FOLDER_PATH + "/" + fileSelect);
            objectInputStream = new ObjectInputStream(fileInputStream);
            
            saveList = (ArrayList<Object>) objectInputStream.readObject();
            
            objectInputStream.close();
            fileInputStream.close();
            
        } catch (IOException ex) {
                ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
                ex.printStackTrace();
        }
        iterSave = saveList.iterator();
        
        hero = (Hero)iterSave.next();
        mapStage = (EnumMap<StageData, StageState>)iterSave.next();

        Stages.setStagesData(mapStage);
       }

}