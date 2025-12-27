package com.noiprocs.core.model.mob;

import com.noiprocs.core.common.Direction;
import com.noiprocs.core.common.Vector3D;
import com.noiprocs.core.model.InteractiveInterface;
import com.noiprocs.core.model.Model;
import com.noiprocs.core.model.behavior.ChasingBehavior;
import com.noiprocs.core.model.behavior.MovingRandomBehavior;
import com.noiprocs.core.model.item.AppleItemModel;
import com.noiprocs.core.model.item.AxeItem;
import com.noiprocs.core.model.item.Item;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CotMobModel extends MobModel implements InteractiveInterface {
  private static final Logger logger = LogManager.getLogger(CotMobModel.class);
  private static final int MAX_HEALTH = 20;
  private static final Vector3D DEFAULT_SPEED = new Vector3D(1, 1, 0);

  public CotMobModel(Vector3D position) {
    super(position, true, MAX_HEALTH, DEFAULT_SPEED);
    this.setMovingDirection(Direction.WEST);
    this.addBehavior(
        new MovingRandomBehavior(),
        new ChasingBehavior(
            AppleItemModel.class, new Vector3D(-10, -12, 0), new Vector3D(20, 20, 0)));
  }

  @Override
  public void interact(Model model, Item item) {
    if (item instanceof AxeItem) {
      this.updateHealth(-4);
    } else {
      this.updateHealth(-1);
    }
  }
}
