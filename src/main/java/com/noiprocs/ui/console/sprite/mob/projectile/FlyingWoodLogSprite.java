package com.noiprocs.ui.console.sprite.mob.projectile;

import com.noiprocs.core.model.Model;
import com.noiprocs.core.model.mob.projectile.ProjectileModel;
import com.noiprocs.ui.console.sprite.ConsoleSprite;

public class FlyingWoodLogSprite extends ConsoleSprite {
  private static final char[][][] ANIMATION_TEXTURES = {{{'='}}, {{'|'}}};

  public FlyingWoodLogSprite() {
    super(EMPTY_TEXTURE);
  }

  @Override
  public char[][] getTexture(Model model) {
    return ANIMATION_TEXTURES[((ProjectileModel) model).getTtl() % ANIMATION_TEXTURES.length];
  }
}
