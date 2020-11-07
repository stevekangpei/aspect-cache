package com.kangpei.cache.serializer.kryo;

/**
 * description: KryoContext <br>
 * date: 2020/11/7 10:26 下午 <br>
 * author: kangpei <br>
 * version: 1.0 <br>
 */
public interface KryoContext<T> {

    byte[] serialize(T obj);


    byte[] serialize(T obj, int buffer);

    T deserialize(byte[] bytes);

    void addKryoClassRegistration(KryoClassRegistration registration);
}
