package com.noiprocs.core.model.mob.character;

import com.noiprocs.core.common.Direction;
import com.noiprocs.core.common.Vector3D;
import com.noiprocs.core.model.LowLatencyModelInterface;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PlayerModel extends Humanoid implements LowLatencyModelInterface {
  private static final Logger logger = LogManager.getLogger(PlayerModel.class);

  public PlayerModel(String id, Vector3D position, boolean isVisible) {
    super(position, isVisible);
    this.id = id;
  }

  public void moveUp() {
    this.setMovingDirection(Direction.NORTH);
  }

  public void moveDown() {
    this.setMovingDirection(Direction.SOUTH);
  }

  public void moveLeft() {
    this.setMovingDirection(Direction.WEST);
  }

  public void moveRight() {
    this.setMovingDirection(Direction.EAST);
  }

  public void stop() {
    this.setMovingDirection(Vector3D.ZERO);
  }

  @Override
  public String toString() {
    return "Player: (" + id + ", " + position + ")";
  }
}
