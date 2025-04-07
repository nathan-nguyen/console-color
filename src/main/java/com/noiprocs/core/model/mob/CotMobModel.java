package com.noiprocs.core.model.mob;

import com.noiprocs.core.GameContext;
import com.noiprocs.core.hitbox.HitboxManagerInterface;
import com.noiprocs.core.model.InteractiveInterface;
import com.noiprocs.core.model.Model;
import com.noiprocs.core.model.item.AppleItemModel;
import com.noiprocs.core.model.item.AxeItem;
import com.noiprocs.core.model.item.Item;

import java.util.List;
import java.util.Optional;

public class CotMobModel extends MobModel implements InteractiveInterface {
    private static final int MAX_HEALTH = 20;
    private static final int HORIZONTAL_SPEED = 1;
    private static final int VERTICAL_SPEED = 1;

    public CotMobModel(int x, int y) {
        super(x, y, true, MAX_HEALTH, HORIZONTAL_SPEED, VERTICAL_SPEED);
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
                this.setMovingDirection(
                        getFollowDirection(appleItemModel.posX, appleItemModel.posY)
                );
            }
        }
    }

    @Override
    public void interact(Model model, Item item) {
        if (item instanceof AxeItem) {
            this.updateHealth(-4);
        }
        else {
            this.updateHealth(-1);
        }
    }
}
