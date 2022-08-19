package com.noiprocs.ui.console.sprite.plant;

import com.noiprocs.core.model.plant.TreeModel;
import com.noiprocs.ui.console.sprite.ConsoleSprite;

public class TreeSprite extends ConsoleSprite {
    private static final int OFFSET_X = 4, OFFSET_Y = 3;
    private static final char[][] TEXTURE = {
            {0, '▒', '▒', '▒', '▒', '▒', '▒', '▒', '▒', 0},
            {'▒','▐','▒','▐','▒','▒','▒','▒','▌','▒'},
            {0,'▒','▀','▄','█','▒','▄','▀','▒', 0},
            {0, 0, 0, 0, '█', '█', 0, 0, 0, 0},
            {0, 0, 0, '▄', '█', '█', '▄', 0, 0, 0}
    };

    private static final char[][] YOUNG_TEXTURE = {
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, '|', '|', 0, 0, 0, 0}
    };

    private static final char[][] MIDDLE_TEXTURE = {
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, '|', '|', 0, 0, 0, 0},
            {0, 0, 0, 0, '|', '|', 0, 0, 0, 0}
    };

    public TreeSprite(String id) {
        super(TEXTURE, id, OFFSET_X, OFFSET_Y);
    }

    public TreeSprite(char[][] texture, String id, int offsetX, int offsetY) {
        super(texture, id, offsetX, offsetY);
    }

    @Override
    public char[][] getTexture() {
        TreeModel model = (TreeModel) getModel();
        if (model == null || model.isOldAge()) return super.getTexture();

        if (model.isYoungAge()) return YOUNG_TEXTURE;
        return MIDDLE_TEXTURE;
    }

    @Override
    public void render() {}
}
