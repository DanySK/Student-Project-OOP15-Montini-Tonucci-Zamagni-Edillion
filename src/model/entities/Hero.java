package model.entities;

import java.util.Optional;
import java.util.Set;

import model.items.Durable;
import model.items.Usable;

/**
 * Main hero class.
 */
public class Hero extends BasicEntity {

    /**
     * Standard mana value (assigned if mana is not explicit on builder).
     */
    public static final int STANDARD_MANA = 50;

    private final Role role;
    private int exp;
    private int mana;
    private Inventory inventory;

    /**
     * Private construction, use the Builder class.
     * @param builder
     */
    private Hero(final Builder builder) {
        super(builder);
        this.role = builder.role;
        this.mana = builder.mana.get();
    }

    /**
     * @return Hero's role.
     */
    public Role getRole() {
        return role;
    }

    /**
     * @return Hero's exp
     */
    public int getExp() {
        return this.exp;
    }

    /**
     * 
     * @param reward exp gained from the stage
     * @return hero's level after reward computation
     */
    public int gainExp(final int reward) {
        this.exp = this.exp + reward;
        if (this.getLevel() < BasicEntity.MAX_LEVEL) {
            while (this.exp > this.expToLevelUp()) {
                this.levelUp();
            }
        }
        return this.getLevel();
    }

    /**
     * Algo that calculates the needed exp to level up.
     * 
     * @return exp needed to level up
     */
    public int expToLevelUp() {
        return this.getLevel() * 100;
    }

    /**
     * @return mana value for the hero.
     */
    public int getMana() {
        return this.mana;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        return sb.append(super.toString())
                 .append("\n\tExp: ").append(this.exp)
                 .append("\nRole: ").append(this.role)
                 .toString();
    }

    /**
     * Function that makes the hero gain a level
     */
    private void levelUp() {
        this.setLevel(this.getLevel() + 1);
    }

    /**
     * The only way to build a new Hero is by using this extended builder from BasicEntity.Builder.
     */
    public static class Builder extends BasicEntity.Builder<Builder> {

        private Role role;
        private Optional<Integer> mana = Optional.empty();

        /**
         * Adds a role to the builder instance.
         * @param role hero's role
         * @return builder instance
         */
        public Builder role(final Role role) {
            this.role = role;
            return this;
        }

        /**
         * Adds a mana value to the builder instance.
         * @param mana her's mana
         * @return builder instance
         */
        public Builder mana(final int mana) {
            this.mana = Optional.ofNullable(mana);
            return this;
        }

        @Override
        public Hero build() throws IllegalArgumentException {
            if (this.role == null) {
                throw new IllegalArgumentException("Insert a role");
            }
            if (!this.mana.isPresent()) {
                this.mana = Optional.of(Hero.STANDARD_MANA);
            }
            super.skillType(this.role.getSkillType());
            return new Hero(this);
        }

    }

    //TODO gestione dell'inventario
    private class Inventory {
        private Set<Usable> bag;
        private Set<Durable> equip;
    }

}
