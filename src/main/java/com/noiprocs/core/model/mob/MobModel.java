package com.noiprocs.core.model.mob;

import com.noiprocs.core.GameContext;
import com.noiprocs.core.model.DurableModel;
import com.noiprocs.core.util.Helper;

public abstract class MobModel extends DurableModel {
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
                    int health,
                    int horizontalSpeed,
                    int verticalSpeed) {
        super(x, y, isVisible, health);
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
        if (movingDirection == MovingDirection.RIGHT || movingDirection == MovingDirection.LEFT) {
            this.facingDirection = movingDirection;
        }
    }

    public MovingDirection getFacingDirection() {
        return this.facingDirection;
    }

    public MovingDirection getFollowDirection(int targetX, int targetY) {
        int up = Math.max(posX - targetX, 0);
        int right = Math.max(targetY - posY, 0);
        int down = Math.max(targetX - posX, 0);
        int left = Math.max(posY - targetY, 0);

        int v = Helper.random.nextInt(up + right + down + left);
        if (v < up) return MovingDirection.UP;
        if (v < up + right) return MovingDirection.RIGHT;
        if (v < up + right + down) return MovingDirection.DOWN;
        return MovingDirection.LEFT;
    }
}
