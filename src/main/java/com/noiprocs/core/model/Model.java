package com.noiprocs.core.model;

import java.io.Serializable;

public abstract class Model implements Serializable {
    public String id = String.valueOf(this.hashCode());

    public int posX, posY;
    public boolean isVisible;
    public boolean isDestroyed = false;

    public final int hitboxHeight, hitboxWidth;

    public Model(int x, int y, boolean isVisible, int hitboxHeight, int hitboxWidth) {
        this.posX = x;
        this.posY = y;
        this.isVisible = isVisible;
        this.hitboxHeight = hitboxHeight;
        this.hitboxWidth = hitboxWidth;
    }

    public int distanceTo(int x, int y) {
        return (int) Math.sqrt((x - posX) * (x - posX) + (y - posY) * (y - posY));
    }

    public abstract void update(int delta);

    public void setPosition(int posX, int posY) {
        this.posX = posX;
        this.posY = posY;
    }

    protected void destroy() {
        this.isDestroyed = true;
    }
}
