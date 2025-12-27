package com.noiprocs.core.model.mob;

import com.noiprocs.core.GameContext;
import com.noiprocs.core.common.Direction;
import com.noiprocs.core.common.Vector3D;
import com.noiprocs.core.model.DurableModel;
import com.noiprocs.core.model.action.Action;
import com.noiprocs.core.model.behavior.BehaviorInterface;
import java.util.ArrayList;
import java.util.List;

public abstract class MobModel extends DurableModel {
  private static final int DEFAULT_SKIP_MOVEMENT_FRAME = 2;

  private Vector3D movingDirection = Vector3D.ZERO;
  protected Vector3D facingDirection = Direction.EAST;
  protected int skipMovementFrame = DEFAULT_SKIP_MOVEMENT_FRAME;
  private Vector3D speed;
  private List<BehaviorInterface> behaviors = new ArrayList<>();
  public Action action = null;

  public MobModel(Vector3D position, boolean isVisible, int health, Vector3D speed) {
    super(position, isVisible, health);
    this.speed = speed;
  }

  @Override
  public void update(int delta) {
    if (GameContext.get().worldCounter % skipMovementFrame == 0) {
      for (BehaviorInterface behavior : behaviors) {
        behavior.update(this);
      }
    }
    if (action != null) {
      action.update(this);
      if (action.isCompleted()) {
        action = null;
      }
    }
  }

  public Vector3D getMovingDirection() {
    return this.movingDirection;
  }

  public void setMovingDirection(Vector3D movingDirection) {
    this.movingDirection = movingDirection;
    if (movingDirection.equals(Direction.WEST) || movingDirection.equals(Direction.EAST)) {
      this.facingDirection = movingDirection;
    }
  }

  public Vector3D getFacingDirection() {
    return this.facingDirection;
  }

  public Vector3D getSpeed() {
    return this.speed;
  }

  protected void addBehavior(BehaviorInterface... behaviors) {
    this.behaviors.addAll(List.of(behaviors));
  }
}
