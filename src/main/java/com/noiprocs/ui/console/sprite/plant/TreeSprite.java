package com.noiprocs.ui.console.sprite.plant;

import com.noiprocs.core.model.Model;
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

    public TreeSprite() {
        super(TEXTURE, OFFSET_X, OFFSET_Y);
    }

    public TreeSprite(char[][] texture, int offsetX, int offsetY) {
        super(texture, offsetX, offsetY);
    }

    @Override
    public char[][] getTexture(Model model) {
        TreeModel treeModel = (TreeModel) model;
        if (model == null || treeModel.isOldAge()) return super.getTexture(model);

        if (treeModel.isYoungAge()) return YOUNG_TEXTURE;
        return MIDDLE_TEXTURE;
    }
}
