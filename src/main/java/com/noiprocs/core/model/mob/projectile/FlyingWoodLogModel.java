package com.noiprocs.core.model.mob.projectile;

import com.noiprocs.core.GameContext;
import com.noiprocs.core.model.InteractiveInterface;
import com.noiprocs.core.model.Model;

import java.util.List;

public class FlyingWoodLogModel extends ProjectileModel {
    private static final int DEFAULT_SKIP_MOVEMENT_FRAME = 3;
    private static final int DEFAULT_TTL = 15;
    private static final int HORIZONTAL_SPEED = 2, VERTICAL_SPEED = 1;

    public FlyingWoodLogModel(int x, int y, MovingDirection movingDirection, Model spawner) {
        super(x, y, HORIZONTAL_SPEED, VERTICAL_SPEED, movingDirection, DEFAULT_TTL, spawner);
        this.skipMovementFrame = DEFAULT_SKIP_MOVEMENT_FRAME;
    }

    @Override
    protected boolean isNextMoveValid(int x, int y) {
        List<Model> collidingModels = GameContext.get().hitboxManager.getCollidingModel(this, x, y);
        if (collidingModels.isEmpty()) return true;

        Model model = collidingModels.get(0);
        System.out.println("Hit " + model.toString());
        if (model instanceof InteractiveInterface) {
            ((InteractiveInterface) model).interact(this);
        }
        this.destroy();
        return false;
    }
}
