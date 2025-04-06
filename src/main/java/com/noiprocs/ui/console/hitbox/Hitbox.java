package com.noiprocs.ui.console.hitbox;

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

    protected int[] getSpawnPointTop() {
        return new int[]{-1, 0};
    }

    protected int[] getSpawnPointRight() {
        return new int[]{0, width};
    }

    protected int[] getSpawnPointBottom() {
        return new int[]{height, 0};
    }

    protected int[] getSpawnPointLeft() {
        return new int[]{0, -1};
    }

    protected int[] getSpawnPointCenter() {
        return new int[]{height / 2, width / 2};
    }

    public int[] getSpawnPoint(int directionX, int directionY) {
        if (directionX == -1 && directionY == 0) return getSpawnPointTop();
        if (directionX == 0 && directionY == 1) return getSpawnPointRight();
        if (directionX == 1 && directionY == 0) return getSpawnPointBottom();
        if (directionX == 0 && directionY == -1) return getSpawnPointLeft();
        return getSpawnPointCenter();
    }

    public int getHeight(Model model) {
        return this.height;
    }

    public int getWidth(Model model) {
        return this.width;
    }
}
