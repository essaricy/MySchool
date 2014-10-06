package com.myschool.infra.cache.agent;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.myschool.common.exception.ConfigurationException;

/**
 * The Class InMemoryCacheAgent.
 */
@Component
public abstract class InMemoryCacheAgent extends CacheAgent {

    /** The application context. */
    @Autowired
    private ApplicationContext applicationContext;

    /* (non-Javadoc)
     * @see com.myschool.infra.agent.Agent#loadConfiguration(java.io.File)
     */
    @Override
    public void loadConfiguration(File configFile)
            throws ConfigurationException {
        throw new ConfigurationException("In Memory cache agent does not require any configuration.");
    }

    /**
     * Gets the message.
     *
     * @param cacheKey the cache key
     * @return the message
     */
    public String getMessage(String cacheKey) {
        return applicationContext.getMessage(cacheKey, null, null);
    }

    /**
     * Gets the entry.
     *
     * @param cacheKey the cache key
     * @return the entry
     */
    public abstract Object getEntry(String cacheKey);

    /**
     * Put entry.
     *
     * @param cacheKey the cache key
     * @param cacheValue the cache value
     */
    public abstract void putEntry(String cacheKey, Object cacheValue);

    /**
     * Removes the entry.
     *
     * @param cacheKey the cache key
     */
    public abstract void removeEntry(String cacheKey);

}
