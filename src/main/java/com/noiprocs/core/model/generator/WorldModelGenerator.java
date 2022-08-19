package com.noiprocs.core.model.generator;

import com.noiprocs.core.GameContext;
import com.noiprocs.core.config.Config;
import com.noiprocs.core.model.Model;
import com.noiprocs.core.model.building.FenceModel;
import com.noiprocs.core.model.environment.WorldBoundaryModel;
import com.noiprocs.core.model.mob.CotLeftModel;
import com.noiprocs.core.model.mob.CotPsychoModel;
import com.noiprocs.core.model.mob.CotRightModel;
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

        // Generate maze
        MazeModelGenerator mmg = new MazeModelGenerator(40);
        mmg.constructMaze(10, 10);
        gameContext.modelManager.addModelList(mmg.getMazePartModelList());

        // Generate trees
        this.generateTree(500, -80, -40, 316, 554);

        this.generateCotMob(500, -80, -40, 316, 554);

        this.generateSupportingObject(0, 0, 10, 10);
    }

    private void generateWorldBoundary(int startX, int startY, int heightPart, int widthPart) {
        List<Model> result = new ArrayList<>();

        for (int i = 0; i < heightPart; ++i) {
            WorldBoundaryModel wbm = new WorldBoundaryModel(
                    startX + i * WorldBoundaryModel.WORLD_BOUNDARY_PART_HEIGHT,
                    startY,
                    true,
                    WorldBoundaryModel.WORLD_BOUNDARY_PART_HEIGHT,
                    1
            );
            result.add(wbm);
            wbm = new WorldBoundaryModel(
                    startX + i * WorldBoundaryModel.WORLD_BOUNDARY_PART_HEIGHT,
                    startY + widthPart * WorldBoundaryModel.WORLD_BOUNDARY_PART_WIDTH,
                    true,
                    WorldBoundaryModel.WORLD_BOUNDARY_PART_HEIGHT,
                    1
            );
            result.add(wbm);
        }

        for (int i = 0; i < widthPart; ++i) {
            WorldBoundaryModel wbm = new WorldBoundaryModel(
                    startX,
                    startY + i * WorldBoundaryModel.WORLD_BOUNDARY_PART_WIDTH,
                    true,
                    1,
                    WorldBoundaryModel.WORLD_BOUNDARY_PART_WIDTH
            );
            result.add(wbm);
            wbm = new WorldBoundaryModel(
                    startX + heightPart * WorldBoundaryModel.WORLD_BOUNDARY_PART_HEIGHT,
                    startY + i * WorldBoundaryModel.WORLD_BOUNDARY_PART_WIDTH,
                    true,
                    1,
                    WorldBoundaryModel.WORLD_BOUNDARY_PART_WIDTH
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
                treeModel = new BirchTreeModel(modelPosX, modelPosY);
            } else if (treeType == 2) {
                treeModel = new PineTreeModel(modelPosX, modelPosY);
            } else {
                treeModel = new TreeModel(modelPosX, modelPosY);
            }

            if (gameContext.hitboxManager.isValid(treeModel, modelPosX, modelPosY)) {
                gameContext.modelManager.addModel(treeModel);
            }
            else ++number;
        }
    }

    private void generateCotMob(int number, int startX, int startY, int endX, int endY) {
        while (number-- > 0) {
            int cotType = random.nextInt(3);
            int modelPosX = random.nextInt(endX - startX) + startX;
            int modelPosY = random.nextInt(endY - startY) + startY;

            Model cotMobModel;
            if (cotType == 0)
                cotMobModel = new CotPsychoModel(modelPosX, modelPosY);
            else if (cotType == 1)
                cotMobModel = new CotRightModel(modelPosX, modelPosY);
            else
                cotMobModel = new CotLeftModel(modelPosX, modelPosY);

            if (gameContext.hitboxManager.isValid(cotMobModel, modelPosX, modelPosY)) {
                gameContext.modelManager.addModel(cotMobModel);
            }
            else ++number;
        }
    }

    private void generateSupportingObject(int startX, int startY, int endX, int endY) {
        while (true) {
            int modelPosX = random.nextInt(endX - startX) + startX;
            int modelPosY = random.nextInt(endY - startY) + startY;

            Model fenceModel = new FenceModel(modelPosX, modelPosY);
            if (gameContext.hitboxManager.isValid(fenceModel, modelPosX, modelPosY)) {
                gameContext.modelManager.addModel(fenceModel);
                break;
            }
        }

    }
}
