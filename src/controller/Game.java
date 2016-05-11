package controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import model.entities.Hero;
import model.entities.Role;
import model.stages.StageData;
import model.stages.StageState;
import view.View;

public class Game implements GameEngine{
    
    private String folderPath = "save";
    public static final int NUM_STAGE = 4;
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
        
        //se non esiste creo la cartella per i salvataggi
        if (!(new File(folderPath).isDirectory()) ) {
            new File(folderPath).mkdir();
        }
        
        StageData.TUTORIAL.setState(StageState.UNLOCKED);
        
        loadSave("");

        //View menu = new StartGUI("Menù principale");
    }

    @Override
    public void gioca() {
        
        //DOVRA' ESSERE SEPARATA IN DUE CLASSI DI MODO DA POTER SELEZIONARE IL SALVATAGGIO
        
        File dir = new File(folderPath);
        String[] presence = dir.list();
        
        if (presence == null) { //SE LA CARTELLA SAVE E' VUOTA
            System.out.println("cartella vuota");
        } else {
            
            for (int i=0; i < presence.length; i++) {
                // Get filename of file or directory
                String filename = presence[i];
                System.out.println(filename);
            }

            loadSave( folderPath + "/" + presence[0] + ".txt");
        }
            
        if (hero == NULL) { //CONTROLLA L'ESISTENAZA DI UN SALVATAGGIO PER CREARE O NO L'EROE (non c'è, crealo)
            
            //View createPG = new CreateHeroGUI("Creazione del personaggio");
            
        } else {
        
           //View selectStage = new SelezionaStageGUI("Selezione dello stage da affrontare"); 
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
    
    private void loadSave( String fileSelect) {
        
        List saveList = null;
        FileInputStream fileInputStream = null;
        Iterator iterSave = null;
        ObjectInputStream objectInputStream = null;
        Map<StageData, StageState> mapStage = null;
        
        try {
            fileInputStream = new FileInputStream(fileSelect);
            objectInputStream = new ObjectInputStream(fileInputStream);
            
            saveList = (ArrayList) objectInputStream.readObject();
            
            objectInputStream.close();
            fileInputStream.close();
            
        } catch (IOException ex) {
                ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
                ex.printStackTrace();
        }
        
        iterSave = saveList.iterator();
        
        hero = (Hero)iterSave.next();

        mapStage = (Map<StageData, StageState>)iterSave.next();

        StageData.TUTORIAL.setState(mapStage.get(StageData.TUTORIAL));
        StageData.FIRSTMISSION.setState(mapStage.get(StageData.FIRSTMISSION));
        StageData.THECAVE.setState(mapStage.get(StageData.THECAVE));
        StageData.UNFAIR.setState(mapStage.get(StageData.UNFAIR));
       }

}