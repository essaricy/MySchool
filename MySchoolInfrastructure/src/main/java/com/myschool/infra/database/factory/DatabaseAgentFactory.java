package com.myschool.infra.database.factory;

import org.springframework.stereotype.Component;

import com.myschool.infra.agent.factory.AgentFactory;

/**
 * A factory for creating DatabaseAgent objects.
 */
@Component
public class DatabaseAgentFactory extends AgentFactory {

    @Override
    public String getAgentBaseName() {
        return "DATABASE AGENT";
    }

}
