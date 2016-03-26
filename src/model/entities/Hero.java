package model.entities;

import java.util.List;

import model.items.Durable;
import model.items.Usable;

public class Hero extends BasicEntity {
    
    public final static int STANDARD_MANA = 50;

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
    }

    public Role getRole() {
        return role;
    }

    public int getExp() {
        return this.exp;
    }

    public int gainExp(int reward) {
        this.exp = this.exp + reward;
        if (this.getLevel() < BasicEntity.MAXLEVEL) {
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
    
    private void levelUp() {
        this.setLevel(this.getLevel() + 1);
    }
    
    /**
     * The only way to build a new Hero is by using this extended builder from BasicEntity.Builder.
     */
    public static class Builder extends BasicEntity.Builder<Builder> {

        private Role role;
        private int mana;

        public Builder role(final Role role) {
            this.role = role;
            return this;
        }
        
        
        public Builder mana(final int mana) {
            this.mana = mana;
            return this;
        }

        //TODO Risistemare con optional
        @Override
        public Hero build() throws IllegalArgumentException {
            if (role == null) {
                throw new IllegalArgumentException("Insert a role");
            }
            if (mana==0) {
                this.mana = Hero.STANDARD_MANA;              
            }
            super.skillType(this.role.getSkillType());
            return new Hero(this);
        }

    }
    
    //TODO gestione dell'inventario
    private class Inventory {
        private List<Usable> bag;
        private List<Durable> equip;
    }

}
