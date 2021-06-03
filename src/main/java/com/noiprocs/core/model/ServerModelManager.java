package com.noiprocs.core.model;

import com.noiprocs.core.model.mob.character.PlayerModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class ServerModelManager implements Serializable {
    private static final Logger logger = LoggerFactory.getLogger(ServerModelManager.class);

    private Map<String, Model> modelMap = new HashMap<>();

    public void addModel(Model model) {
        logger.info(this.getClass() + " - Adding Model: " + model.id);

        // Special treatment when adding PlayerModel
        if (model instanceof PlayerModel) {
            logger.info("Adding PlayerModel");
        }

        modelMap.put(model.id, model);
    }

    public Map<String, Model> getModelMap() {
        return modelMap;
    }
}
