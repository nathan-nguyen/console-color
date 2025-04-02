package com.noiprocs.ui.console.sprite.mob.character;

import com.noiprocs.core.model.mob.MobModel;
import com.noiprocs.core.model.mob.character.PlayerModel;
import com.noiprocs.ui.console.sprite.ConsoleSprite;

public class PlayerSprite extends ConsoleSprite {
    private static final int OFFSET_X = 2, OFFSET_Y = 2;
    private static final long[][] TEXTURE = convertCharTexture(new char[][]{
            {0, 0, 0, 'o', 0},
            {0, 0, '(', '|', ')'},
            {0, 0, '/', 0, '\\'},
    });

    private static final long[][][] RIGHT_ACTION_PERFORMANCE = convertCharTexture(new char[][][]{
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
    });

    private static final long[][][] LEFT_ACTION_PERFORMANCE = convertCharTexture(new char[][][]{
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
    });

    public PlayerSprite(String id) {
        super(id, OFFSET_X, OFFSET_Y);
    }

    @Override
    public void render() {
    }

    @Override
    public long[][] getTexture() {
        PlayerModel model = (PlayerModel) getModel();
        MobModel.MovingDirection facingDirection = model.getFacingDirection();
        if (model.actionCounter == 0) return TEXTURE;

        if (facingDirection == MobModel.MovingDirection.UP
                || facingDirection == MobModel.MovingDirection.RIGHT) {
            return RIGHT_ACTION_PERFORMANCE[(model.actionCounter / 2) % RIGHT_ACTION_PERFORMANCE.length];
        }
        return LEFT_ACTION_PERFORMANCE[(model.actionCounter / 2) % LEFT_ACTION_PERFORMANCE.length];
    }
}
