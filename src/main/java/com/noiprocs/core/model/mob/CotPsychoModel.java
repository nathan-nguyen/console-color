package com.noiprocs.core.model.mob;

import com.noiprocs.core.GameContext;
import com.noiprocs.core.common.Direction;
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
    Vector3D nextPosition = this.position.add(deltaPosition);
    if (GameContext.get().hitboxManager.isValid(this, nextPosition)) {
      this.setPosition(nextPosition);
    } else {
      int nextDirection = Helper.random.nextInt(4);
      if (nextDirection == 0) {
        this.setMovingDirection(Direction.NORTH);
      } else if (nextDirection == 1) {
        this.setMovingDirection(Direction.SOUTH);
      } else if (nextDirection == 2) {
        this.setMovingDirection(Direction.WEST);
      } else {
        this.setMovingDirection(Direction.EAST);
      }
    }
  }
}
