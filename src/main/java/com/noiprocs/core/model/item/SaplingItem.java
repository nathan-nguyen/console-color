package com.noiprocs.core.model.item;

import com.noiprocs.core.GameContext;
import com.noiprocs.core.model.Model;
import com.noiprocs.core.model.plant.TreeModel;

public class SaplingItem extends Item {
    private static final String SAPLING_ITEM_NAME = "Sapling";

    public SaplingItem(int amount) {
        super(SAPLING_ITEM_NAME, amount);
    }

    @Override
    public void use(Model model) {
        System.out.println("Use " + this);
        GameContext.get().modelManager.addSpawnModel(new TreeModel(model.posX, model.posY, 0));
        --amount;
    }
}
