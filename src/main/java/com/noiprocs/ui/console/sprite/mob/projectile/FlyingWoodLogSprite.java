package com.noiprocs.ui.console.sprite.mob.projectile;

import com.noiprocs.core.model.mob.projectile.ProjectileModel;
import com.noiprocs.ui.console.sprite.ConsoleSprite;

public class FlyingWoodLogSprite extends ConsoleSprite {
    private static final int OFFSET_X = 0, OFFSET_Y = 0;
    private static final long[][][] TEXTURES = convertCharTexture(new char[][][]{
            {{'='}},
            {{'|'}}
    });

    public FlyingWoodLogSprite(String id) {
        super(id, OFFSET_X, OFFSET_Y);
    }

    @Override
    public void render() {
    }

    @Override
    public long[][] getTexture() {
        ProjectileModel model = (ProjectileModel) getModel();
        return TEXTURES[model.getTtl() % TEXTURES.length];
    }
}
