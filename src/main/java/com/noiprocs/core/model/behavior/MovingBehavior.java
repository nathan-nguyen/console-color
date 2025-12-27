package com.noiprocs.core.model.behavior;

import com.noiprocs.core.GameContext;
import com.noiprocs.core.common.Direction;
import com.noiprocs.core.common.Vector3D;
import com.noiprocs.core.model.Model;
import com.noiprocs.core.model.mob.MobModel;

public class MovingBehavior implements BehaviorInterface {
  @Override
  public void update(Model model) {
    MobModel mobModel = (MobModel) model;

    Vector3D movingDirection = mobModel.getMovingDirection();
    Vector3D speed = mobModel.getSpeed();
    if (movingDirection.equals(Direction.NORTH) || movingDirection.equals(Direction.SOUTH)) {
      for (int i = 0; i < Math.abs(speed.x); ++i) {
        this.move(mobModel, movingDirection);
      }
    } else if (movingDirection.equals(Direction.WEST) || movingDirection.equals(Direction.EAST)) {
      for (int i = 0; i < Math.abs(speed.y); ++i) {
        this.move(mobModel, movingDirection);
      }
    }
  }

  /**
   * Default logic for Mob movement. Before updating position, check whether next position is valid.
   * If next position is not valid, stop.
   *
   * @param x: Distance to move in horizontal direction.
   * @param y: Distance to move in vertical direction.
   */
  protected void move(MobModel mobModel, Vector3D deltaPosition) {
    if (mobModel.getMovingDirection().equals(Vector3D.ZERO)) {
      return;
    }

    Vector3D nextPosition = mobModel.getPosition().add(deltaPosition);
    // We could consider using Swept AABB here, however if the speed is low, using AABB requires
    // more steps which could lead to lower performance.
    if (this.isNextMoveValid(mobModel, nextPosition)) {
      mobModel.setPosition(nextPosition);
    } else {
      mobModel.setMovingDirection(Vector3D.ZERO);
    }
  }

  protected boolean isNextMoveValid(MobModel mobModel, Vector3D nextPosition) {
    return GameContext.get().hitboxManager.isValid(mobModel, nextPosition);
  }
}
