package com.noiprocs.ui.console.sprite.environment;

import com.noiprocs.core.model.Model;
import com.noiprocs.core.model.environment.WallTrapModel;
import com.noiprocs.ui.console.sprite.ConsoleSprite;

public class WallTrapSprite extends ConsoleSprite {
    private static final char[][] TEXTURE_OPENED = {
            {'v', 'v', 'v', 'v', 'v', 'v', 'v', 'v'},
            {0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0},
            {'^', '^', '^', '^', '^', '^', '^', '^'},
    };
    private static final char[][] TEXTURE_CLOSED = {
            {'░', '░', '░', '░', '░', '░', '░', '░'},
            {'v', 'v', 'v', 'v', 'v', 'v', 'v', 'v'},
            {'^', '^', '^', '^', '^', '^', '^', '^'},
            {'░', '░', '░', '░', '░', '░', '░', '░'},
    };

    public WallTrapSprite() {
        super(EMPTY_TEXTURE);
    }

    @Override
    public char[][] getTexture(Model model) {
        if (((WallTrapModel) model).isClosed()) return TEXTURE_CLOSED;
        return TEXTURE_OPENED;
    }
}
