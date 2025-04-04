package com.noiprocs.core.model.environment;

import com.noiprocs.core.model.Model;

public class MazePartModel extends Model {
    public static final int MAZE_PART_DIMENSION = 10;

    public final int[][] data;
    public final int relativePosX, relativePosY;
    public final int offsetX, offsetY;

    public MazePartModel(int offsetX, int offsetY, int relativePosX, int relativePosY, int[][] data) {
        super(0, 0, true);
        this.offsetX = offsetX;
        this.offsetY = offsetY;
        this.relativePosX = relativePosX;
        this.relativePosY = relativePosY;
        this.data = data;
    }

    @Override
    public void update(int delta) {}
}
