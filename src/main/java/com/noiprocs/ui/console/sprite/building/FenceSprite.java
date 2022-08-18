package com.noiprocs.ui.console.sprite.building;

import com.noiprocs.ui.console.sprite.ConsoleSprite;

public class FenceSprite extends ConsoleSprite {
    private static final char[][] TEXTURE = {
            {'#','#'},
            {'#','#'},
    };

    public FenceSprite(String id) {
        super(TEXTURE, id);
    }

    @Override
    public void render() {

    }
}
