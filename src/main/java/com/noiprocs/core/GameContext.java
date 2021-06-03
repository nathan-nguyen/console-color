package com.noiprocs.core;

import com.noiprocs.core.graphics.GameScreenInterface;
import com.noiprocs.core.graphics.HitboxManagerInterface;
import com.noiprocs.core.graphics.SpriteManager;
import com.noiprocs.core.model.ModelManager;
import com.noiprocs.core.model.WorldModelGenerator;

import java.io.IOException;

public class GameContext {
    public final ModelManager modelManager = new ModelManager();
    public final ControlManager controlManager = new ControlManager(this);

    public SpriteManager spriteManager;
    public HitboxManagerInterface hitboxManager;

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
        new WorldModelGenerator().generateWorld(modelManager.getServerModelManager());

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
        this.spriteManager.setGameContext(this);
    }

    public void setGameScreen(GameScreenInterface gameScreen) {
        this.gameScreen = gameScreen;
        this.gameScreen.setGameContext(this);
    }

    public void setHitboxManager(HitboxManagerInterface hitboxManager) {
        this.hitboxManager = hitboxManager;
        hitboxManager.setGameContext(this);
    }
}
