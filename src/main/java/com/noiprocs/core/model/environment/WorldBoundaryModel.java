package com.noiprocs.core.model.environment;

import com.noiprocs.core.model.Model;

public class WorldBoundaryModel extends Model {
    public static final int WORLD_BOUNDARY_PART_HEIGHT = 40, WORLD_BOUNDARY_PART_WIDTH = 60;

    public WorldBoundaryModel(int posX, int posY, boolean isVisible, int hitboxHeight, int hitboxWidth) {
        super(posX, posY, isVisible, hitboxHeight, hitboxWidth);
    }

    @Override
    public void update(int delta) {}
}
