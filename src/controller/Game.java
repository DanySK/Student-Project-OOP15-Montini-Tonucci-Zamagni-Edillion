package controller;

import java.util.Optional;
import java.util.Scanner;

import model.entities.Hero;
import model.entities.Role;
import model.stages.StageData;
import model.stages.StageState;

public class Game implements GameEngine{
    
    public static final int NUM_STAGE = 4;
    private static Optional<Game> singleton = Optional.empty();
    private int index, fine;
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
        /* Qui il gioco inizia con la creazione (o caricamento da db) dei dati dell'eroe
         * La view dirà lo stage che bisogna caricare passando un indice da inserire su Stage stage = StageData.getData().getStage(index);
         * Inizia la battaglia tra l'eroe e i nemici presi da stage.getEnemyList();
         * Per vedere se uno stage è finito c'è la funzione stage.IsCleared() che torna un boolean
         */
        
        Hero hero = new Hero.Builder().name("Pippo").hp(20).level(1).speed(5).role(Role.WARRIOR).build();

        StageData.TUTORIAL.setState(StageState.UNLOCKED);
        
        
        while ( index != NUM_STAGE+1 && fine != 2 ) {
            
            StageLoop stage = new StageLoopImp();
            
            System.out.println("Quale stage vuoi affrontare? ");
            System.out.println("0) TUTORIAL     1)FIRSTMISSION  2)THECAVE       3)UNFAIR ");
            Scanner input = new Scanner(System.in);
            index = input.nextInt();
            
            System.out.println("STAGE N. " + index);
            System.out.println();
            if (stage.play(index, hero)) {
                System.out.println("STAGE N. " + index + " SUPERATO");
            }
            else { 
                System.out.println("STAGE N. " + index + " FALLITO"); 
            }
            System.out.println();
            index++;

            System.out.println("Vuoi terminare la partita? ");
            System.out.println("1) no           2)si");
            input = new Scanner(System.in);
            fine = input.nextInt();
        }
        
        
        System.out.println("FINE GAME!!!!!!!!!!!!!!!!!!");
        
    }
    
    

    @Override
    public void buildHero(Hero hero) {
        // TODO Auto-generated method stub
        
    } 
}