package com.noiprocs.core.graphics;

import com.noiprocs.core.model.Model;
import com.noiprocs.core.util.Helper;

public abstract class RenderableSprite {
    public String id;

    public RenderableSprite(String id) {
        this.id = id;
    }

    public abstract void render();

    public Model getModel() {
        return Helper.GAME_CONTEXT.modelManager.getModel(id);
    }
}
