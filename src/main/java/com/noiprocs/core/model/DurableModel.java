package com.noiprocs.core.model;

import com.noiprocs.core.common.Vector3D;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class DurableModel extends Model {
  private static final Logger logger = LogManager.getLogger(DurableModel.class);
  private int health;

  public DurableModel(Vector3D position, boolean isVisible, int health) {
    super(position, isVisible);
    this.health = health;
  }

  public void updateHealth(int amount) {
    health += amount;
    if (health <= 0) {
      logger.info("{} is killed!", this);
      this.destroy();
    }
  }

  public int getHealth() {
    return this.health;
  }
}
