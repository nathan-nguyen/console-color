package com.noiprocs.core.model.environment;

import com.noiprocs.core.model.Model;

public class WorldBoundaryHorizontalModel extends Model {
    public static final int WORLD_BOUNDARY_PART_WIDTH = 60;

    public WorldBoundaryHorizontalModel(int posX, int posY, boolean isVisible) {
        super(posX, posY, isVisible);
    }

    @Override
    public void update(int delta) {
    }
}
