package com.noiprocs.core.model;

import com.noiprocs.core.GameContext;
import com.noiprocs.core.model.environment.TreeModel;
import com.noiprocs.core.model.mob.character.PlayerModel;

import java.util.Random;

public class WorldModelGenerator {
    private final GameContext gameContext;
    private final Random random = new Random();

    public WorldModelGenerator(GameContext gameContext) {
        this.gameContext = gameContext;
    }

    public void generateWorld(ServerModelManager serverModelManager) {
        serverModelManager.addModel(new PlayerModel(gameContext.username, 0, 0, true));

        for (int i = 0; i < 20; ++i) {
            Model treeModel = new TreeModel(
                    random.nextInt(60) - 30,
                    random.nextInt(40) - 20,
                    true
            );
            if (gameContext.hitboxManager.isValid(treeModel, treeModel.posX, treeModel.posY)) {
                serverModelManager.addModel(treeModel);
            }
            else --i;
        }
    }
}
