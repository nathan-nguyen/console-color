package com.noiprocs.ui.console.hitbox;

import static com.noiprocs.ui.console.hitbox.HitboxCategory.*;

public class ConsoleHitboxFactory {
    public static Hitbox generateHitbox(String modelClassName) {
        switch (modelClassName) {
            case "com.noiprocs.core.model.mob.character.PlayerModel":
                return new Hitbox(1, 3, PLAYER, WALL | MOB);
            case "com.noiprocs.core.model.mob.CotPsychoModel":
            case "com.noiprocs.core.model.mob.CotRightModel":
            case "com.noiprocs.core.model.mob.CotLeftModel":
                return new Hitbox(1, 4, MOB, WALL | PLAYER | MOB);
            case "com.noiprocs.core.model.plant.TreeModel":
                return new Hitbox(1, 4, WALL, WALL);
            case "com.noiprocs.core.model.plant.PineTreeModel":
            case "com.noiprocs.core.model.plant.BirchTreeModel":
                return new Hitbox(1, 2, WALL, WALL);
            case "com.noiprocs.core.model.environment.WorldBoundaryVerticalModel":
                return new Hitbox(40, 1, WALL, WALL);
            case "com.noiprocs.core.model.environment.WorldBoundaryHorizontalModel":
                return new Hitbox(1, 60, WALL, WALL);
            case "com.noiprocs.core.model.building.FenceModel":
                return new Hitbox(2, 2, WALL, WALL);
            case "com.noiprocs.core.model.plant.SaplingModel":
            case "com.noiprocs.core.model.plant.WoodLogModel":
                return new Hitbox(1, 1, ITEM, WALL);
        }
        throw new UnsupportedOperationException("Generating hitbox for " + modelClassName + " is not supported!");
    }
}
