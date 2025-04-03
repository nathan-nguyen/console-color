package com.noiprocs.ui.console.sprite.mob.projectile;

import com.noiprocs.core.model.mob.projectile.ProjectileModel;
import com.noiprocs.ui.console.sprite.ConsoleSprite;

public class FlyingWoodLogSprite extends ConsoleSprite {
    private static final char[][][] ANIMATION_TEXTURES = {
            {{'='}},
            {{'|'}}
    };

    public FlyingWoodLogSprite(String id) {
        super(ANIMATION_TEXTURES[0], id);
    }

    @Override
    public char[][] getTexture() {
        ProjectileModel model = (ProjectileModel) getModel();
        return ANIMATION_TEXTURES[model.getTtl() % ANIMATION_TEXTURES.length];
    }
}
