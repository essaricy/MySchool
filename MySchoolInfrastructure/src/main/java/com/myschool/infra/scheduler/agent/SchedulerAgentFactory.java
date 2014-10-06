package com.myschool.infra.scheduler.agent;

import org.springframework.stereotype.Component;

import com.myschool.infra.agent.factory.AgentFactory;

/**
 * A factory for creating SchedulerAgent objects.
 */
@Component
public class SchedulerAgentFactory extends AgentFactory {

    /* (non-Javadoc)
     * @see com.myschool.infra.agent.factory.AgentFactory#getAgentBaseName()
     */
    @Override
    public String getAgentBaseName() {
        return "SCHEDULER AGENT";
    }

}
