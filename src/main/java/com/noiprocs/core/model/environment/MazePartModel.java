package com.noiprocs.core.model.environment;

import com.noiprocs.core.model.Model;

public class MazePartModel extends Model {
    public static final int MAZE_PART_DIMENSION = 10;

    public final int[][] data;
    public final int offsetX, offsetY;

    public MazePartModel(int offsetX, int offsetY, int x, int y, boolean isPhysical, int[][] data) {
        super(x, y, isPhysical);
        this.data = data;
        this.offsetX = offsetX;
        this.offsetY = offsetY;
    }
}
