package com.noiprocs.core.network;

import com.noiprocs.core.GameContext;
import com.noiprocs.network.CommunicationManager;
import com.noiprocs.network.ReceiverInterface;
import com.noiprocs.network.client.Client;
import com.noiprocs.network.server.Server;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class NetworkManager implements ReceiverInterface {
    private static final Logger logger = LogManager.getLogger(NetworkManager.class);

    private final GameContext gameContext;
    public final Map<Integer, String> clientIdMap = new HashMap<>();
    private CommunicationManager communicationManager;

    public NetworkManager(GameContext gameContext) {
        this.gameContext = gameContext;
    }

    public void startNetworkService(boolean isServer, String hostname, int port) {
        if (isServer) {
            Server server = new Server();
            this.communicationManager = server.getCommunicationManager();
            server.startService();
        } else {
            Client client = new Client(hostname, port);
            this.communicationManager = client.getCommunicationManager();
            client.startService();
        }

        communicationManager.setReceiver(this);
    }

    /**
     * If server: Send data to all clients.
     * If client: Send data to server.
     *
     * @param object: Information to send to corresponding server / clients
     */
    public void broadcastDataOverNetwork(Object object) {
        communicationManager.sendMessage(object);
    }

    public void sentClientData(int clientId, Object object) {
        communicationManager.sendMessage(clientId, object);
    }

    @Override
    public void receiveMessage(int clientId, Object object) {
        logger.debug("Received message " + object + " from clientId " + clientId);
        if (!gameContext.isServer) gameContext.modelManager.updateSurroundedChunkFromServer(object);
        else processClientCommand(clientId, object);
    }

    @Override
    public void serverDisconnect() {
        logger.info("Disconnected from server! Exit with status 1!");
        System.exit(1);
    }

    @Override
    public void clientConnect(int clientId) {
        logger.info("Client " + clientId + " connected!");
    }

    @Override
    public void clientDisconnect(int clientId) {
        String disconnectedClientUserName = clientIdMap.get(clientId);
        logger.info("Client " + clientId + " - User " + disconnectedClientUserName + " disconnected!");

        gameContext.modelManager.removeModel(disconnectedClientUserName);
        gameContext.modelManager.saveGameData();

        // Avoid ConcurrentModificationException
        synchronized (clientIdMap) {
            clientIdMap.remove(clientId);
        }
    }

    private void processClientCommand(int clientId, Object object) {
        String command = new String((byte[]) object);
        logger.debug("[Server] Receiving message from client: " + clientId + " - Content: " + command);

        if (command.startsWith("join ")) {
            String clientUserName = command.substring(5);

            // Avoid ConcurrentModificationException
            synchronized (clientIdMap) {
                gameContext.modelManager.addPlayerModel(clientUserName);
                clientIdMap.put(clientId, clientUserName);
            }
        } else {
            String[] splitArr = command.split(" ");
            gameContext.controlManager.processCommand(splitArr[0], splitArr[1]);
        }
    }

    public Collection<String> getConnectedPlayerId() {
        return clientIdMap.values();
    }
}
