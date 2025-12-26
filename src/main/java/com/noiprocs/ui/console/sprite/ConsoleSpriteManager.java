package com.noiprocs.ui.console.sprite;

import com.noiprocs.core.graphics.RenderableSprite;
import com.noiprocs.core.graphics.SpriteManager;
import com.noiprocs.core.model.Model;
import com.noiprocs.core.model.item.ItemModel;
import java.util.HashMap;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ConsoleSpriteManager extends SpriteManager {
  private static final Logger logger = LogManager.getLogger(ConsoleSpriteManager.class);

  private final Map<String, RenderableSprite> spriteMap = new HashMap<>();

  @Override
  public RenderableSprite createRenderableObject(Model model) {
    String className =
        (model instanceof ItemModel)
            ? ((ItemModel) model).itemClass.getName()
            : model.getClass().getName();
    return spriteMap.computeIfAbsent(
        className, key -> ConsoleSpriteFactory.generateRenderableSprite(className));
  }
}
