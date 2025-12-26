package com.noiprocs.ui.console.sprite;

import com.noiprocs.core.graphics.RenderableSprite;
import com.noiprocs.core.graphics.SpriteManager;
import com.noiprocs.core.model.Model;
import com.noiprocs.core.model.item.ItemModel;
import com.noiprocs.ui.console.sprite.SpriteConfigLoader.SpriteConfig;
import java.util.HashMap;
import java.util.Map;

public class ConsoleSpriteManager extends SpriteManager {
  private static final Map<String, SpriteConfig> CONFIG_MAP =
      SpriteConfigLoader.loadSpriteConfigs();

  private final Map<String, RenderableSprite> spriteMap = new HashMap<>();

  @Override
  public RenderableSprite createRenderableObject(Model model) {
    String className =
        (model instanceof ItemModel)
            ? ((ItemModel) model).itemClass.getName()
            : model.getClass().getName();
    return spriteMap.computeIfAbsent(className, this::generateSprite);
  }

  private RenderableSprite generateSprite(String className) {
    // Try to load from JSON config first
    SpriteConfig config = CONFIG_MAP.get(className);
    if (config != null) {
      return new ConsoleSprite(config.texture, config.offsetX, config.offsetY);
    }

    // Fall back to factory for special cases
    return ConsoleSpriteFactory.generateRenderableSprite(className);
  }
}
