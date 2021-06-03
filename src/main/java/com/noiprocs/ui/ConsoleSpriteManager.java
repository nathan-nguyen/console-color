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

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ConsoleSpriteManager extends SpriteManager {
    private static final Logger logger = LoggerFactory.getLogger(ConsoleSpriteManager.class);

    @Override
    public RenderableSprite createRenderableObject(Model model) {
        if (model instanceof PlayerModel) {
            logger.info("Creating player " + model.id);
            this.player = (PlayerModel) model;
            return new PlayerSprite(model);
        }
        if (model instanceof TreeModel) return new TreeSprite(model);

        throw new NotImplementedException();
    }

    @Override
    public void update(int dt) {
        this.synchronizeModelData();
    }

    @Override
    public List<RenderableSprite> getAllRenderableObjectWithinRange(int range) {
        List<RenderableSprite> renderableSpriteList = new ArrayList<>(renderableObjectMap.values());
        renderableSpriteList.sort(Comparator.comparingInt(u -> u.model.posY));
        return renderableSpriteList;
    }
}
