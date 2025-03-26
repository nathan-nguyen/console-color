package com.noiprocs.core.network;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class KryoSerializationUtils {
    // Kryo is used in multiple threads in ServerMessageQueue (parallelStream)
    private static final KryoPool kryoPool = new KryoPool();

    public static byte[] serialize(Object obj) {
        try {
            Kryo kryo = kryoPool.borrowKryo();
            try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
                 Output output = new Output(baos)) {
                kryo.writeClassAndObject(output, obj);
                output.close();
                return baos.toByteArray();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            finally {
                kryoPool.returnKryo(kryo);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> T deserialize(byte[] data) {
        try {
            Kryo kryo = kryoPool.borrowKryo();
            try (ByteArrayInputStream bais = new ByteArrayInputStream(data);
                 Input input = new Input(bais)) {
                return (T) kryo.readClassAndObject(input);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            finally {
                kryoPool.returnKryo(kryo);
            }
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
