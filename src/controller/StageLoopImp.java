package controller;


import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import model.entities.Entity;
import model.entities.Hero;
import model.entities.StatType;
import model.entities.BasicEntity.ActionType;
import model.entities.BasicEntity.StatTime;
import model.skills.Skill;
import model.stages.Stage;
import model.stages.StageData;
import model.stages.StageState;
import model.stages.Stages;

public class StageLoopImp implements StageLoop {
    
    public static final double DIV_SPEED = 30.00;
    private Stage stage;
    private int counter, numOpp, numMossa, speedHero;
    private volatile boolean pausa = true;
    private Entity heroM;
    private List<Entity> listMonster;
    
    @Override
    public boolean play(int stagePlay, Hero hero) {
        
        loadStage(stagePlay);
        if ( stage.getState() == StageState.LOCKED )
            return false;
        
        hero.copyStats();
        heroM = hero;
        speedHero = (int) ((DIV_SPEED/hero.getStat(StatType.SPEED, StatTime.CURRENT)   )*1000);
        listMonster = stage.getEnemyList();
        
        final List<Opponent> listOpp = new ArrayList<>();
     
        final Agent agent = new Agent();
        agent.start();
        
        for (Entity monster : listMonster) {
            final Opponent mon = new Opponent(monster);
            listOpp.add(mon);
            
            mon.start();
        }
        
        while (!(Stages.isCleared(listMonster)) && hero.getStat(StatType.HP, StatTime.CURRENT) > 0 ) {
            
            if ( counter != 0 ) {
                agent.pausaCounting();
            }
            
            //SELEZIONA AVVERSARIO
            numOpp = 0;
            if ( listOpp.size() != 1 ) {
                for ( numOpp = 0 ; numOpp < listOpp.size() ; numOpp++ ) {
                    if ( listMonster.get(numOpp).getStat(StatType.HP, StatTime.CURRENT) > 0 ) {
                        System.out.println( (numOpp+1) + ") " + listMonster.get(numOpp).getName() );
                    }
                    
                }
                System.out.println("Quale avversario vuoi selezionare? ");
                Scanner input = new Scanner(System.in);
                numOpp = input.nextInt();
                numOpp--;
            }
            
            
            // SELEZIONA ATTACCO
            numMossa=0;
            for ( Skill mossa : hero.getAllowedSkillList() ) {
                System.out.println( (numMossa+1) + ") " + mossa.getName() );
                numMossa++;
            }
            System.out.println("Quale mossa vuoi selezionare? ");
            Scanner input = new Scanner(System.in);
            numMossa = input.nextInt();
            numMossa--;

            agent.pausaCounting();
            
            try {
                Thread.sleep(speedHero);
                
                if (hero.getStat(StatType.HP, StatTime.CURRENT) > 0) {
                    
                    attack(hero, listMonster.get(numOpp), numMossa);
                    
                    System.out.println("Mostro attaccato: " + 
                                        listMonster.get(numOpp).getName() +
                                        " Attacco con: " + hero.getSkill(numMossa).getName() +
                                        " Vita: " + 
                                        listMonster.get(numOpp).getStat(StatType.HP, StatTime.CURRENT) );
                }
                
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        
        agent.stopCounting();
        
        if ( hero.getStat(StatType.HP, StatTime.CURRENT) <= 0) {
            return false;
        }
        stage.setState(StageState.DONE);
        speedHero = stagePlay +1;
        loadStage(speedHero);
        if ( stage.getState().equals(StageState.LOCKED) ) {
            stage.setState(StageState.UNLOCKED);
            System.out.println( stage.getName() + " was unlocked");
        }
        
        
        System.out.println("Hero ha " + hero.getExp() + "       e ne guadagnerà: " + stage.getReward() + "      per il liv suc ne servono: " +hero.expToLevelUp());
        hero.gainExp(stage.getReward());
        System.out.println("Hero ha " + hero.getExp() + "       e ne ha guadagnati: " + stage.getReward() + "      per il liv suc ne servono: " +hero.expToLevelUp());
        
        return true;
    }
    


    @Override
    public void attack(Entity attacker, Entity target, int skillId) {
        target.setStat(StatType.HP, attacker.getSkill(skillId).useSkill(), StatTime.CURRENT, ActionType.DECREASE);
}

    @Override
    public void loadStage(int index) {
        switch (index) {
        case 0:
            stage = StageData.TUTORIAL;
            break;
        case 1:
            stage = StageData.FIRSTMISSION;
            break;
        case 2:
            stage = StageData.THECAVE;
            break;
        case 3:
            stage = StageData.UNFAIR;
            break;
        default: 
        }
    }
    
    
    private class Opponent extends Thread {
        
        private Entity monster;
        private int evit_repit, speed, i_skill;
        private boolean playerLost;
        
        
        public Opponent(Entity monsterP) {
            monster = monsterP;
        }

        public synchronized void run() {
            speed = (int) ((DIV_SPEED/monster.getStat(StatType.SPEED, StatTime.CURRENT))*100);
            
            while ( monster.getStat(StatType.HP, StatTime.CURRENT) > 0 && !playerLost ) {
                try {
                    Thread.sleep(10);
                    
                    if ( !pausa && colpisce() ) {
                        
                        
                        Skill skill = monster.getAllowedSkillList().stream()
                                                    //.filter(p->p.getMana()<monster.getMana)
                                                    .max((p1, p2) -> Integer.compare( p1.getDamage(), p2.getDamage())).get();
                        
                        int count = 0;
                        for (Skill s : monster.getSkillList()) {
                            if (skill == s) {
                                i_skill = count;
                            }
                            count++;
                        }
                        
                        attack(monster, heroM, i_skill);
                        System.out.println(monster.getName() + " ha attaccato " + "hero");
                        System.out.println("Vita rimanente eroe " + heroM.getStat(StatType.HP, StatTime.CURRENT) );
                        if (heroM.getStat(StatType.HP, StatTime.CURRENT) <= 0) {
                            playerLost = true;
                        }
                    }
                } catch ( InterruptedException ex) {
                    // interrupted: added a system.out but there are much better ways to log exceptions
                    System.out.println("Something went wrong. " + ex);
                }
            }
        }
        
        private synchronized boolean colpisce() {
            if( counter % speed == 0 ) {
                if (evit_repit != counter) {
                    evit_repit = counter;
                    return true;
                }
            }
            return false;
        }
    }
    
    
    
    private class Agent extends Thread {

        private volatile boolean stop;

        public synchronized void run() {
            while (!stop) {
                try {
                    Thread.sleep(10);
                    if (!pausa) {
                        counter += 1;
                        /*if (counter % 10 == 0) {
                            System.out.println("Aumentato: " + counter);
                        }*/
                        
                    }
                } catch ( InterruptedException ex) {
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
