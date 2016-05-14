package model.items;

import model.items.ItemUsable.ItemType;

/**
 * Interface for potion.
 */
public interface Usable extends Item {
    
    ItemType getItemType();
}
