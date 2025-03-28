package com.noiprocs.core.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MetricCollector {
    private static final Logger logger = LogManager.getLogger(MetricCollector.class);

    public static final RollingWindowStatistics frameTimeMsStats = new RollingWindowStatistics(500);

    public static final RollingWindowStatistics modelManagerRuntimeStats = new RollingWindowStatistics(500);
    public static final RollingWindowStatistics broadcastRuntimeStats = new RollingWindowStatistics(500);
    public static final RollingWindowStatistics spriteManagerRuntimeStats = new RollingWindowStatistics(500);

    public static final RollingWindowStatistics updateModelRuntimeStats = new RollingWindowStatistics(500);
    public static final RollingWindowStatistics switchChunkRuntimeStats = new RollingWindowStatistics(500);

    public static final RollingWindowStatistics packageSizePerClientBytes = new RollingWindowStatistics(500);

    public static void printMetrics() {
        logger.info("ModelManager process time (ms): Last {} - Average {}",
                modelManagerRuntimeStats.getLast(),
                modelManagerRuntimeStats.getAvg()
        );
        logger.info("Broadcast process time (ms): {}", broadcastRuntimeStats.getLast());
        logger.info("SpriteManager process time (ms): {}", spriteManagerRuntimeStats.getLast());

        logger.info("ModelManager: Update Models process time (ms): {}", updateModelRuntimeStats.getLast());
        logger.info("ModelManager: Switch Chunk process time (ms): {}", switchChunkRuntimeStats.getLast());

        logger.info("Frame time (ms): Last {} - Average {}",
                frameTimeMsStats.getLast(),
                frameTimeMsStats.getAvg()
        );

        logger.info("Package size per client: Average {} bytes", packageSizePerClientBytes.getAvg());
    }

    public static long getAvgFps() {
        long avg =  frameTimeMsStats.getAvg();
        return avg > 0 ? 1000L / avg : -1;
    }
}
