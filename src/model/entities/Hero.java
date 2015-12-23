package model.entities;

public class Hero extends BasicEntity {

    private final Role role;
    private int exp;

    /**
     * Private construction, use the Builder class.
     * @param builder
     */
    private Hero(final Builder builder) {
        super(builder);
        this.setSkillList(builder.role.getSkillList()); 
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

    private void levelUp() {
        this.setLevel(this.getLevel() + 1);
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
     * The only way to build a new Hero is by using this extended builder from BasicEntity.Builder.
     */
    public static class Builder extends BasicEntity.Builder<Builder> {

        private Role role;

        public Builder role(final Role role) {
            this.role = role;
            return this;
        }

        @Override
        public Hero build() throws IllegalArgumentException {
            if (role == null) {
                throw new IllegalArgumentException("Insert a role");
            }
            return new Hero(this);
        }

    }

}
