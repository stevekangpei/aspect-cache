package com.kangpei.cache.config;

import com.kangpei.cache.constants.CacheConstants;
import com.kangpei.cache.properties.RedisProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import static com.kangpei.cache.constants.CacheConstants.CACHE_REDIS_NAME;

/**
 * description: RedisConfig <br>
 * date: 2020/11/7 7:45 下午 <br>
 * author: kangpei <br>
 * version: 1.0 <br>
 */
@Configuration
@ConditionalOnProperty(prefix = CacheConstants.CACHE_PREFIX, name = "provider", havingValue = CACHE_REDIS_NAME)
public class RedisConfig {

    private RedisProperties properties;

    @Autowired
    public RedisConfig(RedisProperties properties) {
        this.properties = properties;
    }

    @Bean
    public JedisPool jedisPool() {

        if (StringUtils.isEmpty(properties.getPassword())) {
            return new JedisPool(jedisPoolConfig(),
                    properties.getHost(),
                    properties.getTimeOut(),
                    properties.getTimeOut());
        }
        return new JedisPool(jedisPoolConfig(),
                properties.getHost(),
                properties.getTimeOut(),
                properties.getTimeOut(),
                properties.getPassword(),
                properties.getDatabase());
    }

    private JedisPoolConfig jedisPoolConfig() {

        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(properties.getMaxTotal());
        config.setMaxIdle(properties.getMaxIdle());
        config.setMinIdle(properties.getMinIdle());
        config.setMaxWaitMillis(properties.getMaxWaitMillis());
        return config;
    }


}
