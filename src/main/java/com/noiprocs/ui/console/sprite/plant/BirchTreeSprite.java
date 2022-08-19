package com.noiprocs.ui.console.sprite.plant;

public class BirchTreeSprite extends TreeSprite {
    private static final int OFFSET_X = 5, OFFSET_Y = 3;
    private static final char[][] TEXTURE = {
            {0, ',', '%', '%', '&', '%',',', 0},
            {'%', '%', '&', '&', '%', '%', '&', '%'},
            {'&', '%', '%', '&', '%', '%', '&', '&'},
            {'\'', '&', '%', '\\', 'Y', '&', '%', '\''},
            {0, 0, 0, '|', '|' , 0, 0, 0},
            {0, 0, 0,'|', '|', 0, 0, 0}
    };

    public BirchTreeSprite(String id) {
        super(TEXTURE, id, OFFSET_X, OFFSET_Y);
    }
}
