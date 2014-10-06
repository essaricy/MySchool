package com.myschool.infra.agent;

import java.io.File;
import java.util.Properties;

import com.myschool.common.exception.AgentException;
import com.myschool.common.exception.ConfigurationException;


/**
 * The Interface Agent.
 */
public interface Agent {

    /**
     * Load configuration.
     *
     * @param configFile the config file
     * @throws ConfigurationException the configuration exception
     */
    void loadConfiguration(File configFile) throws ConfigurationException;

    /**
     * Validate.
     *
     * @throws AgentException the agent exception
     */
    void validate() throws AgentException;

    /**
     * Sets the file system properties.
     *
     * @param fileSystemProperties the new file system properties
     */
    void setFileSystemProperties(Properties fileSystemProperties);


}
