package com.noiprocs.core.model.mob;

public class CotMobModel extends MobModel {
    private static final int HORIZONTAL_SPEED = 1;
    private static final int VERTICAL_SPEED = 1;

    public CotMobModel(int x, int y, boolean isVisible) {
        super(x, y, isVisible, HORIZONTAL_SPEED, VERTICAL_SPEED);
        this.movingDirection = MovingDirection.LEFT;
    }

    @Override
    public void update(int delta) {
        super.update(delta);
    }
}
