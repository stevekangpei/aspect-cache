package com.kangpei.cache.config;

import com.kangpei.cache.constants.CacheConstants;
import com.kangpei.cache.properties.EHCacheProperties;
import org.ehcache.CacheManager;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.config.builders.ExpiryPolicyBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.config.units.MemoryUnit;
import org.ehcache.xml.XmlConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;
import java.net.URL;
import java.time.Duration;

import static com.kangpei.cache.constants.CacheConstants.CACHE_EHCACHE_NAME;

/**
 * description: EhCacheConfig <br>
 * date: 2020/11/7 7:45 下午 <br>
 * author: kangpei <br>
 * version: 1.0 <br>
 */
@Configuration
@ConditionalOnProperty(prefix = CacheConstants.CACHE_PREFIX, name = "provider", havingValue = CACHE_EHCACHE_NAME)
public class EhCacheConfig {

    private final EHCacheProperties cacheProperties;

    @Autowired
    public EhCacheConfig(EHCacheProperties cacheProperties) {
        this.cacheProperties = cacheProperties;
    }

    @Bean
    @ConditionalOnProperty(prefix = CacheConstants.CACHE_PREFIX + ".ehcache")
    public CacheManager cacheManager() {

        CacheManager cacheManager = CacheManagerBuilder.newCacheManagerBuilder().build();
        cacheManager.createCache(cacheProperties.getDefaultCacheName(),
                CacheConfigurationBuilder.newCacheConfigurationBuilder(String.class, String.class,
                        ResourcePoolsBuilder.heap(cacheProperties.getMaxEntriesLocalHeap()))
                        .withExpiry(ExpiryPolicyBuilder.timeToIdleExpiration(java.time.Duration.ofSeconds(1800)))
                        .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(3000)))
                        .withResourcePools(ResourcePoolsBuilder.newResourcePoolsBuilder().
                                offheap(cacheProperties.getOffHeap(), MemoryUnit.MB)));
        return cacheManager;
    }

    @Bean
    @ConditionalOnProperty(prefix = CacheConstants.CACHE_PREFIX + ".ehcache", name = "use-xml-file", havingValue = "true")
    public CacheManager xmlCacheManager() {

        URL resource = this.getClass().getResource(File.separator + cacheProperties.getEhcacheFileName());
        XmlConfiguration configuration = new XmlConfiguration(resource);
        CacheManager manager = CacheManagerBuilder.newCacheManager(configuration);
        manager.init();
        return manager;
    }
}
