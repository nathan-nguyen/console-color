package com.noiprocs.core.model.item;

import com.noiprocs.core.model.Model;
import com.noiprocs.core.model.plant.TreeModel;
import com.noiprocs.core.util.Helper;

public class SaplingItem extends Item {
    private static final String SAPLING_ITEM_NAME = "Sapling";

    public SaplingItem(int amount) {
        super(SAPLING_ITEM_NAME, amount);
    }

    @Override
    public void use(Model model) {
        System.out.println("Use " + this);
        Helper.GAME_CONTEXT.modelManager.addSpawnModel(new TreeModel(model.posX, model.posY));
        --amount;
    }
}
