package com.noiprocs.core.model.environment;

import com.noiprocs.core.common.Vector3D;
import com.noiprocs.core.model.Model;

public class WorldBoundaryVerticalModel extends Model {
    public static final int WORLD_BOUNDARY_PART_HEIGHT = 40;

    public WorldBoundaryVerticalModel(Vector3D position, boolean isVisible) {
        super(position, isVisible);
    }

    @Override
    public void update(int delta) {
    }
}
