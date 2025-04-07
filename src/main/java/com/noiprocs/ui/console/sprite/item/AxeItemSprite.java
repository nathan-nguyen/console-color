package com.noiprocs.ui.console.sprite.item;

import com.noiprocs.ui.console.sprite.ConsoleSprite;

public class AxeItemSprite extends ConsoleSprite {
    private static final int OFFSET_X = 2, OFFSET_Y = 1;
    private static final char[][] TEXTURE = {
            {'(', '=', ')'},
            {0, '|', 0},
            {0, '|', 0}
    };

    public AxeItemSprite() {
        super(TEXTURE, OFFSET_X, OFFSET_Y);
    }
}
