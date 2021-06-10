package com.noiprocs.core.model;

import com.noiprocs.core.GameContext;
import com.noiprocs.core.SaveLoadManager;
import com.noiprocs.core.config.Config;
import com.noiprocs.core.model.generator.WorldModelGenerator;
import com.noiprocs.core.model.mob.character.PlayerModel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
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
            ModelChunkManager mcm = serverModelManager.modelMap.get(id);

            if (mcm == null) return null;
            return mcm.get(id);
        }
        for (ModelChunkManager mcm : serverModelManager.chunkMap.values()) {
            if (mcm.map.containsKey(id)) return mcm.get(id);
        }
        return null;
    }

    public void addModel(Model model) {
        logger.info("Adding Model: " + model.id + " - " + model.getClass());

        ModelChunkManager mcm = getChunkFromModelPosition(model.posX, model.posY);
        mcm.add(model.id, model);

        serverModelManager.modelMap.put(model.id, mcm);

        gameContext.spriteManager.synchronizeModelData(true);
    }

    public void removeModel(String id) {
        ModelChunkManager mcm = serverModelManager.modelMap.get(id);
        if (mcm != null) mcm.remove(id);

        serverModelManager.modelMap.remove(id);
    }

    public void switchChunkModel(Model model) {
        logger.info("Switch chunk Model: " + model.id + " - " + model.getClass());
        removeModel(model.id);
        addModel(model);
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
        // Avoid ConcurrentModificationException
        synchronized (gameContext.networkManager.clientIdMap) {
            gameContext.networkManager.clientIdMap.forEach((clientId, playerName) -> {
                PlayerModel pm = serverModelManager.playerModelMap.get(playerName);

                Map<String, ModelChunkManager> chunkMap = new HashMap<>();
                getSurroundedChunk(pm).forEach(
                        mcm -> chunkMap.put(mcm.getChunkId(), mcm)
                );
                gameContext.networkManager.sentClientData(clientId, chunkMap);
            });
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
     * This method is for Client only
     *
     * @param object ServerModelManager object.
     */
    public void updateSurroundedChunkFromServer(Object object) {
        serverModelManager.chunkMap = (Map<String, ModelChunkManager>) object;
    }

    public void update(int dt) {
        List<String> destroyModelId = new ArrayList<>();
        List<Model> switchChunkModelList = new ArrayList<>();

        // Get all distinct chunk surrounded players
        Set<ModelChunkManager> processChunk = new HashSet<>();
        gameContext.networkManager.getConnectedPlayerId().forEach(
                playerName -> {
                    PlayerModel pm = serverModelManager.playerModelMap.get(playerName);
                    processChunk.addAll(getSurroundedChunk(pm));
                }
        );

        // Process models from surrounded chunks
        processChunk.forEach(
                mcm -> {
                    String currentChunkId = mcm.getChunkId();
                    mcm.getAllModel().forEach(model -> {
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

        if (gameContext.isServer && gameContext.worldCounter % Config.BROADCAST_DELAY == 0) this.broadcastToClient();

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
        List<String> disconnectedPlayer = serverModelManager.playerModelMap.keySet().stream().filter(
                playername -> !playername.equals(gameContext.username)
        ).collect(Collectors.toList());

        if (Config.DISABLE_PLAYER) disconnectedPlayer.add(gameContext.username);
        disconnectedPlayer.forEach(model -> {
            logger.info("Removing " + model);
            removeModel(model);
        });
    }

    public void addModelList(Iterable<Model> modelList) {
        for (Model model : modelList) addModel(model);
    }

    public void addSpawnModel(Model model) {
        this.spawnModelList.offer(model);
    }

    private ModelChunkManager getChunkFromModelPosition(int posX, int posY) {
        int chunkX = posX / ModelChunkManager.CHUNK_HEIGHT;
        int chunkY = posY / ModelChunkManager.CHUNK_WIDTH;
        return getChunk(chunkX, chunkY);
    }

    private ModelChunkManager getChunk(int posX, int posY) {
        String chunkId = posX + "_" + posY;
        if (serverModelManager.chunkMap.containsKey(chunkId)) return serverModelManager.chunkMap.get(chunkId);

        ModelChunkManager mcm = new ModelChunkManager(posX, posY);
        serverModelManager.chunkMap.put(chunkId, mcm);
        return mcm;
    }

    public List<ModelChunkManager> getSurroundedChunk(Model model) {
        List<ModelChunkManager> result = new ArrayList<>();
        int chunkX = model.posX / ModelChunkManager.CHUNK_HEIGHT;
        int chunkY = model.posY / ModelChunkManager.CHUNK_WIDTH;

        for (int i = -1; i < 2; ++i) {
            for (int j = -1; j < 2; ++j) {
                result.add(getChunk(chunkX + i, chunkY + j));
            }
        }
        return result;
    }

    private String getChunkIdFromModelPosition(int posX, int posY) {
        int chunkX = posX / ModelChunkManager.CHUNK_HEIGHT;
        int chunkY = posY / ModelChunkManager.CHUNK_WIDTH;
        return chunkX + "_" + chunkY;
    }

    public Iterable<ModelChunkManager> getLocalChunk() {
        return serverModelManager.chunkMap.values();
    }
}
