package controller;

import model.entities.Entity;
import model.entities.Hero;
import model.stages.Stage;
import model.stages.StageData;

public class Game implements GameEngine{
    private Stage stage;
    
    public Game() {
        /* Qui il gioco inizia con la creazione (o caricamento da db) dei dati dell'eroe
         * La view dirà lo stage che bisogna caricare passando un indice da inserire su Stage stage = StageData.getData().getStage(index);
         * Inizia la battaglia tra l'eroe e i nemici presi da stage.getEnemyList();
         * Per vedere se uno stage è finito c'è la funzione stage.IsCleared() che torna un boolean
         */
    }
    
    @Override
    public void attack(Entity attacker, Entity target, int skillId) {
        target.decreaseHp(attacker.getSkill(skillId).getDamage());
        
        //Stampa da togliere, solo per test (visualizzare il risultato è un compito da delegare alla view
        System.out.println(attacker.getName() + " attacks " + target.getName() + " with " + attacker.getSkill(skillId)); 
    }

    @Override
    public void loadStage(int index) {
        this.stage = StageData.getData().getStage(index);
    }

    @Override
    public void buildHero(Hero hero) {
        // TODO Auto-generated method stub
        
    } 
}