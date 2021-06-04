package com.noiprocs.core.model;

import java.io.Serializable;

public abstract class Model implements Serializable {
    public String id = String.valueOf(this.hashCode());

    public int posX;
    public int posY;
    public boolean isPhysical;

    public Model(int x, int y, boolean isPhysical) {
        this.posX = x;
        this.posY = y;
        this.isPhysical = isPhysical;
    }

    public int distanceTo(int x, int y) {
        return (int) Math.sqrt((x - posX) * (x - posX) + (y - posY) * (y - posY));
    }

    public void update(int delta) {}
}
