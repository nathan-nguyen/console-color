package com.noiprocs.core;

import com.noiprocs.core.model.mob.character.PlayerModel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class ControlManager {
    private static final Logger logger = LogManager.getLogger(ControlManager.class);

    private final GameContext gameContext;
    private final Queue<String> commandQueue = new ConcurrentLinkedQueue<>();

    public ControlManager(GameContext gameContext) {
        this.gameContext = gameContext;
    }

    /**
     * Process local input
     *
     * @param command: Local input command
     */
    public void processInput(String command) {
        if (command.isEmpty()) return;

        String message = gameContext.username + " " + command;
        if (gameContext.isServer) {
            addCommand(message);
        }
        else {
            gameContext.networkManager.sendDataToServer(message.getBytes());
        }
    }

    public void addCommand(String command) {
        commandQueue.offer(command);
    }

    public void update(int dt) {
        while (!commandQueue.isEmpty()) {
            String[] tokens = commandQueue.poll().split(" ");
            processCommand(tokens[0], tokens[1]);
        }
    }

    /**
     * Process command for a particular user.
     * Only applicable for Server.
     *
     * @param username: Username.
     * @param command:  Command to be processed for this user.
     */
    private void processCommand(String username, String command) {
        if (!gameContext.isServer) {
            throw new RuntimeException("ControlManager.processCommand method is only applicable for Server");
        }

        PlayerModel playerModel = (PlayerModel) gameContext.modelManager.getModel(username);

        logger.debug("Executing command: {} for player {}", command, username);

        for (int i = 0; i < command.length(); ++i) {
            switch (command.charAt(i)) {
                case 'a':
                    playerModel.moveLeft();
                    break;
                case 'd':
                    playerModel.moveRight();
                    break;
                case 'w':
                    playerModel.moveUp();
                    break;
                case 's':
                    playerModel.moveDown();
                    break;
                case 'h':
                    playerModel.stop();
                    break;
                case 'f':
                    playerModel.triggerAction();
                    break;
                case 't':
                    playerModel.useItem();
                    break;
                case '1':
                    playerModel.setCurrentInventorySlot(0);
                    break;
                case '2':
                    playerModel.setCurrentInventorySlot(1);
                    break;
                case '3':
                    playerModel.setCurrentInventorySlot(2);
                    break;
                case '4':
                    playerModel.setCurrentInventorySlot(3);
                    break;
            }
        }
    }
}
