package com.noiprocs.core.common;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Constructor;
import java.util.Random;

public class Helper {
    private static final Logger logger = LogManager.getLogger(Helper.class);
    public static final Random random = new Random();

    public static Object createObject(String className, Object... args) {
        try {
            Class<?> clazz = Class.forName(className);
            return createObject(clazz, args);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static Object createObject(Class<?> clazz, Object... args) {
        try {
            // Get constructor with matching parameter types
            Class<?>[] paramTypes = new Class[args.length];
            for (int i = 0; i < args.length; i++) {
                paramTypes[i] = getPrimitiveType(args[i].getClass());
            }

            Constructor<?> constructor = clazz.getConstructor(paramTypes);

            // Create instance with provided arguments
            return constructor.newInstance(args);
        } catch (Exception e) {
            logger.error("Failed to create object {}", clazz.getName(), e);
            throw new RuntimeException(e);
        }
    }

    // Helper method to handle primitive type matching
    private static Class<?> getPrimitiveType(Class<?> wrapper) {
        if (wrapper == Integer.class) return int.class;
        if (wrapper == Double.class) return double.class;
        if (wrapper == Float.class) return float.class;
        if (wrapper == Long.class) return long.class;
        if (wrapper == Short.class) return short.class;
        if (wrapper == Byte.class) return byte.class;
        if (wrapper == Character.class) return char.class;
        if (wrapper == Boolean.class) return boolean.class;
        return wrapper; // Return original class if not a wrapper
    }
}
