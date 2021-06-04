package com.noiprocs.ui.console.sprite;

import com.noiprocs.core.graphics.RenderableSprite;

public abstract class ConsoleSprite extends RenderableSprite {
    protected char[][] texture;

    public ConsoleSprite(char[][] texture, String id) {
        super(id);
        this.texture = texture;
    }

    public char[][] getTexture() {
        return texture;
    }
}
