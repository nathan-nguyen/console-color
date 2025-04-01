package com.noiprocs.core;

import com.noiprocs.core.config.Config;
import com.noiprocs.core.graphics.GameScreenInterface;
import com.noiprocs.core.graphics.SpriteManager;
import com.noiprocs.core.hitbox.HitboxManagerInterface;
import com.noiprocs.core.model.ModelManager;
import com.noiprocs.core.network.NetworkManager;
import com.noiprocs.core.util.MetricCollector;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GameContext {
    private static final Logger logger = LogManager.getLogger(GameContext.class);
    private static GameContext instance;

    public final NetworkManager networkManager = new NetworkManager(this);
    public final ModelManager modelManager = new ModelManager(this);
    public final ControlManager controlManager = new ControlManager(this);

    public final String platform;
    public final String username;
    public final boolean isServer;
    public final String hostname;
    public final int port;

    public final SpriteManager spriteManager;
    private final GameScreenInterface gameScreen;
    public final HitboxManagerInterface hitboxManager;

    public int worldCounter = 0;

    private GameContext(
            String platform, String username, String type, String hostname, int port,
            HitboxManagerInterface hitboxManager,
            SpriteManager spritemanager,
            GameScreenInterface gameScreen
    ) {
        this.platform = platform;
        this.username = username;
        this.isServer = type.equals("server");
        this.hostname = hostname;
        this.port = port;

        // Does not render player if current instance is server
        if (this.isServer) Config.DISABLE_PLAYER = true;

        this.hitboxManager = hitboxManager;
        this.hitboxManager.setGameContext(this);

        this.spriteManager = spritemanager;
        this.spriteManager.setGameContext(this);

        this.gameScreen = gameScreen;
        this.gameScreen.setGameContext(this);
    }

    public static GameContext build(
            String platform, String username, String type, String hostname, int port,
            HitboxManagerInterface hitboxManager,
            SpriteManager spriteManager,
            GameScreenInterface gameScreen
    ) {
        instance = new GameContext(
                platform, username, type, hostname, port,
                hitboxManager,
                spriteManager,
                gameScreen
        );
        return instance;
    }

    public static GameContext get() {
        return instance;
    }

    public void run() {
        if (isServer) {
            // Server: Load data from save file or generate new world.

            // Start network services
            networkManager.startServerNetworkService(port);
            modelManager.startServer();
        }
        else {
            // Start network services
            // Client: Send join command to server.
            networkManager.startClientNetworkService(hostname, port);
        }

        // Initialize timeManager
        TimeManager timeManager = new TimeManager() {
            @Override
            protected void update(int dt) {
                progress(dt);
            }
        };
        timeManager.run();
    }

    public void progress(int dt) {
        worldCounter += 1;

        controlManager.update(dt);

        long statsTime = System.nanoTime();
        modelManager.update(dt);
        MetricCollector.modelManagerProcessTimeNs.add(System.nanoTime() - statsTime);

        // Server: Broadcast data to all clients
        if (isServer && worldCounter % Config.BROADCAST_DELAY == 0) {
            statsTime = System.currentTimeMillis();
            modelManager.broadcastToClient();
            if (!Config.USE_BROADCAST_BG_THREAD) {
                networkManager.serverMessageQueue.broadcastMessage();
            }
            MetricCollector.broadcastTimeMs.add(System.currentTimeMillis() - statsTime);
        }

        // Server: Periodically save data to disk.
        if (isServer && worldCounter % Config.AUTO_SAVE_DURATION == 0) {
            modelManager.saveGameData();
        }

        // Synchronize data with modelManager
        statsTime = System.currentTimeMillis();
        spriteManager.update(dt);
        MetricCollector.spriteManagerRenderMs.add(System.currentTimeMillis() - statsTime);

        // Render graphics
        if (!Config.DISABLE_PLAYER) gameScreen.render(dt);

        if (Config.DEBUG_MODE && worldCounter % Config.MONITOR_DEBUG_DELAY == 0) {
            MetricCollector.printMetrics();
        }
    }
}
