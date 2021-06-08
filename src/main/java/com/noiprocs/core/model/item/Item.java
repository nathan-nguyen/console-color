package com.noiprocs.core.model.item;

import java.io.Serializable;

public class Item implements Serializable {
    public final String name;
    public int amount;

    public Item(String name, int amount) {
        this.name = name;
        this.amount = amount;
    }
}
