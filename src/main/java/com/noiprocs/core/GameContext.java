package com.noiprocs.core;

import com.noiprocs.core.config.Config;
import com.noiprocs.core.graphics.GameScreenInterface;
import com.noiprocs.core.graphics.HitboxManagerInterface;
import com.noiprocs.core.graphics.SpriteManager;
import com.noiprocs.core.model.ModelManager;
import com.noiprocs.core.network.NetworkManager;
import com.noiprocs.core.util.Helper;
import com.noiprocs.core.util.RollingWindowStatistics;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GameContext {
    private static final Logger logger = LogManager.getLogger(GameContext.class);
    public final NetworkManager networkManager = new NetworkManager(this);
    public final ModelManager modelManager = new ModelManager(this);
    public final ControlManager controlManager = new ControlManager(this);

    public final String platform;
    public final String username;
    public final boolean isServer;
    public final String hostname;
    public final int port;

    public SpriteManager spriteManager;
    public HitboxManagerInterface hitboxManager;
    public int worldCounter = 0;

    private GameScreenInterface gameScreen;

    // For statistics
    private final RollingWindowStatistics modelManagerRuntimeStats = new RollingWindowStatistics(500);
    private final RollingWindowStatistics broadcastRuntimeStats = new RollingWindowStatistics(500);
    private final RollingWindowStatistics spriteManagerRuntimeStats = new RollingWindowStatistics(500);

    public GameContext(String platform, String username, String type, String hostname, int port) {
        this.platform = platform;
        this.username = username;
        this.isServer = type.equals("server");
        this.hostname = hostname;
        this.port = port;

        Helper.GAME_CONTEXT = this;
    }

    public void run() {
        // Start network services
        networkManager.startNetworkService(isServer, hostname, port);

        /*
         * If Server: Load data from save file or generate new world.
         * If client: Send join command to server. This required network connection to be setup beforehand.
         */
        modelManager.start();

        // Initialize timeManager
        TimeManager timeManager = new TimeManager() {
            @Override
            protected void update(int dt) {
                progress(dt);
            }
        };
        timeManager.run();
    }

    public void setSpriteManager(SpriteManager spriteManager) {
        this.spriteManager = spriteManager;
        this.spriteManager.setGameContext(this);
    }

    public void setGameScreen(GameScreenInterface gameScreen) {
        this.gameScreen = gameScreen;
        this.gameScreen.setGameContext(this);
    }

    public void setHitboxManager(HitboxManagerInterface hitboxManager) {
        this.hitboxManager = hitboxManager;
        hitboxManager.setGameContext(this);
    }

    public void progress(int dt) {
        worldCounter += 1;

        /*
         * Server:
         *     - Broadcast data to all clients.
         *     - Periodically save data to disk.
         */
        long statsTime = System.currentTimeMillis();
        modelManager.update(dt);
        modelManagerRuntimeStats.add(System.currentTimeMillis() - statsTime);

        // Broadcast data
        if (isServer && worldCounter % Config.BROADCAST_DELAY == 0) {
            statsTime = System.currentTimeMillis();
            modelManager.broadcastToClient();
            broadcastRuntimeStats.add(System.currentTimeMillis() - statsTime);
        }

        // Synchronize data with modelManager
        statsTime = System.currentTimeMillis();
        spriteManager.update(dt);
        spriteManagerRuntimeStats.add(System.currentTimeMillis() - statsTime);

        if (worldCounter % Config.STATISTICS_LOG_DELAY == 0) {
            logger.info("ModelManager process time (ms): " + modelManagerRuntimeStats.getAvg());
            logger.info("Broadcast process time (ms): " + broadcastRuntimeStats.getAvg());
            logger.info("SpriteManager process time (ms): " + spriteManagerRuntimeStats.getAvg());
        }

        // Render graphics
        if (!Config.DISABLE_PLAYER) gameScreen.render(dt);
    }
}
