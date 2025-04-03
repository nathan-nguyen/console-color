package com.noiprocs.core.model.item;

import com.noiprocs.core.GameContext;
import com.noiprocs.core.model.Model;
import com.noiprocs.core.model.mob.MobModel;
import com.noiprocs.core.model.mob.projectile.FlyingWoodLogModel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class WoodLogItem extends Item {
    private static final Logger logger = LogManager.getLogger(WoodLogItem.class);

    private static final String WOOD_LOG_ITEM_NAME = "Wood Log";

    public WoodLogItem(int amount) {
        super(WOOD_LOG_ITEM_NAME, amount);
    }

    @Override
    public void use(Model model) {
        logger.info("{} used {}", model, this);
        MobModel.MovingDirection movingDirection = ((MobModel) model).getFacingDirection();
        int[] spawnerPoint = GameContext.get().hitboxManager.getSpawnPoint(model, 0, 0);
        GameContext.get().modelManager.addSpawnModel(
                new FlyingWoodLogModel(
                        model.posX + spawnerPoint[0], model.posY + spawnerPoint[1],
                        movingDirection, model
                )
        );
        --amount;
    }
}
