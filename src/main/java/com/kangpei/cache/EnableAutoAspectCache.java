package com.kangpei.cache;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * description: EnableAutoAspectCache <br>
 * date: 2020/11/7 7:51 下午 <br>
 * author: kangpei <br>
 * version: 1.0 <br>
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
@Import(AspectCacheAutoConfiguration.class)
public @interface EnableAutoAspectCache {
}
