package com.noiprocs.core.model.item;

import com.noiprocs.core.GameContext;
import com.noiprocs.core.model.Model;
import com.noiprocs.core.model.building.FenceModel;

public class FenceItem extends Item {
    private static final String FENCE_ITEM_NAME = "Fence";

    public FenceItem(int amount) {
        super(FENCE_ITEM_NAME, amount);
    }

    @Override
    public void use(Model model) {
        System.out.println("Use " + this);
        GameContext.get().modelManager.addSpawnModel(new FenceModel(model.posX, model.posY));
        --amount;
    }
}
