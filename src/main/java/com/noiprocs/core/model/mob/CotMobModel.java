package com.noiprocs.core.model.mob;

public class CotMobModel extends MobModel {
    private static final int HITBOX_HEIGHT = 1, HITBOX_WIDTH = 4;
    private static final int HORIZONTAL_SPEED = 1;
    private static final int VERTICAL_SPEED = 1;

    public CotMobModel(int x, int y) {
        super(x, y, true, HITBOX_HEIGHT, HITBOX_WIDTH, HORIZONTAL_SPEED, VERTICAL_SPEED);
        this.movingDirection = MovingDirection.LEFT;
    }

    @Override
    public void update(int delta) {
        super.update(delta);
    }
}
