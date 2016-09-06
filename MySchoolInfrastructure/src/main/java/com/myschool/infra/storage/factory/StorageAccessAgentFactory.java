package com.myschool.infra.storage.factory;

import org.springframework.stereotype.Component;

import com.myschool.infra.agent.factory.AgentFactory;

@Component
public class StorageAccessAgentFactory extends AgentFactory {

    /* (non-Javadoc)
     * @see com.myschool.infra.agent.factory.AgentFactory#getAgentBaseName()
     */
    @Override
    public String getAgentBaseName() {
        return "STORAGE ACCESS AGENT";
    }

}
