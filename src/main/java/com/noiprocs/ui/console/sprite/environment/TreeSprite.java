package com.noiprocs.ui.console.sprite.environment;

import com.noiprocs.ui.console.sprite.ConsoleSprite;

public class TreeSprite extends ConsoleSprite {
    private static final char[][] TEXTURE = {
            {'(','(',')',')'},
            {0,'\\','/',0},
            {'=','|','|','='},
    };

    public TreeSprite(String id) {
        super(TEXTURE, id);
    }


    @Override
    public void render() {}
}
