package com.noiprocs.core.model.environment;

public class WorldBoundaryHorizontalModel extends WorldBoundaryModel {
    public static final int WORLD_BOUNDARY_PART_HEIGHT = 1, WORLD_BOUNDARY_PART_WIDTH = 60;

    public WorldBoundaryHorizontalModel(int posX, int posY, boolean isVisible) {
        super(posX, posY, isVisible, WORLD_BOUNDARY_PART_HEIGHT, WORLD_BOUNDARY_PART_WIDTH);
    }
}
