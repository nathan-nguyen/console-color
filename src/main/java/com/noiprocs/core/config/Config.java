package com.noiprocs.core.config;

public class Config {
    public static final int MAX_FPS = 60;
    public static final int GAME_TICKS_PER_SECOND = 20;
    public static final int GAME_TICK_FRAMES = MAX_FPS / GAME_TICKS_PER_SECOND;

    public static final String SAVE_FILE_NAME = "last_checkpoint.dat";
    public static final int MODEL_SYNCHRONISATION_DELAY = MAX_FPS / 10;

    public static final int RENDER_RANGE = 120;
    public static final boolean IS_FREEZE = false;

    public static final int BROADCAST_DELAY = 3;
    public static final int AUTO_SAVE_DURATION = 1800;

    public static boolean DISABLE_PLAYER = false;
    public static boolean CLEAR_SCREEN = true;

    public static final int MONITOR_DEBUG_DELAY = 10 * MAX_FPS;
    public static final boolean DEBUG_MODE = false;

    public static final boolean USE_KRYO_SERIALIZATION = true;
    // Whether to use a background thread to broadcast data
    public static final boolean USE_BROADCAST_BG_THREAD = false;

    public static final int MAZE_WALL_THICKNESS_HEIGHT = 10;
    public static final int MAZE_WALL_THICKNESS_WIDTH = 20;
}
