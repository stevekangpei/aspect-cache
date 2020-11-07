package com.kangpei.cache.serializer.kryo;

import com.esotericsoftware.kryo.Kryo;

/**
 * description: KryoClassRegistration <br>
 * date: 2020/11/7 10:28 下午 <br>
 * author: kangpei <br>
 * version: 1.0 <br>
 */
public interface KryoClassRegistration {

    /**
     * 注册类
     * @param kryo
     */
    void register(Kryo kryo);
}
