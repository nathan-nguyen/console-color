package com.noiprocs.core.model;

import java.io.Serializable;

public abstract class Model implements Serializable {
    public String id = String.valueOf(this.hashCode());

    public int posX;
    public int posY;
    public boolean isVisible, isDestroyed;

    public Model(int x, int y, boolean isVisible) {
        this.posX = x;
        this.posY = y;
        this.isVisible = isVisible;
        this.isDestroyed = false;
    }

    public int distanceTo(int x, int y) {
        return (int) Math.sqrt((x - posX) * (x - posX) + (y - posY) * (y - posY));
    }

    public void update(int delta) {}

    public void setPosition(int posX, int posY) {
        this.posX = posX;
        this.posY = posY;
    }

    protected void destroy() {
        this.isDestroyed = true;
    }
}
