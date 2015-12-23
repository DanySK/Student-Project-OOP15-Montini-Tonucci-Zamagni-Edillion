package model.entities;

public class MonsterFactory {

    public Entity createMonster(final BasicMonster enemy){
        return new BasicEntity.Builder<>()
                              .name(enemy.get().getName())
                              .hp(enemy.get().getHp())
                              .level(enemy.get().getLevel())
                              .speed(enemy.get().getSpeed())
                              .skillList(enemy.get().getSkillList())
                              .build();
    }

}
