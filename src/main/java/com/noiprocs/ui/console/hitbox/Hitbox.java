package com.noiprocs.ui.console.hitbox;

import com.noiprocs.core.common.Vector3D;
import com.noiprocs.core.model.Model;

public class Hitbox {
    protected static final int NO_HITBOX_HEIGHT = 0;
    protected static final int NO_HITBOX_WIDTH = 0;

    protected final int height, width;
    public final int categoryBit, maskBit;

    public Hitbox(int categoryBit, int maskBit) {
        this(NO_HITBOX_HEIGHT, NO_HITBOX_WIDTH, categoryBit, maskBit);
    }

    public Hitbox(int height, int width, int categoryBit, int maskBit) {
        this.categoryBit = categoryBit;
        this.maskBit = maskBit;
        this.height = height;
        this.width = width;
    }

    protected Vector3D getSpawnPointTop() {
        return new Vector3D(-1, 0, 0);
    }

    protected Vector3D getSpawnPointRight() {
        return new Vector3D(0, width, 0);
    }

    protected Vector3D getSpawnPointBottom() {
        return new Vector3D(height, 0, 0);
    }

    protected Vector3D getSpawnPointLeft() {
        return new Vector3D(0, -1, 0);
    }

    protected Vector3D getSpawnPointCenter() {
        return new Vector3D(height / 2, width / 2, 0);
    }

    public Vector3D getSpawnPoint(Vector3D direction) {
        if (direction.x == -1 && direction.y == 0) return getSpawnPointTop();
        if (direction.x == 0 && direction.y == 1) return getSpawnPointRight();
        if (direction.x == 1 && direction.y == 0) return getSpawnPointBottom();
        if (direction.x == 0 && direction.y == -1) return getSpawnPointLeft();
        return getSpawnPointCenter();
    }

    public int getHeight(Model model) {
        return this.height;
    }

    public int getWidth(Model model) {
        return this.width;
    }
}
