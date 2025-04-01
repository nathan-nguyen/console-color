package com.noiprocs.core.model.mob.character;

import com.noiprocs.core.GameContext;
import com.noiprocs.core.model.InteractiveInterface;
import com.noiprocs.core.model.ItemModelInterface;
import com.noiprocs.core.model.LowLatencyModelInterface;
import com.noiprocs.core.model.Model;
import com.noiprocs.core.model.item.Item;
import com.noiprocs.core.model.mob.MobModel;

import java.util.List;

public class PlayerModel extends MobModel implements LowLatencyModelInterface {
    private static final int DEFAULT_SKIP_MOVEMENT_FRAME = 6;
    private static final int HORIZONTAL_SPEED = 2, VERTICAL_SPEED = 1;
    private static final int MAX_INVENTORY_SIZE = 4;

    public enum Action {
        RIGHT_NA, LEFT_NA, RIGHT_ACTION, LEFT_ACTION
    }

    public Action action = Action.RIGHT_NA;
    public int actionCounter = 0;
    public Item[] inventory = new Item[MAX_INVENTORY_SIZE];
    public int currentInventorySlot = 0;


    public PlayerModel(String id, int x, int y, boolean isVisible) {
        super(x, y, isVisible, HORIZONTAL_SPEED, VERTICAL_SPEED);
        this.id = id;
        this.skipMovementFrame = DEFAULT_SKIP_MOVEMENT_FRAME;
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

    // Absorb items nearby
    private void absorbItems() {
        List<Model> collidingModels = GameContext.get().hitboxManager.getCollidingModel(this);
        for (Model item: collidingModels) {
            if (item instanceof ItemModelInterface) {
                ((ItemModelInterface) item).interact(this);
            }
        }
    }

    private void executeAction() {
        if (action != Action.RIGHT_ACTION && action != Action.LEFT_ACTION) return;
        ++actionCounter;
        if (actionCounter == 4) interactAction();
        if (actionCounter == 6) stopAction();
    }

    private void interactAction() {
        List<Model> collidingModels = null;
        if (action == Action.RIGHT_ACTION) {
            collidingModels = GameContext.get().hitboxManager.getCollidingModel(this, 0, 1, 0, 0, 1, 1);
        }
        else if (action == Action.LEFT_ACTION) {
            collidingModels = GameContext.get().hitboxManager.getCollidingModel(this, 0, -1, 0, 0, 1, 1);
        }

        if (collidingModels != null && !collidingModels.isEmpty()) {
            Model interactModel = collidingModels.get(0);
            if (interactModel instanceof InteractiveInterface) {
                ((InteractiveInterface) interactModel).interact(this);
            }
        }
    }

    public void stop() {
        this.movingDirection = MovingDirection.STOP;
    }

    @Override
    public void update(int dt) {
        super.update(dt);
        this.executeAction();
        if (GameContext.get().worldCounter % skipMovementFrame == 0) {
            this.absorbItems();
        }
    }

    @Override
    public String toString() {
        return "Player: " + id + " - posX: " + posX + " - posY: " + posY;
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

    public void setCurrentInventorySlot(int currentInventorySlot) {
        this.currentInventorySlot = currentInventorySlot;
    }

    public void useItem() {
        Item item = inventory[currentInventorySlot];
        if (item == null) return;

        item.use(this);
        if (item.amount == 0) inventory[currentInventorySlot] = null;
    }
}
