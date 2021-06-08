package com.noiprocs.core;

import com.noiprocs.core.config.Config;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.TimeUnit;

public abstract class TimeManager {
    private static final Logger logger = LogManager.getLogger(TimeManager.class);
    private final int DELTA_CONSTANT = 1000 / Config.MAX_FPS;
    private long lastTimestamp = 0;

    public void run() {
        while (true) {
            update(DELTA_CONSTANT);
            long waitTime = DELTA_CONSTANT - (System.currentTimeMillis() - lastTimestamp);
            try {
                if (waitTime < 0) logger.warn(" Running longer expected time");
                TimeUnit.MILLISECONDS.sleep(waitTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            lastTimestamp = System.currentTimeMillis();
        }
    }

    protected abstract void update(int dt);
}
