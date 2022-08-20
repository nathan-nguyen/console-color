package com.noiprocs.ui.console;

import com.noiprocs.core.GameContext;
import com.noiprocs.core.config.Config;
import com.noiprocs.core.graphics.HitboxManagerInterface;
import com.noiprocs.core.model.Model;
import com.noiprocs.core.model.mob.character.PlayerModel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.stream.Collectors;

import static com.noiprocs.ui.console.ConsoleUIConfig.HEIGHT;
import static com.noiprocs.ui.console.ConsoleUIConfig.WIDTH;

public class ConsoleHitboxManager implements HitboxManagerInterface {
    private static final Logger logger = LogManager.getLogger(ConsoleHitboxManager.class);

    private GameContext gameContext;

    @Override
    public void setGameContext(GameContext gameContext) {
        this.gameContext = gameContext;
    }

    @Override
    public Model getModel(Model targetModel, int directionX, int directionY) {
        // Generate current hit box map
        List<Model> surroundedModelList = gameContext.modelManager.getSurroundedChunk(targetModel).stream()
                .flatMap(modelChunkManager -> modelChunkManager.map.values().stream())
                .filter(surroundedModel -> surroundedModel.distanceTo(targetModel.posX, targetModel.posY) <= Config.RENDER_RANGE)
                .collect(Collectors.toList());

        int offsetX = targetModel.posX - HEIGHT / 2;
        int offsetY = targetModel.posY - WIDTH / 2;

        Model[][] map = new Model[HEIGHT][WIDTH];

        for (Model model : surroundedModelList) {
            if (model == targetModel) continue;

            int posX = model.posX;
            int posY = model.posY;

            for (int i = 0; i < model.hitboxHeight; ++i) {
                for (int j = 0; j < model.hitboxWidth; ++j) {
                    int x = posX + i - offsetX;
                    int y = posY + j - offsetY;
                    if (x >= 0 && x < HEIGHT && y >= 0 && y < WIDTH) map[x][y] = model;
                }
            }
        }

        int intensityX = 1 - Math.abs(directionX);
        int intensityY = 1 - Math.abs(directionY);
        int count = intensityX * targetModel.hitboxHeight + intensityY * targetModel.hitboxWidth;

        for (int i = 0; i < count; ++i) {
            int pointX = intensityX * i + (directionX == -1 ? -1 : directionX * targetModel.hitboxHeight);
            int pointY = intensityY * i + (directionY == -1 ? -1 : directionY * targetModel.hitboxWidth);

            Model returnModel = map[pointX + HEIGHT / 2][pointY + WIDTH / 2];
            if (returnModel != null) return returnModel;
        }

        return null;
    }

    @Override
    public boolean isValid(Model model, int nextX, int nextY) {
        // Generate current hit box map
        List<Model> surroundedModelList = gameContext.modelManager.getSurroundedChunk(model).stream()
                .flatMap(modelChunkManager -> modelChunkManager.map.values().stream())
                .filter(surroundedModel -> surroundedModel.distanceTo(nextX, nextY) <= Config.RENDER_RANGE)
                .collect(Collectors.toList());

        logger.debug("Model: " + model + "with number of surrounded models: " + surroundedModelList.size());
        int offsetX = nextX - HEIGHT / 2;
        int offsetY = nextY - WIDTH / 2;

        boolean[][] collideHitboxMap = constructCurrentHitboxMap(
                offsetX, offsetY, surroundedModelList, model
        );

        // Check whether next position is valid
        return isValidNextPosition(nextX, nextY, collideHitboxMap, offsetX, offsetY, model);
    }

    // Check whether next object position is valid
    private boolean isValidNextPosition(
            int nextX, int nextY, boolean[][] map, int offsetX, int offsetY, Model targetModel
    ) {
        for (int i = 0; i < targetModel.hitboxHeight; ++i) {
            for (int j = 0; j < targetModel.hitboxWidth; ++j) {
                int x = nextX + i - offsetX;
                int y = nextY + j - offsetY;
                if (x >= 0 && x < HEIGHT && y >= 0 && y < WIDTH) {
                    if (map[x][y]) return false;
                }
            }
        }
        return true;
    }

    // Construct current hitbox map
    private boolean[][] constructCurrentHitboxMap(
            int offsetX, int offsetY, List<Model> surroundedModelList, Model targetModel
    ) {
        boolean ignorePlayer = targetModel instanceof PlayerModel;
        boolean[][] map = new boolean[HEIGHT][WIDTH];

        for (Model model : surroundedModelList) {
            // To avoid targetModel colliding with itself.
            if (model == targetModel) continue;

            // Allow players to go through each other
            if (ignorePlayer && model instanceof PlayerModel) continue;

            int posX = model.posX;
            int posY = model.posY;

            for (int i = 0; i < model.hitboxHeight; ++i) {
                for (int j = 0; j < model.hitboxWidth; ++j) {
                    int x = posX + i - offsetX;
                    int y = posY + j - offsetY;
                    if (x >= 0 && x < HEIGHT && y >= 0 && y < WIDTH) map[x][y] = true;
                }
            }
        }

        return map;
    }
}
