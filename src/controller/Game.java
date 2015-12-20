package controller;

import model.*;

public class Game implements GameEngine{
    public Game() {}
    
    @Override
    public void attack(Entity attacker, Entity target, int skillId) {
        target.decreaseHp(attacker.getSkill(skillId).getDamage());
        System.out.println(attacker.getName() + " attacks " + target.getName() + " with " + attacker.getSkill(skillId).getName()); //Da togliere, solo per test
    }
    
}