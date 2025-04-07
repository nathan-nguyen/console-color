package com.noiprocs.ui.console.sprite.mob.character;

import com.noiprocs.core.model.Model;
import com.noiprocs.core.model.item.AxeItem;
import com.noiprocs.core.model.mob.MobModel;
import com.noiprocs.core.model.mob.character.PlayerModel;
import com.noiprocs.ui.console.sprite.ConsoleSprite;

public class PlayerSprite extends ConsoleSprite {
    private static final int OFFSET_X = 2, OFFSET_Y = 2;
    private static final char[][] TEXTURE = {
            {0, 0, 0, 'o', 0},
            {0, 0, '(', '|', ')'},
            {0, 0, '/', 0, '\\'},
    };

    private static final char[][] PUNCH_ACTION_RIGHT_TEXTURE = {
            {0, 0, 0, 'o', 0, 0},
            {0, 0, '(', '|', '\\', '*'},
            {0, 0, '/', 0, '\\', 0},
    };

    private static final char[][] PUNCH_ACTION_LEFT_TEXTURE = {
            {0, 0, 0, 'o', 0},
            {0, '*', '/', '|', ')'},
            {0, 0, '/', 0, '\\'},
    };

    private static final char[][][] RIGHT_AXE_ACTION_PERFORMANCE = {
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

    private static final char[][][] LEFT_AXE_ACTION_PERFORMANCE = {
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

    public PlayerSprite() {
        super(TEXTURE, OFFSET_X, OFFSET_Y);
    }

    @Override
    public char[][] getTexture(Model model) {
        PlayerModel playerModel = (PlayerModel) model;
        MobModel.MovingDirection facingDirection = playerModel.getFacingDirection();
        if (playerModel.actionCounter == 0) return TEXTURE;

        if (playerModel.getCurrentInventoryItem() instanceof AxeItem) {
            if (facingDirection == MobModel.MovingDirection.RIGHT) {
                return RIGHT_AXE_ACTION_PERFORMANCE[(playerModel.actionCounter / 2) % RIGHT_AXE_ACTION_PERFORMANCE.length];
            }
            return LEFT_AXE_ACTION_PERFORMANCE[(playerModel.actionCounter / 2) % LEFT_AXE_ACTION_PERFORMANCE.length];
        }

        if (facingDirection == MobModel.MovingDirection.RIGHT) {
            return PUNCH_ACTION_RIGHT_TEXTURE;
        }
        return PUNCH_ACTION_LEFT_TEXTURE;
    }
}
