package model.entities;

/**
 * Class that build monsters instances from templates.
 */
public class MonsterFactory {

    /**
     * 
     * @param enemy a monster template
     * @param suffix a random suffix
     * @return a new monster instance
     */
    public Entity createMonster(final MonsterTemplates enemy, final String suffix) {
        return new BasicEntity.Builder<>()
                              .name(enemy.getName() + suffix)
                              .hp(enemy.getHp())
                              .level(enemy.getLevel())
                              .speed(enemy.getSpeed())
                              .mana(enemy.getMana())
                              .manaRegen(enemy.getManaRegen())
                              .skillType(enemy.getAssign())
                              .build();
    }

    /**
     * 
     * @param enemy a monster template
     * @return parent method's monster
     */
    public Entity createMonster(final MonsterTemplates enemy) {
        return this.createMonster(enemy, "");
    }

}
