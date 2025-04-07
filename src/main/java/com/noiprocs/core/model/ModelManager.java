package com.noiprocs.core.model;

import com.noiprocs.core.GameContext;
import com.noiprocs.core.SaveLoadManager;
import com.noiprocs.core.config.Config;
import com.noiprocs.core.model.generator.WorldModelGenerator;
import com.noiprocs.core.model.mob.character.PlayerModel;
import com.noiprocs.core.network.NetworkSerializationUtils;
import com.noiprocs.core.util.MetricCollector;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.Serializable;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ModelManager {
    private static final Logger logger = LogManager.getLogger(ModelManager.class);

    private final GameContext gameContext;
    private final Queue<Model> spawnModelQueue = new ConcurrentLinkedQueue<>();
    private final Queue<String> destroyModelIdQueue = new ConcurrentLinkedQueue<>();
    private ServerModelManager serverModelManager;

    public ModelManager(GameContext gameContext) {
        this.gameContext = gameContext;
    }

    public Model getModel(String id) {
        if (this.serverModelManager == null) return null;

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

    public void spawnModel(Model model) {
        this.spawnModelQueue.offer(model);
    }

    // Use this method with care because it could cause ConcurrentModificationException
    // Use spawnModelQueue instead.
    private void addModel(Model model) {
        logger.debug("Adding Model: {} - {}", model.id, model.getClass());

        ModelChunk modelChunk = getChunkFromModelPosition(model.posX, model.posY);
        modelChunk.add(model.id, model);

        serverModelManager.modelMap.put(model.id, modelChunk);
    }

    public void destroyModelById(String id) {
        this.destroyModelIdQueue.offer(id);
    }

    private void removeModel(String id) {
        ModelChunk modelChunk = serverModelManager.modelMap.get(id);
        if (modelChunk != null) modelChunk.remove(id);

        serverModelManager.modelMap.remove(id);
    }

    private void switchChunkModel(Model model) {
        logger.debug("Switch chunk Model: {} - {}", model.id, model.getClass());
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

            logger.debug("serverModelManager.modelMap: {}", serverModelManager.modelMap);
        } catch (IOException | ClassNotFoundException e) {
            logger.error("Failed to load save file!", e);

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
            gameContext.networkManager.clientIdMap.forEach((clientId, playerName) -> {
                PlayerModel pm = serverModelManager.playerModelMap.get(playerName);

                Map<String, ModelChunk> chunkMap = new HashMap<>();
                getSurroundedChunk(pm).forEach(
                        modelChunk -> chunkMap.put(modelChunk.getChunkId(), modelChunk)
                );
                gameContext.networkManager.sendClientData(clientId, (Serializable) chunkMap);
            });
        }
        catch (Exception e) {
            // Reason: clientIdMap is updated.
            logger.error("Failed to broadcast data!", e);
        }
    }

    /**
     * Add Client Player Model to serverModelManager.modelMap
     *
     * @param playerName: Client Player Username
     */
    public void addPlayerModel(String playerName) {
        PlayerModel playerModel = serverModelManager.playerModelMap.computeIfAbsent(
                playerName, key -> new PlayerModel(key, 0, 0, true)
        );
        // Player initial state must be STOP mode
        playerModel.stop();

        this.spawnModelQueue.offer(playerModel);
    }

    /**
     * Update ServerModelManager from byte array.
     * This method is for Client only.
     *
     * @param bytes ServerModelManager object.
     */
    public void updateSurroundedChunkFromServer(byte[] bytes) {
        try {
            serverModelManager.chunkMap = NetworkSerializationUtils.deserialize(bytes);
        }
        catch (Exception e) {
            // Reason: Due to serialization object under synchronization, server could send corrupted data.
            logger.error("Failed to deserialized data from Server!", e);
        }
    }

    public void update(int dt) {
        List<Model> switchChunkModelList = new ArrayList<>();

        // Get all distinct chunk surrounded players
        Set<ModelChunk> processChunk = gameContext.networkManager.getConnectedPlayerId().stream()
                .flatMap(
                        playerName -> {
                            PlayerModel pm = serverModelManager.playerModelMap.get(playerName);
                            return pm == null ? Stream.empty() : getSurroundedChunk(pm).stream();
                        }
                ).collect(Collectors.toSet());

        // Process models from surrounded chunks
        long statsTime = System.nanoTime();
        processChunk.parallelStream().forEach(
                modelChunk -> {
                    String currentChunkId = modelChunk.getChunkId();
                    modelChunk.map.values().parallelStream().forEach(model -> {
                        if (!(model instanceof LowLatencyModelInterface)
                                // Add model.hashCode() to avoid all models being updated at same tick
                                && (gameContext.worldCounter + model.hashCode()) % Config.GAME_TICK_FRAMES != 0) {
                            return;
                        }
                        model.update(dt);

                        if (model.isDestroyed) destroyModelIdQueue.offer(model.id);

                        String nextChunkId = getChunkIdFromModelPosition(model.posX, model.posY);
                        if (!nextChunkId.equals(currentChunkId)) switchChunkModelList.add(model);
                    });
                });
        MetricCollector.updateModelTimeNs.add(System.nanoTime() - statsTime);

        // Removed destroyed models
        while (!destroyModelIdQueue.isEmpty()) {
            this.removeModel(destroyModelIdQueue.poll());
        }

        // Switch chunks
        statsTime = System.nanoTime();
        switchChunkModelList.forEach(this::switchChunkModel);
        MetricCollector.switchChunkTimeNs.add(System.nanoTime() - statsTime);

        // Add spawn models
        while (!spawnModelQueue.isEmpty()) this.addModel(spawnModelQueue.poll());
    }

    /**
     * Save game data to disk
     */
    public void saveGameData() {
        SaveLoadManager.saveGameData(serverModelManager);
    }

    private void removeDisconnectedPlayer() {
        List<String> disconnectedPlayer = serverModelManager.playerModelMap.keySet().stream().filter(
                playerName -> !playerName.equals(gameContext.username)
        ).collect(Collectors.toList());

        if (Config.DISABLE_PLAYER) disconnectedPlayer.add(gameContext.username);
        disconnectedPlayer.forEach(model -> {
            logger.info("Removing player: {}", model);
            removeModel(model);
        });
    }

    public void addModelList(Iterable<Model> modelList) {
        for (Model model : modelList) addModel(model);
    }

    // Spawn model without checking whether the current position is valid
    public void addSpawnModel(Model... models) {
        this.spawnModelQueue.addAll(Arrays.asList(models));
    }

    // Spawn model only if current position is valid
    public void spawnModelIfValid(Model... models) {
        for (Model model: models) {
            if (gameContext.hitboxManager.isValid(model, model.posX, model.posY)) {
                this.spawnModelQueue.offer(model);
            }
        }
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

    public Stream<ModelChunk> getLocalChunk() {
        return serverModelManager != null ? serverModelManager.chunkMap.values().stream() : Stream.empty();
    }
}
