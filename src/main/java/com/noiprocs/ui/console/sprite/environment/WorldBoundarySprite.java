package com.noiprocs.ui.console.sprite.environment;

import com.noiprocs.ui.console.sprite.ConsoleSprite;

public class WorldBoundarySprite extends ConsoleSprite {
    public WorldBoundarySprite(int height, int width) {
        super(EMPTY_TEXTURE, OFFSET_X, OFFSET_Y);

        char[][] worldBoundaryTexture = new char[height][width];

        for (int i = 0; i < height; ++i) {
            for (int j = 0; j < width; ++j) {
                worldBoundaryTexture[i][j] = 'X';
            }
        }

        this.setTexture(worldBoundaryTexture);
    }
}
