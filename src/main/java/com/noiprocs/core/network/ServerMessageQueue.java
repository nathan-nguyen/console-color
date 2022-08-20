package com.noiprocs.core.network;

import com.noiprocs.network.CommunicationManager;
import org.apache.commons.lang3.SerializationUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.Serializable;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;

public class ServerMessageQueue implements Runnable {
    private static final Logger logger = LogManager.getLogger(ServerMessageQueue.class);

    public static final int MAX_QUEUE_SIZE = 10;
    private final CommunicationManager communicationManager;

    public ServerMessageQueue(CommunicationManager communicationManager) {
        this.communicationManager = communicationManager;
    }

    static class ObjectWrapper {
        public int clientId;
        public Serializable object;

        public ObjectWrapper(int clientId, Object object) {
            this.clientId = clientId;
            this.object = (Serializable) object;
        }
    }

    private final Queue<ObjectWrapper> queue = new ConcurrentLinkedQueue<>();

    public void run() {
        while (true) {
            while (!queue.isEmpty()) {
                ObjectWrapper ow = queue.poll();
                try {
                    communicationManager.sendMessage(ow.clientId, SerializationUtils.serialize(ow.object));
                } catch (Exception e) {
                    logger.error("Failed to send data to client " + ow.clientId);
                    e.printStackTrace();
                }
            }
        }
    }

    public void addMessage(int clientId, Object object) {
        if (queue.size() > MAX_QUEUE_SIZE) return;
        queue.offer(new ObjectWrapper(clientId, object));
    }
}
