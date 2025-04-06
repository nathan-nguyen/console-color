package com.noiprocs.core.model.mob.projectile;

import com.noiprocs.core.model.LowLatencyModelInterface;
import com.noiprocs.core.model.Model;
import com.noiprocs.core.model.mob.MobModel;

public class ProjectileModel extends MobModel implements LowLatencyModelInterface {
    private static final int MAX_HEALTH = 1;
    private int ttl;
    private final Model spawner;

    public ProjectileModel(int x, int y,
                           int horizontalSpeed, int verticalSpeed,
                           MovingDirection movingDirection,
                           int ttl,
                           Model spawner) {
        super(x, y, true, MAX_HEALTH, horizontalSpeed, verticalSpeed);
        this.ttl = ttl;
        this.spawner = spawner;
        this.setMovingDirection(movingDirection);
    }

    public int getTtl() {
        return this.ttl;
    }

    @Override
    protected void move() {
        super.move();

        --ttl;
        if (ttl <= 0) this.destroy();
    }

    public Model getSpawner() {
        return this.spawner;
    }
}
