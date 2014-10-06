package com.myschool.infra.cache.agent;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.myschool.common.exception.AgentException;
import com.myschool.infra.cache.constants.CacheConstants;

/**
 * The Class JvmInMemoryCacheAgent.
 */
@Component
public class JvmInMemoryCacheAgent extends InMemoryCacheAgent {

    /** The Constant CACHE. */
    private static final Map<String, Object> CACHE = new HashMap<String, Object>();

    /* (non-Javadoc)
     * @see com.myschool.infra.cache.agent.InMemoryCacheAgent#getEntry(java.lang.String)
     */
    @Override
    public Object getEntry(String cacheKey) {
        return CACHE.get(cacheKey);
    }

    /* (non-Javadoc)
     * @see com.myschool.infra.cache.agent.InMemoryCacheAgent#putEntry(java.lang.String, java.lang.Object)
     */
    @Override
    public void putEntry(String cacheKey, Object cacheValue) {
        CACHE.put(cacheKey, cacheValue);
    }

    /* (non-Javadoc)
     * @see com.myschool.infra.cache.agent.InMemoryCacheAgent#removeEntry(java.lang.String)
     */
    @Override
    public void removeEntry(String cacheKey) {
        CACHE.remove(cacheKey);
    }

    /* (non-Javadoc)
     * @see com.myschool.infra.agent.Agent#validate()
     */
    @Override
    public void validate() throws AgentException {
        // Validate Heap Cache
        putEntry(CacheConstants.DUMMY_KEY, CacheConstants.DUMMY_VALUE);
        removeEntry(CacheConstants.DUMMY_KEY);
    }

}
