package com.noiprocs.core.model;

import com.noiprocs.core.model.mob.character.PlayerModel;
import com.noiprocs.core.util.Helper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class ServerModelManager implements Serializable {
    private static final Logger logger = LoggerFactory.getLogger(ServerModelManager.class);
    private Map<String, Model> modelMap = new HashMap<>();

    public Map<String, PlayerModel> playerModelMap = new HashMap<>();

    public void addModel(Model model) {
        logger.info(this.getClass() + " - Adding Model: " + model.id);

        // Special treatment when adding PlayerModel
        if (model instanceof PlayerModel) {
            logger.info("Adding PlayerModel");
        }

        modelMap.put(model.id, model);
        Helper.GAME_CONTEXT.spriteManager.synchronizeModelData(true);
    }

    public void addPlayer(String playerName) {
        if (!playerModelMap.containsKey(playerName)) {
            PlayerModel pm = new PlayerModel(playerName, 0, 0, true);
            playerModelMap.put(playerName, pm);
        }
        this.addModel(playerModelMap.get(playerName));
    }

    public void removeModel(String id) {
        modelMap.remove(id);
    }

    public Map<String, Model> getModelMap() {
        return modelMap;
    }
}
