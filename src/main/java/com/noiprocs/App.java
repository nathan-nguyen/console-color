package com.noiprocs;

import com.noiprocs.core.GameContext;
import com.noiprocs.core.graphics.GameScreenInterface;
import com.noiprocs.core.graphics.HitboxManagerInterface;
import com.noiprocs.ui.ConsoleHitboxManager;
import com.noiprocs.core.util.Helper;
import com.noiprocs.ui.ConsoleGameScreen;
import com.noiprocs.ui.ConsoleSpriteManager;

import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        String platform = args[0];
        String username = args[1];
        String type = args[2];
        String hostname = args[3];
        int port = Integer.parseInt(args[4]);

        // Initialize gameContext
        GameContext gameContext = new GameContext(platform, username, type, hostname, port);
        gameContext.setSpriteManager(new ConsoleSpriteManager());

        GameScreenInterface gameScreen = new ConsoleGameScreen();
        gameContext.setGameScreen(gameScreen);

        HitboxManagerInterface hitboxManager = new ConsoleHitboxManager();
        gameContext.setHitboxManager(hitboxManager);

        // Start a separate thread for game, main thread is for control
        Runnable task = () -> gameContext.run();
        Thread thread = new Thread(task);
        thread.start();

        Scanner scanner = new Scanner(System.in);
        while (true) {
            String line = scanner.nextLine();
            gameContext.controlManager.processInput(line);
        }
    }
}
