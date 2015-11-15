package com.myschool.infra.media.factory;

import org.springframework.stereotype.Component;

import com.myschool.infra.agent.factory.AgentFactory;

/**
 * A factory for creating MediaServerAgentFactory.java objects.
 */
@Component
public class MediaServerAgentFactory extends AgentFactory {

    /* (non-Javadoc)
     * @see com.myschool.infra.agent.factory.AgentFactory#getAgentBaseName()
     */
    @Override
    public String getAgentBaseName() {
        return "MEDIA SERVER AGENT";
    }

}
