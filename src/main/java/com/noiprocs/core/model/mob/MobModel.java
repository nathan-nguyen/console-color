package com.noiprocs.core.model.mob;

import com.noiprocs.core.GameContext;
import com.noiprocs.core.model.Model;

public abstract class MobModel extends Model {
    private static final int DEFAULT_SKIP_MOVEMENT_FRAME = 2;
    public enum MovingDirection {
        STOP, UP, DOWN, LEFT, RIGHT
    }

    private MovingDirection movingDirection = MovingDirection.STOP;
    protected MovingDirection facingDirection = MovingDirection.RIGHT;

    protected int skipMovementFrame = DEFAULT_SKIP_MOVEMENT_FRAME;
    private final int horizontalSpeed;
    private final int verticalSpeed;

    public MobModel(int x,
                    int y,
                    boolean isVisible,
                    int horizontalSpeed,
                    int verticalSpeed) {
        super(x, y, isVisible);
        this.horizontalSpeed = horizontalSpeed;
        this.verticalSpeed = verticalSpeed;
    }

    @Override
    public void update(int delta) {
        if (GameContext.get().worldCounter % skipMovementFrame == 0) {
            this.move();
        }
    }

    protected void move() {
        switch (movingDirection) {
            case STOP: return;
            case UP:
                for (int i = 0; i < verticalSpeed; ++i) move(-1, 0);
                break;
            case DOWN:
                for (int i = 0; i < verticalSpeed; ++i) move(1, 0);
                break;
            case LEFT:
                for (int i = 0; i < horizontalSpeed; ++i) move(0, -1);
                break;
            case RIGHT:
                for (int i = 0; i < horizontalSpeed; ++i) move(0, 1);
                break;
        }
    }

    /**
     * Default logic for Mob movement.
     * Before updating position, check whether next position is valid.
     * If next position is not valid, stop.
     *
     * @param x: Distance to move in horizontal direction.
     * @param y: Distance to move in vertical direction.
     */
    protected void move(int x, int y) {
        if (movingDirection == MovingDirection.STOP) return;

        if (this.isNextMoveValid(posX + x, posY + y)) {
            posX += x;
            posY += y;
        }
        else {
            movingDirection = MovingDirection.STOP;
        }
    }

    protected boolean isNextMoveValid(int x, int y) {
        return GameContext.get().hitboxManager.isValid(this, x, y);
    }

    public MovingDirection getMovingDirection() {
        return this.movingDirection;
    }

    protected void setMovingDirection(MovingDirection movingDirection) {
        this.movingDirection = movingDirection;
        if (movingDirection != MovingDirection.STOP) {
            this.facingDirection = movingDirection;
        }
    }

    public MovingDirection getFacingDirection() {
        return this.facingDirection;
    }
}
