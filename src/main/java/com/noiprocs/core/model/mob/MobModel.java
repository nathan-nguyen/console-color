package com.noiprocs.core.model.mob;

import com.noiprocs.core.GameContext;
import com.noiprocs.core.model.Model;

public abstract class MobModel extends Model {
    private static final int DEFAULT_SKIP_MOVEMENT_FRAME = 2;
    public enum MovingDirection {
        STOP, UP, DOWN, LEFT, RIGHT
    }

    public MovingDirection movingDirection = MovingDirection.STOP;
    protected int skipMovementFrame = DEFAULT_SKIP_MOVEMENT_FRAME;
    private final int horizontalSpeed;
    private final int verticalSpeed;

    public MobModel(int x,
                    int y,
                    boolean isVisible,
                    int hitboxHeight,
                    int hitboxWidth,
                    int horizontalSpeed,
                    int verticalSpeed) {
        super(x, y, isVisible, hitboxHeight, hitboxWidth);
        this.horizontalSpeed = horizontalSpeed;
        this.verticalSpeed = verticalSpeed;
    }

    @Override
    public void update(int delta) {
        if (GameContext.get().worldCounter % skipMovementFrame == 0) {
            this.move();
        }
    }

    private void move() {
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

    protected void move(int x, int y) {
        if (movingDirection == MovingDirection.STOP) return;

        if (GameContext.get().hitboxManager.isValid(this, posX + x, posY + y)) {
            posX += x;
            posY += y;
        }
        else movingDirection = MovingDirection.STOP;
    }
}
