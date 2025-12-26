package com.noiprocs.core.model.item;

import com.noiprocs.core.GameContext;
import com.noiprocs.core.common.Vector3D;
import com.noiprocs.core.model.Model;
import com.noiprocs.core.model.mob.MobModel;
import com.noiprocs.core.model.mob.projectile.FlyingWoodLogModel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class WoodLogItem extends Item {
    private static final Logger logger = LogManager.getLogger(WoodLogItem.class);
    private static final String ITEM_NAME = "Wood Log";

    public WoodLogItem(int amount) {
        super(ITEM_NAME, amount);
    }

    @Override
    public void use(Model model) {
        logger.info("{} used {}", model, this);

        // Determine moving direction of the projectile
        Vector3D movingDirection = ((MobModel) model).getMovingDirection();
        if (movingDirection == Vector3D.ZERO) {
            movingDirection = ((MobModel) model).getFacingDirection();
        }

        Vector3D spawnerPoint = GameContext.get().hitboxManager.getSpawnPoint(model, Vector3D.ZERO);
        GameContext.get().modelManager.addSpawnModel(
                new FlyingWoodLogModel(
                        model.position.add(spawnerPoint),
                        movingDirection, model));
        --amount;
    }
}
