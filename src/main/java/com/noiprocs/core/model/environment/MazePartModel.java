package com.noiprocs.core.model.environment;

import com.noiprocs.core.config.Config;
import com.noiprocs.core.model.Model;

public class MazePartModel extends Model {
    public final int relativePosX, relativePosY;
    public final int offsetX, offsetY;

    public MazePartModel(int offsetX, int offsetY, int relativePosX, int relativePosY) {
        super(offsetX + relativePosX * Config.MAZE_WALL_THICKNESS_HEIGHT, offsetY + relativePosY * Config.MAZE_WALL_THICKNESS_WIDTH, true);
        this.offsetX = offsetX;
        this.offsetY = offsetY;
        this.relativePosX = relativePosX;
        this.relativePosY = relativePosY;
    }

    @Override
    public void update(int delta) {}
}
