package com.noiprocs.core.model.item;

import com.noiprocs.core.model.Model;
import com.noiprocs.core.model.building.FenceModel;
import com.noiprocs.core.util.Helper;

public class FenceItem extends Item {
    private static final String FENCE_ITEM_NAME = "Fence";

    public FenceItem(int amount) {
        super(FENCE_ITEM_NAME, amount);
    }

    @Override
    public void use(Model model) {
        System.out.println("Use " + this);
        Helper.GAME_CONTEXT.modelManager.addSpawnModel(
                new FenceModel(model.posX, model.posY, true)
        );
        --amount;
    }
}
