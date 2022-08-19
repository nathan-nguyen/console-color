package com.noiprocs.ui.console;

import com.noiprocs.core.GameContext;
import com.noiprocs.core.config.Config;
import com.noiprocs.core.graphics.HitboxManagerInterface;
import com.noiprocs.core.graphics.RenderableSprite;
import com.noiprocs.core.model.Model;
import com.noiprocs.core.model.mob.character.PlayerModel;
import com.noiprocs.ui.console.sprite.ConsoleSprite;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

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
    public Model getModel(int locX, int locY, String ignoreModelId, int[][] interactivePointArr) {
        // Generate current hit box map
        List<RenderableSprite> renderableSpriteList =
                gameContext.spriteManager.getAllRenderableObjectListWithinRange(locX, locY, Config.RENDER_RANGE);

        int offsetX = locX - HEIGHT / 2;
        int offsetY = locY - WIDTH / 2;

        Model[][] map = new Model[HEIGHT][WIDTH];

        for (RenderableSprite renderableSprite : renderableSpriteList) {
            if (renderableSprite.id.equals(ignoreModelId)) continue;

            char[][] texture = ((ConsoleSprite) renderableSprite).getTexture();

            Model model = renderableSprite.getModel();

            int posX = model.posX;
            int posY = model.posY;

            for (int i = 0; i < texture.length; ++i) {
                for (int j = 0; j < texture[0].length; ++j) {
                    if (texture[i][j] == 0) continue;
                    int x = posX + i - offsetX;
                    int y = posY + j - offsetY;
                    if (x >= 0 && x < HEIGHT && y >= 0 && y < WIDTH) map[x][y] = model;
                }
            }
        }

        for (int[] point: interactivePointArr) {
            Model model = map[point[0] + HEIGHT / 2][point[1] + WIDTH / 2];
            if (model != null) return model;
        }
        return null;
    }

    @Override
    public boolean isValid(Model model, int nextX, int nextY) {
        // Generate current hit box map
        List<RenderableSprite> renderableSpriteList =
                gameContext.spriteManager.getAllRenderableObjectListWithinRange(nextX, nextY, Config.RENDER_RANGE);

        int offsetX = nextX - HEIGHT / 2;
        int offsetY = nextY - WIDTH / 2;

        boolean[][] collideHitboxMap = constructCurrentHitboxMap(
                offsetX, offsetY, renderableSpriteList, model
        );

        RenderableSprite renderableSprite = gameContext.spriteManager.renderableSpriteMap.getOrDefault(
                model.id,
                gameContext.spriteManager.createRenderableObject(model)
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
            int offsetX, int offsetY, List<RenderableSprite> renderableSpriteList, Model targetModel
    ) {
        boolean ignorePlayer = targetModel instanceof PlayerModel;
        boolean[][] map = new boolean[HEIGHT][WIDTH];

        logger.debug("Constructing hit box map with existing model: " + renderableSpriteList.size());

        for (RenderableSprite renderableSprite : renderableSpriteList) {
            Model model = renderableSprite.getModel();

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
