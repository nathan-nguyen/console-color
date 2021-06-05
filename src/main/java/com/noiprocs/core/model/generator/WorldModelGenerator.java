package com.noiprocs.core.model.generator;

import com.noiprocs.core.GameContext;
import com.noiprocs.core.config.Config;
import com.noiprocs.core.model.Model;
import com.noiprocs.core.model.environment.BirchTreeModel;
import com.noiprocs.core.model.environment.TreeModel;
import com.noiprocs.core.model.mob.character.PlayerModel;

import java.util.Random;

public class WorldModelGenerator {
    private final GameContext gameContext;
    private final Random random = new Random();

    public WorldModelGenerator(GameContext gameContext) {
        this.gameContext = gameContext;
    }

    public void generateWorld() {
        if (!Config.DISABLE_PLAYER) {
            gameContext.modelManager.addModel(new PlayerModel(gameContext.username, 0, 0, true));
        }

        // Generate trees
        this.generateTree(20, -60, -10, -40, 40);

        // Generate maze
        MazeModelGenerator mmg = new MazeModelGenerator(40);
        mmg.constructMaze(10, 10);
        gameContext.modelManager.addModelList(mmg.getMazePartModelList());

    }

    private void generateTree(int number, int startX, int endX, int startY, int endY) {
        for (int i = 0; i < number; ++i) {
            int treeType = random.nextInt(2);
            Model treeModel;
            if (treeType == 1) {
                treeModel = new BirchTreeModel(
                        random.nextInt(endX - startX) + startX,
                        random.nextInt(endY - startY) + startY,
                        true
                );
            } else {
                treeModel = new TreeModel(
                        random.nextInt(endX - startX) + startX,
                        random.nextInt(endY - startY) + startY,
                        true
                );
            }

            if (gameContext.hitboxManager.isValid(treeModel, treeModel.posX, treeModel.posY)) {
                gameContext.modelManager.addModel(treeModel);
            }
            else --i;
        }
    }
}
