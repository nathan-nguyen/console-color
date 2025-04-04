package com.noiprocs.ui.console.sprite;

import com.noiprocs.core.graphics.RenderableSprite;
import com.noiprocs.core.model.Model;
import com.noiprocs.core.model.building.FenceModel;
import com.noiprocs.core.model.environment.WorldBoundaryHorizontalModel;
import com.noiprocs.core.model.environment.WorldBoundaryVerticalModel;
import com.noiprocs.core.model.item.*;
import com.noiprocs.core.model.mob.CotMobModel;
import com.noiprocs.core.model.mob.projectile.FlyingWoodLogModel;
import com.noiprocs.core.model.plant.*;
import com.noiprocs.core.model.environment.MazePartModel;
import com.noiprocs.core.model.mob.character.PlayerModel;
import com.noiprocs.ui.console.sprite.environment.WorldBoundarySprite;
import com.noiprocs.ui.console.sprite.item.AxeItemSprite;
import com.noiprocs.ui.console.sprite.mob.CotMobSprite;
import com.noiprocs.ui.console.sprite.mob.projectile.FlyingWoodLogSprite;
import com.noiprocs.ui.console.sprite.plant.*;
import com.noiprocs.ui.console.sprite.environment.MazePartSprite;
import com.noiprocs.ui.console.sprite.mob.character.PlayerSprite;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ConsoleSpriteFactory {
    private static final Logger logger = LogManager.getLogger(ConsoleSpriteFactory.class);

    public static RenderableSprite generateRenderableSprite(Model model) {
        if (model instanceof PlayerModel) {
            logger.info("Creating player {}", model.id);
            return new PlayerSprite(model.id);
        }

        // Note: BirchTreeModel, PineTreeModel extends TreeMode, therefore it need to come first
        if (model instanceof BirchTreeModel) return new BirchTreeSprite(model.id);
        if (model instanceof PineTreeModel) return new PineTreeSprite(model.id);
        if (model instanceof TreeModel) return new TreeSprite(model.id);

        if (model instanceof ItemModel) {
            Class<?> itemClass = ((ItemModel) model).itemClass;
            if (itemClass == WoodLogItem.class) {
                return new ConsoleSprite(new char[][]{{'='}}, model.id);
            }
            if (itemClass == SaplingItem.class) {
                return new ConsoleSprite(new char[][]{{'Y'}}, model.id);
            }
            if (itemClass == AxeItem.class) {
                return new AxeItemSprite(model.id);
            }
            if (itemClass == AppleItem.class) {
                return new ConsoleSprite(new char[][]{{'o'}}, model.id);
            }
        }

        if (model instanceof MazePartModel) return new MazePartSprite(model.id);
        if (model instanceof WorldBoundaryVerticalModel) return new WorldBoundarySprite(model.id, 40, 1);
        if (model instanceof WorldBoundaryHorizontalModel) return new WorldBoundarySprite(model.id, 1, 60);

        if (model instanceof CotMobModel) return new CotMobSprite(model.id);

        if (model instanceof FenceModel) {
            return new ConsoleSprite(
                    new char[][]{
                            {'#','#'},
                            {'#','#'},
                    },
                    model.id
            );
        }

        if (model instanceof FlyingWoodLogModel) {
            return new FlyingWoodLogSprite(model.id);
        }

        throw new UnsupportedOperationException("Could not find corresponding Sprite with model " + model.getClass());
    }
}
