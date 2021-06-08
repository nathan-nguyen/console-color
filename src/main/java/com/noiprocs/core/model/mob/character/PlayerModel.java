package com.noiprocs.core.model.mob.character;

import com.noiprocs.core.config.Config;
import com.noiprocs.core.model.InteractiveInterface;
import com.noiprocs.core.model.Model;
import com.noiprocs.core.model.item.Item;
import com.noiprocs.core.util.Helper;

public class PlayerModel extends Model {
    private static final int HORIZONTAL_SPEED = Config.MAX_FPS / 15;
    private static final int VERTICAL_SPEED = Config.MAX_FPS / 15;
    private static final int MAX_INVENTORY_SIZE = 10;

    public enum MovingDirection {
        STOP, UP, DOWN, LEFT, RIGHT
    }

    public enum Action {
        RIGHT_NA, LEFT_NA, RIGHT_ACTION, LEFT_ACTION
    }

    private MovingDirection movingDirection = MovingDirection.STOP;
    private int[][] leftInteractionPoint, rightInteractionPoint;

    public Action action = Action.RIGHT_NA;
    public int actionCounter = 0;
    public Item[] inventory = new Item[MAX_INVENTORY_SIZE];


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
            interactModel = Helper.GAME_CONTEXT.hitboxManager.getModel(posX, posY, id, leftInteractionPoint);
        }
        else if (action == Action.RIGHT_ACTION) {
            interactModel = Helper.GAME_CONTEXT.hitboxManager.getModel(posX, posY, id, rightInteractionPoint);
        }
        if (interactModel instanceof InteractiveInterface) {
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
        if (movingDirection != MovingDirection.STOP
                && Helper.GAME_CONTEXT.hitboxManager.isValid(this, posX + x, posY + y)) {
            posX += x;
            posY += y;
        }
        else movingDirection = MovingDirection.STOP;
    }

    @Override
    public String toString() {
        return "Player: " + id + " - posX: " + posX + " - posY: " + posY;
    }

    public void setInteractionPoint(int[][] leftInteractionPoint, int[][] rightInteractionPoint) {
        this.leftInteractionPoint = leftInteractionPoint;
        this.rightInteractionPoint = rightInteractionPoint;
    }

    public boolean addInventoryItem(Item item) {
        int emptySlot = Integer.MAX_VALUE;
        for (int i = 0; i < MAX_INVENTORY_SIZE; ++i) {
            Item existingItem = inventory[i];
            if (existingItem == null) emptySlot = Math.min(emptySlot, i);
            if (existingItem != null && existingItem.name.equals(item.name)) {
                existingItem.amount += item.amount;
                return true;
            }
        }
        if (emptySlot > MAX_INVENTORY_SIZE) return false;
        inventory[emptySlot] = item;
        return true;
    }
}
