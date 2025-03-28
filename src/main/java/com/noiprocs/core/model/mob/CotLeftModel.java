package com.noiprocs.core.model.mob;

import com.noiprocs.core.GameContext;

public class CotLeftModel extends CotMobModel {

    public CotLeftModel(int x, int y) {
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
            if (movingDirection == MovingDirection.UP) movingDirection = MovingDirection.LEFT;
            else if (movingDirection == MovingDirection.LEFT) movingDirection = MovingDirection.DOWN;
            else if (movingDirection == MovingDirection.DOWN) movingDirection = MovingDirection.RIGHT;
            else movingDirection = MovingDirection.UP;
        }
    }
}
