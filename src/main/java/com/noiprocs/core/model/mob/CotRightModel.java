package com.noiprocs.core.model.mob;

import com.noiprocs.core.GameContext;

public class CotRightModel extends CotMobModel {

    public CotRightModel(int x, int y) {
        super(x, y);
    }

    @Override
    public void update(int delta) {
        super.update(delta);
    }

    @Override
    protected void move(int x, int y) {
        if (GameContext.get().hitboxManager.isValid(this, posX + x, posY + y)) {
            posX += x;
            posY += y;
        }
        else {
            if (movingDirection == MovingDirection.UP) movingDirection = MovingDirection.RIGHT;
            else if (movingDirection == MovingDirection.RIGHT) movingDirection = MovingDirection.DOWN;
            else if (movingDirection == MovingDirection.DOWN) movingDirection = MovingDirection.LEFT;
            else movingDirection = MovingDirection.UP;
        }
    }
}
