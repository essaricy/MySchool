package com.myschool.infra.cache.factory;

import org.springframework.stereotype.Component;

import com.myschool.infra.agent.factory.AgentFactory;

/**
 * A factory for creating CacheAgent objects.
 */
@Component
public class CacheAgentFactory extends AgentFactory {

    @Override
    public String getAgentBaseName() {
        return "CACHE AGENT";
    }

}
