package com.kangpei.cache.aop;

import org.aspectj.lang.annotation.Pointcut;

/**
 * description: JointPoint <br>
 * date: 2020/11/8 11:47 下午 <br>
 * author: kangpei <br>
 * version: 1.0 <br>
 */
public class JointPoint {

    @Pointcut("@annotation(com.kangpei.annotation.Cacheable)")
    public void cacheableExecute() {
    }

    @Pointcut("@annotation(com.kangpei.annotation.CacheEvict)")
    public void cacheEvictExecute() {
    }
}
