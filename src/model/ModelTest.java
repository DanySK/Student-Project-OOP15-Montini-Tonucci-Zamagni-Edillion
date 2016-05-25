package model;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.EnumMap;


import model.entities.BasicEntity.ActionType;
import model.entities.BasicEntity.StatTime;

import model.items.ItemUsable;
import model.entities.Hero;
import model.entities.MonsterFactory;
import model.entities.MonsterTemplates;
import model.entities.Role;
import model.entities.StatType;
import model.skills.SkillData;
import model.stages.StageData;
import model.stages.StageState;
import model.stages.Stages;

/**
 * Test class for model.
 *
 */
public class ModelTest {

    /**
     * Testing entitiy's constructors and builders.
     */
    @org.junit.Test
    public void testEntityCreations() {
        //Hero builder
        Hero hero = new Hero.Builder().name("MyHero").level(2).speed(10).role(Role.WARRIOR).build();
        //Checking stats
        assertEquals("MyHero", hero.getName());
        assertEquals(2, hero.getStat(StatType.LEVEL, StatTime.CURRENT));
        assertEquals(10, hero.getStat(StatType.SPEED, StatTime.CURRENT));
        assertEquals(StatType.HP.getStdValue(), hero.getStat(StatType.HP, StatTime.CURRENT)); //hitpoint not explained
        assertEquals(Role.WARRIOR, hero.getRole());

        //Decreasing life under 0 to check if goes negative
        hero.setStat(StatType.HP, 500, StatTime.CURRENT, ActionType.DECREASE);
        assertEquals(0, hero.getStat(StatType.HP, StatTime.CURRENT));

        //Setting stats
        hero.setStat(StatType.GOLD, 200, StatTime.GLOBAL, ActionType.INCREASE);
        assertEquals(200, hero.getStat(StatType.GOLD, StatTime.GLOBAL));
        assertEquals(0, hero.getStat(StatType.GOLD, StatTime.CURRENT));

        }

    /**
     * Testing stage's stuff.
     */
    @org.junit.Test
    public void testStage() {
       //Setting two stages' state
       StageData.TUTORIAL.setState(StageState.DONE);
       StageData.FIRSTMISSION.setState(StageState.UNLOCKED);
       //Generate stages' actual states' map
       EnumMap<StageData, StageState> stageMap = Stages.generateStagesData();
       StageState[] dataTest = {StageState.DONE, StageState.UNLOCKED, StageState.LOCKED, StageState.LOCKED};
       assertArrayEquals(dataTest, stageMap.values().toArray());

       //Changing two other values
       StageData.FIRSTMISSION.setState(StageState.DONE);
       StageData.THECAVE.setState(StageState.UNLOCKED);
       StageState[] dataTest2 = {StageState.DONE, StageState.DONE, StageState.UNLOCKED, StageState.LOCKED};
       assertArrayEquals(dataTest2, Stages.generateStagesData().values().toArray());
    }

    /**
     * Testing providers.
     */
    @org.junit.Test
    public void testProvider() {
        MonsterFactory factory = new MonsterFactory();
        //assertEquals(Arrays.asList(factory.createMonster(MonsterTemplates.PEASANT)), StageData.TUTORIAL.getEnemyList());
    }

    /**
     * 
     */
    @org.junit.Test
    public void testInventory() {
        Hero hero = new Hero.Builder().name("MyHero").level(2).speed(10).role(Role.WARRIOR).build();

        ItemUsable item = ItemUsable.CURE;

        hero.getInventory().getBag().add(item);
        hero.getInventory().getBag().add(item);
        
       // System.out.println(hero.getInventory().getBag());
        
        switch (item.getItemType()) {
            case PERSONAL:
                hero.setStat(item.getStatTypeInfluence(), item.getEffectiveness(), 
                                    StatTime.CURRENT, ActionType.INCREASE);
                break;
            case IMPERSONAL:
                System.out.println("Caso IMPERSONAL");
                break;
            default: 
                System.out.println("Caso default");
                break;
        }
        hero.getInventory().getBag().remove(item);
        
       // System.out.println(hero.getInventory().getBag());
       // System.out.println(hero.getStat(StatType.HP, StatTime.GLOBAL));
       // System.out.println(hero.getStat(StatType.HP, StatTime.CURRENT));
    }

    /**
     * Testing skill's modifier is in range on 10000 attempts.
     */
    @org.junit.Test
    public void testSkillModifier() {
        SkillData skill = SkillData.FLARE;
        for (int i = 0; i < 10000; i++) {
            int dmg = skill.useSkill();
            if (dmg > skill.getDamage() + skill.getModifier() || dmg < skill.getDamage() - skill.getModifier()) {
                fail();
            }
        }

    }

    /**
     * Testing if exceptions gets triggered.
     */
    @org.junit.Test
    public void testExceptions() {
        // level higher than MAXLEVEL
        try {
            new Hero.Builder().role(Role.REDMAGE).name("Cecil").speed(5).level(StatType.LEVEL.getMaxValue() + 1).hp(10)
                    .build();
            fail();
        } catch (IllegalArgumentException e) {
        }

        // speed lower than 1
        try {
            new Hero.Builder().role(Role.BLACKMAGE).name("Kael'thas").speed(-5).hp(10).build();
            fail();
        } catch (IllegalArgumentException e) {
            //System.out.println(e.getMessage());
        }

        // null builder
        try {
            new Hero.Builder().build();
            fail();
        } catch (IllegalArgumentException e) {
        }

        // null name
        try {
            new Hero.Builder().speed(2).role(Role.WARRIOR).build();
            fail();
        } catch (IllegalArgumentException e) {
        }

    }

}
