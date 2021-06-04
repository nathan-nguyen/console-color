package com.noiprocs.core;

import com.noiprocs.core.model.mob.character.PlayerModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ControlManager {
    private static final Logger logger = LoggerFactory.getLogger(ControlManager.class);
    private final GameContext gameContext;

    public ControlManager(GameContext gameContext) {
        this.gameContext = gameContext;
    }

    /**
     * Process local input
     * @param command: Local input command
     */
    public void processInput(String command) {
        if (command.length() == 0) return;

        if (!gameContext.isServer) {
            gameContext.networkManager.broadcastDataOverNetwork((gameContext.username + " " + command).getBytes());
            return;
        }

        processCommand(gameContext.username, command);
    }

    /**
     * Process command for a particular user.
     * Only applicable for Server.
     * @param username: Username.
     * @param command: Command to be processed for this user.
     */
    public void processCommand(String username, String command) {
        if (!gameContext.isServer) {
            throw new RuntimeException("ControlManager.processCommand method is only applicable for Server");
        }

        PlayerModel playerModel = (PlayerModel) gameContext.modelManager.getModel(username);

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
            }
        }
    }
}
