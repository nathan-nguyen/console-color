package com.noiprocs.core;

import com.noiprocs.core.graphics.GameScreenInterface;
import com.noiprocs.core.graphics.HitboxManagerInterface;
import com.noiprocs.core.graphics.SpriteManager;
import com.noiprocs.core.model.ModelManager;
import com.noiprocs.core.network.NetworkManager;

public class GameContext {
    private final NetworkManager networkManager = new NetworkManager();
    public final ModelManager modelManager = new ModelManager(this, networkManager);
    public final ControlManager controlManager = new ControlManager(this);

    public final String platform;
    public final String username;
    public final boolean isServer;
    public final String hostname;
    public final int port;

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

    private void startNetworkService() {
        if (isServer) networkManager.startServer(hostname, port);
        else networkManager.startClient(hostname, port);
        networkManager.startService();
        networkManager.setNetworkReceiver(modelManager);
    }

    public void run() {
        // Load data from save file for Server
        modelManager.start();

        // Start network services
        this.startNetworkService();

        // Initialize timeManager
        TimeManager timeManager = new TimeManager() {
            @Override
            protected void update(int dt) {
                spriteManager.update(dt);
                gameScreen.render(dt);
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
}
