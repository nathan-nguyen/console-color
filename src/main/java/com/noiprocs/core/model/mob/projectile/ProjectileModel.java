package com.noiprocs.core.model.mob.projectile;

import com.noiprocs.core.model.LowLatencyModelInterface;
import com.noiprocs.core.model.Model;
import com.noiprocs.core.model.mob.MobModel;

public class ProjectileModel extends MobModel implements LowLatencyModelInterface {
    private int ttl;
    private final Model spawner;

    public ProjectileModel(int x, int y,
                           int horizontalSpeed, int verticalSpeed,
                           MobModel.MovingDirection movingDirection,
                           int ttl,
                           Model spawner) {
        super(x, y, true, horizontalSpeed, verticalSpeed);
        this.ttl = ttl;
        this.spawner = spawner;
        this.setMovingDirection(movingDirection);
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
