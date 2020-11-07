package com.kangpei.cache.generator;

/**
 * description: IGenerator <br>
 * date: 2020/11/7 7:50 下午 <br>
 * author: kangpei <br>
 * version: 1.0 <br>
 */
public interface IGenerator {

    String generate(String expression, Object[] arguments, Object retValue);
}
