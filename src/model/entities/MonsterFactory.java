package model.entities;

/**
 * Class that build monsters instances from templates.
 */
public class MonsterFactory {

    /**
     * 
     * @param enemy a monster template
     * @return a new monster instance
     */
    public Entity createMonster(final MonsterTemplates enemy){
        return new BasicEntity.Builder<>()
                              .name(enemy.getName())
                              .hp(enemy.getHp())
                              .level(enemy.getLevel())
                              .speed(enemy.getSpeed())
                              .mana(enemy.getMana())
                              .manaRegen(enemy.getManaRegen())
                              .skillType(enemy.getAssign())
                              .build();
    }

}
