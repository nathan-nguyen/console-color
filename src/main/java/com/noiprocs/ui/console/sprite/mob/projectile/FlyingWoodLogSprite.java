package com.noiprocs.ui.console.sprite.mob.projectile;

import com.noiprocs.ui.console.sprite.ConsoleSprite;

public class FlyingWoodLogSprite extends ConsoleSprite {
    private static final int OFFSET_X = 0, OFFSET_Y = 0;
    private static final char[][] TEXTURE = {{'='}};

    public FlyingWoodLogSprite(String id) {
        super(TEXTURE, id, OFFSET_X, OFFSET_Y);
    }

    @Override
    public void render() {

    }
}
