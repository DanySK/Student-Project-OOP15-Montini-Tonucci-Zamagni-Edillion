package controller;

import java.util.Optional;

import model.entities.Entity;
import model.entities.Hero;
import model.entities.Role;

public class Game implements GameEngine{
    
    private static Optional<Game> singleton = Optional.empty();
    private int index;
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
        
        Hero hero = new Hero.Builder().name("Pippo").level(1).speed(5).role(Role.WARRIOR).build();
        
        index = 1;
        
        
        StageLoop stage = new StageLoopImp();
        
        win = stage.play(index, hero);
        
        System.out.println(stage);
        
    }
    
    

    @Override
    public void buildHero(Hero hero) {
        // TODO Auto-generated method stub
        
    } 
}