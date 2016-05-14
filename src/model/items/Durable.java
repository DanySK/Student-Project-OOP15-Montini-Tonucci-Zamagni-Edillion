package model.items;

import model.items.ItemDurable.EquipType;

/**
 * Interface for equipment.
 */
public interface Durable extends Item {

    EquipType getEquipType();
}
