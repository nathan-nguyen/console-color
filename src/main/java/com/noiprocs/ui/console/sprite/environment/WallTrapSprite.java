package com.noiprocs.ui.console.sprite.environment;

import com.noiprocs.core.config.Config;
import com.noiprocs.core.model.Model;
import com.noiprocs.core.model.environment.WallTrapModel;
import com.noiprocs.ui.console.sprite.ConsoleSprite;

public class WallTrapSprite extends ConsoleSprite {
  private static final char[][] TEXTURE_OPENED =
      new char[Config.MAZE_WALL_THICKNESS_HEIGHT][Config.MAZE_WALL_THICKNESS_WIDTH];
  private static final char[][] TEXTURE_CLOSED =
      new char[Config.MAZE_WALL_THICKNESS_HEIGHT][Config.MAZE_WALL_THICKNESS_WIDTH];

  static {
    for (int i = 0; i < Config.MAZE_WALL_THICKNESS_HEIGHT; ++i) {
      for (int j = 0; j < Config.MAZE_WALL_THICKNESS_WIDTH; ++j) {
        if (i == Config.MAZE_WALL_THICKNESS_HEIGHT / 2 - 1) {
          TEXTURE_CLOSED[i][j] = 'v';
        } else if (i == Config.MAZE_WALL_THICKNESS_HEIGHT / 2) {
          TEXTURE_CLOSED[i][j] = '^';
        } else TEXTURE_CLOSED[i][j] = '░';

        if (i == 0) TEXTURE_OPENED[i][j] = 'v';
        else if (i == Config.MAZE_WALL_THICKNESS_HEIGHT - 1) TEXTURE_OPENED[i][j] = '^';
      }
    }
  }

  public WallTrapSprite() {
    super(EMPTY_TEXTURE);
  }

  @Override
  public char[][] getTexture(Model model) {
    if (((WallTrapModel) model).isClosed()) return TEXTURE_CLOSED;
    return TEXTURE_OPENED;
  }
}
