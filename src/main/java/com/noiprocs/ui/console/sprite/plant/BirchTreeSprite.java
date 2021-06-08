package com.noiprocs.ui.console.sprite.plant;

public class BirchTreeSprite extends TreeSprite {
    private static final char[][] TEXTURE = {
            {0, ',', '%', '%', '&', '%',',', 0},
            {'%', '%', '&', '&', '%', '%', '&', '%'},
            {'&', '%', '%', '&', '%', '%', '&', '&'},
            {'\'', '&', '%', '\\', 'Y', '&', '%', '\''},
            {0, 0, 0, '|', '|' , 0, 0, 0},
            {0, 0, '~','|', '|', '~', 0, 0}
    };

    public BirchTreeSprite(String id) {
        super(TEXTURE, id);
    }
}
