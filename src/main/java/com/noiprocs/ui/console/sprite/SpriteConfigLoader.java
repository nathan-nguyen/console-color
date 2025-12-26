package com.noiprocs.ui.console.sprite;

import com.google.gson.Gson;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SpriteConfigLoader {
  private static final String SPRITE_CONFIG_FILE_NAME = "sprite-config.json";

  public static Map<String, SpriteConfig> loadSpriteConfigs() {
    try (InputStream is =
        SpriteConfigLoader.class.getResourceAsStream("/" + SPRITE_CONFIG_FILE_NAME)) {
      if (is == null) {
        throw new IllegalStateException(SPRITE_CONFIG_FILE_NAME + " not found in resources");
      }

      try (Reader reader = new InputStreamReader(is, StandardCharsets.UTF_8)) {
        Gson gson = new Gson();
        SpriteConfigFile configFile = gson.fromJson(reader, SpriteConfigFile.class);

        Map<String, SpriteConfig> result = new HashMap<>();
        for (SpriteEntry entry : configFile.sprites) {
          char[][] texture = parseTexture(entry.texture);
          int offsetX = entry.offsetX != null ? entry.offsetX : 0;
          int offsetY = entry.offsetY != null ? entry.offsetY : 0;

          SpriteConfig config = new SpriteConfig(texture, offsetX, offsetY);

          // Handle single class or multiple classes
          if (entry.modelClass != null) {
            result.put(entry.modelClass, config);
          } else if (entry.modelClasses != null) {
            for (String modelClass : entry.modelClasses) {
              result.put(modelClass, config);
            }
          }
        }
        return result;
      }
    } catch (Exception e) {
      throw new IllegalStateException("Failed to load sprite-config.json", e);
    }
  }

  private static char[][] parseTexture(List<String> textureRows) {
    char[][] texture = new char[textureRows.size()][];
    for (int i = 0; i < textureRows.size(); i++) {
      char[] row = textureRows.get(i).toCharArray();
      // Replace '~' with null character (0)
      for (int j = 0; j < row.length; j++) {
        if (row[j] == '~') {
          row[j] = 0;
        }
      }
      texture[i] = row;
    }
    return texture;
  }

  private static class SpriteConfigFile {
    List<SpriteEntry> sprites;
  }

  private static class SpriteEntry {
    String modelClass;
    List<String> modelClasses;
    List<String> texture;
    Integer offsetX;
    Integer offsetY;
  }

  public static class SpriteConfig {
    public final char[][] texture;
    public final int offsetX;
    public final int offsetY;

    public SpriteConfig(char[][] texture, int offsetX, int offsetY) {
      this.texture = texture;
      this.offsetX = offsetX;
      this.offsetY = offsetY;
    }
  }
}
