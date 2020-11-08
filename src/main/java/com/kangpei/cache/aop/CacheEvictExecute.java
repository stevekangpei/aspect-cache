package com.kangpei.cache.aop;

import com.kangpei.cache.annotation.CacheEvict;
import com.kangpei.cache.constants.CacheConstants;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

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

    @Around("com.kangpei.cache.aop.JointPoint.cacheEvictExecute()&&@annotation(annotation)")
    public Object intercept(ProceedingJoinPoint joinPoint, CacheEvict cacheEvict) {

    }
}
