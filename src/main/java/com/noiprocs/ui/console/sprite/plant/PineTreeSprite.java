package com.noiprocs.ui.console.sprite.plant;

public class PineTreeSprite extends TreeSprite {
    private static final char[][] TEXTURE = {
            {0, 0, 0, '/', '\\', 0, 0, 0},
            {0, 0, '/', '-', '-', '\\', 0, 0},
            {0, '/', '-', '_', '_', '-', '\\', 0},
            {'/', '_', '_', '|', '|', '_', '_', '\\'},
            {0, 0, '_', '|', '|', '_', 0, 0}
    };

    public PineTreeSprite(String id) {
        super(TEXTURE, id);
    }
}
