package com.noiprocs.core.model;

import com.noiprocs.core.GameContext;
import com.noiprocs.core.SaveLoadManager;
import com.noiprocs.core.config.Config;
import com.noiprocs.core.model.mob.character.PlayerModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.Map;

public class ModelManager {
    private static final Logger logger = LoggerFactory.getLogger(ModelManager.class);
    private final GameContext gameContext;

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
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();

                // Initialize a new Game
                serverModelManager = new ServerModelManager();
                new WorldModelGenerator(gameContext).generateWorld();

                this.saveGameData();
            }
        } else {
            serverModelManager = new ServerModelManager();
            gameContext.networkManager.broadcastDataOverNetwork(("join " + gameContext.username).getBytes());
        }
    }

    /**
     * This method is used only for Server, broadcast serverModelManager object to all clients
     */
    public void broadcastToClient() {
        if (!gameContext.isServer) return;

        try {
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(buffer);
            oos.writeObject(serverModelManager);
            oos.flush();
            gameContext.networkManager.broadcastDataOverNetwork(buffer.toByteArray());
            buffer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addModel(Model model) {
        logger.info(this.getClass() + " - Adding Model: " + model.id);

        // Special treatment when adding PlayerModel
        if (model instanceof PlayerModel) {
            logger.info("Adding PlayerModel");
        }

        serverModelManager.modelMap.put(model.id, model);
        gameContext.spriteManager.synchronizeModelData(true);
    }

    /**
     * Add Client Player Model to serverModelManager.modelMap
     * @param playerName: Client Player Username
     */
    public void addPlayerModel(String playerName) {
        if (!serverModelManager.playerModelMap.containsKey(playerName)) {
            PlayerModel pm = new PlayerModel(playerName, 0, 0, true);
            serverModelManager.playerModelMap.put(playerName, pm);
        }
        this.addModel(serverModelManager.playerModelMap.get(playerName));
    }

    /**
     * Update ServerModelManager from byte array.
     * This method is for Client only
     * @param bytes ServerModelManager object in byte array.
     */
    public void updateServerModelManager(byte[] bytes) {
        try {
            ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
            ObjectInput in = new ObjectInputStream(bis);
            serverModelManager = (ServerModelManager) in.readObject();
            in.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void update(int dt) {
        this.broadcastToClient();

        if (gameContext.worldCounter % Config.AUTO_SAVE_DURATION == 0) this.saveGameData();
    }

    /**
     * Save game data to disk
     */
    public void saveGameData() {
        SaveLoadManager.saveGameData(serverModelManager);
    }
}
