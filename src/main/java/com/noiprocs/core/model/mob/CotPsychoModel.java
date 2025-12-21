package com.noiprocs.core.model.mob;

import com.noiprocs.core.GameContext;
import com.noiprocs.core.common.Helper;
import com.noiprocs.core.common.Vector3D;

public class CotPsychoModel extends CotMobModel {
    public CotPsychoModel(Vector3D position) {
        super(position);
    }

    @Override
    public void update(int delta) {
        super.update(delta);
    }

    @Override
    protected void move(Vector3D deltaPosition) {
        if (GameContext.get().hitboxManager.isValid(this, this.position.add(deltaPosition))) {
            this.position.addInPlace(deltaPosition);
        } else {
            int nextDirection = Helper.random.nextInt(4);
            if (nextDirection == 0) {
                this.setMovingDirection(MovingDirection.UP);
            } else if (nextDirection == 1) {
                this.setMovingDirection(MovingDirection.DOWN);
            } else if (nextDirection == 2) {
                this.setMovingDirection(MovingDirection.LEFT);
            } else {
                this.setMovingDirection(MovingDirection.RIGHT);
            }
        }
    }
}
