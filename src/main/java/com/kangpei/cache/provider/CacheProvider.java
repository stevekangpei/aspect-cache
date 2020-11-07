package com.kangpei.cache.provider;

import java.lang.reflect.Type;

/**
 * description: CacheProvider <br>
 * date: 2020/11/7 9:36 下午 <br>
 * author: kangpei <br>
 * version: 1.0 <br>
 */
public interface CacheProvider {

    void put(String key, Object value, int expire);

    Object get(String key, Type type);

    boolean del(String key);
}
