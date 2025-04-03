package com.noiprocs.ui.console.sprite;

import com.noiprocs.core.graphics.RenderableSprite;

public class ConsoleSprite extends RenderableSprite {
    protected static final int OFFSET_X = 0, OFFSET_Y = 0;
    protected static final char[][] EMPTY_TEXTURE = new char[0][0];

    public int offsetX, offsetY;
    private char[][] texture;

    public ConsoleSprite(char[][] texture, String id) {
        this(texture, id, OFFSET_X, OFFSET_Y);
    }

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

    public void render() {
    }
}
