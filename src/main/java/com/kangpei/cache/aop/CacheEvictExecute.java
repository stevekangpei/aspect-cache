package com.kangpei.cache.aop;

import com.kangpei.cache.annotation.CacheEvict;
import com.kangpei.cache.constants.CacheConstants;
import com.kangpei.cache.generator.CacheKeyGeneratorHolder;
import com.kangpei.cache.properties.CacheProperties;
import com.kangpei.cache.provider.CacheProvider;
import com.kangpei.cache.provider.CacheProviderHolder;
import com.kangpei.cache.utils.CacheUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * description: CacheEvictExecute <br>
 * date: 2020/11/8 11:51 下午 <br>
 * author: kangpei <br>
 * version: 1.0 <br>
 */
@Component
@Aspect
@ConditionalOnProperty(prefix = CacheConstants.CACHE_PREFIX, name = "enable", havingValue = "true")
public class CacheEvictExecute {

    private static final Logger log = LoggerFactory.getLogger(CacheEvictExecute.class);
    private final CacheKeyGeneratorHolder keyGeneratorHolder;
    private final CacheProviderHolder providerHolder;
    private final CacheProperties cacheProperties;

    @Autowired
    public CacheEvictExecute(CacheKeyGeneratorHolder keyGeneratorHolder,
                             CacheProviderHolder providerHolder,
                             CacheProperties cacheProperties) {
        this.keyGeneratorHolder = keyGeneratorHolder;
        this.providerHolder = providerHolder;
        this.cacheProperties = cacheProperties;
    }

    @Around("com.kangpei.cache.aop.JointPoint.cacheEvictExecute()&&@annotation(annotation)")
    public Object intercept(ProceedingJoinPoint joinPoint, CacheEvict cacheEvict) {

        Object[] arguments = joinPoint.getArgs();
        String key = "";
        Object res = null;
        try {
            String expression = StringUtils.isEmpty(cacheEvict.prefix()) ?
                    cacheEvict.key() : "'" + cacheEvict.prefix() + "+" + cacheEvict.key();
            key = CacheUtils.getCacheKey(keyGeneratorHolder, cacheProperties, expression, arguments, null);
            CacheProvider cacheProvider = providerHolder.findCacheProvider(cacheProperties.getProvider());
            res = joinPoint.proceed();
            cacheProvider.del(key);
        } catch (Throwable e) {
            log.error("del key error", e);
        }
        return res;
    }
}
