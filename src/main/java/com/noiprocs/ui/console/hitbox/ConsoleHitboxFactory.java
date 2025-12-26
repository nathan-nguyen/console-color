package com.noiprocs.ui.console.hitbox;

import static com.noiprocs.ui.console.hitbox.HitboxCategory.*;

import com.noiprocs.core.common.Vector3D;
import com.noiprocs.core.model.environment.WallTrapModel;
import com.noiprocs.core.model.mob.character.PlayerModel;
import com.noiprocs.ui.console.hitbox.HitboxConfigLoader.HitboxConfig;
import com.noiprocs.ui.console.hitbox.environment.WallTrapHitbox;
import java.util.Map;

public class ConsoleHitboxFactory {
  private static final Map<String, HitboxConfig> CONFIG_MAP =
      HitboxConfigLoader.loadHitboxConfigs();

  public static Hitbox generateHitbox(String modelClassName) {
    if (modelClassName.equals(PlayerModel.class.getName())) {
      return new Hitbox(1, 3, PLAYER, WALL | MOB) {
        @Override
        protected Vector3D getSpawnPointCenter() {
          return new Vector3D(-2, 1, 0);
        }
      };
    }

    if (modelClassName.equals(WallTrapModel.class.getName())) {
      return new WallTrapHitbox();
    }

    // Load from JSON config
    HitboxConfig config = CONFIG_MAP.get(modelClassName);
    if (config != null) {
      return new Hitbox(config.height, config.width, config.categoryBit, config.maskBit);
    }

    throw new UnsupportedOperationException(
        "Generating hitbox for " + modelClassName + " is not supported!");
  }
}
