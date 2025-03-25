package com.noiprocs.core.network;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.util.DefaultInstantiatorStrategy;
import com.noiprocs.core.model.item.Item;
import org.reflections.Reflections;
import org.reflections.scanners.Scanners;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Set;

public class KryoUtil {
    private static final Kryo kryo = new Kryo();

    static {
        kryo.register(HashMap.class);
        registerPackage("com.noiprocs.core.model");
        kryo.register(Item[].class);
        kryo.setInstantiatorStrategy(new DefaultInstantiatorStrategy(
                new org.objenesis.strategy.StdInstantiatorStrategy())
        );
    }

    public static void registerPackage(String packageName) {
        Reflections reflections = new Reflections(packageName, Scanners.SubTypes);
        Set<Class<? extends Serializable>> classes = reflections.getSubTypesOf(Serializable.class);

        for (Class<?> clazz : classes) {
            if (!clazz.isInterface() && !java.lang.reflect.Modifier.isAbstract(clazz.getModifiers())) {
                kryo.register(clazz);
                System.out.println("Registered: " + clazz.getName());
            }
        }
    }

    public static Kryo getKryo() {
        return kryo;
    }
}
