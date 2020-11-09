package com.kangpei.cache.aop;

import com.kangpei.cache.annotation.Cacheable;
import com.kangpei.cache.constants.CacheConstants;
import com.kangpei.cache.constants.CacheOpType;
import com.kangpei.cache.generator.CacheKeyGeneratorHolder;
import com.kangpei.cache.properties.CacheProperties;
import com.kangpei.cache.provider.CacheProviderHolder;
import com.kangpei.cache.utils.CacheUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;
import java.lang.reflect.Type;

/**
 * description: CacheableExecute <br>
 * date: 2020/11/8 11:51 下午 <br>
 * author: kangpei <br>
 * version: 1.0 <br>
 */
@Component
@Aspect
@ConditionalOnProperty(prefix = CacheConstants.CACHE_PREFIX, name = "enable", havingValue = "true")
public class CacheableExecute {

    private static final Logger log = LoggerFactory.getLogger(CacheableExecute.class);

    private final CacheKeyGeneratorHolder keyGeneratorHolder;
    private final CacheProviderHolder providerHolder;
    private final CacheProperties cacheProperties;

    @Autowired
    public CacheableExecute(CacheKeyGeneratorHolder keyGeneratorHolder,
                            CacheProviderHolder providerHolder,
                            CacheProperties cacheProperties) {
        this.keyGeneratorHolder = keyGeneratorHolder;
        this.providerHolder = providerHolder;
        this.cacheProperties = cacheProperties;
    }

    @Around("com.kangpei.cache.aop.JointPoint.cacheableExecute()&&@annotation(annotation)")
    public Object intercept(ProceedingJoinPoint joinPoint, Cacheable cacheable) {

        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        Object[] args = joinPoint.getArgs();
        Type returnType = method.getGenericReturnType();
        String key = "";
        Object result = null;
        String expression = StringUtils.isEmpty(cacheable.prefix()) ?
                cacheable.key() : "'" + cacheable.key() + "+" + cacheable.key();
        try {
            if (cacheable.type() == CacheOpType.WRITE) {
                result = joinPoint.proceed();
                key = CacheUtils.getCacheKey(keyGeneratorHolder, cacheProperties, expression, args, result);
                CacheUtils.put(providerHolder, cacheProperties, key, result, cacheable);
            } else if (cacheable.type() == CacheOpType.READ_ONLY) {
                key = CacheUtils.getCacheKey(keyGeneratorHolder, cacheProperties, key, args, returnType);
                result = CacheUtils.getCache(providerHolder, cacheProperties, key, returnType);
            } else {
                key = CacheUtils.getCacheKey(keyGeneratorHolder, cacheProperties, expression, args, null);
                result = CacheUtils.getCache(providerHolder, cacheProperties, key, returnType);
                if (result == null) {
                    result = joinPoint.proceed();
                }
                CacheUtils.put(providerHolder, cacheProperties, key, result, cacheable);
            }
            if (result == null)
                result = joinPoint.proceed();

        } catch (Throwable e) {
            log.error("proceed cacheable error key {}", key, e);
        }
        return result;
    }
}
