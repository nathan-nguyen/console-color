package com.noiprocs.core.model.mob.character;

import com.noiprocs.core.model.InteractiveInterface;
import com.noiprocs.core.model.Model;
import com.noiprocs.core.util.Helper;

public class PlayerModel extends Model {
    private static final int HORIZONTAL_SPEED = 1;
    private static final int VERTICAL_SPEED = 1;

    public enum MovingDirection {
        STOP, UP, DOWN, LEFT, RIGHT
    }

    public enum Action {
        RIGHT_NA, LEFT_NA, RIGHT_ACTION, LEFT_ACTION
    }

    private MovingDirection movingDirection = MovingDirection.STOP;
    private int[] leftInteractionPoint, rightInteractionPoint;

    public Action action = Action.RIGHT_NA;
    public int actionCounter = 0;

    public PlayerModel(String id, int x, int y, boolean isPhysical) {
        super(x, y, isPhysical);
        this.id = id;
    }

    public void moveUp() {
        this.movingDirection = MovingDirection.UP;
        this.action = Action.RIGHT_NA;
    }

    public void moveDown() {
        this.movingDirection = MovingDirection.DOWN;
        this.action = Action.LEFT_NA;
    }

    public void moveLeft() {
        this.movingDirection = MovingDirection.LEFT;
        this.action = Action.LEFT_NA;
    }

    public void moveRight() {
        this.movingDirection = MovingDirection.RIGHT;
        this.action = Action.RIGHT_NA;
    }

    public void triggerAction() {
        if (action == Action.RIGHT_NA) action = Action.RIGHT_ACTION;
        else if (action == Action.LEFT_NA) action = Action.LEFT_ACTION;
    }

    private void stopAction() {
        if (action == Action.RIGHT_ACTION) action = Action.RIGHT_NA;
        else if (action == Action.LEFT_ACTION) action = Action.LEFT_NA;
        actionCounter = 0;
    }

    private void executeAction() {
        if (action != Action.RIGHT_ACTION && action != Action.LEFT_ACTION) return;
        ++actionCounter;
        if (actionCounter == 4) interactAction();
        if (actionCounter == 6) stopAction();
    }

    private void interactAction() {
        Model interactModel = null;
        if (action == Action.LEFT_ACTION) {
            interactModel = Helper.GAME_CONTEXT.hitboxManager.getModel(
                    posX + leftInteractionPoint[0],
                    posY + leftInteractionPoint[1],
                    id
            );
        }
        else if (action == Action.RIGHT_ACTION) {
            interactModel = Helper.GAME_CONTEXT.hitboxManager.getModel(
                    posX + rightInteractionPoint[0],
                    posY + rightInteractionPoint[1],
                    id
            );
        }
        if (interactModel != null && interactModel instanceof InteractiveInterface) {
            ((InteractiveInterface) interactModel).interact(this);
        }
    }

    public void stop() {
        this.movingDirection = MovingDirection.STOP;
    }

    @Override
    public void update(int dt) {
        this.move();
        this.executeAction();
    }

    private void move() {
        switch (movingDirection) {
            case STOP: return;
            case UP: if (Helper.GAME_CONTEXT.worldCounter % VERTICAL_SPEED == 0) move(-1, 0); break;
            case DOWN: if (Helper.GAME_CONTEXT.worldCounter % VERTICAL_SPEED == 0) move(1, 0); break;
            case LEFT: if (Helper.GAME_CONTEXT.worldCounter % HORIZONTAL_SPEED == 0) {
                move(0, -1);
                move(0, -1);
            } break;
            case RIGHT: if (Helper.GAME_CONTEXT.worldCounter % HORIZONTAL_SPEED == 0) {
                move(0, 1);
                move(0, 1);
            } break;
        }
    }

    private void move(int x, int y) {
        if (movingDirection != MovingDirection.STOP && Helper.GAME_CONTEXT.hitboxManager.isValid(this, posX + x, posY + y)) {
            posX += x;
            posY += y;
        }
        else movingDirection = MovingDirection.STOP;
    }

    @Override
    public String toString() {
        return "Player: " + id + " - posX: " + posX + " - posY: " + posY;
    }

    public void setInteractionPoint(int[] leftInteractionPoint, int[] rightInteractionPoint) {
        this.leftInteractionPoint = leftInteractionPoint;
        this.rightInteractionPoint = rightInteractionPoint;
    }
}
