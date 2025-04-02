package com.noiprocs.ui.console.sprite.mob;

import com.noiprocs.core.model.mob.MobModel;
import com.noiprocs.ui.console.sprite.ConsoleSprite;

public class CotMobSprite extends ConsoleSprite {
    private static final int OFFSET_X = 1, OFFSET_Y = 0;
    private static final long[][] TEXTURE = convertCharTexture(new char[][]{
            {'=', '=', '=', '0'},
            {'/', '\\', '/', '\\'}
    });

    private static final long[][] FLIPPED_TEXTURE = convertCharTexture(new char[][]{
            {'0', '=', '=', '='},
            {'/', '\\', '/', '\\'}
    });

    public CotMobSprite(String id) {
        super(id, OFFSET_X, OFFSET_Y);
    }

    @Override
    public void render() {
    }

    @Override
    public long[][] getTexture() {
        MobModel mm = (MobModel) getModel();

        if (mm == null) return TEXTURE;

        MobModel.MovingDirection movingDirection = mm.getMovingDirection();
        if (movingDirection == MobModel.MovingDirection.DOWN
                || movingDirection == MobModel.MovingDirection.LEFT) {
            return FLIPPED_TEXTURE;
        }
        else return TEXTURE;
    }
}
