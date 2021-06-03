package com.noiprocs.ui;

import com.noiprocs.core.config.Config;
import com.noiprocs.core.graphics.GameScreenInterface;
import com.noiprocs.core.graphics.RenderableSprite;
import com.noiprocs.core.graphics.SpriteManager;
import com.noiprocs.ui.sprite.mob.character.ConsoleSprite;

import java.util.List;

public class ConsoleGameScreen implements GameScreenInterface {
    private static final int WIDTH = 60;
    private static final int HEIGHT = 40;
    private final char[][] map = new char[HEIGHT][WIDTH];
    private SpriteManager spriteManager;

    @Override
    public void setSpriteManager(SpriteManager spriteManager) {
        this.spriteManager = spriteManager;
    }

    @Override
    public void render(int delta) {
        List<RenderableSprite> renderableSpriteList =
                spriteManager.getAllRenderableObjectWithinRange(Config.RENDER_RANGE);

        int offsetX = spriteManager.player.posX + HEIGHT / 2;
        int offsetY = spriteManager.player.posY + WIDTH / 2;

        this.clearMap();

        for (RenderableSprite renderableSprite : renderableSpriteList) {
            char[][] texture = ((ConsoleSprite) renderableSprite).getTexture();

            int posX = renderableSprite.model.posX;
            int posY = renderableSprite.model.posY;

            this.updateMap(posX, posY, texture, offsetX, offsetY);
        }

        // Render map
        this.printMap();
    }

    private void clearMap() {
        for (int i = 0; i < HEIGHT; ++i) {
            for (int j = 0; j < WIDTH; ++j) map[i][j] = 0;
        }
    }

    private void updateMap(int posX, int posY, char[][] texture, int offsetX, int offsetY) {
        for (int i = 0; i < texture.length; ++i) {
            for (int j = 0; j < texture[0].length; ++j) {
                int x = offsetX + posX + i;
                int y = offsetY + posY + j;
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
