package model;

import static org.junit.Assert.fail;

import model.entities.BasicEntity;
import model.entities.Hero;
import model.entities.Role;

public class ModelTest {

    @org.junit.Test
    public void testEntityCreations() {
        Hero hero = new Hero.Builder().name("MyHero").level(1).speed(10).role(Role.WARRIOR).build();
    }


    @org.junit.Test
    public void testExceptions() {
        // level higher than MAXLEVEL
        try {
            new Hero.Builder().role(Role.FIREMAGE).name("Cecil").speed(5).level(BasicEntity.MAXLEVEL + 1).hp(10).build();
            fail();
        } catch (IllegalArgumentException e) {
        }

        // speed lower than 1
        try {
            new Hero.Builder().role(Role.FIREMAGE).name("Kael'thas").speed(-5).hp(10).build();
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
