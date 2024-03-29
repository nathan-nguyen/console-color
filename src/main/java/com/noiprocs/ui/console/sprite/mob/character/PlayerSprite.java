package com.noiprocs.ui.console.sprite.mob.character;

import com.noiprocs.core.model.mob.character.PlayerModel;
import com.noiprocs.ui.console.sprite.ConsoleSprite;

public class PlayerSprite extends ConsoleSprite {
    private static final int OFFSET_X = 2, OFFSET_Y = 2;
    private static final char[][] TEXTURE = {
            {0, 0, 0, 'o', 0},
            {0, 0, '(', '|', ')'},
            {0, 0, '/', 0, '\\'},
    };

    private static final char[][][] RIGHT_ACTION_PERFORMANCE = {
            {
                    {0, 0, 0, 'o', 0, '_'},
                    {0, 0, '/', '|', '\\', '|'},
                    {0, 0, '/', 0, '\\', 0}
            },
            {
                    {0, 0, 0, 'o', 0, 0, 0},
                    {0, 0, '/', '|', '\\', '/', '\''},
                    {0, 0, '/', 0, '\\', 0, 0}
            },
            {
                    {0, 0, 0, 'o', 0, 0, 0},
                    {0, 0, '/', '|', '\\', '_', '_'},
                    {0, 0, '/', 0, '\\', 0, '\''}
            }
    };

    private static final char[][][] LEFT_ACTION_PERFORMANCE = {
            {
                    {0, '_', 0, 'o', 0},
                    {0, '|', '/', '|', '\\'},
                    {0, 0, '/', 0, '\\'}
            },
            {
                    {0, 0, 0, 'o', 0},
                    {'\'', '\\', '/', '|', '\\'},
                    {0, 0, '/', 0, '\\'}
            },
            {
                    {0, 0, 0, 'o', 0},
                    {'_', '_', '/', '|', '\\'},
                    {'\'', 0, '/', 0, '\\'}
            }
    };

    public PlayerSprite(String id) {
        super(TEXTURE, id, OFFSET_X, OFFSET_Y);
    }

    @Override
    public void render() {
    }

    @Override
    public char[][] getTexture() {
        PlayerModel pm = (PlayerModel) getModel();
        if (pm.action == PlayerModel.Action.RIGHT_ACTION) {
            return RIGHT_ACTION_PERFORMANCE[(pm.actionCounter / 2) % RIGHT_ACTION_PERFORMANCE.length];
        } else if (pm.action == PlayerModel.Action.LEFT_ACTION) {
            return LEFT_ACTION_PERFORMANCE[(pm.actionCounter / 2) % LEFT_ACTION_PERFORMANCE.length];
        }
        return TEXTURE;
    }
}