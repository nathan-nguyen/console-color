package com.noiprocs.core.model.item;

import com.noiprocs.core.model.Model;

public class AxeItem extends Item {
    private static final String ITEM_NAME = "Axe";

    public AxeItem() {
        super(ITEM_NAME, 1);
    }

    @Override
    public void use(Model model) {
    }
}
