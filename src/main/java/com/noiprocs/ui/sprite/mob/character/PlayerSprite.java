package com.noiprocs.ui.sprite.mob.character;

import com.noiprocs.core.model.Model;

public class PlayerSprite extends ConsoleSprite {
    private static final char[][] TEXTURE = {
            {'X',0,'X'},
            {0,'X',0},
            {'X',0,'X'},
    };

    public PlayerSprite(Model model) {
        super(TEXTURE, model);
    }

    @Override
    public void render() {

    }

    @Override
    public char[][] getTexture() {
        if (System.currentTimeMillis() / 1000 % 2 == 0) return texture;
        else return new char[][]{
                {'O',0,'O'},
                {0,'O',0},
                {'O',0,'O'},
        };
    }
}
