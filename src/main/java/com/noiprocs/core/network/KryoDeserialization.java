package com.noiprocs.core.network;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import java.io.ByteArrayInputStream;
import java.io.IOException;

public class KryoDeserialization {
    public static <T> T deserialize(byte[] data) {
        Kryo kryo = KryoUtil.getKryo();
        try (ByteArrayInputStream bais = new ByteArrayInputStream(data);
             Input input = new Input(bais)) {
            return (T) kryo.readClassAndObject(input);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
