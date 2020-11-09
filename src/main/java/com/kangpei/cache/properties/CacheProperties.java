package com.kangpei.cache.properties;

import com.kangpei.cache.constants.CacheConstants;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.stereotype.Component;

/**
 * description: CacheProperties <br>
 * date: 2020/11/9 11:42 下午 <br>
 * author: kangpei <br>
 * version: 1.0 <br>
 */
@Component
@ConfigurationProperties(prefix = CacheConstants.CACHE_PREFIX)
public class CacheProperties {

    @NestedConfigurationProperty
    private EHCacheProperties ehCacheProperties = new EHCacheProperties();
    @NestedConfigurationProperty
    private RedisProperties redisProperties = new RedisProperties();

    private boolean enable = false;
    private String provider = CacheConstants.DEFAULT_CACHE_PROVIDER;
    private Integer expireTime = 3600;

    public EHCacheProperties getEhCacheProperties() {
        return ehCacheProperties;
    }

    public void setEhCacheProperties(EHCacheProperties ehCacheProperties) {
        this.ehCacheProperties = ehCacheProperties;
    }

    public RedisProperties getRedisProperties() {
        return redisProperties;
    }

    public void setRedisProperties(RedisProperties redisProperties) {
        this.redisProperties = redisProperties;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public Integer getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(Integer expireTime) {
        this.expireTime = expireTime;
    }

    public String getKeyGenerator() {
        return keyGenerator;
    }

    public void setKeyGenerator(String keyGenerator) {
        this.keyGenerator = keyGenerator;
    }

    private String keyGenerator = CacheConstants.DEFAULT_CACHE_KEY_GENERATOR_NAME;
}
