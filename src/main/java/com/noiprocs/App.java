package com.noiprocs;

import com.noiprocs.core.GameContext;
import com.noiprocs.core.config.Config;
import com.noiprocs.ui.console.ConsoleGameScreen;
import com.noiprocs.ui.console.hitbox.ConsoleHitboxManager;
import com.noiprocs.ui.console.sprite.ConsoleSpriteManager;

import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        String platform = args[0];
        String username = args[1];
        String type = args[2];
        String hostname = args[3];
        int port = Integer.parseInt(args[4]);

        // Initialize gameContext
        GameContext gameContext = GameContext.build(
                platform, username, type, hostname, port,
                new ConsoleHitboxManager(),
                new ConsoleSpriteManager(),
                new ConsoleGameScreen()
        );

        // Start a separate thread for game, main thread is for control
        Thread thread = new Thread(gameContext::run);
        thread.start();

        if (Config.DISABLE_PLAYER) {
            return;
        }

        try (Scanner scanner = new Scanner(System.in)) {
            while (true) {
                String line = scanner.nextLine();
                gameContext.controlManager.processInput(line);
            }
        }
    }
}
