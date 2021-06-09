package com.noiprocs.ui.console.sprite.plant;

import com.noiprocs.ui.console.sprite.ConsoleSprite;

public class SaplingSprite extends ConsoleSprite {
    private static final char[][] TEXTURE = {{'Y'}};

    public SaplingSprite(String id) {
        super(TEXTURE, id);
    }

    @Override
    public void render() {}
}
