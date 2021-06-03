package com.noiprocs.core.model;

import com.noiprocs.core.config.Config;
import com.noiprocs.core.model.mob.character.PlayerModel;

import java.util.Map;

public class ModelManager {
    private ServerModelManager serverModelManager = new ServerModelManager();
    private PlayerModel playerModel;

    public ServerModelManager getServerModelManager() {
        return serverModelManager;
    }

    public void setServerModelManager(ServerModelManager serverModelManager) {
        this.serverModelManager = serverModelManager;
    }

    public Map<String, Model> getModelMap() {
        return serverModelManager.getModelMap();
    }

    public PlayerModel getPlayerModel() {
        if (playerModel == null) playerModel = (PlayerModel) getModelMap().get(Config.USER_NAME);
        return playerModel;
    }
}
