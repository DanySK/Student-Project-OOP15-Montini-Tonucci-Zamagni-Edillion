package model;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import model.entities.Entity;
import model.entities.Hero;
import model.entities.Role;
import model.entities.BasicEntity;
import model.skills.BasicSkills;
import model.skills.Skill;
import model.stages.Stage;
import model.stages.StageData;

public class ModelTest {

    @org.junit.Test
    public void testNewEntity() {
        // New Entity
        Entity player1 = new BasicEntity.Builder<>()
                                        .name("Mario")
                                        .speed(9)
                                        .level(7)
                                        .hp(1000)
                                        .skillArr(BasicSkills.values())
                                        .build();
        //System.out.println(player1);

        // Builder worked
        assertEquals(player1.getHp(), 1000);

        // Skill list test
        List<Skill> s = new ArrayList<Skill>();
        s.add(BasicSkills.TACKLE);
        s.add(BasicSkills.PUNCH);
        s.add(BasicSkills.KICK);
        assertTrue(player1.getSkillList().equals(s));

        player1.decreaseHp(player1.getSkillList().get(0).getDamage()); // Danni della mossa 0,TACKLE (5 danni)
        assertEquals(player1.getHp(), 995);
    }

    @org.junit.Test
    public void testBasicActions() {
        // Exp/level system
        Hero hero = new Hero.Builder().name("Mr LevelUp").role(Role.WARRIOR).build();
        assertEquals(1, hero.getLevel());
        hero.gainExp(150); //Levelup at 100
        assertEquals(2, hero.getLevel());
        hero.gainExp(600); //Levelup at 200, 300 and 400
        assertEquals(8, hero.getLevel());
        assertEquals(750, hero.getExp());

        //Skillcap
        Hero skillcaped = new Hero.Builder().name("n00b").role(Role.FMAGE).build();
        assertEquals(BasicEntity.MINLEVEL,skillcaped.getLevel());
        assertEquals(2, skillcaped.getAllowedSkillList().size());
        assertEquals(3, skillcaped.getSkillList().size());
    }

    @org.junit.Test
    public void testStage() {
        Hero hero = new Hero.Builder().name("StageClearer").role(Role.FMAGE).level(BasicEntity.MAXLEVEL).build();
        Stage currStage = StageData.getData().getNext();
        Random r = new Random(); //Random skill
        for(int i = 0; i < currStage.getEnemyList().size(); i++) {
            assertFalse(currStage.isCleared());
            //System.out.println(currStage.getEnemyList().get(1));  
            while (currStage.getEnemyList().get(i).getHp() > 0) {
                int index = r.nextInt(3);
                //System.out.println("Player uses " + hero.getAllowedSkillList().get(index).getName());
                currStage.getEnemyList().get(i).decreaseHp(hero.getAllowedSkillList().get(index).getDamage());
            }
        }
        assertTrue(currStage.isCleared());
        //System.out.println(currStage.getEnemyList().get(0));
        //System.out.println(hero);
    }

    @org.junit.Test
    public void testExceptions() {
        // level higher than MAXLEVEL
        try {
            new Hero.Builder().role(Role.FMAGE).name("Arthas").speed(5).level(BasicEntity.MAXLEVEL + 1).hp(10).build();
            fail();
        } catch (IllegalArgumentException e) {
        }

        // speed lower than 1
        try {
            new Hero.Builder().role(Role.FMAGE).name("Kael'thas").speed(-5).hp(10).build();
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
