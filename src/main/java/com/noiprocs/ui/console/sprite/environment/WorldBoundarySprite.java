package com.noiprocs.ui.console.sprite.environment;

import com.noiprocs.ui.console.sprite.ConsoleSprite;

public class WorldBoundarySprite extends ConsoleSprite {
    private static final int OFFSET_X = 0, OFFSET_Y = 0;

    public WorldBoundarySprite(String id, int height, int width) {
        super(EMPTY_TEXTURE, id, OFFSET_X, OFFSET_Y);

        char[][] worldBoundaryTexture = new char[height][width];

        for (int i = 0; i < height; ++i) {
            for (int j = 0; j < width; ++j) {
                worldBoundaryTexture[i][j] = 'X';
            }
        }

        this.setTexture(worldBoundaryTexture);
    }

    @Override
    public void render() {}
}
