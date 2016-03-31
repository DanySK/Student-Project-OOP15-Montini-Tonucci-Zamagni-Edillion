package model;

import static org.junit.Assert.*;

import java.lang.reflect.Array;
import java.util.Arrays;

import model.entities.BasicEntity;
import model.entities.Hero;
import model.entities.MonsterFactory;
import model.entities.MonsterTemplates;
import model.entities.Role;
import model.stages.StageData;

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
        assertEquals(2, hero.getLevel());
        assertEquals(10, hero.getSpeed());
        assertEquals(BasicEntity.STANDARD_HP, hero.getHp()); //hitpoint not explained
        assertEquals(Role.WARRIOR, hero.getRole());
        }

    /**
     * Testing providers.
     */
    @org.junit.Test
    public void testProvider() {
        MonsterFactory factory = new MonsterFactory();
        assertEquals(Arrays.asList(factory.createMonster(MonsterTemplates.PEASANT)), StageData.TUTORIAL.getEnemyList());
    }

    /**
     * Testing if exceptions gets triggered.
     */
    @org.junit.Test
    public void testExceptions() {
        // level higher than MAXLEVEL
        try {
            new Hero.Builder().role(Role.REDMAGE).name("Cecil").speed(5).level(BasicEntity.MAX_LEVEL + 1).hp(10)
                    .build();
            fail();
        } catch (IllegalArgumentException e) {
        }

        // speed lower than 1
        try {
            new Hero.Builder().role(Role.BLACKMAGE).name("Kael'thas").speed(-5).hp(10).build();
            fail();
        } catch (IllegalArgumentException e) {
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
