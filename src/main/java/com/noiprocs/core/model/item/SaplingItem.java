package com.noiprocs.core.model.item;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.noiprocs.core.GameContext;
import com.noiprocs.core.model.Model;
import com.noiprocs.core.model.plant.TreeModel;

public class SaplingItem extends Item {
    private static final Logger logger = LogManager.getLogger(SaplingItem.class);
    private static final String ITEM_NAME = "Sapling";

    public SaplingItem(int amount) {
        super(ITEM_NAME, amount);
    }

    @Override
    public void use(Model model) {
        logger.info("Use {}", this);
        if (GameContext.get().modelManager.spawnModelIfValid(new TreeModel(model.posX, model.posY, 0))) {
            --amount;
        }
    }
}
