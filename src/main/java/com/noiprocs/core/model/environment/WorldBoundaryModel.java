package com.noiprocs.core.model.environment;

import com.noiprocs.core.model.Model;

public class WorldBoundaryModel extends Model {
    public final boolean isVertical;
    public final int relativePosX, relativePosY;
    public final int offsetX, offsetY;

    public WorldBoundaryModel(
            int offsetX, int offsetY, int relativePosX, int relativePosY, boolean isVertical, boolean isVisible
    ) {
        super(0, 0, isVisible);

        this.offsetX = offsetX;
        this.offsetY = offsetY;
        this.relativePosX = relativePosX;
        this.relativePosY = relativePosY;
        this.isVertical = isVertical;
    }
}
