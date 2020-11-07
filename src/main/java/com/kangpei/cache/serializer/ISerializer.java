package com.kangpei.cache.serializer;

import java.lang.reflect.Type;

/**
 * description: ISerializer <br>
 * date: 2020/11/7 10:10 下午 <br>
 * author: kangpei <br>
 * version: 1.0 <br>
 */
public interface ISerializer<T> {

    byte[] serialize(final T obj) throws Exception;

    T deserialize(final byte[] bytes, final Type returnType) throws Exception;
}
