package com.noiprocs.ui.console.sprite;

import com.noiprocs.core.graphics.RenderableSprite;
import com.noiprocs.core.model.environment.MazePartModel;
import com.noiprocs.core.model.environment.WallTrapModel;
import com.noiprocs.core.model.environment.WorldBoundaryHorizontalModel;
import com.noiprocs.core.model.environment.WorldBoundaryVerticalModel;
import com.noiprocs.core.model.mob.CotMobModel;
import com.noiprocs.core.model.mob.character.PlayerModel;
import com.noiprocs.core.model.mob.projectile.FlyingWoodLogModel;
import com.noiprocs.core.model.plant.BirchTreeModel;
import com.noiprocs.core.model.plant.PineTreeModel;
import com.noiprocs.core.model.plant.TreeModel;
import com.noiprocs.ui.console.sprite.environment.MazePartSprite;
import com.noiprocs.ui.console.sprite.environment.WallTrapSprite;
import com.noiprocs.ui.console.sprite.environment.WorldBoundarySprite;
import com.noiprocs.ui.console.sprite.mob.CotMobSprite;
import com.noiprocs.ui.console.sprite.mob.character.PlayerSprite;
import com.noiprocs.ui.console.sprite.mob.projectile.FlyingWoodLogSprite;
import com.noiprocs.ui.console.sprite.plant.BirchTreeSprite;
import com.noiprocs.ui.console.sprite.plant.PineTreeSprite;
import com.noiprocs.ui.console.sprite.plant.TreeSprite;

public class ConsoleSpriteFactory {
  public static RenderableSprite generateRenderableSprite(String modelClassName) {
    if (modelClassName.equals(PlayerModel.class.getName())) {
      return new PlayerSprite();
    }

    if (modelClassName.equals(BirchTreeModel.class.getName())) {
      return new BirchTreeSprite();
    }
    if (modelClassName.equals(PineTreeModel.class.getName())) {
      return new PineTreeSprite();
    }
    if (modelClassName.equals(TreeModel.class.getName())) {
      return new TreeSprite();
    }

    if (modelClassName.equals(MazePartModel.class.getName())) {
      return new MazePartSprite();
    }
    if (modelClassName.equals(WallTrapModel.class.getName())) {
      return new WallTrapSprite();
    }

    if (modelClassName.equals(WorldBoundaryVerticalModel.class.getName())) {
      return new WorldBoundarySprite(40, 1);
    }
    if (modelClassName.equals(WorldBoundaryHorizontalModel.class.getName())) {
      return new WorldBoundarySprite(1, 60);
    }

    if (modelClassName.equals(CotMobModel.class.getName())) {
      return new CotMobSprite();
    }

    if (modelClassName.equals(FlyingWoodLogModel.class.getName())) {
      return new FlyingWoodLogSprite();
    }

    throw new UnsupportedOperationException(
        "Could not find corresponding Sprite with model " + modelClassName);
  }
}
