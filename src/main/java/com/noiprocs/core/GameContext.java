package com.noiprocs.core;

import com.noiprocs.core.graphics.GameScreenInterface;
import com.noiprocs.core.graphics.HitboxManagerInterface;
import com.noiprocs.core.graphics.SpriteManager;
import com.noiprocs.core.model.ModelManager;
import com.noiprocs.core.network.NetworkManager;

public class GameContext {
    public final NetworkManager networkManager = new NetworkManager(this);
    public final ModelManager modelManager = new ModelManager(this);
    public final ControlManager controlManager = new ControlManager(this);

    public final String platform;
    public final String username;
    public final boolean isServer;
    public final String hostname;
    public final int port;

    public int worldCounter = 0;

    public SpriteManager spriteManager;
    public HitboxManagerInterface hitboxManager;

    private GameScreenInterface gameScreen;

    public GameContext(String platform, String username, String type, String hostname, int port) {
        this.platform = platform;
        this.username = username;
        this.isServer = type.equals("server");
        this.hostname = hostname;
        this.port = port;
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
        modelManager.update(dt);

        // Synchronize data with modelManager
        spriteManager.update(dt);

        // Render graphics
        gameScreen.render(dt);
    }
}
