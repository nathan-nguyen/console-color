package com.noiprocs.core.model.generator;

import com.noiprocs.core.GameContext;
import com.noiprocs.core.config.Config;
import com.noiprocs.core.model.Model;
import com.noiprocs.core.model.environment.WorldBoundaryModel;
import com.noiprocs.core.model.mob.CotMobModel;
import com.noiprocs.core.model.mob.character.PlayerModel;
import com.noiprocs.core.model.plant.BirchTreeModel;
import com.noiprocs.core.model.plant.PineTreeModel;
import com.noiprocs.core.model.plant.TreeModel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class WorldModelGenerator {
    private static final Logger logger = LogManager.getLogger(WorldModelGenerator.class);

    private final GameContext gameContext;
    private final Random random = new Random();

    public WorldModelGenerator(GameContext gameContext) {
        this.gameContext = gameContext;
    }

    public void generateWorld() {
        if (!Config.DISABLE_PLAYER) {
            gameContext.modelManager.addModel(new PlayerModel(gameContext.username, 0, 0, true));
        }

        // Generate world boundary
        this.generateWorldBoundary(-80, -40, 10, 10);

        // Generate trees
        this.generateTree(20, -60, -40, -10, 40);

        this.generateMob(100, -60, -40, -10, 40);

        // Generate maze
        MazeModelGenerator mmg = new MazeModelGenerator(40);
        mmg.constructMaze(10, 10);
        gameContext.modelManager.addModelList(mmg.getMazePartModelList());
    }

    private void generateWorldBoundary(int startX, int startY, int heightPart, int widthPart) {
        List<Model> result = new ArrayList<>();

        for (int i = 0; i < heightPart; ++i) {
            WorldBoundaryModel wbm = new WorldBoundaryModel(
                    startX, startY, i, 0, true, false
            );
            result.add(wbm);
            wbm = new WorldBoundaryModel(
                    startX, startY, i, widthPart, true, false
            );
            result.add(wbm);
        }

        for (int i = 0; i < widthPart; ++i) {
            WorldBoundaryModel wbm = new WorldBoundaryModel(
                    startX, startY, 0, i, false, false
            );
            result.add(wbm);
            wbm = new WorldBoundaryModel(
                    startX, startY, heightPart, i, false, false
            );
            result.add(wbm);
        }

        gameContext.modelManager.addModelList(result);
    }

    private void generateTree(int number, int startX, int startY, int endX, int endY) {
        while (number-- > 0) {
            int treeType = random.nextInt(3);
            Model treeModel;
            int modelPosX = random.nextInt(endX - startX) + startX;
            int modelPosY = random.nextInt(endY - startY) + startY;
            if (treeType == 1) {
                treeModel = new BirchTreeModel(modelPosX, modelPosY, true);
            } else if (treeType == 2) {
                treeModel = new PineTreeModel(modelPosX, modelPosY, true);
            } else {
                treeModel = new TreeModel(modelPosX, modelPosY, true);
            }

            if (gameContext.hitboxManager.isValid(treeModel, modelPosX, modelPosY)) {
                gameContext.modelManager.addModel(treeModel);
            }
            else ++number;
        }
    }

    private void generateMob(int number, int startX, int startY, int endX, int endY) {
        while (number-- > 0) {
            int modelPosX = random.nextInt(endX - startX) + startX;
            int modelPosY = random.nextInt(endY - startY) + startY;
            Model cotMobModel = new CotMobModel(modelPosX, modelPosY, true);

            if (gameContext.hitboxManager.isValid(cotMobModel, modelPosX, modelPosY)) {
                gameContext.modelManager.addModel(cotMobModel);
            }
            else ++number;
        }
    }
}
