package com.noiprocs.core.graphics;

import com.noiprocs.core.GameContext;
import com.noiprocs.core.model.Model;

public abstract class RenderableSprite {
    public String id;

    public RenderableSprite(String id) {
        this.id = id;
    }

    public abstract void render();

    public Model getModel() {
        return GameContext.get().modelManager.getModel(id);
    }
}
