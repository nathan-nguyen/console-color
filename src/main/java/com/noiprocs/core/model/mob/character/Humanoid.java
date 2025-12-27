package com.noiprocs.core.model.mob.character;

import com.noiprocs.core.common.Vector3D;
import com.noiprocs.core.model.behavior.AbsorbNearbyItemBehavior;
import com.noiprocs.core.model.behavior.MovingBehavior;
import com.noiprocs.core.model.item.Item;
import com.noiprocs.core.model.mob.MobModel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Humanoid extends MobModel {
  private static final Logger logger = LogManager.getLogger(Humanoid.class);

  private static final int MAX_HEALTH = 100;
  private static final int DEFAULT_SKIP_MOVEMENT_FRAME = 6;
  private static final Vector3D DEFAULT_SPEED = new Vector3D(1, 2, 0);
  private static final int MAX_INVENTORY_SIZE = 4;

  public final Inventory inventory = new Inventory(MAX_INVENTORY_SIZE);

  public Humanoid(Vector3D position, boolean isVisible) {
    super(position, isVisible, MAX_HEALTH, DEFAULT_SPEED);
    this.skipMovementFrame = DEFAULT_SKIP_MOVEMENT_FRAME;
    this.addBehavior(new MovingBehavior(), new AbsorbNearbyItemBehavior());
  }

  public Item getHoldingItem() {
    return null;
  }
}
