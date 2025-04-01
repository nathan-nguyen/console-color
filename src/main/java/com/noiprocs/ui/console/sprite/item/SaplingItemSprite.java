package com.noiprocs.ui.console.sprite.item;

import com.noiprocs.ui.console.sprite.ConsoleSprite;

public class SaplingItemSprite extends ConsoleSprite {
    private static final int OFFSET_X = 0, OFFSET_Y = 0;
    private static final char[][] TEXTURE = {{'Y'}};

    public SaplingItemSprite(String id) {
        super(TEXTURE, id, OFFSET_X, OFFSET_Y);
    }

    @Override
    public void render() {}
}
