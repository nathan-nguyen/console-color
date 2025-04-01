package com.noiprocs.ui.console;

import com.noiprocs.core.GameContext;
import com.noiprocs.core.HitboxManagerInterface;
import com.noiprocs.core.config.Config;
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

    public void setGameContext(GameContext gameContext) {
        this.gameContext = gameContext;
    }

    // Check whether 2 rectangles overlapped, providing top-left and bottom-right positions
    // Note: X is increasing from top to bottom, Y is increasing from left to right.
    public static boolean isOverlapped(
            int tlX1, int tlY1, int brX1, int brY1,
            int tlX2, int tlY2, int brX2, int brY2
    ) {
        return tlX1 < brX2 && brX1 > tlX2 && tlY1 < brY2  && brY1 > tlY2;
    }

    /**
     * Check whether the model could be relocated to provided position.
     * - Find all chunks surrounded the model.
     * - Find all models in Step 1 chunks.
     * - Check whether provided position is valid for the model.
     *
     * @param model: Checking model
     * @param nextX: Next position in X coordinate.
     * @param nextY: Next position in Y coordinate.
     * @return True if model could be relocated.
     */
    @Override
    public boolean isValid(Model model, int nextX, int nextY) {
        boolean ignorePlayer = model instanceof PlayerModel;

        return gameContext.modelManager.getSurroundedChunk(model)
                .stream()
                .flatMap(modelChunkManager -> modelChunkManager.map.values().stream())
                .noneMatch(surroundedModel -> {
                    if (surroundedModel == model) return false;
                    // Allow players to go through each other
                    if (ignorePlayer && surroundedModel instanceof PlayerModel) return false;
                    return isOverlapped(
                            nextX, nextY, nextX + model.hitboxHeight, nextY + model.hitboxWidth,
                            surroundedModel.posX, surroundedModel.posY, surroundedModel.posX + surroundedModel.hitboxHeight, surroundedModel.posY + surroundedModel.hitboxWidth
                    );
                });
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
}
