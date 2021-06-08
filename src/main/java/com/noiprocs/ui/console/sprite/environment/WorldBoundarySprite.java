package com.noiprocs.ui.console.sprite.environment;

import com.noiprocs.core.model.environment.WorldBoundaryModel;
import com.noiprocs.ui.console.sprite.ConsoleSprite;

public class WorldBoundarySprite extends ConsoleSprite {
    private static final int WORLD_BOUNDARY_PART_HEIGHT = 40;
    private static final int WORLD_BOUNDARY_PART_WIDTH = 60;

    public WorldBoundarySprite(String id) {
        super(EMPTY_TEXTURE, id);

        WorldBoundaryModel wbm = (WorldBoundaryModel) getModel();
        int height = wbm.isVertical ? WORLD_BOUNDARY_PART_HEIGHT : 1;
        int width = wbm.isVertical ? 1 : WORLD_BOUNDARY_PART_WIDTH;

        char[][] worldBoundaryTexture = new char[height][width];

        for (int i = 0; i < height; ++i) {
            for (int j = 0; j < width; ++j) worldBoundaryTexture[i][j] = 'X';
        }

        this.setTexture(worldBoundaryTexture);
        wbm.setPosition(
                wbm.offsetX + wbm.relativePosX * WORLD_BOUNDARY_PART_HEIGHT,
                wbm.offsetY + wbm.relativePosY * WORLD_BOUNDARY_PART_WIDTH
        );
    }

    @Override
    public void render() {}
}
