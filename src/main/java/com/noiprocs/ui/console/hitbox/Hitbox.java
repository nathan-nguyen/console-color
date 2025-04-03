package com.noiprocs.ui.console.hitbox;

public class Hitbox {
    public final int height, width;
    public final int categoryBit, maskBit;

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
}
