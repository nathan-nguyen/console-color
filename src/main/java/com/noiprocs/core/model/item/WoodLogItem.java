package com.noiprocs.core.model.item;

import com.noiprocs.core.GameContext;
import com.noiprocs.core.model.Model;
import com.noiprocs.core.model.mob.MobModel;
import com.noiprocs.core.model.mob.projectile.FlyingWoodLogModel;

public class WoodLogItem extends Item {
    private static final String WOOD_LOG_ITEM_NAME = "Wood Log";

    public WoodLogItem(int amount) {
        super(WOOD_LOG_ITEM_NAME, amount);
    }

    @Override
    public void use(Model model) {
        System.out.println("Use " + this);
        MobModel.MovingDirection movingDirection = ((MobModel) model).getFacingDirection();
        GameContext.get().modelManager.addSpawnModel(new FlyingWoodLogModel(model.posX - 2, model.posY + 1, movingDirection, model));
        --amount;
    }
}
