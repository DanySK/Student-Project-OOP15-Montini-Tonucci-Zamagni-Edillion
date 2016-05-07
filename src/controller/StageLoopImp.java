package controller;


import java.util.ArrayList;
import java.util.List;

import model.entities.Entity;
import model.entities.Hero;
import model.entities.StatType;
import model.entities.BasicEntity.ActionType;
import model.entities.BasicEntity.StatTime;
import model.skills.Skill;
import model.stages.Stage;
import model.stages.StageData;
import model.stages.Stages;
import view.CombatGUI;

public class StageLoopImp implements StageLoop {
    
    public static final double DIV_SPEED = 30.00;
    private Stage stage;
    private int counter, speedHero;
    private volatile boolean pausa = true;
    private Entity heroCurrent;
    private List<Entity> listMonster;
    final Agent agent = new Agent();
    
    @Override
    public void load(StageData stagePlay, Hero hero) {
        
        stage = stagePlay;
        
        hero.copyStats();
        heroCurrent = hero;
        speedHero = (int) ((DIV_SPEED/hero.getStat(StatType.SPEED, StatTime.CURRENT)   )*1000);
        listMonster = stage.getEnemyList();
        
        final List<Opponent> listOpp = new ArrayList<>();
        agent.start();
        
        for (Entity monster : listMonster) {
            final Opponent mon = new Opponent(monster);
            listOpp.add(mon);
            mon.start();
        }

        CombatGUI riferimentoView = new CombatGUI(/**/"kjuvc"); //this, stage, hero.getName(), hero.getStatMap(StatTime.CURRENT), hero.getAllowedSkillList());
    }
    
    
    @Override
    public void attack(Skill mossa, int monsterId) {
        
        //disabilita pulsanti
        
        if ( !(Stages.isCleared(listMonster)) && heroCurrent.getStat(StatType.HP, StatTime.CURRENT) > 0 ) {
            
            agent.pausaCounting();
            
            try {
                Thread.sleep(speedHero);
                
                if (heroCurrent.getStat(StatType.HP, StatTime.CURRENT) > 0) {
                    
                    attackEffective(heroCurrent, listMonster.get(monsterId), mossa);
                    
                    
                    //ti dovrò chiamare la refreshMionster
                    
                    
                    /*
                    System.out.println("Mostro attaccato: " + 
                                        listMonster.get(numOpp).getName() +
                                        " Attacco con: " + heroCurrent.getSkill(numMossa).getName() +
                                        " Vita: " + 
                                        listMonster.get(numOpp).getStat(StatType.HP, StatTime.CURRENT) );
                                        */
                }
                
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            agent.pausaCounting();
        }
        
        /*
        agent.stopCounting();
        
        if ( hero.getStat(StatType.HP, StatTime.CURRENT) <= 0) {
            return false;
        }
        stage.setState(StageState.DONE);
        if ( stage.getState().equals(StageState.LOCKED) ) {
            stage.setState(StageState.UNLOCKED);
            System.out.println( stage.getName() + " was unlocked");
        }
        
        
        System.out.println("Hero ha " + hero.getExp() + "       e ne guadagnerà: " + stage.getReward() + "      per il liv suc ne servono: " +hero.expToLevelUp());
        hero.gainExp(stage.getReward());
        System.out.println("Hero ha " + hero.getExp() + "       e ne ha guadagnati: " + stage.getReward() + "      per il liv suc ne servono: " +hero.expToLevelUp());
        
        return true;
        */
        
        
      //abilita pulsanti
    }


    public void attackEffective(Entity attacker, Entity target, Skill skill) {
        target.setStat(StatType.HP, skill.useSkill(), StatTime.CURRENT, ActionType.DECREASE);
}

    
    
    private class Opponent extends Thread {
        
        private Entity monster;
        private int evit_repit, speed;
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
                        
                        attackEffective(monster, heroCurrent, skill);
                        System.out.println(monster.getName() + " ha attaccato " + "hero");
                        System.out.println("Vita rimanente eroe " + heroCurrent.getStat(StatType.HP, StatTime.CURRENT) );
                        if (heroCurrent.getStat(StatType.HP, StatTime.CURRENT) <= 0) {
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
