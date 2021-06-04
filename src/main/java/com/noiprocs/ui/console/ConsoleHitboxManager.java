package com.noiprocs.ui.console;

import com.noiprocs.core.GameContext;
import com.noiprocs.core.config.Config;
import com.noiprocs.core.graphics.HitboxManagerInterface;
import com.noiprocs.core.graphics.RenderableSprite;
import com.noiprocs.core.model.Model;
import com.noiprocs.ui.console.sprite.ConsoleSprite;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static com.noiprocs.ui.console.ConsoleUIConfig.HEIGHT;
import static com.noiprocs.ui.console.ConsoleUIConfig.WIDTH;

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

        boolean isExisting = gameContext.spriteManager.renderableSpriteMap.containsKey(model.id);
        RenderableSprite renderableSprite = isExisting ? gameContext.spriteManager.renderableSpriteMap.get(model.id)
                : gameContext.spriteManager.createRenderableObject(model);

//        logger.info("Model " + model + " is already existing: " + isExisting);

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

        Model model = renderableSprite.getModel();
        int posX = model.posX;
        int posY = model.posY;

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

//        logger.info("Constructing hit box map with existing model: " + renderableSpriteList.size());

        for (RenderableSprite renderableSprite : renderableSpriteList) {
            char[][] texture = ((ConsoleSprite) renderableSprite).getTexture();

            Model model = renderableSprite.getModel();
            int posX = model.posX;
            int posY = model.posY;

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
