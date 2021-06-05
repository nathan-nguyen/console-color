package com.noiprocs.ui.console;

import com.noiprocs.core.graphics.RenderableSprite;
import com.noiprocs.core.graphics.SpriteManager;
import com.noiprocs.core.model.Model;
import com.noiprocs.core.model.environment.MazePartModel;
import com.noiprocs.core.model.environment.TreeModel;
import com.noiprocs.core.model.mob.character.PlayerModel;
import com.noiprocs.ui.console.sprite.environment.MazePartSprite;
import com.noiprocs.ui.console.sprite.environment.TreeSprite;
import com.noiprocs.ui.console.sprite.mob.character.PlayerSprite;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.stream.Collectors;

public class ConsoleSpriteManager extends SpriteManager {
    private static final Logger logger = LoggerFactory.getLogger(ConsoleSpriteManager.class);

    @Override
    public RenderableSprite createRenderableObject(Model model) {
        if (model instanceof PlayerModel) {
            logger.info("Creating player " + model.id);
            return new PlayerSprite(model.id);
        }
        if (model instanceof TreeModel) return new TreeSprite(model.id);
        if (model instanceof MazePartModel) return new MazePartSprite(model.id);

        throw new UnsupportedOperationException();
    }

    @Override
    public void update(int dt) {
        this.synchronizeModelData(false);
    }

    @Override
    public List<RenderableSprite> getRenderableObjectListWithinRange(int x, int y, int range) {
        return renderableSpriteMap.values().stream().filter(
                renderableSprite -> {
                    Model model = renderableSprite.getModel();
                    return model != null && renderableSprite.getModel().distanceTo(x, y) <= range;
                }
        ).collect(Collectors.toList());
    }
}
