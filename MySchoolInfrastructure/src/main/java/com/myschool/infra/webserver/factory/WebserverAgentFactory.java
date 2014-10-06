package com.myschool.infra.webserver.factory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.myschool.common.exception.AgentException;
import com.myschool.infra.agent.Agent;
import com.myschool.infra.agent.factory.AgentFactory;
import com.myschool.infra.webserver.agent.JBossServerAgent;
import com.myschool.infra.webserver.agent.StandaloneAgent;
import com.myschool.infra.webserver.agent.TomcatServerAgent;
import com.myschool.infra.webserver.agent.WebServerAgent;
import com.myschool.infra.webserver.constants.ServerType;

/**
 * A factory for creating WebserverAgent objects.
 */
@Component
public class WebserverAgentFactory extends AgentFactory {

    /** The j boss server agent. */
    @Autowired
    private JBossServerAgent jBossServerAgent;

    /** The standalone agent. */
    @Autowired
    private StandaloneAgent standaloneAgent;

    /** The tomcat server agent. */
    @Autowired
    private TomcatServerAgent tomcatServerAgent;

    /* (non-Javadoc)
     * @see com.myschool.infra.agent.factory.AgentFactory#getAgentBaseName()
     */
    @Override
    public String getAgentBaseName() {
        return "WEBSERVER AGENT";
    }

    /* (non-Javadoc)
     * @see com.myschool.infra.agent.factory.AgentFactory#getAgent(java.lang.String)
     */
    @Override
    public Agent getAgent(String agentName) throws AgentException {
        throw new AgentException("WebserverAgent is runtime-deterministic agent. Please use WebserverAgentFactory.getAgent() to retrieve WebserverAgent");
        
    }

    /**
     * Gets the agent.
     *
     * @return the agent
     * @throws AgentException the agent exception
     */
    public Agent getAgent() throws AgentException {
        ServerType serverType = ServerType.getServerType();
        return getServerAgent(serverType);
    }

    /**
     * Gets the server agent.
     *
     * @param serverType the server type
     * @return the server agent
     */
    private WebServerAgent getServerAgent(ServerType serverType) {
        WebServerAgent webServerAgent = null;
        if (serverType == ServerType.JBOSS) {
            webServerAgent = jBossServerAgent;
        } else if (serverType == ServerType.STAND_ALONE) {
            webServerAgent = standaloneAgent;
        } else if (serverType == ServerType.TOMCAT) {
            webServerAgent = tomcatServerAgent;
        }
        return webServerAgent;
    }

}
