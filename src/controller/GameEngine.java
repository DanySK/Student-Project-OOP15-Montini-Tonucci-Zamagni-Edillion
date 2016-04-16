package controller;

import model.entities.Hero;

public interface GameEngine {


    /**
     * view gives to controller the new hero built (e.g. from a gui panel)
     * @param hero
     */
    void buildHero(Hero hero);
}
