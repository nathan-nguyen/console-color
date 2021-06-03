package com.noiprocs.core;

public class ControlManager {
    private GameContext gameContext;

    public ControlManager(GameContext gameContext) {
        this.gameContext = gameContext;
    }

    public void processCommand(String command) {
        if (command.length() == 0) return;

        for (int i = 0; i < command.length(); ++i) processCommand(command.charAt(i));
    }

    public void processCommand(char c) {
        switch (c) {
            case 'a': gameContext.modelManager.getPlayerModel().moveLeft(); break;
            case 'd': gameContext.modelManager.getPlayerModel().moveRight(); break;
            case 'w': gameContext.modelManager.getPlayerModel().moveUp(); break;
            case 's': gameContext.modelManager.getPlayerModel().moveDown(); break;
        }
    }
}
