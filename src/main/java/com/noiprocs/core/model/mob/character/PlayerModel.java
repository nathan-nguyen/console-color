package com.noiprocs.core.model.mob.character;

import com.noiprocs.core.model.Model;
import com.noiprocs.core.util.Helper;

public class PlayerModel extends Model {
    public enum MovingDirection {
        STOP, UP, DOWN, LEFT, RIGHT
    }

    private MovingDirection movingDirection = MovingDirection.STOP;

    public PlayerModel(String id, int x, int y, boolean isPhysical) {
        super(x, y, isPhysical);
        this.id = id;
    }

    public void moveUp() {
        this.movingDirection = MovingDirection.UP;
    }

    public void moveDown() {
        this.movingDirection = MovingDirection.DOWN;
    }

    public void moveLeft() {
        this.movingDirection = MovingDirection.LEFT;
    }

    public void moveRight() {
        this.movingDirection = MovingDirection.RIGHT;
    }

    public void stop() {
        this.movingDirection = MovingDirection.STOP;
    }

    @Override
    public void update(int dt) {
        this.move();
    }

    private void move() {
        switch (movingDirection) {
            case STOP: return;
            case UP: move(-1, 0); break;
            case DOWN: move(1, 0); break;
            case LEFT: move(0, -1); break;
            case RIGHT: move(0, 1); break;
        }
    }

    private void move(int x, int y) {
        if (Helper.GAME_CONTEXT.hitboxManager.isValid(this, posX + x, posY + y)) {
            posX += x;
            posY += y;
        }
        else movingDirection = MovingDirection.STOP;
    }
}
