package com.myschool.infra.middleware.factory;

import org.springframework.stereotype.Component;

import com.myschool.infra.agent.factory.AgentFactory;

/**
 * A factory for creating MiddlewareAgent objects.
 */
@Component
public class MiddlewareAgentFactory extends AgentFactory {

    /* (non-Javadoc)
     * @see com.myschool.infra.agent.factory.AgentFactory#getAgentBaseName()
     */
    @Override
    public String getAgentBaseName() {
        return "MIDDLEWARE AGENT";
    }

}
