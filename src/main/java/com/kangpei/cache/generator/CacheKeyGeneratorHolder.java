package com.kangpei.cache.generator;

import com.kangpei.cache.constants.CacheConstants;
import com.kangpei.cache.generator.impl.SpelKeyGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * description: CacheKeyGeneratorHolder <br>
 * date: 2020/11/9 11:18 下午 <br>
 * author: kangpei <br>
 * version: 1.0 <br>
 */
@Component
@ConditionalOnProperty(prefix = CacheConstants.CACHE_PREFIX, name = "enable", havingValue = "true")
public class CacheKeyGeneratorHolder {

    private final Map<String, IGenerator> keyGenerator;

    @Autowired
    public CacheKeyGeneratorHolder(Map<String, IGenerator> keyGenerator) {
        this.keyGenerator = keyGenerator;
    }

    public IGenerator findKeyGenerator(String type) {
        String name = type.toLowerCase() + IGenerator.class.getName();
        IGenerator keyGenerator = this.keyGenerator.get(name);
        if (keyGenerator == null) {
            return new SpelKeyGenerator();
        }
        return keyGenerator;
    }

}
