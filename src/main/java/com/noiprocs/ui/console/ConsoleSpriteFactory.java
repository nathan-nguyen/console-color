package com.noiprocs.ui.console;

import com.noiprocs.core.graphics.RenderableSprite;
import com.noiprocs.core.model.Model;
import com.noiprocs.core.model.environment.WorldBoundaryModel;
import com.noiprocs.core.model.plant.BirchTreeModel;
import com.noiprocs.core.model.environment.MazePartModel;
import com.noiprocs.core.model.plant.TreeModel;
import com.noiprocs.core.model.plant.WoodLogModel;
import com.noiprocs.core.model.mob.character.PlayerModel;
import com.noiprocs.ui.console.sprite.environment.WorldBoundarySprite;
import com.noiprocs.ui.console.sprite.plant.BirchTreeSprite;
import com.noiprocs.ui.console.sprite.environment.MazePartSprite;
import com.noiprocs.ui.console.sprite.plant.TreeSprite;
import com.noiprocs.ui.console.sprite.plant.WoodLogSprite;
import com.noiprocs.ui.console.sprite.mob.character.PlayerSprite;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ConsoleSpriteFactory {
    private static final Logger logger = LogManager.getLogger(ConsoleSpriteFactory.class);

    public RenderableSprite generateRenderableSprite(Model model) {
        if (model instanceof PlayerModel) {
            logger.info("Creating player " + model.id);
            return new PlayerSprite(model.id);
        }

        // Note: BirchTreeModel extends TreeMode, therefore it need to come first
        if (model instanceof BirchTreeModel) return new BirchTreeSprite(model.id);
        if (model instanceof TreeModel) return new TreeSprite(model.id);

        if (model instanceof WoodLogModel) return new WoodLogSprite(model.id);

        if (model instanceof MazePartModel) return new MazePartSprite(model.id);
        if (model instanceof WorldBoundaryModel) return new WorldBoundarySprite(model.id);

        throw new UnsupportedOperationException();
    }
}
