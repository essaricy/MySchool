package com.myschool.infra.application.dto;

import java.io.File;
import java.io.Serializable;

/**
 * The Class AgentDto.
 */
public class AgentDto implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The agent id. */
    private String agentId;

    /** The class name. */
    private String className;

    /** The config file. */
    private File configFile;

    /**
     * Gets the agent id.
     *
     * @return the agent id
     */
    public String getAgentId() {
        return agentId;
    }

    /**
     * Sets the agent id.
     *
     * @param agentId the new agent id
     */
    public void setAgentId(String agentId) {
        this.agentId = agentId;
    }

    /**
     * Gets the class name.
     *
     * @return the class name
     */
    public String getClassName() {
        return className;
    }

    /**
     * Sets the class name.
     *
     * @param className the new class name
     */
    public void setClassName(String className) {
        this.className = className;
    }

    /**
     * Gets the config file.
     *
     * @return the config file
     */
    public File getConfigFile() {
        return configFile;
    }

    /**
     * Sets the config file.
     *
     * @param configFile the new config file
     */
    public void setConfigFile(File configFile) {
        this.configFile = configFile;
    }


    /**
     * Constructs a <code>String</code> with all attributes
     * in name = value format.
     *
     * @return a <code>String</code> representation 
     * of this object.
     */
    public String toString() {
        final String SEPARATOR = ", ";
        StringBuilder retValue = new StringBuilder();
        retValue.append("AgentDto ( ")
            .append(super.toString()).append(SEPARATOR)
            .append("agentId = ").append(this.agentId).append(SEPARATOR)
            .append("className = ").append(this.className).append(SEPARATOR)
            .append("configFile = ").append(this.configFile).append(SEPARATOR)
            .append(" )\n");
        return retValue.toString();
    }

}
