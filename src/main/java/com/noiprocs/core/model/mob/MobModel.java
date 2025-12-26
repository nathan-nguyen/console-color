package com.noiprocs.core.model.mob;

import com.noiprocs.core.GameContext;
import com.noiprocs.core.model.DurableModel;
import com.noiprocs.core.common.Direction;
import com.noiprocs.core.common.Helper;
import com.noiprocs.core.common.Vector3D;

public abstract class MobModel extends DurableModel {
    private static final int DEFAULT_SKIP_MOVEMENT_FRAME = 2;

    private Vector3D movingDirection = Vector3D.ZERO;
    protected Vector3D facingDirection = Direction.EAST;
    protected int skipMovementFrame = DEFAULT_SKIP_MOVEMENT_FRAME;
    private Vector3D speed;

    public MobModel(Vector3D position, boolean isVisible, int health, Vector3D speed) {
        super(position, isVisible, health);
        this.speed = speed;
    }

    @Override
    public void update(int delta) {
        if (GameContext.get().worldCounter % skipMovementFrame == 0) {
            this.move();
        }
    }

    protected void move() {
        if (movingDirection.equals(Direction.NORTH) || movingDirection.equals(Direction.SOUTH)) {
            for (int i = 0; i < Math.abs(this.speed.x); ++i) {
                this.move(movingDirection);
            }
        } else if (movingDirection.equals(Direction.WEST) || movingDirection.equals(Direction.EAST)) {
            for (int i = 0; i < Math.abs(this.speed.y); ++i) {
                this.move(movingDirection);
            }
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
        if (movingDirection == Vector3D.ZERO) {
            return;
        }

        Vector3D nextPosition = this.position.add(deltaPosition);
        if (this.isNextMoveValid(nextPosition)) {
            this.setPosition(nextPosition);
        } else {
            movingDirection = Vector3D.ZERO;
        }
    }

    protected boolean isNextMoveValid(Vector3D nextPosition) {
        return GameContext.get().hitboxManager.isValid(this, nextPosition);
    }

    public Vector3D getMovingDirection() {
        return this.movingDirection;
    }

    protected void setMovingDirection(Vector3D movingDirection) {
        this.movingDirection = movingDirection;
        if (movingDirection.equals(Direction.WEST) || movingDirection.equals(Direction.EAST)) {
            this.facingDirection = movingDirection;
        }
    }

    public Vector3D getFacingDirection() {
        return this.facingDirection;
    }

    public Vector3D getFollowDirection(Vector3D targetPosition) {
        int up = Math.max(position.x - targetPosition.x, 0);
        int right = Math.max(targetPosition.y - position.y, 0);
        int down = Math.max(targetPosition.x - position.x, 0);
        int left = Math.max(position.y - targetPosition.y, 0);

        int v = Helper.random.nextInt(up + right + down + left);
        if (v < up)
            return Direction.NORTH;
        if (v < up + right)
            return Direction.EAST;
        if (v < up + right + down)
            return Direction.SOUTH;
        return Direction.WEST;
    }
}
