package com.noiprocs.core;

import com.noiprocs.core.config.Config;
import com.noiprocs.core.model.ModelManager;
import com.noiprocs.core.model.mob.character.PlayerModel;

public class ControlManager {
    private ModelManager modelManager;
    private PlayerModel playerModel;

    public ControlManager(ModelManager modelManager) {
        this.modelManager = modelManager;
    }

    public void processCommand(String command) {
        if (playerModel == null) this.playerModel = (PlayerModel) modelManager.getModelMap().get(Config.USER_NAME);
        if (command.length() == 0) return;

        for (int i = 0; i < command.length(); ++i) processCommand(command.charAt(i));
    }

    public void processCommand(char c) {
        switch (c) {
            case 'a': playerModel.moveLeft(); break;
            case 'd': playerModel.moveRight(); break;
            case 'w': playerModel.moveUp(); break;
            case 's': playerModel.moveDown(); break;
        }
    }
}
