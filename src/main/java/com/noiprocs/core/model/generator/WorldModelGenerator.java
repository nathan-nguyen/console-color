package com.noiprocs.core.model.generator;

import com.noiprocs.core.GameContext;
import com.noiprocs.core.config.Config;
import com.noiprocs.core.model.Model;
import com.noiprocs.core.model.building.FenceModel;
import com.noiprocs.core.model.environment.WorldBoundaryHorizontalModel;
import com.noiprocs.core.model.environment.WorldBoundaryVerticalModel;
import com.noiprocs.core.model.item.AxeItem;
import com.noiprocs.core.model.item.ItemModel;
import com.noiprocs.core.model.mob.CotPsychoModel;
import com.noiprocs.core.model.mob.character.PlayerModel;
import com.noiprocs.core.model.plant.BirchTreeModel;
import com.noiprocs.core.model.plant.PineTreeModel;
import com.noiprocs.core.model.plant.TreeModel;
import com.noiprocs.core.util.Helper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class WorldModelGenerator {
    private static final Logger logger = LogManager.getLogger(WorldModelGenerator.class);
    private static final int MAX_TRIES = 10;

    private final GameContext gameContext;
    private final Random random = new Random();

    public WorldModelGenerator(GameContext gameContext) {
        this.gameContext = gameContext;
    }

    public void generateWorld() {
        if (!Config.DISABLE_PLAYER) {
            gameContext.modelManager.spawnModel(new PlayerModel(gameContext.username, 0, 0, true));
        }

        // Generate world boundary
        this.generateWorldBoundary(-80, -40, 10, 10);

        // Generate maze
        // this.generateMaze(10, 10, 40);

        // Generate trees
        this.generateTree(1000, -80, -40, 316, 554);

        this.generateCotMob(1000, -80, -40, 316, 554);

        this.generateSupportingObject(ItemModel.class, 1, 0, 0, 10, 10, AxeItem.class);
        this.generateSupportingObject(FenceModel.class, 2, 10, 10, 60, 60);
    }

    private void generateWorldBoundary(int startX, int startY, int heightPart, int widthPart) {
        List<Model> result = new ArrayList<>();

        for (int i = 0; i < heightPart; ++i) {
            Model wbm = new WorldBoundaryVerticalModel(
                    startX + i * WorldBoundaryVerticalModel.WORLD_BOUNDARY_PART_HEIGHT,
                    startY,
                    true
            );
            result.add(wbm);
            wbm = new WorldBoundaryVerticalModel(
                    startX + i * WorldBoundaryVerticalModel.WORLD_BOUNDARY_PART_HEIGHT,
                    startY + widthPart * WorldBoundaryHorizontalModel.WORLD_BOUNDARY_PART_WIDTH,
                    true
            );
            result.add(wbm);
        }

        for (int i = 0; i < widthPart; ++i) {
            Model wbm = new WorldBoundaryHorizontalModel(
                    startX,
                    startY + i * WorldBoundaryHorizontalModel.WORLD_BOUNDARY_PART_WIDTH,
                    true
            );
            result.add(wbm);
            wbm = new WorldBoundaryHorizontalModel(
                    startX + heightPart * WorldBoundaryVerticalModel.WORLD_BOUNDARY_PART_HEIGHT,
                    startY + i * WorldBoundaryHorizontalModel.WORLD_BOUNDARY_PART_WIDTH,
                    true
            );
            result.add(wbm);
        }

        gameContext.modelManager.addModelList(result);
    }

    private void generateMaze(int posX, int posY, int mazeDimension) {
        MazeModelGenerator mmg = new MazeModelGenerator(mazeDimension);
        mmg.constructMaze(posX, posY);
        gameContext.modelManager.addModelList(mmg.getMazePartModelList());
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
                gameContext.modelManager.spawnModel(treeModel);
            }
            else ++number;
        }
    }

    private void generateCotMob(int number, int startX, int startY, int endX, int endY) {
        while (number-- > 0) {
            int modelPosX = random.nextInt(endX - startX) + startX;
            int modelPosY = random.nextInt(endY - startY) + startY;
            Model cotMobModel = new CotPsychoModel(modelPosX, modelPosY);

            if (gameContext.hitboxManager.isValid(cotMobModel, modelPosX, modelPosY)) {
                gameContext.modelManager.spawnModel(cotMobModel);
            }
            else ++number;
        }
    }

    private void generateSupportingObject(Class<?> clazz, int amount, int startX, int startY, int endX, int endY, Object... args) {
        Object[] fullArgs = new Object[args.length + 2];
        System.arraycopy(args, 0, fullArgs, 2, args.length);

        int count = 0;
        for (int i = 0; i < MAX_TRIES; ++i) {
            fullArgs[0] = random.nextInt(endX - startX) + startX;
            fullArgs[1] = random.nextInt(endY - startY) + startY;

            Model model = (Model) Helper.createObject(clazz, fullArgs);
            if (gameContext.hitboxManager.isValid(model, (int) fullArgs[0], (int) fullArgs[1])) {
                gameContext.modelManager.spawnModel(model);
                ++count;
                if (count >= amount) break;
            }
        }
    }
}
