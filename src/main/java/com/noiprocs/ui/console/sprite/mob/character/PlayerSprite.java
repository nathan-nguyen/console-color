package com.noiprocs.ui.console.sprite.mob.character;

import com.noiprocs.core.model.mob.character.PlayerModel;
import com.noiprocs.ui.console.sprite.ConsoleSprite;

public class PlayerSprite extends ConsoleSprite {
    private static final char[][] TEXTURE = {
            {0, 0, 0, 'o', 0},
            {0, 0, '(', '|', ')'},
            {0, 0, '/', 0, '\\'},
    };

    private static final char[][] RIGHT_ACTION_PERFORMANCE_0 = {
            {0, 0, 0, 'o', 0, '_'},
            {0, 0, '/', '|', '\\', '|'},
            {0, 0, '/', 0, '\\', 0}
    };

    private static final char[][] RIGHT_ACTION_PERFORMANCE_1 = {
            {0, 0, 0, 'o', 0, 0, 0},
            {0, 0, '/', '|', '\\', '/', '\''},
            {0, 0, '/', 0, '\\', 0, 0}
    };

    private static final char[][] RIGHT_ACTION_PERFORMANCE_2 = {
            {0, 0, 0, 'o', 0, 0, 0},
            {0, 0, '/', '|', '\\', '_', '_'},
            {0, 0, '/', 0, '\\', 0, '\''}
    };

    private static final char[][] LEFT_ACTION_PERFORMANCE_0 = {
            {0, '_', 0, 'o', 0},
            {0, '|', '/', '|', '\\'},
            {0, 0, '/', 0, '\\'}
    };

    private static final char[][] LEFT_ACTION_PERFORMANCE_1 = {
            {0, 0, 0, 'o', 0},
            {'\'', '\\', '/', '|', '\\'},
            {0, 0, '/', 0, '\\'}
    };

    private static final char[][] LEFT_ACTION_PERFORMANCE_2 = {
            {0, 0, 0, 'o', 0},
            {'_', '_', '/', '|', '\\'},
            {'\'', 0, '/', 0, '\\'}
    };

    private static final int[] LEFT_INTERACTION_POINT = {1, 0};
    private static final int[] RIGHT_INTERACTION_POINT = {1, 6};

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
            if (pm.actionCounter / 2 == 0) return RIGHT_ACTION_PERFORMANCE_0;
            else if (pm.actionCounter / 2 == 1) return RIGHT_ACTION_PERFORMANCE_1;
            else if (pm.actionCounter / 2 == 2) return RIGHT_ACTION_PERFORMANCE_2;
        } else if (pm.action == PlayerModel.Action.LEFT_ACTION) {
            if (pm.actionCounter / 2 == 0) return LEFT_ACTION_PERFORMANCE_0;
            else if (pm.actionCounter / 2 == 1) return LEFT_ACTION_PERFORMANCE_1;
            else if (pm.actionCounter / 2 == 2) return LEFT_ACTION_PERFORMANCE_2;
        }
        return TEXTURE;
    }
}