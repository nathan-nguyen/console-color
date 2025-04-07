package com.noiprocs.ui.console.hitbox;

import com.noiprocs.core.config.Config;
import com.noiprocs.core.model.building.FenceModel;
import com.noiprocs.core.model.environment.MazePartModel;
import com.noiprocs.core.model.environment.WallTrapModel;
import com.noiprocs.core.model.environment.WorldBoundaryHorizontalModel;
import com.noiprocs.core.model.environment.WorldBoundaryVerticalModel;
import com.noiprocs.core.model.item.*;
import com.noiprocs.core.model.mob.CotPsychoModel;
import com.noiprocs.core.model.mob.character.PlayerModel;
import com.noiprocs.core.model.mob.projectile.FlyingWoodLogModel;
import com.noiprocs.core.model.plant.BirchTreeModel;
import com.noiprocs.core.model.plant.PineTreeModel;
import com.noiprocs.core.model.plant.TreeModel;
import com.noiprocs.ui.console.hitbox.environment.WallTrapHitbox;

import static com.noiprocs.ui.console.hitbox.HitboxCategory.*;

public class ConsoleHitboxFactory {
    public static Hitbox generateHitbox(String modelClassName) {
        if (modelClassName.equals(PlayerModel.class.getName())) {
            return new Hitbox(1, 3, PLAYER, WALL | MOB) {
                @Override
                protected int[] getSpawnPointCenter() {
                    return new int[]{-2, 1};
                }
            };
        }

        if (modelClassName.equals(CotPsychoModel.class.getName())) {
            return new Hitbox(1, 4, MOB, WALL | PLAYER | MOB);
        }
        if (modelClassName.equals(TreeModel.class.getName())) {
            return new Hitbox(1, 4, WALL, WALL);
        }
        if (modelClassName.equals(PineTreeModel.class.getName())
                || modelClassName.equals(BirchTreeModel.class.getName())) {
            return new Hitbox(1, 2, WALL, WALL);
        }
        if (modelClassName.equals(WorldBoundaryVerticalModel.class.getName())) {
            return new Hitbox(40, 1, WALL, WALL);
        }
        if (modelClassName.equals(WorldBoundaryHorizontalModel.class.getName())) {
            return new Hitbox(1, 60, WALL, WALL);
        }
        if (modelClassName.equals(FenceModel.class.getName())) {
            return new Hitbox(2, 2, WALL, WALL);
        }
        if (modelClassName.equals(WoodLogItem.class.getName())
                || modelClassName.equals(SaplingItem.class.getName())
                || modelClassName.equals(AppleItem.class.getName())
        ) {
            return new Hitbox(1, 1, ITEM, WALL);
        }
        if (modelClassName.equals(AxeItem.class.getName())) {
            return new Hitbox(3, 3, ITEM, WALL);
        }

        if (modelClassName.equals(FlyingWoodLogModel.class.getName())) {
            return new Hitbox(1, 1, PROJECTILE, MASK_ALL);
        }

        if (modelClassName.equals(MazePartModel.class.getName())) {
            return new Hitbox(
                    Config.MAZE_WALL_THICKNESS_HEIGHT, Config.MAZE_WALL_THICKNESS_WIDTH,
                    WALL, WALL | PLAYER | MOB | PROJECTILE
            );
        }
        if (modelClassName.equals(WallTrapModel.class.getName())) {
            return new WallTrapHitbox();
        }

        throw new UnsupportedOperationException("Generating hitbox for " + modelClassName + " is not supported!");
    }
}
