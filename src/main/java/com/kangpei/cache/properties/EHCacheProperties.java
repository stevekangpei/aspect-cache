package com.kangpei.cache.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import static com.kangpei.cache.constants.CacheConstants.CACHE_PREFIX;

/**
 * description: EHCacheProperties <br>
 * date: 2020/11/7 7:44 下午 <br>
 * author: kangpei <br>
 * version: 1.0 <br>
 */
@Component
@ConfigurationProperties(prefix = CACHE_PREFIX + ".ehcache")
public class EHCacheProperties {

    private boolean useXmlFileConfig = false;
    private String ehcacheFileName = "ehcache.xml";
    private String defaultCacheName = "ec_cache";
    private int maxEntriesLocalHeap = 1000;
    private int offHeap = 20;
    private int disk = 200;

    public boolean isUseXmlFileConfig() {
        return useXmlFileConfig;
    }

    public void setUseXmlFileConfig(boolean useXmlFileConfig) {
        this.useXmlFileConfig = useXmlFileConfig;
    }

    public String getEhcacheFileName() {
        return ehcacheFileName;
    }

    public void setEhcacheFileName(String ehcacheFileName) {
        this.ehcacheFileName = ehcacheFileName;
    }

    public String getDefaultCacheName() {
        return defaultCacheName;
    }

    public void setDefaultCacheName(String defaultCacheName) {
        this.defaultCacheName = defaultCacheName;
    }

    public int getMaxEntriesLocalHeap() {
        return maxEntriesLocalHeap;
    }

    public void setMaxEntriesLocalHeap(int maxEntriesLocalHeap) {
        this.maxEntriesLocalHeap = maxEntriesLocalHeap;
    }

    public int getOffHeap() {
        return offHeap;
    }

    public void setOffHeap(int offHeap) {
        this.offHeap = offHeap;
    }

    public int getDisk() {
        return disk;
    }

    public void setDisk(int disk) {
        this.disk = disk;
    }
}
