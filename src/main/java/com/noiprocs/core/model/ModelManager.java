package com.noiprocs.core.model;

import com.noiprocs.core.GameContext;
import com.noiprocs.core.SaveLoadManager;
import com.noiprocs.core.config.Config;
import com.noiprocs.core.model.generator.WorldModelGenerator;
import com.noiprocs.core.model.mob.character.PlayerModel;
import org.apache.commons.lang3.SerializationUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

public class ModelManager {
    private static final Logger logger = LogManager.getLogger(ModelManager.class);

    private final GameContext gameContext;
    private final Queue<Model> spawnModelList = new LinkedList<>();

    public ServerModelManager serverModelManager;

    public ModelManager(GameContext gameContext) {
        this.gameContext = gameContext;
    }

    public Model getModel(String id) {
        if (gameContext.isServer) {
            ModelChunk modelChunk = serverModelManager.modelMap.get(id);

            if (modelChunk == null) return null;
            return modelChunk.get(id);
        }
        for (ModelChunk modelChunk : serverModelManager.chunkMap.values()) {
            if (modelChunk.map.containsKey(id)) return modelChunk.get(id);
        }
        return null;
    }

    public void addModel(Model model) {
        logger.debug("Adding Model: " + model.id + " - " + model.getClass());

        ModelChunk modelChunk = getChunkFromModelPosition(model.posX, model.posY);
        modelChunk.add(model.id, model);

        serverModelManager.modelMap.put(model.id, modelChunk);

        gameContext.spriteManager.synchronizeModelData(true);
    }

    public void removeModel(String id) {
        ModelChunk modelChunk = serverModelManager.modelMap.get(id);
        if (modelChunk != null) modelChunk.remove(id);

        serverModelManager.modelMap.remove(id);
    }

    public void switchChunkModel(Model model) {
        logger.debug("Switch chunk Model: " + model.id + " - " + model.getClass());
        removeModel(model.id);
        addModel(model);
    }

    /**
     * Load data from save file or generate new world.
     */
    public void startServer() {
        logger.info("Starting server");
        try {
            serverModelManager = SaveLoadManager.loadGameData();

            // Remove disconnected player when server starts
            this.removeDisconnectedPlayer();

            logger.debug("serverModelManager.modelMap: " + serverModelManager.modelMap);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();

            // Initialize a new Game
            serverModelManager = new ServerModelManager();
            new WorldModelGenerator(gameContext).generateWorld();
        }
    }

    /**
     * Send `join` command to server.
     */
    public void startClient() {
        logger.info("Starting client");
        serverModelManager = new ServerModelManager();
        gameContext.networkManager.sendDataToServer(("join " + gameContext.username).getBytes());
    }

    /**
     * This method is used only for Server, broadcast serverModelManager object to all clients
     */
    public void broadcastToClient() {
        try {
            gameContext.networkManager.clientIdMap.forEach((key, playerName) -> {
                int clientId = key;
                PlayerModel pm = serverModelManager.playerModelMap.get(playerName);

                Map<String, ModelChunk> chunkMap = new HashMap<>();
                getSurroundedChunk(pm).forEach(
                        modelChunk -> chunkMap.put(modelChunk.getChunkId(), modelChunk)
                );
                gameContext.networkManager.sentClientData(clientId, (Serializable) chunkMap);
            });
        }
        catch (Exception e) {
            logger.error("Failed to broadcast data!");
            e.printStackTrace();
        }
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
     * This method is for Client only.
     *
     * @param bytes ServerModelManager object.
     */
    public void updateSurroundedChunkFromServer(byte[] bytes) {
        try {
            serverModelManager.chunkMap = SerializationUtils.deserialize(bytes);
        }
        catch (Exception e) {
            logger.error("Failed to deserialized data from Server!");
            e.printStackTrace();
        }
    }

    public void update(int dt) {
        List<String> destroyModelId = new ArrayList<>();
        List<Model> switchChunkModelList = new ArrayList<>();

        // Get all distinct chunk surrounded players
        Set<ModelChunk> processChunk = gameContext.networkManager.getConnectedPlayerId().stream()
                .flatMap(
                        playerName -> {
                            PlayerModel pm = serverModelManager.playerModelMap.get(playerName);
                            return getSurroundedChunk(pm).stream();
                        }
                ).collect(Collectors.toSet());

        // Process models from surrounded chunks
        processChunk.forEach(
                modelChunk -> {
                    String currentChunkId = modelChunk.getChunkId();
                    modelChunk.getAllModel().forEach(model -> {
                        model.update(dt);

                        if (model.isDestroyed) destroyModelId.add(model.id);

                        String nextChunkId = getChunkIdFromModelPosition(model.posX, model.posY);
                        if (!nextChunkId.equals(currentChunkId)) switchChunkModelList.add(model);
                    });
                });

        // Removed destroyed models
        destroyModelId.forEach(this::removeModel);

        // Switch chunks
        switchChunkModelList.forEach(this::switchChunkModel);

        // Add spawn models
        while (!spawnModelList.isEmpty()) this.addModel(spawnModelList.poll());
    }

    /**
     * Save game data to disk
     */
    public void saveGameData() {
        SaveLoadManager.saveGameData(serverModelManager);
    }

    private void removeDisconnectedPlayer() {
        List<String> disconnectedPlayer = serverModelManager.playerModelMap.keySet().stream().filter(
                playername -> !playername.equals(gameContext.username)
        ).collect(Collectors.toList());

        if (Config.DISABLE_PLAYER) disconnectedPlayer.add(gameContext.username);
        disconnectedPlayer.forEach(model -> {
            logger.info("Removing player: " + model);
            removeModel(model);
        });
    }

    public void addModelList(Iterable<Model> modelList) {
        for (Model model : modelList) addModel(model);
    }

    /**
     * This method allows model to be spawned asynchronously.
     */
    public void addSpawnModel(Model model) {
        this.spawnModelList.offer(model);
    }

    private ModelChunk getChunkFromModelPosition(int posX, int posY) {
        int chunkX = posX / ModelChunk.CHUNK_HEIGHT;
        int chunkY = posY / ModelChunk.CHUNK_WIDTH;
        return getChunk(chunkX, chunkY);
    }

    private ModelChunk getChunk(int posX, int posY) {
        String chunkId = posX + "_" + posY;
        if (serverModelManager.chunkMap.containsKey(chunkId)) {
            return serverModelManager.chunkMap.get(chunkId);
        }

        ModelChunk modelChunk = new ModelChunk(posX, posY);
        serverModelManager.chunkMap.put(chunkId, modelChunk);
        return modelChunk;
    }

    public List<ModelChunk> getSurroundedChunk(int posX, int posY) {
        List<ModelChunk> result = new ArrayList<>();
        int chunkX = posX / ModelChunk.CHUNK_HEIGHT;
        int chunkY = posY / ModelChunk.CHUNK_WIDTH;

        for (int i = -1; i < 2; ++i) {
            for (int j = -1; j < 2; ++j) {
                result.add(getChunk(chunkX + i, chunkY + j));
            }
        }
        return result;
    }

    public List<ModelChunk> getSurroundedChunk(Model model) {
        return getSurroundedChunk(model.posX, model.posY);
    }

    private String getChunkIdFromModelPosition(int posX, int posY) {
        int chunkX = posX / ModelChunk.CHUNK_HEIGHT;
        int chunkY = posY / ModelChunk.CHUNK_WIDTH;
        return chunkX + "_" + chunkY;
    }

    public Iterable<ModelChunk> getLocalChunk() {
        return serverModelManager.chunkMap.values();
    }
}
