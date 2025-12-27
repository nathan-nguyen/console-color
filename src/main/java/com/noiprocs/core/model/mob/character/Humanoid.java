package com.noiprocs.core.model.mob.character;

import com.noiprocs.core.GameContext;
import com.noiprocs.core.common.Vector3D;
import com.noiprocs.core.model.InteractiveInterface;
import com.noiprocs.core.model.Model;
import com.noiprocs.core.model.behavior.AbsorbNearbyItemBehavior;
import com.noiprocs.core.model.behavior.MovingBehavior;
import com.noiprocs.core.model.item.Item;
import com.noiprocs.core.model.mob.MobModel;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Humanoid extends MobModel {
  private static final Logger logger = LogManager.getLogger(Humanoid.class);

  private static final int MAX_HEALTH = 100;
  private static final int DEFAULT_SKIP_MOVEMENT_FRAME = 6;
  private static final Vector3D DEFAULT_SPEED = new Vector3D(1, 2, 0);
  private static final int MAX_INVENTORY_SIZE = 4;

  public final Inventory inventory = new Inventory(MAX_INVENTORY_SIZE);

  public int actionCounter = 0;

  public Humanoid(Vector3D position, boolean isVisible) {
    super(position, isVisible, MAX_HEALTH, DEFAULT_SPEED);
    this.skipMovementFrame = DEFAULT_SKIP_MOVEMENT_FRAME;
    this.addBehavior(new MovingBehavior(), new AbsorbNearbyItemBehavior());
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
        ((InteractiveInterface) interactModel).interact(this, this.getHoldingItem());
      }
    }
  }

  public Item getHoldingItem() {
    return null;
  }

  @Override
  public void update(int dt) {
    super.update(dt);
    this.updateAction();
  }
}
