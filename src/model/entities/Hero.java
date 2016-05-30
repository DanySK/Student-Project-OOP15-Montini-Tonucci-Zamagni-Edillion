package model.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import model.items.Durable;
import model.items.Usable;

/**
 * Main hero class.
 */
public class Hero extends BasicEntity implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 5584220020125287376L;
    private static final int HP_INCREASE_FACTOR = 15;
    private static final int MANA_INCREASE_FACTOR = 4;
    private static final int MANAREGEN_INCREASE_FACTOR = 1;

    private final Role role;

    private Inventory inventory;

    /**
     * Private construction, use the Builder class.
     * @param builder
     */
    private Hero(final Builder builder) {
        super(builder);
        super.globalStats.put(StatType.EXP, 0);
        super.globalStats.put(StatType.GOLD, 0);
        super.copyStats();
        this.role = builder.role;
        this.inventory = new Inventory();
    }

    /**
     * @return Hero's role.
     */
    public Role getRole() {
        return role;
    }

    /**
     * @return hero's inventory
     */
    public Inventory getInventory() {
        return this.inventory;
    }

    /**
     * 
     * @param reward exp gained from the stage
     * @return hero's level after reward computation
     */
    public int gainExp(final int reward) {
        this.setStat(StatType.EXP, reward, StatTime.GLOBAL, ActionType.INCREASE);
        if (this.getStat(StatType.LEVEL, StatTime.GLOBAL) < StatType.LEVEL.getMaxValue()) {
            while (this.getStat(StatType.EXP, StatTime.GLOBAL) > this.expToLevelUp()) {
                this.levelUp();
            }
        }
        return this.getStat(StatType.LEVEL, StatTime.GLOBAL);
    }

    /**
     * Algo that calculates the needed exp to level up.
     * 
     * @return exp needed to level up
     */
    public int expToLevelUp() {
        return this.getStat(StatType.LEVEL, StatTime.GLOBAL) * 100;
    }

    /**
     * Function that makes the hero gain a level
     */
    private void levelUp() {
        this.setStat(StatType.EXP, this.expToLevelUp(), StatTime.GLOBAL, ActionType.DECREASE);
        int newLevel = this.setStat(StatType.LEVEL, 1, StatTime.GLOBAL, ActionType.INCREASE);
        this.setStat(StatType.HP, newLevel * Hero.HP_INCREASE_FACTOR, StatTime.GLOBAL, ActionType.INCREASE);
        this.setStat(StatType.MANA, newLevel * Hero.MANA_INCREASE_FACTOR, StatTime.GLOBAL, ActionType.INCREASE);
        this.setStat(StatType.MANAREGEN, newLevel * Hero.MANAREGEN_INCREASE_FACTOR, StatTime.GLOBAL, ActionType.INCREASE);
    }
    
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        return sb.append(super.toString())
                 .append("\n\tExp: ").append(this.getStat(StatType.EXP, StatTime.GLOBAL))
                 .append("\n\tGold: ").append(this.getStat(StatType.GOLD, StatTime.GLOBAL))
                 .append("\nRole: ").append(this.role)
                 .toString();
    }

    /**
     * The only way to build a new Hero is by using this extended builder from BasicEntity.Builder.
     */
    public static class Builder extends BasicEntity.Builder<Builder> {

        private Role role;

        /**
         * Adds a role to the builder instance.
         * @param role hero's role
         * @return builder instance
         */
        public Builder role(final Role role) {
            this.role = role;
            return this;
        }



        @Override
        public Hero build() throws IllegalArgumentException {
            if (this.role == null) {
                throw new IllegalArgumentException("Insert a role");
            }
            super.skillType(this.role.getSkillType());
            return new Hero(this);
        }

    }


    public class Inventory implements Serializable {
        /**
         * 
         */
        private static final long serialVersionUID = 1L;
        private List<Usable> bag = new ArrayList<>();
        private Set<Durable> equip = new HashSet<>();
        
        
        public List<Usable> getBag() {
            return bag;
        }
        
        public Set<Durable> getEquip() {
            return equip;
        }
    }

}
