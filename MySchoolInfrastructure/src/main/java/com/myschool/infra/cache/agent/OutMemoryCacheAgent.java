package com.myschool.infra.cache.agent;

import org.springframework.stereotype.Component;

import com.myschool.infra.cache.exception.CacheException;

/**
 * The Class OutMemoryCacheAgent.
 */
@Component
public abstract class OutMemoryCacheAgent extends CacheAgent {

    /**
     * Gets the entry.
     *
     * @param region the region
     * @param cacheKey the cache key
     * @return the entry
     * @throws CacheException the cache exception
     */
    public abstract Object getEntry(String region, Object cacheKey) throws CacheException;

    /**
     * Put entry.
     *
     * @param region the region
     * @param cacheKey the cache key
     * @param cacheValue the cache value
     * @throws CacheException the cache exception
     */
    public abstract void putEntry(String region, Object cacheKey, Object cacheValue) throws CacheException;

    /**
     * Removes the entry.
     *
     * @param region the region
     * @param cacheKey the cache key
     * @throws CacheException the cache exception
     */
    public abstract void removeEntry(String region, Object cacheKey) throws CacheException;

    /**
     * Creates the region.
     * 
     * @param region the region
     * @throws CacheException the cache exception
     */
    public abstract void createRegion(String region) throws CacheException;

    /**
     * Destroy region.
     * 
     * @param region the region
     * @throws CacheException the cache exception
     */
    public abstract void destroyRegion(String region) throws CacheException;

}
