package model;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Map;
import model.entities.BasicEntity;
import model.entities.BasicEntity.StatTime;
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
        Hero hero = new Hero.Builder().name("MyHero").level(2).speed(10).role(Role.WARRIOR).build();
        assertEquals("MyHero", hero.getName());
        assertEquals(2, hero.getStat(StatType.LEVEL, StatTime.CURRENT));
        assertEquals(10, hero.getStat(StatType.SPEED, StatTime.CURRENT));
        assertEquals(StatType.HP.getStdValue(), hero.getStat(StatType.HP, StatTime.CURRENT)); //hitpoint not explained
        assertEquals(Role.WARRIOR, hero.getRole());
        }
    
    /**
     * 
     */
    @org.junit.Test
    public void testStage() {
       StageData.TUTORIAL.setState(StageState.DONE);
       StageData.THECAVE.setState(StageState.UNLOCKED);
       Map<StageData, StageState> stageMap = Stages.generateStagesData();
       System.out.println(stageMap.values());
       StageData.FIRSTMISSION.setState(StageState.DONE);
       System.out.println(Stages.generateStagesData().values());
       Stages.setStagesData(stageMap);
       System.out.println(stageMap.values());
    }

    /**
     * Testing providers.
     */
    @org.junit.Test
    public void testProvider() {
        MonsterFactory factory = new MonsterFactory();
     //   assertEquals(Arrays.asList(factory.createMonster(MonsterTemplates.PEASANT)), StageData.TUTORIAL.getEnemyList());
    }
    
    @org.junit.Test
    public void testSkillModifier() {
        SkillData skill = SkillData.FLARE;
        for (int i = 0; i < 100; i++) {
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
