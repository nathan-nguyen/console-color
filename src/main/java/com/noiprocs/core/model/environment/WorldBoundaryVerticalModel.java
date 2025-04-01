package com.noiprocs.core.model.environment;

import com.noiprocs.core.model.Model;

public class WorldBoundaryVerticalModel extends Model {
    public static final int WORLD_BOUNDARY_PART_HEIGHT = 40;

    public WorldBoundaryVerticalModel(int posX, int posY, boolean isVisible) {
        super(posX, posY, isVisible);
    }

    @Override
    public void update(int delta) {
    }
}
