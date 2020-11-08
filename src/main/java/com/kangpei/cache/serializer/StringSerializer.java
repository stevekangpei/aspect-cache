package com.kangpei.cache.serializer;

import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * description: StringSerializer <br>
 * date: 2020/11/8 10:45 下午 <br>
 * author: kangpei <br>
 * version: 1.0 <br>
 */
public class StringSerializer implements ISerializer<String>, ICloner<String> {

    private final Charset charset;

    public StringSerializer(Charset charset) {
        this.charset = charset;
    }

    public StringSerializer() {
        this(StandardCharsets.UTF_8);
    }

    @Override
    public byte[] serialize(String obj) throws Exception {
        return obj == null ? null : obj.getBytes(charset);
    }

    @Override
    public String deserialize(byte[] bytes, Type returnType) throws Exception {
        return bytes == null ? null : new String(bytes, charset);
    }

    @Override
    public Object deepClone(Object obj, Type type) throws Exception {
        if (null == obj)
            return obj;
        String str = (String) obj;
        return String.copyValueOf(str.toCharArray());
    }
}
