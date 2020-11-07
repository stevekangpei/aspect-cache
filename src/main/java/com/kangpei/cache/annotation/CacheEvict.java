package com.kangpei.cache.annotation;

import java.lang.annotation.*;

/**
 * description: CacheEvict <br>
 * date: 2020/11/7 7:35 下午 <br>
 * author: kangpei <br>
 * version: 1.0 <br>
 */
@Inherited
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface CacheEvict {

    /**
     * prefix of cache key
     * @return
     */
    String prefix() default "";

    /**
     * cache的key
     * @return
     */
    String key() default  "";

    /**
     * 描述信息
     * @return
     */
    String desc() default "";
}
