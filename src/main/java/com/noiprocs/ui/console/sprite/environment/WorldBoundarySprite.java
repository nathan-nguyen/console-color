package com.noiprocs.ui.console.sprite.environment;

import com.noiprocs.core.model.environment.WorldBoundaryModel;
import com.noiprocs.ui.console.sprite.ConsoleSprite;

public class WorldBoundarySprite extends ConsoleSprite {
    private static final int OFFSET_X = 0, OFFSET_Y = 0;

    public WorldBoundarySprite(String id) {
        super(EMPTY_TEXTURE, id, OFFSET_X, OFFSET_Y);

        WorldBoundaryModel wbm = (WorldBoundaryModel) getModel();
        int height = wbm.hitboxHeight;
        int width = wbm.hitboxWidth;

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
