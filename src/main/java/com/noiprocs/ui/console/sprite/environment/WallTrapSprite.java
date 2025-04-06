package com.noiprocs.ui.console.sprite.environment;

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

    public WallTrapSprite(String id) {
        super(EMPTY_TEXTURE, id);
    }

    @Override
    public char[][] getTexture() {
        WallTrapModel model = (WallTrapModel) getModel();
        if (model.isClosed()) return TEXTURE_CLOSED;
        return TEXTURE_OPENED;
    }
}
