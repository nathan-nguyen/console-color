package com.noiprocs.ui;

import com.noiprocs.core.graphics.RenderableSprite;
import com.noiprocs.core.graphics.SpriteManager;
import com.noiprocs.core.model.Model;
import com.noiprocs.core.model.environment.TreeModel;
import com.noiprocs.core.model.mob.character.PlayerModel;
import com.noiprocs.ui.sprite.environment.TreeSprite;
import com.noiprocs.ui.sprite.mob.character.PlayerSprite;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.List;
import java.util.stream.Collectors;

public class ConsoleSpriteManager extends SpriteManager {
    private static final Logger logger = LoggerFactory.getLogger(ConsoleSpriteManager.class);

    @Override
    public RenderableSprite createRenderableObject(Model model) {
        if (model instanceof PlayerModel) {
            logger.info("Creating player " + model.id);
            return new PlayerSprite(model);
        }
        if (model instanceof TreeModel) return new TreeSprite(model);

        throw new NotImplementedException();
    }

    @Override
    public void update(int dt) {
        this.synchronizeModelData(false);
    }

    @Override
    public List<RenderableSprite> getRenderableObjectListWithinRange(int x, int y, int range) {
        return renderableObjectMap.values().stream().filter(
                renderableSprite -> renderableSprite.model.distanceTo(x, y) <= range
        ).collect(Collectors.toList());
    }
}
