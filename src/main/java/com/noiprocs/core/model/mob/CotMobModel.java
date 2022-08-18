package com.noiprocs.core.model.mob;

import com.noiprocs.core.util.Helper;

import java.util.Random;

public class CotMobModel extends MobModel {
    private static final int HORIZONTAL_SPEED = 1;
    private static final int VERTICAL_SPEED = 1;

    private final Random random = new Random();

    public CotMobModel(int x, int y, boolean isVisible) {
        super(x, y, isVisible, HORIZONTAL_SPEED, VERTICAL_SPEED);
        this.movingDirection = MovingDirection.LEFT;
    }

    @Override
    public void update(int delta) {
        super.update(delta);
    }

    @Override
    protected void move(int x, int y) {
        if (Helper.GAME_CONTEXT.hitboxManager.isValid(this, posX + x, posY + y)) {
            posX += x;
            posY += y;
        }
        else {
            int nextDirection = random.nextInt(4);
            if (nextDirection == 0) movingDirection = MovingDirection.UP;
            else if (nextDirection == 1) movingDirection = MovingDirection.DOWN;
            else if (nextDirection == 2) movingDirection = MovingDirection.LEFT;
            else movingDirection = MovingDirection.RIGHT;
        }
    }
}
