package com.noiprocs.ui.console.sprite.plant;

import com.noiprocs.ui.console.sprite.ConsoleSprite;

public class TreeSprite extends ConsoleSprite {
    private static final char[][] TEXTURE = {
            {0, '▒', '▒', '▒', '▒', '▒', '▒', '▒', '▒', 0},
            {'▒','▐','▒','▐','▒','▒','▒','▒','▌','▒'},
            {0,'▒','▀','▄','█','▒','▄','▀','▒', 0},
            {0, 0, 0, 0, '█', '█', 0, 0, 0, 0},
            {0, 0, 0, '▄', '█', '█', '▄', 0, 0, 0}
    };

    public TreeSprite(String id) {
        super(TEXTURE, id);
    }

    public TreeSprite(char[][] texture, String id) {
        super(texture, id);
    }


    @Override
    public void render() {}
}
