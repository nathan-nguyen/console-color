package com.noiprocs.core.graphics;

import com.noiprocs.core.model.Model;
import com.noiprocs.core.model.ModelManager;
import com.noiprocs.core.util.Helper;

public abstract class RenderableSprite {
    private String id;
    private ModelManager modelManager;

    public RenderableSprite(String id) {
        this.id = id;
    }

    public abstract void render();

    public Model getModel() {
        return Helper.GAME_CONTEXT.modelManager.getModel(id);
    }
}
