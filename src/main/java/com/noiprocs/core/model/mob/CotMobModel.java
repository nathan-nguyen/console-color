package com.noiprocs.core.model.mob;

import com.noiprocs.core.GameContext;
import com.noiprocs.core.common.Vector3D;
import com.noiprocs.core.hitbox.HitboxManagerInterface;
import com.noiprocs.core.model.InteractiveInterface;
import com.noiprocs.core.model.Model;
import com.noiprocs.core.model.item.AppleItemModel;
import com.noiprocs.core.model.item.AxeItem;
import com.noiprocs.core.model.item.Item;

import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CotMobModel extends MobModel implements InteractiveInterface {
    private static final Logger logger = LogManager.getLogger(CotMobModel.class);
    private static final int MAX_HEALTH = 20;
    private static final int HORIZONTAL_SPEED = 1;
    private static final int VERTICAL_SPEED = 1;

    public CotMobModel(Vector3D position) {
        super(position, true, MAX_HEALTH, HORIZONTAL_SPEED, VERTICAL_SPEED);
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
        Vector3D distance = new Vector3D(-10, -12, 0);
        Vector3D hitboxDimension = new Vector3D(20, 20, 0);
        // Get list of models in circle radius 20, find nearest AppleItemModel
        List<Model> surroundedModels = hitboxManager.getCollidingModel(this, Vector3D.ZERO, distance, hitboxDimension);
        Optional<AppleItemModel> nearestAppleItemModelOption = surroundedModels.stream()
                .filter(model -> model instanceof AppleItemModel)
                .min((u, v) -> Integer.compare(u.position.distanceTo(this.position),
                        v.position.distanceTo(this.position)))
                .map(model -> (AppleItemModel) model);

        if (nearestAppleItemModelOption.isPresent()) {
            AppleItemModel appleItemModel = nearestAppleItemModelOption.get();
            if (hitboxManager.isColliding(this, appleItemModel)) {
                appleItemModel.interact(this, null);
            } else {
                this.setMovingDirection(getFollowDirection(appleItemModel.position));
            }
        }
    }

    @Override
    public void interact(Model model, Item item) {
        if (item instanceof AxeItem) {
            this.updateHealth(-4);
        } else {
            this.updateHealth(-1);
        }
    }
}
