package com.kangpei.cache.provider.impl;

import com.kangpei.cache.provider.AbstractCacheProvider;

import java.lang.reflect.Type;

/**
 * description: RedisCacheProvider <br>
 * date: 2020/11/7 10:04 下午 <br>
 * author: kangpei <br>
 * version: 1.0 <br>
 */
public class RedisCacheProvider extends AbstractCacheProvider {

    @Override
    public void put(String key, Object value, int expire) {

    }

    @Override
    public Object get(String key, Type type) {
        return null;
    }

    @Override
    public boolean del(String key) {
        return false;
    }
}
