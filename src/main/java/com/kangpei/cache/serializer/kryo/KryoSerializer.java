package com.kangpei.cache.serializer.kryo;

import com.kangpei.cache.serializer.ICloner;
import com.kangpei.cache.serializer.ISerializer;
import com.kangpei.cache.utils.CacheUtils;

import java.lang.reflect.Type;
import java.util.Calendar;
import java.util.Date;

/**
 * description: KryoSerializer <br>
 * date: 2020/11/7 10:44 下午 <br>
 * author: kangpei <br>
 * version: 1.0 <br>
 */
public class KryoSerializer<T> implements ISerializer<T>, ICloner<T> {

    private KryoContext kryoContext;

    public KryoSerializer(KryoContext kryoContext) {

        this.kryoContext = new DefaultKryoContext();
    }

    @Override
    public Object deepClone(Object obj, Type type) throws Exception {
        if (null == obj)
            return null;
        Class<?> clazz = obj.getClass();
        if (CacheUtils.isPrimitive(obj) || clazz.isEnum()
                || clazz.isSynthetic() || clazz.isAnnotation()
                || obj instanceof Class) {
            return obj;
        }
        if (obj instanceof Date) {
            return ((Date) obj).clone();
        } else if (obj instanceof Calendar) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(((Calendar) obj).getTime().getTime());
            return calendar;
        }
        return deserialize(serialize((T) obj), null);

    }

    @Override
    public byte[] serialize(T obj) throws Exception {
        if (obj == null) {
            return null;
        }
        return kryoContext.serialize(obj);
    }

    @Override
    public T deserialize(byte[] bytes, Type returnType) throws Exception {
        if (null == bytes || bytes.length == 0) {
            return null;
        }
        return (T) kryoContext.deserialize(bytes);
    }
}
