package com.noiprocs.core.common;

import java.io.Serializable;

public class Vector3D implements Serializable {
    public static final Vector3D ZERO = new Vector3D(0, 0, 0);

    public int x, y, z;

    public Vector3D(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vector3D add(Vector3D other) {
        return new Vector3D(this.x + other.x, this.y + other.y, this.z + other.z);
    }

    public void addInPlace(Vector3D other) {
        this.x += other.x;
        this.y += other.y;
        this.z += other.z;
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ", " + z + ")";
    }

    public int distanceTo(Vector3D position) {
        return (int) Math.sqrt((x - position.x) * (x - position.x) + (y - position.y) * (y - position.y)
                + (z - position.z) * (z - position.z));
    }
}
