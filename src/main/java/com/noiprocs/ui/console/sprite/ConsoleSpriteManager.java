package com.noiprocs.ui.console.sprite;

import com.noiprocs.core.graphics.RenderableSprite;
import com.noiprocs.core.graphics.SpriteManager;
import com.noiprocs.core.model.Model;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.stream.Collectors;

public class ConsoleSpriteManager extends SpriteManager {
    private static final Logger logger = LogManager.getLogger(ConsoleSpriteManager.class);

    @Override
    public RenderableSprite createRenderableObject(Model model) {
        return ConsoleSpriteFactory.generateRenderableSprite(model);
    }

    @Override
    public void update(int dt) {
        this.synchronizeModelData(false);
    }

    @Override
    public List<RenderableSprite> getVisibleRenderableObjectListWithinRange(int x, int y, int range) {
        return renderableSpriteMap.values().stream().filter(
                renderableSprite -> {
                    Model model = renderableSprite.getModel();
                    return model != null && model.isVisible && renderableSprite.getModel().distanceTo(x, y) <= range;
                }
        ).collect(Collectors.toList());
    }
}
