package data;

import model.Entity;
import model.EntityImpl;

public class MonsterFactory {

    public Entity createMonster(BasicMonster enemy){
        return new EntityImpl(enemy.getType().getName(), enemy.getType().getHp(), enemy.getType().getSkillList());
    }
    
}
