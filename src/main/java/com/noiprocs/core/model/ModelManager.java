package com.noiprocs.core.model;

import com.noiprocs.core.GameContext;
import com.noiprocs.core.SaveLoadManager;
import com.noiprocs.core.config.Config;
import com.noiprocs.core.model.generator.WorldModelGenerator;
import com.noiprocs.core.model.mob.character.PlayerModel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ModelManager {
    private static final Logger logger = LogManager.getLogger(ModelManager.class);

    private final GameContext gameContext;
    private List<Model> spawnModelList = new ArrayList<>();

    public ServerModelManager serverModelManager;

    public ModelManager(GameContext gameContext) {
        this.gameContext = gameContext;
    }

    public Map<String, Model> getModelMap() {
        return serverModelManager.modelMap;
    }

    public Model getModel(String id) {
        return serverModelManager.modelMap.get(id);
    }

    public void removeModel(String id) {
        serverModelManager.modelMap.remove(id);
    }

    /**
     * If server: Load data from save file or generate new world.
     * If client: Send join command to server.
     */
    public void start() {
        if (gameContext.isServer) {
            try {
                serverModelManager = SaveLoadManager.loadGameData();

                // Remove disconnected player when server starts
                this.removeDisconnectedPlayer();

                logger.info("serverModelManager.modelMap: " + serverModelManager.modelMap);
                logger.info("serverModelManager.playerModelMap: " + serverModelManager.playerModelMap);
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();

                // Initialize a new Game
                serverModelManager = new ServerModelManager();
                new WorldModelGenerator(gameContext).generateWorld();
            }
        } else {
            serverModelManager = new ServerModelManager();
            gameContext.networkManager.broadcastDataOverNetwork("join " + gameContext.username);
        }
    }

    /**
     * This method is used only for Server, broadcast serverModelManager object to all clients
     */
    public void broadcastToClient() {
        if (!gameContext.isServer) return;

        gameContext.networkManager.broadcastDataOverNetwork(serverModelManager);
    }

    public void addModel(Model model) {
        logger.info(this.getClass() + " - Adding Model: " + model.id + " - " + model.getClass());
        serverModelManager.modelMap.put(model.id, model);
        gameContext.spriteManager.synchronizeModelData(true);
    }

    /**
     * Add Client Player Model to serverModelManager.modelMap
     *
     * @param playerName: Client Player Username
     */
    public void addPlayerModel(String playerName) {
        PlayerModel playerModel;
        if (!serverModelManager.playerModelMap.containsKey(playerName)) {
            playerModel = new PlayerModel(playerName, 0, 0, true);
            serverModelManager.playerModelMap.put(playerName, playerModel);
        } else playerModel = serverModelManager.playerModelMap.get(playerName);

        // Player initial state must be STOP mode
        playerModel.stop();

        this.addModel(playerModel);
    }

    /**
     * Update ServerModelManager from byte array.
     * This method is for Client only
     *
     * @param object ServerModelManager object.
     */
    public void updateServerModelManager(Object object) {
        this.serverModelManager = (ServerModelManager) object;
    }

    public void update(int dt) {
        List<String> destroyModelId = new ArrayList<>();
        spawnModelList.clear();

        serverModelManager.modelMap.values().forEach(model -> {
            model.update(dt);
            if (model.isDestroyed) destroyModelId.add(model.id);
        });

        // Removed destroyed models
        destroyModelId.forEach(id -> serverModelManager.modelMap.remove(id));

        // Add spawn models
        spawnModelList.forEach(this::addModel);

        this.broadcastToClient();

        if (gameContext.isServer && gameContext.worldCounter % Config.AUTO_SAVE_DURATION == 0) {
            this.saveGameData();
        }
    }

    /**
     * Save game data to disk
     */
    public void saveGameData() {
        SaveLoadManager.saveGameData(serverModelManager);
    }

    private void removeDisconnectedPlayer() {
        List<String> disconnectedPlayer = new ArrayList<>();

        for (Model model : serverModelManager.modelMap.values()) {
            if (model instanceof PlayerModel && !model.id.equals(gameContext.username)) {
                disconnectedPlayer.add(model.id);
            }
        }

        if (Config.DISABLE_PLAYER) disconnectedPlayer.add(gameContext.username);
        for (String disconnectedUsername : disconnectedPlayer) this.removeModel(disconnectedUsername);
    }

    public void addModelList(Iterable<Model> modelList) {
        for (Model model : modelList) addModel(model);
    }

    public void addSpawnModel(Model model) {
        this.spawnModelList.add(model);
    }
}
