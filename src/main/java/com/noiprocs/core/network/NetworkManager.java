package com.noiprocs.core.network;

import com.noiprocs.core.GameContext;
import com.noiprocs.core.config.Config;
import com.noiprocs.network.CommunicationManager;
import com.noiprocs.network.ReceiverInterface;
import com.noiprocs.network.client.Client;
import com.noiprocs.network.server.Server;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.Serializable;
import java.util.Collection;
import java.util.Hashtable;
import java.util.Map;

public class NetworkManager implements ReceiverInterface {
    private static final Logger logger = LogManager.getLogger(NetworkManager.class);

    private final GameContext gameContext;
    public final Map<Integer, String> clientIdMap = new Hashtable<>();
    private CommunicationManager communicationManager;

    public ServerMessageQueue serverMessageQueue;

    public NetworkManager(GameContext gameContext) {
        this.gameContext = gameContext;
    }

    public void startServerNetworkService(int port) {
        Server server = new Server(port);
        this.communicationManager = server.getCommunicationManager();
        server.startService();

        communicationManager.setReceiver(this);

        serverMessageQueue = new ServerMessageQueue(communicationManager, clientIdMap.keySet());
        if (gameContext.isServer && Config.USE_BROADCAST_BG_THREAD) {
            new Thread(serverMessageQueue).start();
        }
    }

    public void startClientNetworkService(String hostname, int port) {
        Client client = new Client(hostname, port);
        this.communicationManager = client.getCommunicationManager();

        client.startService();
        communicationManager.setReceiver(this);
    }

    /**
     * This method is used only for Client.
     */
    public void sendDataToServer(byte[] bytes) {
        communicationManager.sendMessage(bytes);
    }

    /**
     * This method is used only for Server.
     */
    public void sentClientData(int clientId, Serializable object) {
        serverMessageQueue.addMessage(clientId, object);
    }

    @Override
    public void receiveMessage(int clientId, byte[] bytes) {
        logger.debug("Received byte[] size {} from clientId {}", bytes.length, clientId);

        if (!gameContext.isServer) {
            gameContext.modelManager.updateSurroundedChunkFromServer(bytes);
        }
        else {
            processClientCommand(clientId, bytes);
        }
    }

    @Override
    public void serverDisconnect() {
        logger.info("Disconnected from server! Exit with status 1!");
        System.exit(1);
    }

    @Override
    public void clientConnect(int clientId) {
        logger.info("Client {} connected!", clientId);
    }

    @Override
    public void clientDisconnect(int clientId) {
        String disconnectedClientUserName = clientIdMap.get(clientId);
        logger.info("Client {} - User {} disconnected!", clientId, disconnectedClientUserName);

        gameContext.modelManager.removeModel(disconnectedClientUserName);
        gameContext.modelManager.saveGameData();

        clientIdMap.remove(clientId);
    }

    private void processClientCommand(int clientId, byte[] bytes) {
        String command = new String(bytes);
        logger.debug("[Server] Receiving message from client: {} - Content: {}", clientId, command);

        if (command.startsWith("join ")) {
            String clientUserName = command.substring(5);
            gameContext.modelManager.addPlayerModel(clientUserName);

            clientIdMap.put(clientId, clientUserName);
        } else {
            String[] splitArr = command.split(" ");
            gameContext.controlManager.processCommand(splitArr[0], splitArr[1]);
        }
    }

    public Collection<String> getConnectedPlayerId() {
        return clientIdMap.values();
    }
}
