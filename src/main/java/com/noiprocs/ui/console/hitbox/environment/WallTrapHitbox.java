package com.noiprocs.ui.console.hitbox.environment;

import static com.noiprocs.ui.console.hitbox.HitboxCategory.*;

import com.noiprocs.core.common.Vector3D;
import com.noiprocs.core.config.Config;
import com.noiprocs.core.model.Model;
import com.noiprocs.core.model.environment.WallTrapModel;
import com.noiprocs.ui.console.hitbox.Hitbox;

public class WallTrapHitbox extends Hitbox {
  public WallTrapHitbox() {
    super(WALL, WALL | PLAYER | MOB | PROJECTILE);
  }

  @Override
  public Vector3D getDimension(Model model) {
    if (((WallTrapModel) model).isClosed()) {
      return new Vector3D(Config.MAZE_WALL_THICKNESS_HEIGHT, Config.MAZE_WALL_THICKNESS_WIDTH, 0);
    }
    return Vector3D.ZERO;
  }
}
