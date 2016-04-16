package controller;


import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import model.entities.Entity;
import model.entities.Hero;
import model.entities.MonsterTemplates;
import model.skills.Skill;
import model.stages.Stage;
import model.stages.StageData;

public class StageLoopImp implements StageLoop {
    
    private Stage stage;
    private Hero hero;
    private int counter, hpHero, num_opp, y, x;
    private volatile boolean pausa = true;
    
    @Override
    public boolean play(int stagePlay, Hero heroP) {
        hero = heroP;
        hpHero = hero.getHp();
        loadStage(stagePlay);
        
        final List<Opponent> listOpp = new ArrayList<>();
     
        final Agent agent = new Agent();
        agent.start();
        
        for (Entity monster : stage.getEnemyList()) {
            final Opponent mon = new Opponent(monster);
            listOpp.add(mon);
            
            num_opp++;
            mon.start();
        }
        
        hero.setHp(20);
        
        while (!(StageData.isCleared(stage.getEnemyList())) && hero.getHp() > 0 ) {
            
            if ( counter != 0 ) {
                agent.pausaCounting();
            }
            
            //SELEZIONA AVVERSARIO
            if ( num_opp != 1 ) {
                for ( y = 0 ; y < num_opp ; y++ ) {
                    System.out.println( (y+1) + ") " + stage.getEnemyList().get(y).getName() );
                }
                System.out.println("Quale avversario vuoi selezionare? ");
                Scanner input = new Scanner(System.in);
                y = input.nextInt();
                y--;
            }
            
            
            // PRESA DELL'ATTACCO
            x=0;
            for ( Skill e : hero.getSkillList() ) {
                if (e.getRequiredLevel() <= hero.getLevel()) {
                    System.out.println( (x+1) + ") " + e );
                }
                x++;
            }
            System.out.println("Quale mossa vuoi selezionare? ");
            Scanner input = new Scanner(System.in);
            x = input.nextInt();
            x--;
            System.out.println("Attacco con: " + hero.getSkill(x));

            agent.pausaCounting();
            
            try {
                Thread.sleep((8 - hero.getSpeed()) * 1000);
                
                attack(hero, stage.getEnemyList().get(y), x);
                System.out.println("Mostro attaccato: " + stage.getEnemyList().get(y).getName() + " Vita: " + stage.getEnemyList().get(y).getHp() );
            
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        
        agent.stopCounting();
        hero.setHp(hpHero);
        
        return false;
    }
    


    @Override
    public void attack(Entity attacker, Entity target, int skillId) {
        target.decreaseHp(attacker.getSkill(skillId).getDamage());
    }

    @Override
    public void loadStage(int index) {
        switch (index) {
        case 0:
            this.stage = StageData.TUTORIAL;
            break;
        case 1:
            this.stage = StageData.FIRSTMISSION;
            break;
        case 2:
            this.stage = StageData.THECAVE;
            break;
        case 3:
            this.stage = StageData.UNFAIR;
            break;
        default: 
        }
    }
    
    
    private class Opponent extends Thread {
        
        private Entity monster;
        
        public Opponent(Entity monsterP) {
            monster = monsterP;
        }

        public void run() {
            while ( monster.getHp() > 0 && hero.getHp() > 0 ) {
                
                if ( colpisce() && !pausa ) {
                    attack(monster, hero, 0);
                    System.out.println("Ha attaccato " + monster.getName());
                    System.out.println("Vita rimanente eroe " + hero.getHp() );
                }

                try {
                    Thread.sleep(1000);
                } catch ( InterruptedException ex) {
                    // interrupted: added a system.out but there are much better ways to log exceptions
                    System.out.println("Something went wrong. " + ex);
                }
                
            }
        }
        
        private boolean colpisce() {
            if( counter % (8 - monster.getSpeed()) == 0 )
                return true;
            
            return false;
        }
    }
    
    
    
    private class Agent extends Thread {

        private volatile boolean stop;

        public void run() {
            while (!stop) {
                try {
                    if (!pausa) {
                        counter += 1;
                        System.out.println("Aumentato: " + counter);
                    }

                    Thread.sleep(1000);
                } catch ( InterruptedException ex) {
                    // interrupted: added a system.out but there are much better ways to log exceptions
                    System.out.println("Something went wrong. " + ex);
                }
            }
        }

        public void stopCounting() {
            this.stop = true;
        }
        
        public void pausaCounting() {
                if (pausa == true) {
                        pausa = false;
                } else {
                        pausa = true;
                }
        }
    }

}
