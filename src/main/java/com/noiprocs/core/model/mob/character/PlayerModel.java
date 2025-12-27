package com.noiprocs.core.model.mob.character;

import com.noiprocs.core.GameContext;
import com.noiprocs.core.common.Direction;
import com.noiprocs.core.common.Vector3D;
import com.noiprocs.core.model.InteractiveInterface;
import com.noiprocs.core.model.LowLatencyModelInterface;
import com.noiprocs.core.model.Model;
import com.noiprocs.core.model.behavior.AbsorbNearbyItemBehavior;
import com.noiprocs.core.model.behavior.MovingBehavior;
import com.noiprocs.core.model.item.Item;
import com.noiprocs.core.model.mob.MobModel;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PlayerModel extends MobModel implements LowLatencyModelInterface {
  private static final Logger logger = LogManager.getLogger(PlayerModel.class);
  private static final int MAX_HEALTH = 100;
  private static final int DEFAULT_SKIP_MOVEMENT_FRAME = 6;
  private static final Vector3D DEFAULT_SPEED = new Vector3D(1, 2, 0);
  private static final int MAX_INVENTORY_SIZE = 4;

  public int actionCounter = 0;
  public Item[] inventory = new Item[MAX_INVENTORY_SIZE];
  public int currentInventorySlot = 0;

  public PlayerModel(String id, Vector3D position, boolean isVisible) {
    super(position, isVisible, MAX_HEALTH, DEFAULT_SPEED);
    this.id = id;
    this.skipMovementFrame = DEFAULT_SKIP_MOVEMENT_FRAME;
    this.addBehavior(new MovingBehavior(), new AbsorbNearbyItemBehavior());
  }

  public void moveUp() {
    this.setMovingDirection(Direction.NORTH);
  }

  public void moveDown() {
    this.setMovingDirection(Direction.SOUTH);
  }

  public void moveLeft() {
    this.setMovingDirection(Direction.WEST);
  }

  public void moveRight() {
    this.setMovingDirection(Direction.EAST);
  }

  public void stop() {
    this.setMovingDirection(Vector3D.ZERO);
  }

  public void triggerAction() {
    if (actionCounter > 0) {
      return;
    }
    actionCounter = 6;
  }

  private void updateAction() {
    if (actionCounter == 0) return;

    --actionCounter;
    if (actionCounter == 2) interactAction();
  }

  private void interactAction() {
    List<Model> collidingModels = null;
    Vector3D hitboxDimension = new Vector3D(2, 1, 0);
    Vector3D distance = new Vector3D(-1, 0, 0);
    collidingModels =
        GameContext.get()
            .hitboxManager
            .getCollidingModel(this, facingDirection, distance, hitboxDimension);

    if (collidingModels != null && !collidingModels.isEmpty()) {
      Model interactModel = collidingModels.get(0);
      if (interactModel instanceof InteractiveInterface) {
        logger.info("Interact with model {}", interactModel);
        ((InteractiveInterface) interactModel).interact(this, inventory[currentInventorySlot]);
      }
    }
  }

  @Override
  public void update(int dt) {
    super.update(dt);
    this.updateAction();
  }

  @Override
  public String toString() {
    return "Player: (" + id + ", " + position + ")";
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
