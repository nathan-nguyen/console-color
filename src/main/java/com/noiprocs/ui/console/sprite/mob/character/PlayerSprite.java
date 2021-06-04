package com.noiprocs.ui.console.sprite.mob.character;

import com.noiprocs.ui.console.sprite.ConsoleSprite;

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