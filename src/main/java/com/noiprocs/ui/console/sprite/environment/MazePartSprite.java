package com.noiprocs.ui.console.sprite.environment;

import com.noiprocs.core.config.Config;
import com.noiprocs.ui.console.sprite.ConsoleSprite;

public class MazePartSprite extends ConsoleSprite {
    private static final char WALL_TEXTURE = '░';
    private static final char[][] TEXTURE = new char[Config.MAZE_WALL_THICKNESS_HEIGHT][Config.MAZE_WALL_THICKNESS_WIDTH];

    static {
        for (int i = 0; i < Config.MAZE_WALL_THICKNESS_HEIGHT; ++i) {
            for (int j = 0; j < Config.MAZE_WALL_THICKNESS_WIDTH; ++j) {
                TEXTURE[i][j] = WALL_TEXTURE;
            }
        }
    }

    public MazePartSprite() {
        super(TEXTURE);
    }
}
