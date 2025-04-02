package com.noiprocs.ui.console.sprite.mob.projectile;

import com.noiprocs.core.model.mob.projectile.ProjectileModel;
import com.noiprocs.ui.console.sprite.ConsoleSprite;

public class FlyingWoodLogSprite extends ConsoleSprite {
    private static final int OFFSET_X = 0, OFFSET_Y = 0;
    private static final char[][][] TEXTURES = {
            {{'='}},
            {{'|'}}
    };

    public FlyingWoodLogSprite(String id) {
        super(TEXTURES[0], id, OFFSET_X, OFFSET_Y);
    }

    @Override
    public void render() {
    }

    @Override
    public char[][] getTexture() {
        ProjectileModel model = (ProjectileModel) getModel();
        return TEXTURES[model.getTtl() % TEXTURES.length];
    }
}
