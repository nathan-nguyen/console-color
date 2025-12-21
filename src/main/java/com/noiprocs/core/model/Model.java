package com.noiprocs.core.model;

import com.noiprocs.core.common.Vector3D;

import java.io.Serializable;

public abstract class Model implements Serializable {
    public String id = String.valueOf(this.hashCode());

    public Vector3D position;
    public Vector3D rotation;
    public boolean isVisible;
    public boolean isDestroyed = false;

    public Model(Vector3D position, boolean isVisible) {
        this.position = new Vector3D(position.x, position.y , position.z);
        this.rotation = new Vector3D(0, 0, 0);
        this.isVisible = isVisible;
    }

    public abstract void update(int delta);

    public void setPosition(Vector3D nextPosition) {
        this.position.x = nextPosition.x;
        this.position.y = nextPosition.y;
        this.position.z = nextPosition.z;
    }

    protected void destroy() {
        this.isDestroyed = true;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "(" + id + " at " + position + ")";
    }
}
