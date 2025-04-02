package com.noiprocs.core.model.item;

import com.noiprocs.core.model.Model;

import java.io.Serializable;

public abstract class Item implements Serializable {
    public final String name;
    public int amount;

    public Item(String name, int amount) {
        this.name = name;
        this.amount = amount;
    }

    public abstract void use(Model model);

    @Override
    public String toString() {
        return name;
    }
}
