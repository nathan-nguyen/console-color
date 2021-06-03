package com.noiprocs.core.graphics;

import com.noiprocs.core.config.Config;
import com.noiprocs.core.model.Model;
import com.noiprocs.core.model.ModelManager;
import com.noiprocs.core.model.mob.character.PlayerModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class SpriteManager {
    private static final Logger logger = LoggerFactory.getLogger(SpriteManager.class);
    protected ModelManager modelManager;
    public PlayerModel player;

    protected final Map<String, RenderableSprite> renderableObjectMap = new HashMap<>();
    private int modelSynchronizationDelay = 0;

    public void setModelManager(ModelManager modelManager) {
        this.modelManager = modelManager;
    }

    protected void synchronizeModelData() {
        if (modelSynchronizationDelay++ % Config.MODEL_SYNCHRONISATION_DELAY != 0) return;

        Map<String, Model> modelMap = modelManager.getModelMap();
        List<String> removedKeyList = new ArrayList<>();

        for (String key: renderableObjectMap.keySet()) {
            if (!modelMap.containsKey(key)) {
                logger.info("Removing object: " + key);
                removedKeyList.add(key);
            }
        }

        for (String key: removedKeyList) renderableObjectMap.remove(key);

        for (String key: modelMap.keySet()) {
            if (!renderableObjectMap.containsKey(key)) {
                Model model = modelMap.get(key);
                RenderableSprite renderableObject = createRenderableObject(model);
                if (renderableObject != null) renderableObjectMap.put(key, renderableObject);
            }
        }
    }

    public abstract RenderableSprite createRenderableObject(Model model);
    public abstract void update(int dt);

    public abstract List<RenderableSprite> getAllRenderableObjectWithinRange(int range);
}
