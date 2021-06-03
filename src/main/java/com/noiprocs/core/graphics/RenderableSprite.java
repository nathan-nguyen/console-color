package com.noiprocs.core.graphics;

import com.noiprocs.core.model.Model;

public abstract class RenderableSprite {
    public Model model;

    public RenderableSprite(Model model) {
        this.model = model;
    }

    public abstract void render();
}
