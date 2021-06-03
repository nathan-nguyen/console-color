package com.noiprocs.ui.sprite.mob.character;

import com.noiprocs.core.model.Model;

public class PlayerSprite extends ConsoleSprite {
    private static final char[][] TEXTURE = {
            {0,'o',0},
            {'/','+','\\'},
            {'/',0,'\\'},
    };

    public PlayerSprite(Model model) {
        super(TEXTURE, model);
    }

    @Override
    public void render() {}
}