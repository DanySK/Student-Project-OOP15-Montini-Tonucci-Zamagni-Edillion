package controller;

import model.entities.Entity;
import model.entities.Hero;

public interface GameEngine {

    /**
     * view tells controller an attacker wants to attack a target.
     * @param attacker
     * @param target
     * @param skillId
     */
    void attack(Entity attacker, Entity target, int skillId);

    /**
     * view tells controller which stage it has to load.
     * @param index
     */
    void loadStage(int index);

    /**
     * view gives to controller the new hero built (e.g. from a gui panel)
     * @param hero
     */
    void buildHero(Hero hero);
}
