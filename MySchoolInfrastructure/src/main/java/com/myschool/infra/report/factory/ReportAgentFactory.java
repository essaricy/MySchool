package com.myschool.infra.report.factory;

import org.springframework.stereotype.Component;

import com.myschool.infra.agent.factory.AgentFactory;

/**
 * A factory for creating ReportAgent objects.
 */
@Component
public class ReportAgentFactory extends AgentFactory {

    /* (non-Javadoc)
     * @see com.myschool.infra.agent.factory.AgentFactory#getAgentBaseName()
     */
    @Override
    public String getAgentBaseName() {
        return "REPORT AGENT";
    }

}
