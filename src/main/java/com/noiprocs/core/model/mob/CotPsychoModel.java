package com.noiprocs.core.model.mob;

import com.noiprocs.core.util.Helper;

import java.util.Random;

public class CotPsychoModel extends CotMobModel {
    private final Random random = new Random();

    public CotPsychoModel(int x, int y) {
        super(x, y);
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
