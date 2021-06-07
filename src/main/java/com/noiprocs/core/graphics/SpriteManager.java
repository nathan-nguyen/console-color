package com.noiprocs.core.graphics;

import com.noiprocs.core.GameContext;
import com.noiprocs.core.config.Config;
import com.noiprocs.core.model.Model;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class SpriteManager {
    private static final Logger logger = LogManager.getLogger(SpriteManager.class);

    public final Map<String, RenderableSprite> renderableSpriteMap = new HashMap<>();

    private GameContext gameContext;

    public void setGameContext(GameContext gameContext) {
        this.gameContext = gameContext;
    }

    public void synchronizeModelData(boolean forceSynchronize) {
        if (!forceSynchronize && gameContext.worldCounter % Config.MODEL_SYNCHRONISATION_DELAY != 0) return;

        Map<String, Model> modelMap = gameContext.modelManager.getModelMap();
        List<String> removedKeyList = new ArrayList<>();

        // Remove no-longer-exist models
        for (String key : renderableSpriteMap.keySet()) {
            if (!modelMap.containsKey(key)) {
                logger.info("Remove RenderableSprite: " + key);
                removedKeyList.add(key);
            }
        }
        for (String key : removedKeyList) renderableSpriteMap.remove(key);

        // Add newly-created models
        for (String key : modelMap.keySet()) {
            if (!renderableSpriteMap.containsKey(key)) {
                Model model = modelMap.get(key);
                RenderableSprite renderableObject = createRenderableObject(model);
                if (renderableObject != null) renderableSpriteMap.put(key, renderableObject);
            }
        }
    }

    public abstract RenderableSprite createRenderableObject(Model model);

    public abstract void update(int dt);

    public abstract List<RenderableSprite> getRenderableObjectListWithinRange(int x, int y, int range);
}
