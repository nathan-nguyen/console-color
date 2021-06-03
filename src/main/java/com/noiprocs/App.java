package com.noiprocs;

import com.noiprocs.core.GameContext;
import com.noiprocs.ui.ConsoleGameScreen;
import com.noiprocs.ui.ConsoleSpriteManager;

public class App {
    public static void main(String[] args) {
        // Initialize gameContext
        GameContext gameContext = new GameContext();
        gameContext.setSpriteManager(new ConsoleSpriteManager());
        gameContext.setGameScreen(new ConsoleGameScreen());

        gameContext.run();
    }
}
