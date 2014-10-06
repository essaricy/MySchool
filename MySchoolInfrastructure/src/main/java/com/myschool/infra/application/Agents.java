package com.myschool.infra.application;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.myschool.infra.agent.Agent;
import com.myschool.infra.application.constants.AgentConstants;
import com.myschool.infra.image.agent.ImageScalingAgent;
import com.myschool.infra.oxo.agent.OxoAgent;
import com.myschool.infra.webserver.agent.WebServerAgent;

/**
 * The Class Agents.
 */
@Component
public class Agents {

    /** The Constant AGENTS. */
    private static final Map<String, Agent> AGENTS = new HashMap<String, Agent>();

    /**
     * Put.
     *
     * @param agentId the agent id
     * @param agent the agent
     */
    public void put(String agentId, Agent agent) {
        AGENTS.put(agentId, agent);
    }

    /**
     * Gets the agent.
     *
     * @param agentId the agent id
     * @return the agent
     */
    private Agent getAgent(String agentId) {
        return AGENTS.get(agentId);
    }

    /**
     * Gets the web server agent.
     *
     * @return the web server agent
     */
    public WebServerAgent getWebServerAgent() {
        return (WebServerAgent) getAgent(AgentConstants.WEBSERVER);
    }

    /**
     * Gets the image scaling agent.
     * 
     * @return the image scaling agent
     */
    public ImageScalingAgent getImageScalingAgent() {
        return (ImageScalingAgent) getAgent(AgentConstants.IMAGE_SCALING);
    }

    /**
     * Gets the oxo agent.
     *
     * @return the oxo agent
     */
    public OxoAgent getOxoAgent() {
        return (OxoAgent) getAgent(AgentConstants.OXO);
    }

}
