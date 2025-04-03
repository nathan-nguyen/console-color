package com.noiprocs.core.model.item;

import com.noiprocs.core.model.Model;

public class AxeItem extends Item {
    private static final String ITEM_NAME = "Axe";

    public AxeItem(int amount) {
        super(ITEM_NAME, amount);
    }

    @Override
    public void use(Model model) {}
}
