package controller;

import model.Entity;

public interface GameEngine {

    public void attack(Entity attacker, Entity target, int skillId);
    
}
