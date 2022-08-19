package com.noiprocs.ui.console.sprite.plant;

public class PineTreeSprite extends TreeSprite {
    private static final int OFFSET_X = 5, OFFSET_Y = 2;
    private static final char[][] TEXTURE = {
            {0, 0, '/', '\\', 0, 0},
            {0, '/', '-', '_', '\\', 0},
            {'/', '_', '-', '-', '_', '\\'},
            {'/', '_', '-', '_', '_', '\\'},
            {'/', '_', '|', '|', '_', '\\'},
            {0, 0, '|', '|', 0, 0}
    };

    public PineTreeSprite(String id) {
        super(TEXTURE, id, OFFSET_X, OFFSET_Y);
    }
}
