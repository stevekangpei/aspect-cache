package com.kangpei.cache.serializer;

import java.lang.reflect.Type;

/**
 * description: ICloner <br>
 * date: 2020/11/7 10:13 下午 <br>
 * author: kangpei <br>
 * version: 1.0 <br>
 */
public interface ICloner<T> {

    /**
     * add deep copy method and impl
     * @param obj
     * @param type
     * @return
     * @throws Exception
     */
    Object deepClone(Object obj, final Type type) throws Exception;
}
