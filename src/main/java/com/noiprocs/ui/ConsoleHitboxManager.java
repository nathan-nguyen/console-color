package com.noiprocs.ui;

import com.noiprocs.core.GameContext;
import com.noiprocs.core.config.Config;
import com.noiprocs.core.graphics.HitboxManagerInterface;
import com.noiprocs.core.graphics.RenderableSprite;
import com.noiprocs.core.model.Model;
import com.noiprocs.ui.sprite.mob.character.ConsoleSprite;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static com.noiprocs.ui.UIConfig.HEIGHT;
import static com.noiprocs.ui.UIConfig.WIDTH;

public class ConsoleHitboxManager implements HitboxManagerInterface {
    private static final Logger logger = LoggerFactory.getLogger(ConsoleHitboxManager.class);

    private GameContext gameContext;

    @Override
    public void setGameContext(GameContext gameContext) {
        this.gameContext = gameContext;
    }

    @Override
    public boolean isValid(Model model, int nextX, int nextY) {
        // Generate current hit box map
        List<RenderableSprite> renderableSpriteList =
                gameContext.spriteManager.getRenderableObjectListWithinRange(nextX, nextY, Config.RENDER_RANGE);

        int offsetX = nextX - HEIGHT / 2;
        int offsetY = nextY - WIDTH / 2;

        boolean[][] map = constructCurrentHitboxMap(offsetX, offsetY, renderableSpriteList);

        boolean isExisting = gameContext.spriteManager.renderableObjectMap.containsKey(model.id);
        RenderableSprite renderableSprite = isExisting ? gameContext.spriteManager.renderableObjectMap.get(model.id)
                : gameContext.spriteManager.createRenderableObject(model);

        logger.info("Model " + model + " is already existing: " + isExisting);

        // Model is already existing
        if (isExisting) {
            this.removeFromHitboxMap(map, offsetX, offsetY, renderableSprite);
        }

        // Check whether next position is valid
        return isValidNextPosition(nextX, nextY, map, offsetX, offsetY, renderableSprite);
    }

    // Check whether next object position is valid
    private boolean isValidNextPosition(
            int nextX, int nextY, boolean[][] map, int offsetX, int offsetY, RenderableSprite renderableSprite
    ) {
        char[][] texture = ((ConsoleSprite) renderableSprite).getTexture();

        for (int i = 0; i < texture.length; ++i) {
            for (int j = 0; j < texture[0].length; ++j) {
                int x = nextX + i - offsetX;
                int y = nextY + j - offsetY;
                if (x >= 0 && x < HEIGHT && y >= 0 && y < WIDTH) {
                    if (map[x][y]) return false;
                }
            }
        }
        return true;
    }

    // Remove current object from hitbox map
    private void removeFromHitboxMap(boolean[][] map, int offsetX, int offsetY, RenderableSprite renderableSprite) {
        char[][] texture = ((ConsoleSprite) renderableSprite).getTexture();
        int posX = renderableSprite.model.posX;
        int posY = renderableSprite.model.posY;

        for (int i = 0; i < texture.length; ++i) {
            for (int j = 0; j < texture[0].length; ++j) {
                int x = posX + i - offsetX;
                int y = posY + j - offsetY;
                if (x >= 0 && x < HEIGHT && y >= 0 && y < WIDTH) map[x][y] = false;
            }
        }
    }

    // Construct current hitbox map
    private boolean[][] constructCurrentHitboxMap(
            int offsetX, int offsetY, List<RenderableSprite> renderableSpriteList
    ) {
        boolean[][] map = new boolean[HEIGHT][WIDTH];

        logger.info("Constructing hit box map with existing model: " + renderableSpriteList.size());

        for (RenderableSprite renderableSprite : renderableSpriteList) {
            char[][] texture = ((ConsoleSprite) renderableSprite).getTexture();

            int posX = renderableSprite.model.posX;
            int posY = renderableSprite.model.posY;

            for (int i = 0; i < texture.length; ++i) {
                for (int j = 0; j < texture[0].length; ++j) {
                    int x = posX + i - offsetX;
                    int y = posY + j - offsetY;
                    if (x >= 0 && x < HEIGHT && y >= 0 && y < WIDTH) map[x][y] = texture[i][j] != 0;
                }
            }
        }

        return map;
    }
}
