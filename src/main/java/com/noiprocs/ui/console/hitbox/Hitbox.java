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
}
