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
import model.stages.Stages;
import view.CreateHeroGUI;
import view.SelezionaStageGUI;
import view.StartGUI;
import view.View;

public class Game implements GameEngine{

    public static final String FOLDER_PATH = System.getProperty("user.home") 
        + System.getProperty("file.separator") + "rpgSave";
    public static final int NUM_STAGE = 4;
    private static Optional<Game> singleton = Optional.empty();
    private Hero NULL;
    private Hero hero = NULL;
    public boolean win;
    private int primaPartita = 0;
    
    
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
        if (!(new File(FOLDER_PATH).isDirectory()) ) {
            new File(FOLDER_PATH).mkdir();
        }
        
        StageData.TUTORIAL.setState(StageState.UNLOCKED);
        

        View menu = new StartGUI("Menù principale");
    }

    @Override
    public void gioca() {
        
        //DOVRA' ESSERE SEPARATA IN DUE CLASSI DI MODO DA POTER SELEZIONARE IL SALVATAGGIO
        //PER ORA DATO CHE NON C'E' ANCORA MODO DI GIOCARE NON CARICHERO' MAI IL SALVATAGGIO
        
        //IL PULSANTE "GIOCA" DOVRA' DIFFERENZIARE LA POSSIBILITA' O MENO DI CONTINUARE UNA PARTITA GIA' AVVIATA
        //DEVO CREARE SICURAMENTE UN ALTRO METODO, DOBBIAMO PARLARE PER I CAMBIAMENTI
        
        File dir = new File(FOLDER_PATH);
        String[] presence = dir.list();
        
        if (primaPartita != 0) {
            System.out.println("cartella piena");
            for (int i=0; i < presence.length; i++) {
                // Get filename of file or directory
                String filename = presence[i];
                System.out.println(filename);
            }
            
            loadSave( FOLDER_PATH + "/" + presence[0] + ".dat");
        }
            
        if (hero == NULL) { //PER ORA LO CREO POI VADO ALLO STAGE, SARA' DA CAMBIARE
            
            View createPG = new CreateHeroGUI("Creazione del personaggio");
            
        } else {
        
            View selectStage = new SelezionaStageGUI("Selezione dello stage da affrontare"); 
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
    
    @SuppressWarnings("unchecked")
    private void loadSave(String fileSelect) {
        
        List<Object> saveList = null;
        FileInputStream fileInputStream = null;
        Iterator<Object> iterSave = null;
        ObjectInputStream objectInputStream = null;
        Map<StageData, StageState> mapStage = null;
        
        try {
            fileInputStream = new FileInputStream(fileSelect);
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
        mapStage = (Map<StageData, StageState>)iterSave.next();

        Stages.setStagesData(mapStage);
       }

}