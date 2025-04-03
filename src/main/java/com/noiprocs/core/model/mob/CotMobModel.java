package com.noiprocs.core.model.mob;

import com.noiprocs.core.GameContext;
import com.noiprocs.core.hitbox.HitboxManagerInterface;
import com.noiprocs.core.model.Model;
import com.noiprocs.core.model.item.AppleItemModel;

import java.util.List;
import java.util.Optional;

public class CotMobModel extends MobModel {
    private static final int HORIZONTAL_SPEED = 1;
    private static final int VERTICAL_SPEED = 1;

    public CotMobModel(int x, int y) {
        super(x, y, true, HORIZONTAL_SPEED, VERTICAL_SPEED);
        this.setMovingDirection(MovingDirection.LEFT);
    }

    @Override
    public void update(int delta) {
        super.update(delta);
    }

    @Override
    protected void move() {
        super.move();

        HitboxManagerInterface hitboxManager = GameContext.get().hitboxManager;
        // Get list of models in circle radius 20, find nearest AppleItemModel
        List<Model> surroundedModels = hitboxManager.getCollidingModel(this, 0, 0, -10, -12, 20, 20);
        Optional<AppleItemModel> nearestAppleItemModelOption = surroundedModels.stream()
                .filter(model -> model instanceof AppleItemModel)
                .min(
                        (u, v) -> Integer.compare(u.distanceTo(posX, posY), v.distanceTo(posX, posY))
                )
                .map(model -> (AppleItemModel) model);

        if (nearestAppleItemModelOption.isPresent()) {
            AppleItemModel appleItemModel = nearestAppleItemModelOption.get();
            if (hitboxManager.isColliding(this, appleItemModel)) {
                appleItemModel.interact(this, null);
            }
            else {
                if (posX < appleItemModel.posX) this.setMovingDirection(MovingDirection.DOWN);
                else if (posX > appleItemModel.posX) this.setMovingDirection(MovingDirection.UP);
                else if (posY > appleItemModel.posY) this.setMovingDirection(MovingDirection.LEFT);
                else this.setMovingDirection(MovingDirection.RIGHT);
            }
        }
    }
}
