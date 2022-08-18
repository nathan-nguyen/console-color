package com.noiprocs.ui.console.sprite.mob.character;

import com.noiprocs.core.model.mob.character.PlayerModel;
import com.noiprocs.ui.console.sprite.ConsoleSprite;

public class PlayerSprite extends ConsoleSprite {
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

    private static final int[][] LEFT_INTERACTION_POINT = {{0, 1}, {1, 0}, {1, 1}, {2, 0}};
    private static final int[][] RIGHT_INTERACTION_POINT = {{0, 5}, {1, 5}, {1, 6}, {2, 6}};

    public PlayerSprite(String id) {
        super(TEXTURE, id);
        ((PlayerModel) this.getModel()).setInteractionPoint(LEFT_INTERACTION_POINT, RIGHT_INTERACTION_POINT);
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