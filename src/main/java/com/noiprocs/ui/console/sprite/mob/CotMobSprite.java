package com.noiprocs.ui.console.sprite.mob;

import com.noiprocs.core.model.Model;
import com.noiprocs.core.model.mob.MobModel;
import com.noiprocs.ui.console.sprite.ConsoleSprite;

public class CotMobSprite extends ConsoleSprite {
    private static final int OFFSET_X = 1, OFFSET_Y = 0;
    private static final char[][] TEXTURE = {
            {'=', '=', '=', '0'},
            {'/', '\\', '/', '\\'}
    };

    private static final char[][] FLIPPED_TEXTURE = {
            {'0', '=', '=', '='},
            {'/', '\\', '/', '\\'}
    };

    public CotMobSprite() {
        super(TEXTURE, OFFSET_X, OFFSET_Y);
    }

    @Override
    public char[][] getTexture(Model model) {
        if (model == null) return TEXTURE;

        if (((MobModel) model).getFacingDirection() == MobModel.MovingDirection.LEFT) {
            return FLIPPED_TEXTURE;
        }
        else return TEXTURE;
    }
}
