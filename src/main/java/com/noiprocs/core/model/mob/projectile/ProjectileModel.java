package com.noiprocs.core.model.mob.projectile;

import com.noiprocs.core.common.Vector3D;
import com.noiprocs.core.model.LowLatencyModelInterface;
import com.noiprocs.core.model.Model;
import com.noiprocs.core.model.behavior.ProjectileMovingBehavior;
import com.noiprocs.core.model.mob.MobModel;

public class ProjectileModel extends MobModel implements LowLatencyModelInterface {
  private static final int MAX_HEALTH = 1;
  private int ttl;
  private final Model spawner;

  public ProjectileModel(
      Vector3D position, Vector3D speed, Vector3D movingDirection, int ttl, Model spawner) {
    super(position, true, MAX_HEALTH, speed);
    this.ttl = ttl;
    this.spawner = spawner;
    this.setMovingDirection(movingDirection);
    this.addBehavior(new ProjectileMovingBehavior());
  }

  public int getTtl() {
    return this.ttl;
  }

  public void updateTtl(int amount) {
    this.ttl += amount;
    if (this.ttl <= 0) {
      this.destroy();
    }
  }

  public Model getSpawner() {
    return this.spawner;
  }
}
