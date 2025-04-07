package com.noiprocs.ui.console;

import com.noiprocs.core.GameContext;
import com.noiprocs.core.config.Config;
import com.noiprocs.core.graphics.GameScreenInterface;
import com.noiprocs.core.model.Model;
import com.noiprocs.core.model.item.Item;
import com.noiprocs.core.model.mob.character.PlayerModel;
import com.noiprocs.core.util.MetricCollector;
import com.noiprocs.ui.console.sprite.ConsoleSprite;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static com.noiprocs.ui.console.ConsoleUIConfig.HEIGHT;
import static com.noiprocs.ui.console.ConsoleUIConfig.WIDTH;

public class ConsoleGameScreen implements GameScreenInterface {
    private final char[][] map = new char[HEIGHT][WIDTH];
    protected GameContext gameContext;

    @Override
    public void setGameContext(GameContext gameContext) {
        this.gameContext = gameContext;
    }

    @Override
    public void render(int delta) {
        Model playerModel = gameContext.modelManager.getModel(gameContext.username);
        // Only render when playerModel is existing
        if (playerModel == null) return;

        // Render map
        System.out.println(getScreenContentInString((PlayerModel) playerModel));
    }

    protected String getScreenContentInString(PlayerModel playerModel) {
        // Get list of visible objects not far from player
        // Render order: Models with smaller posX render first.
        int offsetX = playerModel.posX - HEIGHT / 2;
        int offsetY = playerModel.posY - WIDTH / 2;
        this.clearMap();

        List<Model> renderableModelList = gameContext.modelManager.getLocalChunk()
                .flatMap(modelChunk -> modelChunk.map.values().stream())
                .filter(model -> model.isVisible
                        && model.distanceTo(playerModel.posX, playerModel.posY) <= Config.RENDER_RANGE)
                .sorted(Comparator.comparingInt(u -> u.posX))
                .collect(Collectors.toList());

        for (Model model : renderableModelList) {
            ConsoleSprite consoleSprite = (ConsoleSprite) gameContext.spriteManager.createRenderableObject(model);
            char[][] texture = consoleSprite.getTexture(model);

            if (model == null) continue;
            int posX = model.posX - offsetX - consoleSprite.offsetX;
            int posY = model.posY - offsetY - consoleSprite.offsetY;

            // Main player sprite position is always fixed and does not depend on current model position
            // Main player model position could be updated after offsetX and offsetY were calculated. If we use normal
            // way to calculate sprite position, main player sprite might not be rendered in the middle of screen
            if (model.id.equals(playerModel.id)) {
                posX = HEIGHT / 2 - consoleSprite.offsetX;
                posY = WIDTH / 2 - consoleSprite.offsetY;
            }

            this.updateMap(posX, posY, texture);
        }

        // Render map
        return convertMapToString(playerModel);
    }

    private void clearMap() {
        for (int i = 0; i < HEIGHT; ++i) {
            for (int j = 0; j < WIDTH; ++j) map[i][j] = 0;
        }
    }

    private void updateMap(int posX, int posY, char[][] texture) {
        for (int i = 0; i < texture.length; ++i) {
            for (int j = 0; j < texture[0].length; ++j) {
                if (texture[i][j] == 0) continue;
                int x = posX + i;
                int y = posY + j;
                if (x >= 0 && x < HEIGHT && y >= 0 && y < WIDTH) map[x][y] = texture[i][j];
            }
        }
    }

    private String getHudString(PlayerModel playerModel) {
        StringBuilder inventorySb = new StringBuilder();

        Item item = playerModel.inventory[playerModel.currentInventorySlot];
        if (item != null) inventorySb.append(item.name).append(": ").append(item.amount);

        return playerModel.id + " - [" + playerModel.posX + ", " + playerModel.posY +
                "] - Health: " + playerModel.getHealth() + " - Inventory: [" + inventorySb + "] - FPS: " + MetricCollector.getAvgFps();
    }

    private String convertMapToString(PlayerModel playerModel) {
        if (Config.CLEAR_SCREEN) {
            System.out.print("\033[H\033[2J");
            System.out.flush();
        }

        StringBuilder sb = new StringBuilder();
        sb.append(getHudString(playerModel)).append('\n');

        for (int i = 0; i < map.length; ++i) {
            // Add top border
            if (i == 0) {
                for (int j = 0; j < map[0].length + 2; ++j) sb.append('-');
                sb.append('\n');
            }

            // Add map content
            for (int j = 0; j < map[0].length; ++j) {
                // Add left border
                if (j == 0) sb.append('|');

                sb.append(map[i][j] == 0 ? ' ' : map[i][j]);

                // Add right border
                if (j == map[0].length - 1) sb.append('|');
            }
            sb.append('\n');

            // Add bottom border
            if (i == map.length - 1) {
                for (int j = 0; j < map[0].length + 2; ++j) sb.append('-');
                sb.append('\n');
            }
        }
        return sb.toString();
    }
}
