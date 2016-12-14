package com.myschool.infra.application;

import java.io.File;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.myschool.common.exception.AgentException;
import com.myschool.common.exception.ConfigurationException;
import com.myschool.common.exception.FileSystemException;
import com.myschool.file.util.FileUtil;
import com.myschool.infra.agent.Agent;
import com.myschool.infra.agent.factory.AgentFactory;
import com.myschool.infra.agent.factory.AgentFactoryFactory;
import com.myschool.infra.application.dto.AgentDto;
import com.myschool.infra.webserver.factory.WebserverAgentFactory;

/**
 * The Class AgentsLoader.
 */
@Component
public class AgentsLoader {

    /** The Constant LOGGER. */
    private static final Logger LOGGER = Logger.getLogger(AgentsLoader.class);

    /** The agents. */
    @Autowired
    private Agents agents;

    /** The agent factory factory. */
    @Autowired
    private AgentFactoryFactory agentFactoryFactory;

    /**
     * Load all agents. This method is the entry point for all the agents.
     * 
     * @param fileSystemProperties the file system properties
     * @param agentDtos the agent dtos
     * @throws AgentException the agent exception
     */
    public void loadAgents(Properties fileSystemProperties, List<AgentDto> agentDtos) throws AgentException {
        String agentId = null;
        String className = null;
        File configFile = null;
        String message = null;

        Agent agent = null;
        AgentDto agentConfig = null;
        AgentFactory agentFactory = null;
        try {
            for (Iterator<AgentDto> iterator = agentDtos.iterator(); iterator.hasNext();) {
                agentConfig = (AgentDto) iterator.next();
                agentId = agentConfig.getAgentId();
                className = agentConfig.getClassName();
                configFile = agentConfig.getConfigFile();

                //System.out.println("agentId=" + agentId);
                agentFactory = agentFactoryFactory.getAgentFactory(agentId);
                if (agentFactory == null) {
                    throw new AgentException("Agent Factory not found for the agent " + agentId);
                }
                if (agentFactory instanceof WebserverAgentFactory) {
                    agent = ((WebserverAgentFactory) agentFactory).getAgent();
                } else {
                    agent = agentFactory.getAgent(className);
                }
                if (configFile != null) {
                    message = configFile + " is missing/inaccessible for the agent " + agentId;
                    FileUtil.checkFile(configFile.getAbsolutePath(), message, message);
                    agent.loadConfiguration(configFile);
                }
                agent.setFileSystemProperties(fileSystemProperties);
                agent.validate();
                LOGGER.info("Loaded agent '" + agentId + "' successfully.");
                agents.put(agentId, agent);
            }
            LOGGER.info("Loaded all agents successfully.");
        } catch (ConfigurationException configurationException) {
            throw new AgentException(configurationException.getMessage(), configurationException);
        } catch (FileSystemException fileSystemException) {
            throw new AgentException(fileSystemException.getMessage(), fileSystemException);
        }
    }

}
