package com.noiprocs.core.network;

import com.noiprocs.core.config.Config;
import com.noiprocs.core.util.Helper;
import com.noiprocs.network.ClientInterface;
import com.noiprocs.network.CommunicationManager;
import com.noiprocs.network.client.Client;
import com.noiprocs.network.server.Server;

public class NetworkManager {
    private CommunicationManager communicationManager;
    private Server server;
    private Client client;

    public void broadcast(String message) {
        communicationManager.sendMessage(message);
    }

    public void setNetworkReceiver(ClientInterface networkReceiver) {
        communicationManager.setReceiver(networkReceiver);
    }

    public void startServer(String host, int port) {
        server = new Server();
        this.communicationManager = server.getCommunicationManager();
    }

    public void startClient(String host, int port) {
        client = new Client(host, port);
        this.communicationManager = client.getCommunicationManager();
    }

    public void startService() {
        if (server != null) server.startService();
        else client.startService();
    }
}
