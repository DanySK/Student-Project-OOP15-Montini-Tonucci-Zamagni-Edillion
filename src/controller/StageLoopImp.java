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

public class StageLoopImp implements StageLoop {
    
    public static final double DIV_SPEED = 30.00;
    public static final int ATTESA_EXP = 1000;
    public static final int SPEED_THREAD = 10;
    public static final int TIME_VIEW = 20;
    public static final int TIME_MANA = TIME_VIEW * 5;
    public static final int INCREMENT_MANA = 5;
    private Stage stage;
    private int counter, speedHero, maxHPhero, maxMANAhero, viewTime;
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
        
        for ( Durable e : heroCurrent.getInventory().getEquip()) {
            heroCurrent.setStat(e.getStatTypeInfluence(), e.getEffectiveness(), StatTime.CURRENT, ActionType.INCREASE);
        }
        maxHPhero = heroCurrent.getStat(StatType.HP, StatTime.CURRENT);
        maxMANAhero = heroCurrent.getStat(StatType.MANA, StatTime.CURRENT);
        
        listMonster = stage.getEnemyList();
        
        final List<Opponent> listOpp = new ArrayList<>();
        agent.start();
        
        for (Entity monster : listMonster) {
            final Opponent mon = new Opponent(monster);
            listOpp.add(mon);
            mon.start();
        }

        riferimentoView = new CombatGUI( "Schermata di combattimento", this, listMonster, heroCurrent.getName(), 
                hero.getStatMap(StatTime.CURRENT), heroCurrent.getAllowedSkillList());
    }

    @Override
    public void attack(Skill mossa, int monsterId) {
        final HeroAttack hero_thread = new HeroAttack(mossa, monsterId);
        hero_thread.start();
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


    public void attackEffective(Entity attacker, Entity target, Skill skill) {
        int damage = skill.useSkill();
        //riferimentoView.logStringAmbarabacciCocco(attacker.getName(), target.getName(), skill.getName(), damage);
        target.setStat(StatType.HP, damage, StatTime.CURRENT, ActionType.DECREASE);
    }

    
    private class HeroAttack extends Thread {
        private Skill mossa;
        private int monsterId;

        public HeroAttack(Skill mossa, int monsterId) {
            this.mossa = mossa;
            this.monsterId = monsterId;
        }
        
        public synchronized void run() {
            riferimentoView.enableButtons(false);
            
            if ( heroCurrent.getStat(StatType.HP, StatTime.CURRENT) > 0 && monsterId != -1 ) {

                speedHero = (int) ((DIV_SPEED/heroCurrent.getStat(StatType.SPEED, StatTime.CURRENT)   )*1000);
                viewTime = speedHero/200;
                
                agent.pausaCounting();

                try {
                    Thread.sleep(speedHero);
                    
                    if (heroCurrent.getStat(StatType.HP, StatTime.CURRENT) > 0) {
                        
                        attackEffective(heroCurrent, listMonster.get(monsterId), mossa);
                        
                        riferimentoView.generateEnemiesPanel(listMonster);

                        agent.pausaCounting();
                        
                        if ( Stages.isCleared(listMonster) ) {
                            heroWin();
                        }
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            riferimentoView.enableButtons(true);
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
                    
                    if ( !pausa && colpisce() ) {
                        
                        
                        Skill skill = monster.getAllowedSkillList().stream()
                                                    //.filter(p->p.getMana()<monster.getMana)
                                                    .max((p1, p2) -> Integer.compare( p1.getDamage(), p2.getDamage())).get();
                        
                        attackEffective(monster, heroCurrent, skill);
                        
                        riferimentoView.generateHeroPanel(heroCurrent.getName(),heroCurrent.getStatMap(StatTime.CURRENT));
                        
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
                    Thread.sleep(SPEED_THREAD);
                    if (!pausa) {
                        counter += 1;
                        
                        if (counter % TIME_VIEW == 0) {
                            //riferimentoView.refreshCount(viewTime--);
                            System.out.println("Tempo restante: " + viewTime--);
                            
                            if (counter % TIME_MANA == 0) {
                                if ( (heroCurrent.getStat(StatType.MANA, StatTime.CURRENT) + INCREMENT_MANA) < maxMANAhero ) {
                                    heroCurrent.setStat(StatType.MANA, INCREMENT_MANA, StatTime.CURRENT, ActionType.INCREASE);
                                } else { 
                                    heroCurrent.setStat(StatType.MANA, maxMANAhero, StatTime.CURRENT, ActionType.SET);
                                }
                                riferimentoView.generateHeroPanel(heroCurrent.getName(),heroCurrent.getStatMap(StatTime.CURRENT));
                            }
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
                if (pausa == true) {
                        pausa = false;
                } else {
                        pausa = true;
                }
        }
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
            
            System.out.println("Oggetto correttamente salvato su file.");
            
        } catch (IOException ex) {
                ex.printStackTrace();
        }
    }


    @Override
    public void useItem(ItemUsable item, int targetId) {
        final HeroUseItem hero_thread = new HeroUseItem(item, targetId);
        hero_thread.start();
    }
    
    private class HeroUseItem extends Thread {
        private ItemUsable item;
        private int targetId;

        public HeroUseItem(ItemUsable item, int targetId) {
            this.item = item;
            this.targetId = targetId;
        }
        
        public synchronized void run() {
            riferimentoView.enableButtons(false);
            
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
                        riferimentoView.generateHeroPanel(heroCurrent.getName(),heroCurrent.getStatMap(StatTime.CURRENT));
                        
                        break;
                    case IMPERSONAL:
                        listMonster.get(targetId).setStat(item.getStatTypeInfluence(), item.getEffectiveness(), 
                                                          StatTime.CURRENT, ActionType.DECREASE);
                        riferimentoView.generateEnemiesPanel(listMonster);
                        break;
                    default: 
                        for (Entity e: listMonster) {
                            e.setStat(item.getStatTypeInfluence(), item.getEffectiveness(), 
                                      StatTime.CURRENT, ActionType.DECREASE);
                        }
                        riferimentoView.generateEnemiesPanel(listMonster);
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
            riferimentoView.enableButtons(true);
        }
    }
    
    private void heroWin() {
        agent.stopCounting();

        heroCurrent.setStat(StatType.EXP, stage.getReward(), StatTime.GLOBAL, ActionType.INCREASE);
        heroCurrent.setStat(StatType.GOLD, stage.getGoldReward(), StatTime.GLOBAL, ActionType.INCREASE);

        setStage();
        heroCurrent.copyStats();
        riferimentoView.generateHeroPanel(heroCurrent.getName(),heroCurrent.getStatMap(StatTime.CURRENT));
        save(Game.FOLDER_PATH + "/" + heroCurrent.getName() + ".dat");
        
        try {
            Thread.sleep(ATTESA_EXP);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        riferimentoView.Victory();
    }
}
