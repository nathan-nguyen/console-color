package com.noiprocs.ui.sprite.mob.character;

import com.noiprocs.core.model.Model;
import com.noiprocs.ui.sprite.ConsoleSprite;

public class PlayerSprite extends ConsoleSprite {
    private static final char[][] TEXTURE = {
            {0,'o',0},
            {'/','+','\\'},
            {'/',0,'\\'},
    };

    public PlayerSprite(String id) {
        super(TEXTURE, id);
    }

    @Override
    public void render() {}
}