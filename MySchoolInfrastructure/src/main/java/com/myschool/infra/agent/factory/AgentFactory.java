package com.myschool.infra.agent.factory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.myschool.common.exception.AgentException;
import com.myschool.common.util.StringUtil;
import com.myschool.infra.agent.Agent;

/**
 * A factory for creating Agent objects.
 */
@Component
public abstract class AgentFactory {

    /** The application context. */
    @Autowired
    private ApplicationContext applicationContext;

    /**
     * Gets the agent base name.
     *
     * @return the agent base name
     */
    public abstract String getAgentBaseName();

    /**
     * Gets the agent.
     *
     * @param agentName the agent name
     * @return the agent
     * @throws AgentException the agent exception
     */
    public Agent getAgent(String agentName) throws AgentException {
        Agent agent = null;
        try {
            if (StringUtil.isNullOrBlank(agentName)) {
                throw new AgentException("Agent name is not specified.");
            }
            Class agentBeanClass = Class.forName(agentName);
            agent = (Agent) applicationContext.getBean(agentBeanClass);
            if (agent == null) {
                throw new AgentException("No such Agent with name '" + agentName + "'");
            }
        } catch (ClassNotFoundException classNotFoundException) {
            throw new AgentException(classNotFoundException.getMessage(), classNotFoundException);
        }
        return agent;
    }

}
