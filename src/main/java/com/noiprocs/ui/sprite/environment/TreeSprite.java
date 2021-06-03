package com.noiprocs.ui.sprite.environment;

import com.noiprocs.core.model.Model;
import com.noiprocs.ui.sprite.mob.character.ConsoleSprite;

public class TreeSprite extends ConsoleSprite {
    private static final char[][] TEXTURE = {
            {'(','(',')',')'},
            {0,'\\','/',0},
            {'=','|','|','='},
    };

    public TreeSprite(Model model) {
        super(TEXTURE, model);
    }


    @Override
    public void render() {}
}
