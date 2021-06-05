package com.noiprocs.core.model.environment;

import com.noiprocs.core.model.Model;

public class MazePartModel extends Model {
    public static final int MAZE_PART_DIMENSION = 10;
    public static final int WALL_THICKNESS_HEIGHT = 4;
    public static final int WALL_THICKNESS_WIDTH = 8;

    public final int[][] data;

    public MazePartModel(int x, int y, boolean isPhysical, int[][] data) {
        super(x, y, isPhysical);
        this.data = data;
    }
}
