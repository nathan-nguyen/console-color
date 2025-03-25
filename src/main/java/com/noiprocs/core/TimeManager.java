package com.noiprocs.core;

import com.noiprocs.core.config.Config;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.TimeUnit;

public abstract class TimeManager {
    private static final Logger logger = LogManager.getLogger(TimeManager.class);

    private long lastTimestamp = 0;

    public void run() {
        int deltaMs = 1000 / Config.MAX_FPS;

        while (true) {
            update(deltaMs);
            long waitTime = deltaMs - (System.currentTimeMillis() - lastTimestamp);
            try {
                if (waitTime < 0) {
                    logger.warn("Running longer expected time: {}", waitTime);
                }
                TimeUnit.MILLISECONDS.sleep(waitTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            lastTimestamp = System.currentTimeMillis();
        }
    }

    protected abstract void update(int dt);
}
