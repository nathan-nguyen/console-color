package com.noiprocs.ui;

import com.noiprocs.core.config.Config;
import com.noiprocs.core.graphics.GameScreenInterface;
import com.noiprocs.core.graphics.RenderableSprite;
import com.noiprocs.core.graphics.SpriteManager;
import com.noiprocs.ui.sprite.mob.character.ConsoleSprite;

import java.util.List;

public class ConsoleGameScreen implements GameScreenInterface {
    private SpriteManager spriteManager;
    private static final int WIDTH = 60;
    private static final int HEIGHT = 40;

    @Override
    public void setSpriteManager(SpriteManager spriteManager) {
        this.spriteManager = spriteManager;
    }

    @Override
    public void render(int delta) {
        List<RenderableSprite> renderableSpriteList =
                spriteManager.getAllRenderableObjectWithinRange(Config.RENDER_RANGE);

        char[][] map = new char[HEIGHT][WIDTH];
        for (RenderableSprite renderableSprite: renderableSpriteList) {
            char[][] texture = ((ConsoleSprite) renderableSprite).getTexture();

            int posX = renderableSprite.model.posX;
            int posY = renderableSprite.model.posY;

            for (int i = 0; i < texture.length; ++i) {
                for (int j = 0; j < texture[0].length; ++j) {
                    map[posX + i][posY + j] = texture[i][j];
                }
            }

            System.out.print("\033[H\033[2J");
            System.out.flush();
            for (int i = 0; i < HEIGHT; ++i) {
                for (int j = 0; j < WIDTH; ++j) {
                    System.out.print(map[i][j] == 0 ? " " : map[i][j]);
                }
                System.out.println();
            }
        }
    }
}
