package com.noiprocs.core;

import com.noiprocs.core.config.Config;
import com.noiprocs.core.util.MetricCollector;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.TimeUnit;

public abstract class TimeManager {
    private static final Logger logger = LogManager.getLogger(TimeManager.class);

    private long lastTimestamp = System.currentTimeMillis();

    public void run() {
        int deltaMs = 1000 / Config.MAX_FPS;

        while (true) {
            try {
                update(deltaMs);
                long waitTime = deltaMs - (System.currentTimeMillis() - lastTimestamp);
                TimeUnit.MILLISECONDS.sleep(waitTime);
                MetricCollector.frameTimeMsStats.add(System.currentTimeMillis() - lastTimestamp);
                if (Config.DEBUG_MODE && waitTime < 0) {
                    logger.warn("Running longer expected time: {}", waitTime);
                    MetricCollector.printMetrics();
                }
                lastTimestamp = System.currentTimeMillis();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    protected abstract void update(int dt);
}
