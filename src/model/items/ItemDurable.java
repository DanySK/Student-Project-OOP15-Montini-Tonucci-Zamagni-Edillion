package model.items;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import model.entities.StatType;


public enum ItemDurable implements Durable  {
    //               name                 price    effectiv     stat             equipType
    HELMET_ANUBI(  "Anubi's Helmet",        500,     500, StatType.MANA, EquipType.HELMET),
    ARMOR_BASE(    "Standard Armor",        100,      50, StatType.HP, EquipType.ARMOR),
    GREAVES_BASE("Standard Greaves",        200,       5, StatType.SPEED, EquipType.GREAVES),
    AEMLET_BASE(  "Standard Armlet",        100,      30, StatType.HP, EquipType.ARMLET),
    ARMOR_DRAKE(    "Drake's Armor",       5000,    1000, StatType.HP,  EquipType.ARMOR),
    GREAVES_LIGHTNING("Lightning Greaves", 1300,      20, StatType.SPEED, EquipType.GREAVES);

    private final String name;
    private final int price;
    private final int effectiveness;
    private final StatType stat;
    private final EquipType equip;

    /**
     * 
     * @param name
     * @param price
     * @param effectiveness
     * @param stat
     * @param equip
     */
    ItemDurable(final String name, final int price, final int effectiveness, final StatType stat, final EquipType equip) {
        this.name = name;
        this.price = price;
        this.effectiveness = effectiveness;
        this.stat = stat;
        this.equip = equip;
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
    public EquipType getEquipType() {
        return this.equip;
    }
    

    public enum EquipType {
        HELMET, ARMOR, GREAVES, ARMLET;
    }
    
    public static List<Durable> getItemUsableList() {
        List<Durable> list = new ArrayList<>();

        list.addAll(Arrays.asList(ItemDurable.values()).stream().collect(Collectors.toList()));
        
        return list;
    }

}
