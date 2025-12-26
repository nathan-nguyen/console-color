package com.noiprocs.core.network;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.noiprocs.core.config.Config;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Serializable;
import org.apache.commons.lang3.SerializationUtils;

public class NetworkSerializationUtils {
  // Kryo is used in multiple threads in ServerMessageQueue (parallelStream)
  private static final KryoPool kryoPool = new KryoPool();

  private static byte[] kryoSerialize(Object obj) {
    try {
      Kryo kryo = kryoPool.borrowKryo();
      try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
          Output output = new Output(baos)) {
        kryo.writeClassAndObject(output, obj);
        output.close();
        return baos.toByteArray();
      } catch (IOException e) {
        throw new RuntimeException(e);
      } finally {
        kryoPool.returnKryo(kryo);
      }
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  private static <T> T kryoDeserialize(byte[] data) {
    try {
      Kryo kryo = kryoPool.borrowKryo();
      try (ByteArrayInputStream bais = new ByteArrayInputStream(data);
          Input input = new Input(bais)) {
        return (T) kryo.readClassAndObject(input);
      } catch (IOException e) {
        throw new RuntimeException(e);
      } finally {
        kryoPool.returnKryo(kryo);
      }
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  public static byte[] serialize(Serializable obj) {
    if (Config.USE_KRYO_SERIALIZATION) {
      return kryoSerialize(obj);
    }
    return SerializationUtils.serialize(obj);
  }

  public static <T> T deserialize(byte[] data) {
    if (Config.USE_KRYO_SERIALIZATION) {
      return kryoDeserialize(data);
    }
    return SerializationUtils.deserialize(data);
  }
}
