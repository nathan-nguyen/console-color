package com.noiprocs.core.model.item;

import com.noiprocs.core.GameContext;
import com.noiprocs.core.model.Model;
import com.noiprocs.core.model.building.FenceModel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class FenceItem extends Item {
  private static final Logger logger = LogManager.getLogger(FenceItem.class);
  private static final String FENCE_ITEM_NAME = "Fence";

  public FenceItem(int amount) {
    super(FENCE_ITEM_NAME, amount);
  }

  @Override
  public void use(Model model) {
    logger.info("Use {}", this);
    if (GameContext.get().modelManager.spawnModelIfValid(new FenceModel(model.position))) {
      --amount;
    } else {
      logger.info("Cannot place {} at position {}", this, model.position);
    }
  }
}
