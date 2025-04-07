package com.noiprocs.ui.console.sprite.environment;

import com.noiprocs.ui.console.sprite.ConsoleSprite;

public class MazePartSprite extends ConsoleSprite {
    public static final int WALL_THICKNESS_HEIGHT = 4;
    public static final int WALL_THICKNESS_WIDTH = 8;
    private static final char WALL_TEXTURE = '░';
    private static final char[][] TEXTURE = new char[WALL_THICKNESS_HEIGHT][WALL_THICKNESS_WIDTH];

    static {
        for (int i = 0; i < WALL_THICKNESS_HEIGHT; ++i) {
            for (int j = 0; j < WALL_THICKNESS_WIDTH; ++j) {
                TEXTURE[i][j] = WALL_TEXTURE;
            }
        }
    }

    public MazePartSprite() {
        super(TEXTURE);
    }
}
