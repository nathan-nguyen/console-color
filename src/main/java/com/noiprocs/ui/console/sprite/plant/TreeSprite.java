package com.noiprocs.ui.console.sprite.plant;

import com.noiprocs.core.model.plant.TreeModel;
import com.noiprocs.ui.console.sprite.ConsoleSprite;

public class TreeSprite extends ConsoleSprite {
    private static final int OFFSET_X = 4, OFFSET_Y = 3;
    private static final long[][] TEXTURE = convertCharTexture(new char[][]{
            {0, '▒', '▒', '▒', '▒', '▒', '▒', '▒', '▒', 0},
            {'▒','▐','▒','▐','▒','▒','▒','▒','▌','▒'},
            {0,'▒','▀','▄','█','▒','▄','▀','▒', 0},
            {0, 0, 0, 0, '█', '█', 0, 0, 0, 0},
            {0, 0, 0, '▄', '█', '█', '▄', 0, 0, 0}
    });

    private static final long[][] YOUNG_TEXTURE = convertCharTexture(new char[][]{
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, '|', '|', 0, 0, 0, 0}
    });

    private static final long[][] MIDDLE_TEXTURE = convertCharTexture(new char[][]{
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, '|', '|', 0, 0, 0, 0},
            {0, 0, 0, 0, '|', '|', 0, 0, 0, 0}
    });

    public TreeSprite(String id) {
        super(id, OFFSET_X, OFFSET_Y);
    }

    public TreeSprite(char[][] texture, String id, int offsetX, int offsetY) {
        super(texture, id, offsetX, offsetY);
    }

    @Override
    public long[][] getTexture() {
        TreeModel model = (TreeModel) getModel();
        if (model == null || model.isOldAge()) return super.getTexture();

        if (model.isYoungAge()) return YOUNG_TEXTURE;
        return MIDDLE_TEXTURE;
    }

    @Override
    public void render() {}
}
