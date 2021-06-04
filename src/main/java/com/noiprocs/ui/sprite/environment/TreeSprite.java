package com.noiprocs.ui.sprite.environment;

import com.noiprocs.core.model.Model;
import com.noiprocs.ui.sprite.ConsoleSprite;

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
