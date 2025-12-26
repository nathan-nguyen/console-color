package com.noiprocs.core.network;

import com.noiprocs.core.GameContext;
import com.noiprocs.core.config.Config;
import com.noiprocs.network.CommunicationManager;
import com.noiprocs.network.ReceiverInterface;
import com.noiprocs.network.client.Client;
import com.noiprocs.network.server.Server;
import java.io.Serializable;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class NetworkManager implements ReceiverInterface {
  private static final Logger logger = LogManager.getLogger(NetworkManager.class);

  private final GameContext gameContext;
  public final Map<String, String> clientIdMap = new ConcurrentHashMap<>();
  private CommunicationManager communicationManager;

  public ServerMessageQueue serverMessageQueue;

  public NetworkManager(GameContext gameContext) {
    this.gameContext = gameContext;
  }

  public void startServerNetworkService(int port) {
    Server server = new Server(this, port);
    this.communicationManager = server.getCommunicationManager();
    server.startService();

    serverMessageQueue = new ServerMessageQueue(communicationManager, clientIdMap.keySet());
    if (gameContext.isServer && Config.USE_BROADCAST_BG_THREAD) {
      new Thread(serverMessageQueue).start();
    }
  }

  public void startClientNetworkService(String hostname, int port) {
    Client client = new Client(this, hostname, port);
    this.communicationManager = client.getCommunicationManager();
    client.startService();
  }

  /** This method is used only for Client. */
  public void sendDataToServer(byte[] bytes) {
    communicationManager.broadcastMessage(bytes);
  }

  /** This method is used only for Server. */
  public void sendClientData(String clientId, Serializable object) {
    serverMessageQueue.addMessage(clientId, object);
  }

  @Override
  public void receiveMessage(String clientId, byte[] bytes) {
    logger.debug("Received byte[] size {} from clientId {}", bytes.length, clientId);

    if (!gameContext.isServer) {
      gameContext.modelManager.updateSurroundedChunkFromServer(bytes);
    } else {
      processClientCommand(clientId, bytes);
    }
  }

  @Override
  public void serverConnect() {
    logger.info("Connected to server!");
    gameContext.modelManager.startClient();
  }

  @Override
  public void serverDisconnect() {
    logger.info("Disconnected from server! Exit with status 1!");
    System.exit(1);
  }

  @Override
  public void clientConnect(String clientId) {
    logger.info("Client {} connected!", clientId);
  }

  @Override
  public void clientDisconnect(String clientId) {
    String disconnectedClientUserName = clientIdMap.get(clientId);
    logger.info("Client {} - User {} disconnected!", clientId, disconnectedClientUserName);

    gameContext.modelManager.destroyModelById(disconnectedClientUserName);
    gameContext.modelManager.saveGameData();

    clientIdMap.remove(clientId);
  }

  private void processClientCommand(String clientId, byte[] bytes) {
    String command = new String(bytes);
    logger.debug("[Server] Receiving message from client: {} - Content: {}", clientId, command);

    if (command.startsWith("join ")) {
      String clientUserName = command.substring(5);
      gameContext.modelManager.addPlayerModel(clientUserName);

      clientIdMap.put(clientId, clientUserName);
    } else {
      gameContext.controlManager.addCommand(command);
    }
  }

  public Collection<String> getConnectedPlayerId() {
    return clientIdMap.values();
  }
}
