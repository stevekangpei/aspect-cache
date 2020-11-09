package com.kangpei.cache.utils;

import com.kangpei.cache.annotation.Cacheable;
import com.kangpei.cache.generator.CacheKeyGeneratorHolder;
import com.kangpei.cache.generator.IGenerator;
import com.kangpei.cache.properties.CacheProperties;
import com.kangpei.cache.provider.CacheProvider;
import com.kangpei.cache.provider.CacheProviderHolder;
import org.springframework.util.StringUtils;

import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collection;
import java.util.Map;

/**
 * description: CacheUtils <br>
 * date: 2020/11/7 9:31 下午 <br>
 * author: kangpei <br>
 * version: 1.0 <br>
 */
public final class CacheUtils {

    public static boolean isEmpty(Object obj) {
        if (null == obj) return true;
        if (obj instanceof String) {
            return ((String) obj).length() == 0;
        }
        Class<?> clazz = obj.getClass();
        if (clazz.isArray()) {
            int len = Array.getLength(obj);
            return len == 0;
        }
        if (obj instanceof Collection) {
            Collection col = (Collection) obj;
            return col.isEmpty();
        }
        if (obj instanceof Map) {
            Map tempMap = (Map) obj;
            return tempMap.isEmpty();
        }
        return false;
    }

    public static boolean isPrimitive(Object obj) {
        return obj.getClass().isPrimitive() || obj instanceof String
                || obj instanceof Integer || obj instanceof Long ||
                obj instanceof Short || obj instanceof Character || obj instanceof Boolean
                || obj instanceof Float || obj instanceof Double || obj instanceof BigDecimal
                || obj instanceof BigInteger;
    }

    private static IGenerator getKeyGenerator(CacheKeyGeneratorHolder holder, String keyGenerator) {
        return holder.findKeyGenerator(keyGenerator);
    }

    private static CacheProvider getCacheProvider(CacheProviderHolder holder, String cacheProvider) {
        return holder.findCacheProvider(cacheProvider);
    }

    public static String getCacheKey(CacheKeyGeneratorHolder holder,
                                     CacheProperties cacheProperties,
                                     String key,
                                     Object[] arguments, Object returnVal) {
        return getKeyGenerator(holder, cacheProperties.getKeyGenerator()).generate(key, arguments, returnVal);
    }

    public static Object getCache(CacheProviderHolder holder, CacheProperties cacheProperties,
                                  String key, Type returnType) {
        return getCacheProvider(holder, cacheProperties.getProvider()).get(key, returnType);
    }

    public static void put(CacheProviderHolder holder, CacheProperties cacheProperties,
                           String key, Object res, Cacheable cacheable) {
        if (!StringUtils.isEmpty(key)) {
            getCacheProvider(holder, cacheProperties.getProvider()).put(key, res, cacheable.expire());
        }
    }
}
