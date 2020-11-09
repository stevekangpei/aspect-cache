package com.kangpei.cache.provider;

import com.kangpei.cache.constants.CacheConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * description: CacheProviderHolder <br>
 * date: 2020/11/9 11:26 下午 <br>
 * author: kangpei <br>
 * version: 1.0 <br>
 */
@Component
@ConditionalOnProperty(prefix = CacheConstants.CACHE_PREFIX, name = "enable", havingValue = "true")
public class CacheProviderHolder {


    private final Map<String, CacheProvider> cacheProviders;

    @Autowired
    public CacheProviderHolder(Map<String, CacheProvider> cacheProviders) {
        this.cacheProviders = cacheProviders;
    }

    public CacheProvider findCacheProvider(String type) {
        String name = type.toLowerCase() + CacheProvider.class.getName();
        CacheProvider cacheProvider = cacheProviders.get(name);
        if (cacheProvider == null) {
            throw new RuntimeException("cache provider {} not found");
        }
        return cacheProvider;
    }


}
