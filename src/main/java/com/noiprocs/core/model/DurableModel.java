package com.noiprocs.core.model;

public abstract class DurableModel extends Model {
    private int health;

    public DurableModel(int x, int y, boolean isVisible, int health) {
        super(x, y, isVisible);
        this.health = health;
    }

    public void updateHealth(int amount) {
        health += amount;
        if (health <= 0) {
            this.destroy();
        }
    }

    public int getHealth() {
        return this.health;
    }
}
