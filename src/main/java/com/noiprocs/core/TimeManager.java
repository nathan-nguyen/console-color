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
            update(deltaMs);
            long waitTime = deltaMs - (System.currentTimeMillis() - lastTimestamp);
            if (MetricCollector.getAvgFps() < Config.MONITOR_MIN_FPS_THRESHOLD) {
                logger.warn("Running longer expected time: {}", waitTime);
                MetricCollector.printMetrics();
            }
            try {
                TimeUnit.MILLISECONDS.sleep(waitTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            MetricCollector.frameTimeMsStats.add(System.currentTimeMillis() - lastTimestamp);
            lastTimestamp = System.currentTimeMillis();
        }
    }

    protected abstract void update(int dt);
}
