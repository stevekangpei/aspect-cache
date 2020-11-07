package com.kangpei.cache.utils;

import java.lang.reflect.Array;
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
}
