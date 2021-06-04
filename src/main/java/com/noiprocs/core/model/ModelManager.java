package com.noiprocs.core.model;

import com.noiprocs.core.GameContext;
import com.noiprocs.core.SaveLoadManager;
import com.noiprocs.core.model.mob.character.PlayerModel;
import com.noiprocs.core.network.NetworkManager;
import com.noiprocs.network.ClientInterface;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Map;

public class ModelManager implements ClientInterface {
    private static final Logger logger = LoggerFactory.getLogger(ModelManager.class);

    private final GameContext gameContext;
    private final NetworkManager networkManager;

    private ServerModelManager serverModelManager;
    private PlayerModel playerModel;

    public ModelManager(GameContext gameContext, NetworkManager networkManager) {
        this.gameContext = gameContext;
        this.networkManager = networkManager;
    }

    public Map<String, Model> getModelMap() {
        return serverModelManager.getModelMap();
    }

    public PlayerModel getPlayerModel() {
        if (playerModel == null) playerModel = (PlayerModel) getModelMap().get(gameContext.username);
        return playerModel;
    }

    public void start() {
        if (gameContext.isServer) {
            try {
                serverModelManager = SaveLoadManager.loadGameData();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();

                // Initialize a new Game
                serverModelManager = new ServerModelManager();
                new WorldModelGenerator(gameContext).generateWorld(serverModelManager);

                SaveLoadManager.saveGameData(serverModelManager);
            }
        }
        else serverModelManager = new ServerModelManager();
    }

    @Override
    public void receiveMessage(int clientId, String message) {
        logger.info("Receiving message from " + clientId + " - Content: " + message);
    }

    @Override
    public void serverDisconnect() {
        logger.info("Disconnected from server! Exit with status 1!");
        System.exit(1);
    }

    @Override
    public void clientConnectionNotify(int clientId) {
        logger.info("Client " + clientId + " connected!");
    }

    @Override
    public void clientDisconnect(int clientId) {
        logger.info("Client " + clientId + " disconnected!");
    }
}
