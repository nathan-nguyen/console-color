package com.noiprocs.core.config;

public class Config {
    public static final int MAX_FPS = 60;
    public static final int GAME_TICKS_PER_SECOND = 20;
    public static final int GAME_TICK_FRAMES = MAX_FPS / GAME_TICKS_PER_SECOND;

    public static final String SAVE_FILE_NAME = "last_checkpoint.dat";
    public static final int MODEL_SYNCHRONISATION_DELAY = MAX_FPS / 3;

    public static final int RENDER_RANGE = 120;
    public static final boolean IS_FREEZE = false;

    public static final int BROADCAST_DELAY = 3;
    public static final int AUTO_SAVE_DURATION = 1800;

    public static boolean DISABLE_PLAYER = false;
}
