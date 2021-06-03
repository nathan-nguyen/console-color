package com.noiprocs.core;

import com.noiprocs.core.config.Config;

import java.util.concurrent.TimeUnit;

public abstract class TimeManager {
    private final int DELTA_CONSTANT = 1000 / Config.MAX_FPS;
    private long lastTimestamp = 0;

    public void run() {
        while (true) {
            update(DELTA_CONSTANT);
            long waitTime = DELTA_CONSTANT - (System.currentTimeMillis() - lastTimestamp);
            try {
                TimeUnit.MILLISECONDS.sleep(waitTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            lastTimestamp = System.currentTimeMillis();
        }
    }

    protected abstract void update(int dt);
}
