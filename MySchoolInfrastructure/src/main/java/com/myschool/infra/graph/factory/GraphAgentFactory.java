package com.myschool.infra.graph.factory;

import org.springframework.stereotype.Component;

import com.myschool.infra.agent.factory.AgentFactory;

/**
 * A factory for creating GraphAgent objects.
 */
@Component
public class GraphAgentFactory extends AgentFactory {

    /* (non-Javadoc)
     * @see com.myschool.infra.agent.factory.AgentFactory#getAgentBaseName()
     */
    @Override
    public String getAgentBaseName() {
        return "GRAPH AGENT";
    }

}
