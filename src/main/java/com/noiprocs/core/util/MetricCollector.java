package com.noiprocs.core.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MetricCollector {
    private static final Logger logger = LogManager.getLogger(MetricCollector.class);

    public static final RollingWindowStatistics frameTimeMsStats = new RollingWindowStatistics(500);

    public static final RollingWindowStatistics modelManagerProcessTimeNs = new RollingWindowStatistics(500);
    public static final RollingWindowStatistics broadcastTimeNs = new RollingWindowStatistics(500);
    public static final RollingWindowStatistics gameScreenRenderNs = new RollingWindowStatistics(500);

    public static final RollingWindowStatistics updateModelTimeNs = new RollingWindowStatistics(500);
    public static final RollingWindowStatistics switchChunkTimeNs = new RollingWindowStatistics(500);

    public static final RollingWindowStatistics packageSizePerClientBytes = new RollingWindowStatistics(500);

    public static void printMetrics() {
        logger.info("ModelManager process time (ns): Last {} - Average {}",
                String.format("%,d", modelManagerProcessTimeNs.getLast()),
                String.format("%,d", modelManagerProcessTimeNs.getAvg())
        );
        logger.info("Broadcast process time (ns): Last {} - Average {}",
                String.format("%,d", broadcastTimeNs.getLast()),
                String.format("%,d", broadcastTimeNs.getAvg())
        );
        logger.info("GameScreen render time (ns): Last {} - Average {}",
                String.format("%,d", gameScreenRenderNs.getLast()),
                String.format("%,d", gameScreenRenderNs.getAvg())
        );

        logger.info("ModelManager: Update Models process time (ns): Last {} - Average {}",
                String.format("%,d", updateModelTimeNs.getLast()),
                String.format("%,d", updateModelTimeNs.getAvg())
        );
        logger.info("ModelManager: Switch Chunk process time (ns): Last {} - Average {}",
                String.format("%,d", switchChunkTimeNs.getLast()),
                String.format("%,d", switchChunkTimeNs.getAvg())
        );

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
