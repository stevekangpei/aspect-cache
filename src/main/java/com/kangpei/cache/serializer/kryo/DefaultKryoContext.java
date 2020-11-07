package com.kangpei.cache.serializer.kryo;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.esotericsoftware.kryo.pool.KryoPool;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * description: DefaultKryoContext <br>
 * date: 2020/11/7 10:32 下午 <br>
 * author: kangpei <br>
 * version: 1.0 <br>
 */
public class DefaultKryoContext implements KryoContext {

    private static final int DEFAULT_BUFFER_SIZE = 1024 * 1000;
    private KryoPool pool;
    private List<KryoClassRegistration> registrations;

    public DefaultKryoContext() {

        registrations = new ArrayList<>();
        pool = new KryoPool.Builder(() -> {
            Kryo kryo = new Kryo();
            registrations.forEach(reg -> reg.register(kryo));
            return kryo;
        }).softReferences().build();
    }

    public static KryoContext newKryoContextFactory(KryoClassRegistration registration) {
        KryoContext context = new DefaultKryoContext();
        context.addKryoClassRegistration(registration);
        return context;
    }


    @Override
    public byte[] serialize(Object obj) {

        return serialize(obj, DEFAULT_BUFFER_SIZE);
    }

    @Override
    public byte[] serialize(Object obj, int buffer) {
        Kryo kryo = pool.borrow();
        try (Output output = new Output(new ByteArrayOutputStream(), buffer)) {
            kryo.writeClassAndObject(output, obj);
            return output.toBytes();
        } finally {
            pool.release(kryo);
        }
    }

    @Override
    public Object deserialize(byte[] bytes) {
        Kryo kryo = pool.borrow();
        try(Input input = new Input(new ByteArrayInputStream(bytes))) {
            Object o = kryo.readClassAndObject(input);
            return o;
        } finally {
            pool.release(kryo);
        }
    }

    @Override
    public void addKryoClassRegistration(KryoClassRegistration registration) {

        if (null != registration) {
            registrations.add(registration);
        }
    }
}
