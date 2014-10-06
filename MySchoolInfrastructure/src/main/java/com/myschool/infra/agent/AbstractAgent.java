package com.myschool.infra.agent;

import java.util.Properties;

import org.springframework.stereotype.Component;

/**
 * The Class AbstractAgent.
 */
@Component
public abstract class AbstractAgent implements Agent {

    /** The properties. */
    protected Properties properties;

    /** The file system properties. */
    protected Properties fileSystemProperties;

    /* (non-Javadoc)
     * @see com.myschool.infra.agent.Agent#setFileSystemProperties(java.util.Properties)
     */
    public void setFileSystemProperties(Properties fileSystemProperties) {
        this.fileSystemProperties = fileSystemProperties;
    }
}
