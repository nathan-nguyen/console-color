package com.noiprocs.ui.console.sprite;

import com.noiprocs.core.graphics.RenderableSprite;

public abstract class ConsoleSprite extends RenderableSprite {
    protected static final char[][] EMPTY_TEXTURE = new char[0][0];

    public int offsetX, offsetY;
    private char[][] texture;

    public ConsoleSprite(char[][] texture, String id, int offsetX, int offsetY) {
        super(id);
        this.texture = texture;
        this.offsetX = offsetX;
        this.offsetY = offsetY;
    }

    protected void setTexture(char[][] texture) {
        this.texture = texture;
    }
    public char[][] getTexture() {
        return texture;
    }
}
