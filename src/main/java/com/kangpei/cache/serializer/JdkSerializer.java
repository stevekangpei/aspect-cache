package com.kangpei.cache.serializer;

import com.kangpei.cache.utils.CacheUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Type;
import java.util.Calendar;
import java.util.Date;

/**
 * description: JdkSerializer <br>
 * date: 2020/11/7 10:15 下午 <br>
 * author: kangpei <br>
 * version: 1.0 <br>
 */
public class JdkSerializer<T> implements ISerializer<T>, ICloner<T> {

    @Override
    public Object deepClone(Object obj, Type type) throws Exception {

        if (null == obj) {
            return null;
        }
        Class<?> clazz = obj.getClass();
        if (CacheUtils.isPrimitive(obj) || clazz.isEnum()
                || obj instanceof Class
                || clazz.isAnnotation() || clazz.isSynthetic()) {
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
            return new byte[0];
        }
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ObjectOutputStream output = new ObjectOutputStream(outputStream);
        output.writeObject(obj);
        output.flush();
        return outputStream.toByteArray();
    }

    @Override
    public T deserialize(byte[] bytes, Type returnType) throws Exception {

        if (null == bytes || bytes.length == 0) {
            return null;
        }
        ByteArrayInputStream is = new ByteArrayInputStream(bytes);
        ObjectInputStream inputStream = new ObjectInputStream(is);
        return (T) inputStream.readObject();
    }
}
