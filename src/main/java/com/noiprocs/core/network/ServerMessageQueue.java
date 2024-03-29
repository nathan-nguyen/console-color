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

    public static final int MAX_QUEUE_SIZE = 3;
    private final CommunicationManager communicationManager;
    private final Set<Integer> clientIdSet;

    public ServerMessageQueue(CommunicationManager communicationManager, Set<Integer> clientIdSet) {
        this.communicationManager = communicationManager;
        this.clientIdSet = clientIdSet;
    }

    private final Map<Integer, Queue<Serializable>> clientQueueMap = new HashMap<>();

    public void run() {
        while (true) {
            try {
                clientIdSet.parallelStream().forEach(
                        clientId -> {
                            Queue<Serializable> queue = clientQueueMap.get(clientId);
                            if (queue == null || queue.isEmpty()) return;

                            try {
                                communicationManager.sendMessage(clientId, SerializationUtils.serialize(queue.poll()));
                            } catch (Exception e) {
                                // Reason: client was disconnected by clientId has been removed from clientIdSet.
                                logger.error("Failed to send data to client " + clientId);
                                e.printStackTrace();
                                clientQueueMap.remove(clientId);
                            }
                        }
                );
            } catch (ConcurrentModificationException e) {
                // Reason: clientIdSet was updated.
                e.printStackTrace();
            }
        }
    }

    public void addMessage(int clientId, Serializable object) {
        Queue<Serializable> queue;
        if (!clientQueueMap.containsKey(clientId)) {
            queue = new ConcurrentLinkedQueue<>();
            clientQueueMap.put(clientId, queue);
        } else {
            queue = clientQueueMap.get(clientId);
        }
        if (queue.size() > MAX_QUEUE_SIZE) return;
        queue.offer(object);
    }
}
