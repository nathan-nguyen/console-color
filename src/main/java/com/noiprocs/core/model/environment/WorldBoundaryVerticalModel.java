package com.noiprocs.core.model.environment;

public class WorldBoundaryVerticalModel extends WorldBoundaryModel {
    public static final int WORLD_BOUNDARY_PART_HEIGHT = 40, WORLD_BOUNDARY_PART_WIDTH = 1;

    public WorldBoundaryVerticalModel(int posX, int posY, boolean isVisible) {
        super(posX, posY, isVisible, WORLD_BOUNDARY_PART_HEIGHT, WORLD_BOUNDARY_PART_WIDTH);
    }
}
