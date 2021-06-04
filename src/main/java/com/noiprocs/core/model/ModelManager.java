package com.noiprocs.core.model;

import com.noiprocs.core.GameContext;
import com.noiprocs.core.SaveLoadManager;
import com.noiprocs.core.config.Config;
import com.noiprocs.core.model.mob.character.PlayerModel;
import com.noiprocs.network.ClientInterface;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class ModelManager implements ClientInterface {
    private static final Logger logger = LoggerFactory.getLogger(ModelManager.class);

    private final GameContext gameContext;

    private ServerModelManager serverModelManager;

    private final Map<Integer, String> clientIdMap = new HashMap<>();

    public ModelManager(GameContext gameContext) {
        this.gameContext = gameContext;
    }

    public Map<String, Model> getModelMap() {
        return serverModelManager.getModelMap();
    }

    public Model getModel(String id) {
        return getModelMap().get(id);
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
        } else {
            serverModelManager = new ServerModelManager();
            gameContext.networkManager.broadcast(("join " + gameContext.username).getBytes());
        }
    }

    public void broadcastToClient() {
        if (!gameContext.isServer) return;
        if (gameContext.worldCounter % Config.BROADCAST_DELAY != 0) return;

        try {
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(buffer);
            oos.writeObject(serverModelManager);
            oos.flush();
            gameContext.networkManager.broadcast(buffer.toByteArray());
            buffer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void receiveMessage(int clientId, byte[] bytes) {
        if (!gameContext.isServer) {
            try {
                ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
                ObjectInput in = new ObjectInputStream(bis);
                this.serverModelManager = (ServerModelManager) in.readObject();
                in.close();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            String command = new String(bytes);

            logger.info("[Server] Receiving message from client: " + clientId + " - Content: " + command);

            if (command.startsWith("join ")) {
                String clientUserName = command.substring(5);
                serverModelManager.addPlayer(clientUserName);
                clientIdMap.put(clientId, clientUserName);
            }
            else {
                String[] splitArr = command.split(" ");
                gameContext.controlManager.processCommand(splitArr[0], splitArr[1]);
            }
        }
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
        String disconnectedClientUserName = clientIdMap.get(clientId);
        logger.info("Client " + clientId + " - User " + disconnectedClientUserName + " disconnected!");
        serverModelManager.removeModel(disconnectedClientUserName);
    }
}
