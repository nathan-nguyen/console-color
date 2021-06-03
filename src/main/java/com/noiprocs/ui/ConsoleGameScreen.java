package com.noiprocs.ui;

import com.noiprocs.core.GameContext;
import com.noiprocs.core.config.Config;
import com.noiprocs.core.graphics.GameScreenInterface;
import com.noiprocs.core.graphics.RenderableSprite;
import com.noiprocs.core.graphics.SpriteManager;
import com.noiprocs.core.model.mob.character.PlayerModel;
import com.noiprocs.ui.sprite.mob.character.ConsoleSprite;

import java.util.List;

import static com.noiprocs.ui.UIConfig.HEIGHT;
import static com.noiprocs.ui.UIConfig.WIDTH;

public class ConsoleGameScreen implements GameScreenInterface {
    private final char[][] map = new char[HEIGHT][WIDTH];
    private GameContext gameContext;

    @Override
    public void setGameContext(GameContext gameContext) {
        this.gameContext = gameContext;
    }

    @Override
    public void render(int delta) {
        PlayerModel playerModel = gameContext.modelManager.getPlayerModel();

        // Get list of objects not far from player
        List<RenderableSprite> renderableSpriteList = gameContext.spriteManager.getRenderableObjectListWithinRange(
                playerModel.posX,
                playerModel.posY,
                Config.RENDER_RANGE
        );

        /* Render order:
         * - PlayerModel renders last.
         * - Models with smaller posY render first.
         */
        renderableSpriteList.sort(
                (u, v) -> (u.model instanceof PlayerModel) ? 1 : Integer.compare(u.model.posY, v.model.posY)
        );

        int offsetX = playerModel.posX - HEIGHT / 2;
        int offsetY = playerModel.posY - WIDTH / 2;

        this.clearMap();

        for (RenderableSprite renderableSprite : renderableSpriteList) {
            char[][] texture = ((ConsoleSprite) renderableSprite).getTexture();

            int posX = renderableSprite.model.posX;
            int posY = renderableSprite.model.posY;

            this.updateMap(posX, posY, texture, offsetX, offsetY);
        }

        // Render map
        if (!Config.IS_FREEZE) this.printMap();
    }

    private void clearMap() {
        for (int i = 0; i < HEIGHT; ++i) {
            for (int j = 0; j < WIDTH; ++j) map[i][j] = 0;
        }
    }

    private void updateMap(int posX, int posY, char[][] texture, int offsetX, int offsetY) {
        for (int i = 0; i < texture.length; ++i) {
            for (int j = 0; j < texture[0].length; ++j) {
                int x = posX + i - offsetX;
                int y = posY + j - offsetY;
                if (x >= 0 && x < HEIGHT && y >= 0 && y < WIDTH) map[x][y] = texture[i][j];
            }
        }
    }

    private void printMap() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
        StringBuilder sb = new StringBuilder();
        for (char[] chars : map) {
            for (int j = 0; j < map[0].length; ++j) sb.append(chars[j] == 0 ? ' ' : chars[j]);
            sb.append('\n');
        }
        System.out.println(sb);
    }
}
