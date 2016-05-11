package controller;


import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
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
import model.stages.StageState;
import model.stages.Stages;
import view.CombatGUI;
import view.View;

public class StageLoopImp implements StageLoop {
    
    public static final double DIV_SPEED = 30.00;
    public static final int ATTESA_EXP = 1000;
    private Stage stage;
    private int counter, speedHero;
    private volatile boolean pausa = true;
    private Hero heroCurrent;
    private CombatGUI riferimentoView;
    private List<Entity> listMonster;
    final Agent agent = new Agent();
    
    @Override
    public void load(StageData stagePlay, Hero hero) {
        
        stage = stagePlay;
        
        hero.copyStats();
        heroCurrent = hero;
        listMonster = stage.getEnemyList();
        
        final List<Opponent> listOpp = new ArrayList<>();
        agent.start();
        
        for (Entity monster : listMonster) {
            final Opponent mon = new Opponent(monster);
            listOpp.add(mon);
            mon.start();
        }

        riferimentoView = new CombatGUI(/**/"kjuvc"); //this, stage, hero.getName(), hero.getStatMap(StatTime.CURRENT), hero.getAllowedSkillList());
    }
    
    
    @Override
    public void attack(Skill mossa, int monsterId) {
        
        //riferimentoView.enableButtons(false);
        
        if ( heroCurrent.getStat(StatType.HP, StatTime.CURRENT) > 0 ) {
            
            agent.pausaCounting();

            speedHero = (int) ((DIV_SPEED/heroCurrent.getStat(StatType.SPEED, StatTime.CURRENT)   )*1000);
            try {
                Thread.sleep(speedHero);
                
                if (heroCurrent.getStat(StatType.HP, StatTime.CURRENT) > 0) {
                    
                    attackEffective(heroCurrent, listMonster.get(monsterId), mossa);
                    //riferimentoView.refreshMonsters(listMonster);
                    
                    agent.pausaCounting();
                    
                    if ( Stages.isCleared(listMonster) ) {
                        
                        agent.stopCounting();

                        setStage();
                        
                        heroCurrent.setStat(StatType.EXP, stage.getReward(), StatTime.GLOBAL, ActionType.INCREASE);
                        heroCurrent.copyStats();
                        //riferimentoView.refreshHero( StatType.EXP, heroCurrent.getStat(StatType.EXP, StatTime.GLOBAL));
                        save( "save/" + heroCurrent.getName() + ".txt" );
                        
                        Thread.sleep(ATTESA_EXP);
                        //View win = new Victory("Vittoria");
                    }
                    
                }
                
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        
        //riferimentoView.enableButtons(true);
    }


    private void setStage() {
        stage.setState(StageState.DONE);
        
        switch (stage.getName()) {
        
            case "Tutorial":
                stage = StageData.FIRSTMISSION;
                break;
                
            case "First mission":
                stage = StageData.THECAVE;
                break;
                
            case "The Cave":
                stage = StageData.UNFAIR;
                break;
            
            default:
                break;
        }
        if ( stage.getState().equals(StageState.LOCKED) ) {
            stage.setState(StageState.UNLOCKED);
        }
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

                        //riferimentoView.refreshHero( StatType.HP, heroCurrent.getStat(StatType.HP, StatTime.CURRENT));
                        
                        if (heroCurrent.getStat(StatType.HP, StatTime.CURRENT) <= 0) {
                            playerLost = true;
                            agent.stopCounting();
                            //View gameOver = new Defeat("Game Over");
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
    
    
    private void save(String nameSave) {
        
        List list = new ArrayList();
        list.add(heroCurrent);
        list.add(Stages.generateStagesData());
        

        FileOutputStream fileOutputStream = null;
        ObjectOutputStream objectOutputStream = null;
        
        try {
            fileOutputStream = new FileOutputStream(nameSave);
            objectOutputStream = new ObjectOutputStream(fileOutputStream);
            
            objectOutputStream.writeObject(list);
            objectOutputStream.close();
            fileOutputStream.close();
            
            System.out.println("Oggetto correttamente salvato su file.");
            
        } catch (IOException ex) {
                ex.printStackTrace();
        }
        
    } 
}
