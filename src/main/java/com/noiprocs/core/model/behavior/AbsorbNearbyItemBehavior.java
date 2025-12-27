package com.noiprocs.core.model.behavior;

import com.noiprocs.core.GameContext;
import com.noiprocs.core.model.ItemModelInterface;
import com.noiprocs.core.model.Model;
import com.noiprocs.core.model.mob.character.Humanoid;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AbsorbNearbyItemBehavior implements BehaviorInterface {
  private static final Logger logger = LogManager.getLogger(AbsorbNearbyItemBehavior.class);

  @Override
  public void update(Model model) {
    Humanoid humanoidModel = (Humanoid) model;
    List<Model> collidingModels =
        GameContext.get().hitboxManager.getCollidingModel(model, model.getPosition());
    for (Model item : collidingModels) {
      if (item instanceof ItemModelInterface) {
        logger.info("Absorbed item {}", item);
        ((ItemModelInterface) item).interact(model, humanoidModel.getHoldingItem());
      }
    }
  }
}
