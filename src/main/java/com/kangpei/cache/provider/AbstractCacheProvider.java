package com.kangpei.cache.provider;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.cache.CacheProperties;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

/**
 * description: AbstractCacheProvider <br>
 * date: 2020/11/7 9:37 下午 <br>
 * author: kangpei <br>
 * version: 1.0 <br>
 */
public abstract class AbstractCacheProvider implements CacheProvider {

    private static final Logger log = LoggerFactory.getLogger(AbstractCacheProvider.class);

    @Autowired
    protected CacheProperties cacheProperties;

    public Object handleReturnValue(String cacheValue, Type type) {

        Object res = null;

        try {
            if (type instanceof ParameterizedType) {
                ParameterizedType returnType = (ParameterizedType) type;
                Type rawType = returnType.getRawType();
                Type[] arguments = returnType.getActualTypeArguments();

                if (((Class) rawType).isAssignableFrom(List.class)) {
                    res = JSON.parseArray(cacheValue, (Class) arguments[0]);
                } else if (((Class) rawType).isAssignableFrom(Map.class)) {
                    res = parseMap(cacheValue, (Class) arguments[0], (Class) arguments[1]);
                } else {
                    res = JSON.parseObject(cacheValue, type);
                }
            }
        } catch (Exception e) {
            log.error("parse value {} error", cacheValue);
        }
        return res;
    }

    private <K, V> Map<K, V> parseMap(String json, Class<K> key, Class<V> value) {
        return JSON.parseObject(json, new TypeReference<Map<K, V>>(key, value) {
        });
    }


}
