package com.noiprocs.core.model.mob.character;

import com.noiprocs.core.model.item.Item;
import java.io.Serializable;

public class Inventory implements Serializable {

  public final Item[] items;

  public Inventory(int maxInventorySize) {
    this.items = new Item[maxInventorySize];
  }

  public Item getItem(int index) {
    return this.items[index];
  }

  public boolean addItem(Item item) {
    int emptySlot = Integer.MAX_VALUE;
    for (int i = 0; i < items.length; ++i) {
      Item existingItem = items[i];
      if (existingItem == null) emptySlot = Math.min(emptySlot, i);
      if (existingItem != null && existingItem.name.equals(item.name)) {
        existingItem.amount += item.amount;
        return true;
      }
    }
    if (emptySlot > items.length) return false;
    items[emptySlot] = item;
    return true;
  }
}
