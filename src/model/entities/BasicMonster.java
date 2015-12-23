package model.entities;

import model.skills.*;

public enum BasicMonster {
    GOBLIN(new BasicEntity.Builder<>()
                                .name("Gobelino")
                                .hp(25)
                                .speed(3)
                                .level(1)
                                .skillArr(BasicSkills.values())
                                .build()), 

    UOMOGATTO(new BasicEntity.Builder<>()
                                .name("UomoGatto")
                                .hp(25)
                                .speed(3)
                                .level(3)
                                .skillArr(BasicSkills.values())
                                .build()),

    FIREMAGE(new BasicEntity.Builder<>()
                                .name("Fire Mage")
                                .hp(80)
                                .speed(4)
                                .level(5)
                                .skillArr(FMageSkills.values())
                                .build());

    private Entity type;

    private BasicMonster(Entity type) {
        this.type = type;
    }

    public Entity get() {
        return this.type;
    }
}