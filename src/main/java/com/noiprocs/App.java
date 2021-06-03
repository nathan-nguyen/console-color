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
        // Initialize gameContext
        GameContext gameContext = new GameContext();
        gameContext.setSpriteManager(new ConsoleSpriteManager());

        GameScreenInterface gameScreen = new ConsoleGameScreen();
        gameContext.setGameScreen(gameScreen);

        HitboxManagerInterface hitboxManager = new ConsoleHitboxManager();
        gameContext.setHitboxManager(hitboxManager);

        Helper.GAME_CONTEXT = gameContext;

        // Start a separate thread for game, main thread is for control
        Runnable task = () -> gameContext.run();
        Thread thread = new Thread(task);
        thread.start();

        Scanner scanner = new Scanner(System.in);
        while (true) {
            String line = scanner.nextLine();
            gameContext.controlManager.processCommand(line);
        }
    }
}
