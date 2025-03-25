package com.noiprocs.core.network;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.esotericsoftware.kryo.util.DefaultInstantiatorStrategy;
import com.noiprocs.core.model.item.Item;
import org.reflections.Reflections;
import org.reflections.scanners.Scanners;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Set;

public class KryoSerializationUtils {
    private static final Kryo kryo = new Kryo();

    static {
        kryo.register(int[].class);
        kryo.register(int[][].class);
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

    public static byte[] serialize(Object obj) {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
             Output output = new Output(baos)) {
            kryo.writeClassAndObject(output, obj);
            output.close();
            return baos.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> T deserialize(byte[] data) {
        try (ByteArrayInputStream bais = new ByteArrayInputStream(data);
             Input input = new Input(bais)) {
            return (T) kryo.readClassAndObject(input);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
