package com.noiprocs.core.network;

import com.noiprocs.core.GameContext;
import com.noiprocs.network.ClientInterface;
import com.noiprocs.network.CommunicationManager;
import com.noiprocs.network.client.Client;
import com.noiprocs.network.server.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class NetworkManager implements ClientInterface {
    private static final Logger logger = LoggerFactory.getLogger(NetworkManager.class);

    private final GameContext gameContext;

    private CommunicationManager communicationManager;

    private final Map<Integer, String> clientIdMap = new HashMap<>();


    public NetworkManager(GameContext gameContext) {
        this.gameContext = gameContext;
    }

    public void startNetworkService(boolean isServer, String hostname, int port) {
        if (isServer) {
            Server server = new Server();
            this.communicationManager = server.getCommunicationManager();
            server.startService();
        }
        else {
            Client client = new Client(hostname, port);
            this.communicationManager = client.getCommunicationManager();
            client.startService();
        }

        communicationManager.setReceiver(this);
    }

    /**
     * If server: Send data to all clients.
     * If client: Send data to server.
     * @param object: Information to send to corresponding server / clients
     */
    public void broadcastDataOverNetwork(Object object) {
        communicationManager.sendMessage(object);
    }

    @Override
    public void receiveMessage(int clientId, Object object) {
//        logger.info("Received message " + object + " from clientId " + clientId);
        if (!gameContext.isServer) gameContext.modelManager.updateServerModelManager(object);
        else processClientCommand(clientId, object);
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
        gameContext.modelManager.removeModel(disconnectedClientUserName);
        gameContext.modelManager.saveGameData();
    }

    private void processClientCommand(int clientId, Object object) {
        logger.info("[Server] Object:" + object);
        String command = new String((byte[]) object);

        logger.info("[Server] Receiving message from client: " + clientId + " - Content: " + command);

        if (command.startsWith("join ")) {
            String clientUserName = command.substring(5);
            gameContext.modelManager.addPlayerModel(clientUserName);
            clientIdMap.put(clientId, clientUserName);
        }
        else {
            String[] splitArr = command.split(" ");
            gameContext.controlManager.processCommand(splitArr[0], splitArr[1]);
        }
    }
}
