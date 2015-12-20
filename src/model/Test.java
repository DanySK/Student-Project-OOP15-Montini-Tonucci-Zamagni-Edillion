package model;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import model.skills.BasicSkills;
import model.skills.Skill;

public class Test {

    @org.junit.Test
    public void test() {
        //Nuovo giocatore
        Entity player1 = new EntityImpl("Mario", 50, BasicSkills.values());
        System.out.println(player1.getSkillList());

        assertEquals(player1.getHp(),50);

        List<Skill> s = new ArrayList<Skill>();
        s.add(BasicSkills.TACKLE);
        s.add(BasicSkills.PUNCH);

        assertTrue(player1.getSkillList().equals(s));
        
        player1.decreaseHp(player1.getSkillList().get(0).getDamage()); //Danni della mossa 0, TACKLE (5 danni)
        
        assertEquals(player1.getHp(), 45);
       
    }

}
