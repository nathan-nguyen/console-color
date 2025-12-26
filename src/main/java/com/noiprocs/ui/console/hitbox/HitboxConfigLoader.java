package com.noiprocs.ui.console.hitbox;

import com.google.gson.Gson;
import com.noiprocs.core.config.Config;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.noiprocs.ui.console.hitbox.HitboxCategory.*;

public class HitboxConfigLoader {
    private static final Map<String, Integer> CATEGORY_MAP = new HashMap<>();

    static {
        CATEGORY_MAP.put("NONE", NONE);
        CATEGORY_MAP.put("WALL", WALL);
        CATEGORY_MAP.put("PLAYER", PLAYER);
        CATEGORY_MAP.put("MOB", MOB);
        CATEGORY_MAP.put("ITEM", ITEM);
        CATEGORY_MAP.put("PROJECTILE", PROJECTILE);
        CATEGORY_MAP.put("MASK_ALL", MASK_ALL);
    }

    public static Map<String, HitboxConfig> loadHitboxConfigs() {
        try (InputStream is = HitboxConfigLoader.class.getResourceAsStream("/hitbox-config.json")) {
            if (is == null) {
                throw new IllegalStateException("hitbox-config.json not found in resources");
            }

            try (Reader reader = new InputStreamReader(is, StandardCharsets.UTF_8)) {
                Gson gson = new Gson();
                HitboxConfigFile configFile = gson.fromJson(reader, HitboxConfigFile.class);

                Map<String, HitboxConfig> result = new HashMap<>();
                for (HitboxEntry entry : configFile.hitboxes) {
                    int height = parseDimension(entry.height);
                    int width = parseDimension(entry.width);
                    int categoryBit = parseCategoryBit(entry.category);
                    int maskBit = parseMaskBit(entry.mask);

                    HitboxConfig config = new HitboxConfig(height, width, categoryBit, maskBit);

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
            throw new IllegalStateException("Failed to load hitbox-config.json", e);
        }
    }

    private static int parseCategoryBit(String category) {
        Integer bit = CATEGORY_MAP.get(category);
        if (bit == null) {
            throw new IllegalArgumentException("Unknown category: " + category);
        }
        return bit;
    }

    private static int parseMaskBit(List<String> masks) {
        return masks.stream()
                .map(HitboxConfigLoader::parseCategoryBit)
                .reduce(0, (a, b) -> a | b);
    }

    private static int parseDimension(Object value) {
        if (value instanceof Number) {
            return ((Number) value).intValue();
        }

        String strValue = value.toString();
        if (strValue.startsWith("Config.")) {
            String fieldName = strValue.substring(7);
            try {
                Field field = Config.class.getField(fieldName);
                return field.getInt(null);
            } catch (Exception e) {
                throw new IllegalArgumentException(
                        "Failed to resolve Config reference: " + strValue, e);
            }
        }

        return Integer.parseInt(strValue);
    }

    private static class HitboxConfigFile {
        List<HitboxEntry> hitboxes;
    }

    private static class HitboxEntry {
        String modelClass;
        List<String> modelClasses;
        Object height;
        Object width;
        String category;
        List<String> mask;
    }

    public static class HitboxConfig {
        public final int height;
        public final int width;
        public final int categoryBit;
        public final int maskBit;

        public HitboxConfig(int height, int width, int categoryBit, int maskBit) {
            this.height = height;
            this.width = width;
            this.categoryBit = categoryBit;
            this.maskBit = maskBit;
        }
    }
}
