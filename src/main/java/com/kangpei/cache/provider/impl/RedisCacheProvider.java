package com.kangpei.cache.provider.impl;

import com.kangpei.cache.constants.CacheConstants;
import com.kangpei.cache.properties.RedisProperties;
import com.kangpei.cache.provider.AbstractCacheProvider;
import com.kangpei.cache.serializer.ISerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.lang.reflect.Type;

/**
 * description: RedisCacheProvider <br>
 * date: 2020/11/7 10:04 下午 <br>
 * author: kangpei <br>
 * version: 1.0 <br>
 */
@Component
@ConditionalOnProperty(prefix = CacheConstants.CACHE_PREFIX, name = "provider", havingValue = CacheConstants.CACHE_REDIS_NAME)
public class RedisCacheProvider extends AbstractCacheProvider {

    private static final Logger log = LoggerFactory.getLogger(RedisCacheProvider.class);
    @Autowired
    private RedisProperties properties;
    private final JedisPool jedisPool;

    @Autowired
    public RedisCacheProvider(ISerializer<Object> valueSerializer, JedisPool jedisPool) {
        super(valueSerializer);
        this.jedisPool = jedisPool;
    }

    @Override
    public void put(String key, Object value, int expire) {
        try {
            byte[] keyBytes = keySerializer.serialize(key);
            byte[] valueBytes = valueSerializer.serialize(value);
            try (Jedis jedis = jedisPool.getResource()) {
                jedis.setex(keyBytes, expire, valueBytes);
            }
        } catch (Exception e) {
            log.error("put key {} error", key, e);
        }
    }

    @Override
    public Object get(String key, Type type) {
        try {
            byte[] keyBytes = keySerializer.serialize(key);
            try (Jedis jedis = jedisPool.getResource()) {
                byte[] bytes = jedis.get(keyBytes);
                return valueSerializer.deserialize(bytes, type);
            }
        } catch (Exception e) {
            log.error("get key {} error", key, e);
        }
        return null;
    }

    @Override
    public boolean del(String key) {
        try {
            try (Jedis jedis = jedisPool.getResource()) {
                byte[] keyBytes = keySerializer.serialize(key);
                jedis.del(keyBytes);
            }
        } catch (Exception e) {
            log.error("del key {} error", key, e);
            return false;
        }
        return true;
    }
}
