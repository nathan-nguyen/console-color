package com.noiprocs.core.config;

public class Config {
    public static final int MAX_FPS = 30;
    public static final String SAVE_FILE_NAME = "last_checkpoint.dat";
    public static final int MODEL_SYNCHRONISATION_DELAY = MAX_FPS / 3;

    public static final int RENDER_RANGE = 120;
    public static final boolean IS_FREEZE = false;

    public static final int BROADCAST_DELAY = 1;
    public static final int AUTO_SAVE_DURATION = 1800;

    public static boolean DISABLE_PLAYER = false;
}
