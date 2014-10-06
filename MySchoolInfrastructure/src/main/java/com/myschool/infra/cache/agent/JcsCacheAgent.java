package com.myschool.infra.cache.agent;

import java.io.File;

import org.apache.jcs.JCS;
import org.apache.jcs.access.exception.CacheException;
import org.apache.jcs.engine.control.CompositeCacheManager;
import org.springframework.stereotype.Component;

import com.myschool.common.exception.AgentException;
import com.myschool.common.exception.ConfigurationException;
import com.myschool.common.exception.FileSystemException;
import com.myschool.common.util.PropertiesUtil;
import com.myschool.infra.cache.constants.CacheConstants;

/**
 * The Class JcsCacheAgent.
 */
@Component
public class JcsCacheAgent extends OutMemoryCacheAgent {

    /* (non-Javadoc)
     * @see com.myschool.infra.agent.Agent#loadConfiguration(java.io.File)
     */
    @Override
    public void loadConfiguration(File configFile)
            throws ConfigurationException {
        try {
            properties = PropertiesUtil.loadProperties(configFile);
            //JCS.setConfigFilename(configFile.getAbsolutePath());
            CompositeCacheManager compositeCacheManager = CompositeCacheManager.getUnconfiguredInstance();
            compositeCacheManager.configure(properties);
        } catch (FileSystemException fileSystemException) {
            throw new ConfigurationException(fileSystemException.getMessage(), fileSystemException);
        }
    }

    /* (non-Javadoc)
     * @see com.myschool.infra.agent.Agent#validate()
     */
    @Override
    public void validate() throws AgentException {
        try {
            // Validate region cache
            putEntry(CacheConstants.DUMMY_REGION, CacheConstants.DUMMY_KEY, CacheConstants.DUMMY_VALUE);
            removeEntry(CacheConstants.DUMMY_REGION, CacheConstants.DUMMY_KEY);
        } catch (com.myschool.infra.cache.exception.CacheException cacheException) {
            throw new AgentException("Errors Occurred while validating CacheAgent", cacheException);
        }
    }

    @Override
    public void createRegion(String region)
            throws com.myschool.infra.cache.exception.CacheException {
        try {
            JCS.defineRegion(region);
        } catch (CacheException cacheException) {
            throw new com.myschool.infra.cache.exception.CacheException(
                    cacheException.getMessage(), cacheException);
        }
    }

    @Override
    public void destroyRegion(String region)
            throws com.myschool.infra.cache.exception.CacheException {
        try {
            JCS jcsInstance = JCS.getInstance(region);
            jcsInstance.dispose();
        } catch (CacheException cacheException) {
            throw new com.myschool.infra.cache.exception.CacheException(
                    cacheException.getMessage(), cacheException);
        }
    }

    /* (non-Javadoc)
     * @see com.myschool.infra.cache.agent.OutMemoryCacheAgent#putEntry(java.lang.String, java.lang.Object, java.lang.Object)
     */
    @Override
    public void putEntry(String region, Object cacheKey, Object cacheValue)
            throws com.myschool.infra.cache.exception.CacheException {
        try {
            JCS jcsInstance = JCS.getInstance(region);
            jcsInstance.put(cacheKey, cacheValue);
        } catch (CacheException cacheException) {
            throw new com.myschool.infra.cache.exception.CacheException(
                    cacheException.getMessage(), cacheException);
        }
    }

    /* (non-Javadoc)
     * @see com.myschool.infra.cache.agent.OutMemoryCacheAgent#getEntry(java.lang.String, java.lang.Object)
     */
    @Override
    public Object getEntry(String region, Object cacheKey)
            throws com.myschool.infra.cache.exception.CacheException {
        try {
            JCS jcsInstance = JCS.getInstance(region);
            return jcsInstance.get(cacheKey);
        } catch (CacheException cacheException) {
            throw new com.myschool.infra.cache.exception.CacheException(
                    cacheException.getMessage(), cacheException);
        }
    }

    /* (non-Javadoc)
     * @see com.myschool.infra.cache.agent.OutMemoryCacheAgent#removeEntry(java.lang.String, java.lang.Object)
     */
    @Override
    public void removeEntry(String region, Object cacheKey)
            throws com.myschool.infra.cache.exception.CacheException {
        try {
            JCS jcsInstance = JCS.getInstance(region);
            jcsInstance.remove(cacheKey, region);
        } catch (CacheException cacheException) {
            throw new com.myschool.infra.cache.exception.CacheException(
                    cacheException.getMessage(), cacheException);
        }
    }

}
