package com.kangpei.cache.annotation;

import com.kangpei.cache.constants.CacheOpType;

import java.lang.annotation.*;
import java.util.concurrent.TimeUnit;

/**
 * description: Cacheable <br>
 * date: 2020/11/7 7:34 下午 <br>
 * author: kangpei <br>
 * version: 1.0 <br>
 */
@Inherited
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Cacheable {


    String prefix() default "";

    String key() default "";

    String desc() default "";

    CacheOpType type() default CacheOpType.WRITE;

    int expire() default 200;

    TimeUnit timeUnit() default TimeUnit.MINUTES;
}
