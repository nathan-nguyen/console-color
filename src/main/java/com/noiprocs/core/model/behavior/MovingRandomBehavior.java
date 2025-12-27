package com.noiprocs.core.model.behavior;

import com.noiprocs.core.GameContext;
import com.noiprocs.core.common.Direction;
import com.noiprocs.core.common.Helper;
import com.noiprocs.core.common.Vector3D;
import com.noiprocs.core.model.mob.MobModel;

public class MovingRandomBehavior extends MovingBehavior {
  @Override
  protected void move(MobModel mobModel, Vector3D deltaPosition) {
    Vector3D nextPosition = mobModel.getPosition().add(deltaPosition);
    if (GameContext.get().hitboxManager.isValid(mobModel, nextPosition)) {
      mobModel.setPosition(nextPosition);
    } else {
      int nextDirection = Helper.random.nextInt(4);
      if (nextDirection == 0) {
        mobModel.setMovingDirection(Direction.NORTH);
      } else if (nextDirection == 1) {
        mobModel.setMovingDirection(Direction.SOUTH);
      } else if (nextDirection == 2) {
        mobModel.setMovingDirection(Direction.WEST);
      } else {
        mobModel.setMovingDirection(Direction.EAST);
      }
    }
  }
}
