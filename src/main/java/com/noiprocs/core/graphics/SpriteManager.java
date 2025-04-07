package com.noiprocs.core.graphics;

import com.noiprocs.core.GameContext;
import com.noiprocs.core.model.Model;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class SpriteManager {
    private static final Logger logger = LogManager.getLogger(SpriteManager.class);

    private GameContext gameContext;

    public void setGameContext(GameContext gameContext) {
        this.gameContext = gameContext;
    }

    public abstract RenderableSprite createRenderableObject(Model model);
}
