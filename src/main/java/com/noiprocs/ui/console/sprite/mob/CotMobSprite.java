package com.noiprocs.ui.console.sprite.mob;

import com.noiprocs.ui.console.sprite.ConsoleSprite;

public class CotMobSprite extends ConsoleSprite {
    private static final char[][] TEXTURE = {
            {'0', '=', '=', '='},
            {'/', '\\', '/', '\\'}
    };
    public CotMobSprite(String id) {
        super(TEXTURE, id);
    }

    @Override
    public void render() {

    }
}
