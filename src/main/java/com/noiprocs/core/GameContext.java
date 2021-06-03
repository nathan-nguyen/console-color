package com.noiprocs.core;

import com.noiprocs.core.graphics.GameScreenInterface;
import com.noiprocs.core.graphics.SpriteManager;
import com.noiprocs.core.model.mob.character.PlayerModel;
import com.noiprocs.core.model.ModelManager;

import java.io.IOException;

public class GameContext {
    private final ModelManager modelManager = new ModelManager();
    private SpriteManager spriteManager;
    private GameScreenInterface gameScreen;

    public void run() {
        this.loadGameFromSaveFile();

        // Initialize timeManager
        TimeManager timeManager = new TimeManager() {
            @Override
            protected void update(int dt) {
                spriteManager.update(dt);
                gameScreen.render(dt);
            }
        };
        timeManager.run();
    }

    private void initializeNewGame() {
        PlayerModel playerModel = new PlayerModel(20, 20, true);

        modelManager.getServerModelManager().addModel(playerModel);

        this.saveGameToFile();
    }

    // Save game to file
    private void saveGameToFile() {
        // Save game
        SaveLoadManager.saveGameData(modelManager.getServerModelManager());
    }

    // Load game from save file
    private void loadGameFromSaveFile() {
        try {
            modelManager.setServerModelManager(SaveLoadManager.loadGameData());
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            initializeNewGame();
        }
    }

    public void setSpriteManager(SpriteManager spriteManager) {
        this.spriteManager = spriteManager;
        this.spriteManager.setModelManager(modelManager);
    }

    public void setGameScreen(GameScreenInterface gameScreen) {
        this.gameScreen = gameScreen;
        this.gameScreen.setSpriteManager(spriteManager);
    }
}
