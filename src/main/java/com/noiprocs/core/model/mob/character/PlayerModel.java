package com.noiprocs.core.model.mob.character;

import com.noiprocs.core.common.Direction;
import com.noiprocs.core.common.Vector3D;
import com.noiprocs.core.model.LowLatencyModelInterface;
import com.noiprocs.core.model.action.AttackAction;
import com.noiprocs.core.model.item.Item;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PlayerModel extends Humanoid implements LowLatencyModelInterface {
  private static final Logger logger = LogManager.getLogger(PlayerModel.class);
  public int currentInventorySlot = 0;

  public PlayerModel(String id, Vector3D position, boolean isVisible) {
    super(position, isVisible);
    this.id = id;
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

  @Override
  public String toString() {
    return "Player: (" + id + ", " + position + ")";
  }

  public void setCurrentInventorySlot(int currentInventorySlot) {
    this.currentInventorySlot = currentInventorySlot;
  }

  public Item getHoldingItem() {
    return this.inventory.getItem(this.currentInventorySlot);
  }

  public void useItem() {
    Item item = inventory.getItem(this.currentInventorySlot);
    if (item == null) return;

    item.use(this);
    if (item.amount == 0) inventory.items[this.currentInventorySlot] = null;
  }

  public void triggerAction() {
    if (this.action != null && !this.action.isCompleted()) {
      return;
    }
    this.action = new AttackAction(new Vector3D(2, 1, 0), new Vector3D(-1, 0, 0));
  }
}
