package controller;


import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import model.entities.Entity;
import model.entities.Hero;
import model.entities.StatType;
import model.items.Durable;
import model.items.ItemUsable;
import model.entities.BasicEntity.ActionType;
import model.entities.BasicEntity.StatTime;
import model.skills.Skill;
import model.stages.Stage;
import model.stages.StageData;
import model.stages.StageState;
import model.stages.Stages;
import view.CombatGUI;
import view.CombatGUIImpl;

public class StageLoopImp implements StageLoop {
    
    private static final double DIV_SPEED = 30.00;
    private static final int TIME_WAIT_EXP = 500;
    private static final int SPEED_THREAD = 10;
    private static final int TIME_VIEW = 100;
    private static final int TIME_MANA = 100;
    private Stage stage;
    private int counter, speedHero, maxHPhero, maxMANAhero;
    private volatile boolean pause = true;
    private Hero heroCurrent;
    private CombatGUI referenceCombatGUI;
    private List<Entity> listMonster;
    final Agent agent = new Agent();
    
    @Override
    public void load(StageData stagePlay, Hero hero) {
        final List<Opponent> listOpp = new ArrayList<>();
        
        stage = stagePlay;
        
        hero.copyStats();
        heroCurrent = hero;
        
        for ( Durable e : heroCurrent.getInventory().getEquip()) {
            heroCurrent.setStat(e.getStatTypeInfluence(), e.getEffectiveness(), StatTime.CURRENT, ActionType.INCREASE);
        }
        maxHPhero = heroCurrent.getStat(StatType.HP, StatTime.CURRENT);
        maxMANAhero = heroCurrent.getStat(StatType.MANA, StatTime.CURRENT);
        
        listMonster = stage.getEnemyList();
        
        agent.start();
        
        for (Entity monster : listMonster) {
            final Opponent mon = new Opponent(monster);
            listOpp.add(mon);
            mon.start();
        }

        referenceCombatGUI = new CombatGUIImpl( "Schermata di combattimento", this, listMonster, heroCurrent.getName(), 
                hero.getStatMap(StatTime.CURRENT), heroCurrent.getAllowedSkillList());
        
        speedHero = (int) ((DIV_SPEED/heroCurrent.getStat(StatType.SPEED, StatTime.CURRENT)   )*1000);
        final WaitFirst firstWait = new WaitFirst(speedHero);
        firstWait.start();
    }

    @Override
    public void attack(Skill skill, int monsterId) {
        final HeroAttack hero_thread = new HeroAttack(skill, monsterId);
        hero_thread.start();
    }


    @Override
    public void useItem(ItemUsable item, int targetId) {
        final HeroUseItem hero_thread = new HeroUseItem(item, targetId);
        hero_thread.start();
    }
    
    private class HeroAttack extends Thread {
        private Skill skill;
        private int monsterId;

        public HeroAttack(Skill skill, int monsterId) {
            this.skill = skill;
            this.monsterId = monsterId;
        }
        
        public synchronized void run() {
            
            if ( heroCurrent.getStat(StatType.HP, StatTime.CURRENT) > 0 && 
                    heroCurrent.getStat(StatType.MANA, StatTime.CURRENT) >= skill.getMana()) {
                
                speedHero = (int) ((DIV_SPEED/heroCurrent.getStat(StatType.SPEED, StatTime.CURRENT)   )*1000);
                
                attackEffective(heroCurrent, listMonster.get(monsterId), skill);

                referenceCombatGUI.generateEnemiesPanel(listMonster);
                referenceCombatGUI.generateHeroPanel(heroCurrent.getName(),heroCurrent.getStatMap(StatTime.CURRENT));

                if ( Stages.isCleared(listMonster) ) {
                    heroWin();
                } else {
                    agent.pausaCounting();
                    
                    pauseHero(speedHero);
    
                    agent.pausaCounting();
                }
            }
        }
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
                    Thread.sleep(SPEED_THREAD);
                    
                    if ( !pause && colpisce() ) {
                        
                        Skill skill = monster.getAllowedSkillList().stream()
                                                    //.filter(p->p.getMana()>monster.getStat(StatType.MANA, StatTime.CURRENT))
                                                    .max((p1, p2) -> Integer.compare( p1.getDamage(), p2.getDamage())).get();
                        
                        attackEffective(monster, heroCurrent, skill);
                        
                        referenceCombatGUI.generateHeroPanel(heroCurrent.getName(),heroCurrent.getStatMap(StatTime.CURRENT));
                        
                        if (heroCurrent.getStat(StatType.HP, StatTime.CURRENT) <= 0) {
                            playerLost = true;
                            agent.stopCounting();
                            referenceCombatGUI.defeat();
                            stage.restoreEnemyList();
                        }
                    }
                } catch ( InterruptedException ex) {
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
                    Thread.sleep(SPEED_THREAD);
                    if (!pause) {
                        counter += 1;
                        
                        if (counter % TIME_MANA == 0) {
                            if ( (heroCurrent.getStat(StatType.MANA, StatTime.CURRENT) + 
                                  heroCurrent.getStat(StatType.MANAREGEN, StatTime.CURRENT)) < maxMANAhero ) {
                                heroCurrent.setStat(StatType.MANA, heroCurrent.getStat(StatType.MANAREGEN, StatTime.CURRENT), 
                                StatTime.CURRENT, ActionType.INCREASE);
                            } else { 
                                heroCurrent.setStat(StatType.MANA, maxMANAhero, StatTime.CURRENT, ActionType.SET);
                            }
                            referenceCombatGUI.generateHeroPanel(heroCurrent.getName(),heroCurrent.getStatMap(StatTime.CURRENT));
                            
                            
                            for (Entity e: listMonster) {
                                if(e.getStat(StatType.HP, StatTime.CURRENT) > 0) {
                                    if ( (e.getStat(StatType.MANA, StatTime.CURRENT) + 
                                            e.getStat(StatType.MANAREGEN, StatTime.CURRENT)) < e.getStat(StatType.MANAREGEN, StatTime.GLOBAL) ) {
                                          e.setStat(StatType.MANA, e.getStat(StatType.MANAREGEN, StatTime.CURRENT), 
                                                      StatTime.CURRENT, ActionType.INCREASE);
                                    } else { 
                                          e.setStat(StatType.MANA, e.getStat(StatType.MANAREGEN, StatTime.GLOBAL), StatTime.CURRENT, ActionType.SET);
                                    }
                                }
                            }
                            referenceCombatGUI.generateEnemiesPanel(listMonster);
                        }
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
                if (pause == true) {
                        pause = false;
                } else {
                        pause = true;
                }
        }
    }
    
    private class WaitFirst extends Thread {
        private int speed;

        public WaitFirst(int speed) {
            this.speed = speed;
        }
        
        public synchronized void run() {
            agent.pausaCounting();
            pauseHero(speed);
            agent.pausaCounting();
        }
    }
    
    private class HeroUseItem extends Thread {
        private ItemUsable item;
        private int targetId;

        public HeroUseItem(ItemUsable item, int targetId) {
            this.item = item;
            this.targetId = targetId;
        }
        
        public synchronized void run() {
            referenceCombatGUI.enableButtons(false);
            
            if ( heroCurrent.getStat(StatType.HP, StatTime.CURRENT) > 0 ) {
                switch (item.getItemType()) {
                    case PERSONAL:
                        heroCurrent.setStat(item.getStatTypeInfluence(), item.getEffectiveness(), 
                                            StatTime.CURRENT, ActionType.INCREASE);
                        
                        if (heroCurrent.getStat(StatType.HP, StatTime.CURRENT) > maxHPhero) {
                            heroCurrent.setStat(StatType.HP, maxHPhero, StatTime.CURRENT, ActionType.SET);
                        }
                        if (heroCurrent.getStat(StatType.MANA, StatTime.CURRENT) > maxMANAhero) {
                            heroCurrent.setStat(StatType.MANA, maxMANAhero, StatTime.CURRENT, ActionType.SET);
                        }
                        referenceCombatGUI.generateHeroPanel(heroCurrent.getName(),heroCurrent.getStatMap(StatTime.CURRENT));
                        
                        break;
                    case IMPERSONAL:
                        listMonster.get(targetId).setStat(item.getStatTypeInfluence(), item.getEffectiveness(), 
                                                          StatTime.CURRENT, ActionType.DECREASE);
                        referenceCombatGUI.generateEnemiesPanel(listMonster);
                        break;
                    default: 
                        for (Entity e: listMonster) {
                            e.setStat(item.getStatTypeInfluence(), item.getEffectiveness(), 
                                      StatTime.CURRENT, ActionType.DECREASE);
                        }
                        referenceCombatGUI.generateEnemiesPanel(listMonster);
                        break;
                }
                heroCurrent.getInventory().getBag().remove(item);
                
                speedHero = (int) ((DIV_SPEED/heroCurrent.getStat(StatType.SPEED, StatTime.CURRENT)   )*1000);
                
                if ( Stages.isCleared(listMonster) ) {
                    heroWin();
                } else {
                    agent.pausaCounting();
        
                    try {
                        Thread.sleep(speedHero);
                        
                        if (heroCurrent.getStat(StatType.HP, StatTime.CURRENT) > 0) {
                            agent.pausaCounting();
                        }
                        
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            referenceCombatGUI.enableButtons(true);
        }
    }
    
    private void attackEffective(Entity attacker, Entity target, Skill skill) {
        int damage = skill.useSkill();
        referenceCombatGUI.refreshCombatLog(attacker.getName(), target.getName(), skill.getName(), damage);
        attacker.setStat(StatType.MANA, skill.getMana(), StatTime.CURRENT, ActionType.DECREASE);
        target.setStat(StatType.HP, damage, StatTime.CURRENT, ActionType.DECREASE);
    }

    private void setStage() {
        stage.setState(StageState.DONE);
        
        try {
            stage = stage.getNext();
        } catch (IllegalStateException e) { }
        
        if ( stage.getState().equals(StageState.LOCKED) ) {
            stage.setState(StageState.UNLOCKED);
        }
    }
    
    private void pauseHero(int speed) {
        int speedUsed = speed / TIME_VIEW;

        referenceCombatGUI.enableButtons(false);
        
        for( int i = TIME_VIEW ; i >= 0  ; i--) {
            try {
                if (heroCurrent.getStat(StatType.HP, StatTime.CURRENT) > 0) {
                    referenceCombatGUI.refreshCount(i);
                }
                Thread.sleep(speedUsed);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        referenceCombatGUI.enableButtons(true);
    }
    
    private void heroWin() {
        agent.stopCounting();

        heroCurrent.gainExp(stage.getReward());
        heroCurrent.setStat(StatType.GOLD, stage.getGoldReward(), StatTime.GLOBAL, ActionType.INCREASE);

        heroCurrent.copyStats();
        referenceCombatGUI.generateHeroPanel(heroCurrent.getName(),heroCurrent.getStatMap(StatTime.CURRENT));
        save(Game.FOLDER_PATH + "/" + heroCurrent.getName() + ".dat");
        
        try {
            Thread.sleep(TIME_WAIT_EXP);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        setStage();
        referenceCombatGUI.victory();

        stage.restoreEnemyList();
    }
    
    private void save(String nameSave) {
        
        List<Object> list = new ArrayList<>();
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
            
        } catch (IOException ex) {
                ex.printStackTrace();
        }
    }
}