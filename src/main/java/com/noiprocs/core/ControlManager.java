package com.noiprocs.core;

import com.noiprocs.core.model.mob.character.PlayerModel;

public class ControlManager {
    private final GameContext gameContext;

    public ControlManager(GameContext gameContext) {
        this.gameContext = gameContext;
    }

    public void processInput(String command) {
        if (command.length() == 0) return;

        if (!gameContext.isServer) {
            gameContext.networkManager.broadcast((gameContext.username + " " + command).getBytes());
            return;
        }

        processCommand(gameContext.username, command);
    }

    public void processCommand(String username, String command) {
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
