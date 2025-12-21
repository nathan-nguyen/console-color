package com.noiprocs.core.network;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.util.DefaultInstantiatorStrategy;
import com.noiprocs.core.common.Vector3D;
import com.noiprocs.core.model.item.Item;
import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.reflections.Reflections;
import org.reflections.scanners.Scanners;

import java.io.Closeable;
import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class KryoPool implements Closeable {
    private static final Logger logger = LogManager.getLogger(KryoPool.class);
    private final GenericObjectPool<Kryo> pool;

    public KryoPool() {
        this(new GenericObjectPoolConfig<>());
    }

    public KryoPool(GenericObjectPoolConfig<Kryo> config) {
        // Create a pool factory for Kryo instances
        pool = new GenericObjectPool<>(new BasePooledObjectFactory<Kryo>() {
            @Override
            public Kryo create() {
                // Create a new Kryo instance with custom configuration
                Kryo kryo = new Kryo();

                // Optional: Configure Kryo settings
                kryo.setRegistrationRequired(false);
                kryo.setWarnUnregisteredClasses(true);

                registerKryo(kryo);

                return kryo;
            }

            @Override
            public PooledObject<Kryo> wrap(Kryo kryo) {
                return new DefaultPooledObject<>(kryo);
            }

            @Override
            public void passivateObject(PooledObject<Kryo> pooledObject) {
                // Reset Kryo instance between uses
                pooledObject.getObject().reset();
            }
        }, config);
    }

    private static void registerKryo(Kryo kryo) {
        kryo.register(int[].class);
        kryo.register(int[][].class);
        kryo.register(java.lang.Class.class);
        kryo.register(HashMap.class);
        kryo.register(ConcurrentHashMap.class);
        registerPackage(kryo, "com.noiprocs.core.model");
        kryo.register(Item[].class);
        kryo.register(Vector3D.class);
        kryo.setInstantiatorStrategy(new DefaultInstantiatorStrategy(
                new org.objenesis.strategy.StdInstantiatorStrategy())
        );
    }

    private static void registerPackage(Kryo kryo, String packageName) {
        Reflections reflections = new Reflections(packageName, Scanners.SubTypes);
        Set<Class<? extends Serializable>> classes = reflections.getSubTypesOf(Serializable.class);

        for (Class<?> clazz : classes) {
            if (!clazz.isInterface() && !java.lang.reflect.Modifier.isAbstract(clazz.getModifiers())) {
                kryo.register(clazz);
                logger.debug("Registered: {}", clazz.getName());
            }
        }
    }

    /**
     * Borrow a Kryo instance from the pool.
     *
     * @return Kryo instance from the pool
     * @throws Exception if unable to borrow an object
     */
    public Kryo borrowKryo() throws Exception {
        return pool.borrowObject();
    }

    /**
     * Return a Kryo instance to the pool.
     *
     * @param kryo Kryo instance to return
     */
    public void returnKryo(Kryo kryo) {
        pool.returnObject(kryo);
    }

    @Override
    public void close() throws IOException {
        pool.close();
    }
}
