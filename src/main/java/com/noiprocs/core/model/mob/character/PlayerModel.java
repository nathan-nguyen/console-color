package com.noiprocs.core.model.mob.character;

import com.noiprocs.core.GameContext;
import com.noiprocs.core.model.InteractiveInterface;
import com.noiprocs.core.model.ItemModelInterface;
import com.noiprocs.core.model.LowLatencyModelInterface;
import com.noiprocs.core.model.Model;
import com.noiprocs.core.model.item.Item;
import com.noiprocs.core.model.mob.MobModel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class PlayerModel extends MobModel implements LowLatencyModelInterface {
    private static final Logger logger = LogManager.getLogger(PlayerModel.class);
    private static final int MAX_HEALTH = 100;
    private static final int DEFAULT_SKIP_MOVEMENT_FRAME = 6;
    private static final int HORIZONTAL_SPEED = 2, VERTICAL_SPEED = 1;
    private static final int MAX_INVENTORY_SIZE = 4;

    public int actionCounter = 0;
    public Item[] inventory = new Item[MAX_INVENTORY_SIZE];
    public int currentInventorySlot = 0;

    public PlayerModel(String id, int x, int y, boolean isVisible) {
        super(x, y, isVisible, MAX_HEALTH, HORIZONTAL_SPEED, VERTICAL_SPEED);
        this.id = id;
        this.skipMovementFrame = DEFAULT_SKIP_MOVEMENT_FRAME;
    }

    public void moveUp() {
        this.setMovingDirection(MovingDirection.UP);
    }

    public void moveDown() {
        this.setMovingDirection(MovingDirection.DOWN);
    }

    public void moveLeft() {
        this.setMovingDirection(MovingDirection.LEFT);
    }

    public void moveRight() {
        this.setMovingDirection(MovingDirection.RIGHT);
    }

    public void triggerAction() {
        if (actionCounter > 0) return;
        actionCounter = 6;
    }

    // Absorb nearby items
    private void absorbItems() {
        List<Model> collidingModels = GameContext.get().hitboxManager.getCollidingModel(this, this.posX, this.posY);
        for (Model item: collidingModels) {
            if (item instanceof ItemModelInterface) {
                logger.info("Absorbed item {}", item);
                ((ItemModelInterface) item).interact(this, inventory[currentInventorySlot]);
            }
        }
    }

    private void updateAction() {
        if (actionCounter == 0) return;

        --actionCounter;
        if (actionCounter == 2) interactAction();
    }

    private void interactAction() {
        List<Model> collidingModels = null;
        if (facingDirection == MovingDirection.RIGHT) {
            collidingModels = GameContext.get().hitboxManager.getCollidingModel(this, 0, 1, -1, 0, 2, 1);
        }
        else if (facingDirection == MovingDirection.LEFT) {
            collidingModels = GameContext.get().hitboxManager.getCollidingModel(this, 0, -1, -1, 0, 2, 1);
        }

        if (collidingModels != null && !collidingModels.isEmpty()) {
            Model interactModel = collidingModels.get(0);
            if (interactModel instanceof InteractiveInterface) {
                logger.info("Interact with model {}", interactModel);
                ((InteractiveInterface) interactModel).interact(this, inventory[currentInventorySlot]);
            }
        }
    }

    public void stop() {
        this.setMovingDirection(MovingDirection.STOP);
    }

    @Override
    public void update(int dt) {
        super.update(dt);
        this.updateAction();
    }

    @Override
    protected void move() {
        super.move();
        if (GameContext.get().worldCounter % this.skipMovementFrame == 0) {
            this.absorbItems();
        }
    }

    @Override
    public String toString() {
        return "Player: (" + id + ", " + posX + ", " + posY + ")";
    }

    public Item getCurrentInventoryItem() {
        return this.inventory[currentInventorySlot];
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
