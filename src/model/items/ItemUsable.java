package model.items;


public enum ItemUsable implements Usable  {
    //               name        price    effectiv     stat           target
    CURE(           "Cure",         50,     300, StatType.HP, ItemType.PERSONAL),
    TOT_CURE(   "Total Cure",     1000,   20000, StatType.HP, ItemType.PERSONAL),
    MANA(       "Mana Potion",      80,     800, StatType.MANA, ItemType.PERSONAL),
    SWIFTNESS( "Speed Potion",     100,       5, StatType.SPEED, ItemType.PERSONAL),
    BOMB(              "Bomb",     400,      20, StatType.HP, ItemType.IMPERSONAL),
    POWER_BOMB(  "Power Bomb",     800,      15, StatType.HP, ItemType.COLLECTIVE);

    private final String name;
    private final int price;
    private final int effectiveness;
    private final StatType stat;
    private final ItemType target;

    /**
     * 
     * @param name
     * @param price
     * @param effectiveness
     * @param stat
     * @param target
     */
    ItemUsable(final String name, final int price, final int effectiveness, final StatType stat, final ItemType target) {
        this.name = name;
        this.price = price;
        this.effectiveness = effectiveness;
        this.stat = stat;
        this.target = target;
    }
    
    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public int getPrice() {
        return this.price;
    }

    @Override
    public int getEffectiveness() {
        return this.effectiveness;
    }

    @Override
    public StatType getStatTypeInfluence() {
        return this.stat;
    }

    @Override
    public ItemType getItemType() {
        return this.target;
    }
    
    public enum ItemType {
        PERSONAL, COLLECTIVE, IMPERSONAL;
    }
}
