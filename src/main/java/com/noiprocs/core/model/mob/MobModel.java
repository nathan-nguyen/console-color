package com.noiprocs.core.model.mob;

import com.noiprocs.core.GameContext;
import com.noiprocs.core.model.DurableModel;
import com.noiprocs.core.common.Helper;
import com.noiprocs.core.common.Vector3D;

public abstract class MobModel extends DurableModel {
    private static final int DEFAULT_SKIP_MOVEMENT_FRAME = 2;

    public enum MovingDirection {
        STOP, UP, DOWN, LEFT, RIGHT
    }

    public static final Vector3D NORTH = new Vector3D(-1, 0, 0);
    public static final Vector3D SOUTH = new Vector3D(1, 0, 0);
    public static final Vector3D WEST = new Vector3D(0, -1, 0);
    public static final Vector3D EAST = new Vector3D(0, 1, 0);

    private MovingDirection movingDirection = MovingDirection.STOP;
    protected MovingDirection facingDirection = MovingDirection.RIGHT;

    protected int skipMovementFrame = DEFAULT_SKIP_MOVEMENT_FRAME;
    private final int horizontalSpeed;
    private final int verticalSpeed;

    public MobModel(Vector3D position,
            boolean isVisible,
            int health,
            int horizontalSpeed,
            int verticalSpeed) {
        super(position, isVisible, health);
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
            case STOP:
                return;
            case UP:
                for (int i = 0; i < verticalSpeed; ++i)
                    move(NORTH);
                break;
            case DOWN:
                for (int i = 0; i < verticalSpeed; ++i)
                    move(SOUTH);
                break;
            case LEFT:
                for (int i = 0; i < horizontalSpeed; ++i)
                    move(WEST);
                break;
            case RIGHT:
                for (int i = 0; i < horizontalSpeed; ++i)
                    move(EAST);
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
    protected void move(Vector3D deltaPosition) {
        if (movingDirection == MovingDirection.STOP)
            return;

        if (this.isNextMoveValid(this.position.add(deltaPosition))) {
            this.position.addInPlace(deltaPosition);
        } else {
            movingDirection = MovingDirection.STOP;
        }
    }

    protected boolean isNextMoveValid(Vector3D nextPosition) {
        return GameContext.get().hitboxManager.isValid(this, nextPosition);
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

    public MovingDirection getFollowDirection(Vector3D targetPosition) {
        int up = Math.max(position.x - targetPosition.x, 0);
        int right = Math.max(targetPosition.y - position.y, 0);
        int down = Math.max(targetPosition.x - position.x, 0);
        int left = Math.max(position.y - targetPosition.y, 0);

        int v = Helper.random.nextInt(up + right + down + left);
        if (v < up)
            return MovingDirection.UP;
        if (v < up + right)
            return MovingDirection.RIGHT;
        if (v < up + right + down)
            return MovingDirection.DOWN;
        return MovingDirection.LEFT;
    }
}
