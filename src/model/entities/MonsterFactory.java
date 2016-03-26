package model.entities;

public class MonsterFactory {

    public Entity createMonster(final MonsterTemplates enemy){
        return new BasicEntity.Builder<>()
                              .name(enemy.getName())
                              .hp(enemy.getHp())
                              .level(enemy.getLevel())
                              .speed(enemy.getSpeed())
                              .skillType(enemy.getAssign())
                              .build();
    }

}
