package com.noiprocs.core.model.item;

import com.noiprocs.core.model.Model;

public class WoodLogItem extends Item {
    private static final String WOOD_LOG_ITEM_NAME = "Wood Log";

    public WoodLogItem(int amount) {
        super(WOOD_LOG_ITEM_NAME, amount);
    }

    @Override
    public void use(Model moel) {}
}
