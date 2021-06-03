package com.noiprocs.ui.sprite.mob.character;

import com.noiprocs.core.graphics.RenderableSprite;
import com.noiprocs.core.model.Model;

public abstract class ConsoleSprite extends RenderableSprite {
    protected char[][] texture;

    public ConsoleSprite(char[][] texture, Model model) {
        super(model);
        this.texture = texture;
    }

    public char[][] getTexture() {
        return texture;
    }
}
