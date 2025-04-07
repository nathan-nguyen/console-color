package com.noiprocs.core.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class DurableModel extends Model {
    private static final Logger logger = LogManager.getLogger(DurableModel.class);
    private int health;

    public DurableModel(int x, int y, boolean isVisible, int health) {
        super(x, y, isVisible);
        this.health = health;
    }

    public void updateHealth(int amount) {
        health += amount;
        if (health <= 0) {
            logger.info("{} is killed!", this);
            this.destroy();
        }
    }

    public int getHealth() {
        return this.health;
    }
}
