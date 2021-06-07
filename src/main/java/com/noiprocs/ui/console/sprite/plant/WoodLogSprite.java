package com.noiprocs.ui.console.sprite.plant;

import com.noiprocs.ui.console.sprite.ConsoleSprite;

public class WoodLogSprite extends ConsoleSprite {
    private static final char[][] TEXTURE = {{'='}};

    public WoodLogSprite(String id) {
        super(TEXTURE, id);
    }

    @Override
    public void render() {}
}
