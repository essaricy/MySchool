package com.myschool.infra.webserver.agent;

import java.io.File;
import java.util.Properties;

import javax.sql.DataSource;

import com.myschool.common.exception.AgentException;
import com.myschool.common.exception.ConfigurationException;
import com.myschool.infra.agent.AbstractAgent;

/**
 * The Class WebServerAgent.
 */
public abstract class WebServerAgent extends AbstractAgent {

    /* (non-Javadoc)
     * @see com.myschool.infra.agent.Agent#loadConfiguration(java.io.File)
     */
    @Override
    public void loadConfiguration(File configFile)
            throws ConfigurationException {
        throw new ConfigurationException("WebServerAgent is a runtime deterministic agent and cannot load configuration.");
    }

    /* (non-Javadoc)
     * @see com.myschool.infra.agent.Agent#validate()
     */
    @Override
    public void validate() throws AgentException {
    }

    /**
     * Gets the data source.
     *
     * @param dataSourceProperties the data source properties
     * @return the data source
     * @throws AgentException the agent exception
     */
    public abstract DataSource getDataSource(Properties dataSourceProperties) throws AgentException;

}
