package com.kangpei.cache.provider.impl;

import com.kangpei.cache.constants.CacheConstants;
import com.kangpei.cache.properties.EHCacheProperties;
import com.kangpei.cache.provider.AbstractCacheProvider;
import com.kangpei.cache.serializer.ISerializer;
import org.ehcache.Cache;
import org.ehcache.CacheManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.lang.reflect.Type;

import static com.kangpei.cache.constants.CacheConstants.CACHE_EHCACHE_NAME;

/**
 * description: EhCacheProvider <br>
 * date: 2020/11/7 10:04 下午 <br>
 * author: kangpei <br>
 * version: 1.0 <br>
 */
@Component()
@ConditionalOnProperty(prefix = CacheConstants.CACHE_PREFIX, name = "provider", havingValue = CACHE_EHCACHE_NAME)
public class EhCacheProvider extends AbstractCacheProvider {

    private static final Logger log = LoggerFactory.getLogger(EhCacheProvider.class);

    private final CacheManager cacheManager;
    @Autowired
    protected EHCacheProperties cacheProperties;


    @Autowired
    public EhCacheProvider(CacheManager cacheManager, ISerializer<Object> valueSerializer) {
        super(valueSerializer);
        this.cacheManager = cacheManager;
    }

    @Override
    public void put(String key, Object value, int expire) {
        try {
            byte[] keyBytes = keySerializer.serialize(key);
            byte[] valueBytes = valueSerializer.serialize(value);
            getCache().put(keyBytes, valueBytes);
        } catch (Exception e) {
            log.error("put {} error", key, e);
        }
    }

    @Override
    public Object get(String key, Type type) {
        try {
            byte[] keyBytes = keySerializer.serialize(key);
            byte[] value = getCache().get(keyBytes);
            return valueSerializer.deserialize(value, type);
        } catch (Exception e) {
            log.error("get {} error", key, e);
        }
        return null;
    }

    @Override
    public boolean del(String key) {
        try {
            byte[] keyBytes = keySerializer.serialize(key);
            getCache().remove(keyBytes);
        } catch (Exception e) {
            log.error("del {} error", key, e);
            return false;
        }
        return true;
    }

    private Cache<byte[], byte[]> getCache() {
        return cacheManager.getCache(cacheProperties.getDefaultCacheName(), byte[].class, byte[].class);
    }
}
