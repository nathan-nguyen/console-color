package com.noiprocs.core.model;

import java.util.Map;

public class ModelManager {
    private ServerModelManager serverModelManager = new ServerModelManager();

    public ServerModelManager getServerModelManager() {
        return serverModelManager;
    }

    public void setServerModelManager(ServerModelManager serverModelManager) {
        this.serverModelManager = serverModelManager;
    }

    public Map<String, Model> getModelMap() {
        return serverModelManager.getModelMap();
    }
}
