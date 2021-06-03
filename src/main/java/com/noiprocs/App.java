package com.noiprocs;

import com.noiprocs.core.GameContext;
import com.noiprocs.ui.ConsoleGameScreen;
import com.noiprocs.ui.ConsoleSpriteManager;

import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        // Initialize gameContext
        GameContext gameContext = new GameContext();
        gameContext.setSpriteManager(new ConsoleSpriteManager());
        gameContext.setGameScreen(new ConsoleGameScreen());

        Runnable task = () -> {
            gameContext.run();
        };

        Thread thread = new Thread(task);
        thread.start();

        Scanner scanner = new Scanner(System.in);
        while (true) {
            String line = scanner.nextLine();
            gameContext.controlManager.processCommand(line);
        }
    }
}
