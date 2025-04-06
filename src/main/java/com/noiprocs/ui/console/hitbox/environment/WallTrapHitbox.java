package com.noiprocs.ui.console.hitbox.environment;

import com.noiprocs.core.model.Model;
import com.noiprocs.core.model.environment.WallTrapModel;
import com.noiprocs.ui.console.hitbox.Hitbox;
import com.noiprocs.ui.console.sprite.environment.MazePartSprite;

import static com.noiprocs.ui.console.hitbox.HitboxCategory.*;

public class WallTrapHitbox extends Hitbox {
    public WallTrapHitbox() {
        super(WALL, WALL | PLAYER | MOB | PROJECTILE);
    }

    @Override
    public int getHeight(Model model) {
        if (((WallTrapModel) model).isClosed()) {
            return MazePartSprite.WALL_THICKNESS_HEIGHT;
        }
        return NO_HITBOX_HEIGHT;
    }

    @Override
    public int getWidth(Model model) {
        if (((WallTrapModel) model).isClosed()) {
            return MazePartSprite.WALL_THICKNESS_WIDTH;
        }
        return NO_HITBOX_WIDTH;
    }
}
