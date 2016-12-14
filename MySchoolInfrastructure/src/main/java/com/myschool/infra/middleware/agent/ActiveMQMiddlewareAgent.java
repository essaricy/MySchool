package com.myschool.infra.middleware.agent;

import java.io.File;

import javax.jms.Connection;

import org.springframework.stereotype.Component;

import com.myschool.common.exception.ConfigurationException;
import com.myschool.common.exception.ConnectionException;
import com.myschool.common.exception.FileSystemException;
import com.myschool.common.util.PropertiesUtil;

/**
 * The Class ActiveMQMiddlewareAgent.
 */
@Component
public class ActiveMQMiddlewareAgent extends MiddlewareAgent {

    /** The active mq connection factory. */
    //private static ActiveMQConnectionFactory activeMQConnectionFactory;

    /* (non-Javadoc)
     * @see com.myschool.infra.agent.Agent#loadConfiguration(java.io.File)
     */
    @Override
    public void loadConfiguration(File configFile)
            throws ConfigurationException {
        try {
            properties = PropertiesUtil.loadProperties(configFile);
            //activeMQConnectionFactory = new ActiveMQConnectionFactory();
            //activeMQConnectionFactory.setBrokerURL(properties.getProperty(MiddlwareConstants.PROVIDER_URL));
        } catch (FileSystemException fileSystemException) {
            throw new ConfigurationException(fileSystemException.getMessage(), fileSystemException);
        }
    }

    /* (non-Javadoc)
     * @see com.myschool.infra.middleware.agent.AbstractMiddlewareAgent#getConnection()
     */
    @Override
    public Connection getConnection() throws ConnectionException {
        /*try {
            return activeMQConnectionFactory.createConnection();
        } catch (JMSException jmsException) {
            throw new ConnectionException(jmsException.getMessage(), jmsException);
        }*/
        return null;
    }

}
