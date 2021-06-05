package com.noiprocs.ui.console.sprite.environment;

import com.noiprocs.core.model.environment.MazePartModel;
import com.noiprocs.ui.console.sprite.ConsoleSprite;

import static com.noiprocs.core.model.environment.MazePartModel.MAZE_PART_DIMENSION;
import static com.noiprocs.core.model.environment.MazePartModel.WALL_THICKNESS_HEIGHT;
import static com.noiprocs.core.model.environment.MazePartModel.WALL_THICKNESS_WIDTH;

public class MazePartSprite extends ConsoleSprite {
    private static final char WALL_TEXTURE = '░';

    public MazePartSprite(String id) {
        super(EMPTY_TEXTURE, id);

        int[][] mazeData = ((MazePartModel) getModel()).data;
        char[][] mazeTexture = new char[MAZE_PART_DIMENSION * WALL_THICKNESS_HEIGHT][MAZE_PART_DIMENSION * WALL_THICKNESS_WIDTH];
        for (int i = 0; i < MAZE_PART_DIMENSION; ++i){
            for (int j = 0; j < MAZE_PART_DIMENSION; ++j){
                if (mazeData[i][j] > 0) continue;
                for (int x = 0; x < WALL_THICKNESS_HEIGHT; ++x) {
                    for (int y = 0; y < WALL_THICKNESS_WIDTH; ++y) {
                        mazeTexture[i * WALL_THICKNESS_HEIGHT + x][j * WALL_THICKNESS_WIDTH + y] = WALL_TEXTURE;
                    }
                }
            }
        }

        this.setTexture(mazeTexture);
    }

    @Override
    public void render() {
    }
}
