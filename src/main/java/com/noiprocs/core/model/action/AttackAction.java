package com.noiprocs.core.model.action;

import com.noiprocs.core.GameContext;
import com.noiprocs.core.common.Vector3D;
import com.noiprocs.core.model.InteractiveInterface;
import com.noiprocs.core.model.item.Item;
import com.noiprocs.core.model.mob.MobModel;
import com.noiprocs.core.model.mob.character.Humanoid;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AttackAction extends Action {
  private static final Logger logger = LogManager.getLogger(AttackAction.class);
  private final Vector3D hitboxDimension;
  private final Vector3D distance;

  public AttackAction(Vector3D hitboxDimension, Vector3D distance) {
    super(6, 2);
    this.hitboxDimension = hitboxDimension;
    this.distance = distance;
  }

  @Override
  void interactAction(MobModel model) {
    GameContext.get()
        .hitboxManager
        .getCollidingModel(model, model.getFacingDirection(), distance, hitboxDimension)
        .stream()
        .filter(collidingModel -> collidingModel instanceof InteractiveInterface)
        .forEach(
            interactModel -> {
              Item holdingItem =
                  model instanceof Humanoid ? ((Humanoid) model).getHoldingItem() : null;
              logger.info("Interact with model {}", interactModel);
              ((InteractiveInterface) interactModel).interact(model, holdingItem);
            });
  }
}
