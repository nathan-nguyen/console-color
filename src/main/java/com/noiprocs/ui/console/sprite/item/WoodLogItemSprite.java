package com.noiprocs.ui.console.sprite.item;

import com.noiprocs.core.config.Config;
import com.noiprocs.ui.console.sprite.ConsoleSprite;

public class WoodLogItemSprite extends ConsoleSprite {
    private static final int OFFSET_X = 0, OFFSET_Y = 0;
    private static final long[][] TEXTURE = convertCharTexture(new char[][]{
            {'='}
    });
    private static final long[][] EMOJI_TEXTURE = convertTexture(new String[][]{
            {"\uD83E\uDEB5"}
    });

    public WoodLogItemSprite(String id) {
        super(id, OFFSET_X, OFFSET_Y);
    }

    @Override
    public void render() {}

    @Override
    public long[][] getTexture() {
        if (Config.USE_EMOJI_TEXTURE) return EMOJI_TEXTURE;
        return TEXTURE;
    }
}
