package com.myschool.infra.middleware.agent;

import java.io.File;
import java.util.List;

import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.myschool.common.exception.AgentException;
import com.myschool.common.exception.ConfigurationException;
import com.myschool.common.exception.ConnectionException;
import com.myschool.infra.agent.AbstractAgent;
import com.myschool.infra.filesystem.constants.FileSystemConstants;
import com.myschool.infra.middleware.constants.QueueCategory;
import com.myschool.infra.middleware.constants.QueueType;
import com.myschool.infra.middleware.dto.MessageQueue;
import com.myschool.infra.middleware.dto.MqConfiguration;
import com.myschool.infra.middleware.exception.MessageException;
import com.myschool.infra.middleware.readers.MessageQueueConfigReader;

/**
 * The Class MiddlewareAgent.
 */
@Component
public abstract class MiddlewareAgent extends AbstractAgent {

    /** The message queue config reader. */
    @Autowired
    private MessageQueueConfigReader messageQueueConfigReader;

    /** The mq configuration. */
    private MqConfiguration mqConfiguration;

    /**
     * Gets the connection.
     *
     * @return the connection
     * @throws ConnectionException the connection exception
     */
    public abstract Connection getConnection() throws ConnectionException;

    /* (non-Javadoc)
     * @see com.myschool.infra.agent.Agent#validate()
     */
    public void validate() throws AgentException {
        /*try {
            Connection connection = getConnection();
            // Load this file during start up.
            if (mqConfiguration == null) {
                loadMqConfiguration();
            }
            connection.close();
        } catch (ConnectionException connectionException) {
            throw new AgentException(connectionException.getMessage(), connectionException);
        } catch (JMSException jmsException) {
            throw new AgentException(jmsException.getMessage(), jmsException);
        } catch (ConfigurationException configurationException) {
            throw new AgentException(configurationException.getMessage(), configurationException);
        }*/
    }

    /**
     * Produce message.
     * 
     * @param queueName the queue name
     * @param queueType the queue type
     * @param message the message
     * @throws MessageException the message exception
     */
    public void produceMessage(QueueCategory queueName, QueueType queueType,
            String message) throws MessageException {
        Connection connection = null;
        Session session = null;
        Destination destination = null;
        MessageProducer producer = null;
        try {
            if (queueName != null && queueType != null && message != null) {
                connection = getConnection();
                connection.start();

                // Create a Session
                session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
                // Create the destination (Topic or Queue)
                // Get the queueName by queueName and queueType
                destination = session.createQueue(getQueueName(queueName, queueType));
                // Create a MessageProducer from the Session to the Topic or Queue
                producer = session.createProducer(destination);
                producer.setDeliveryMode(DeliveryMode.PERSISTENT);
                // Create a messages
                TextMessage textMessage = session.createTextMessage(message);
                // Tell the producer to send the message
                producer.send(textMessage);
            }
        } catch (ConnectionException connectionException) {
            throw new MessageException(connectionException.getMessage(), connectionException);
        } catch (JMSException jmsException) {
            throw new MessageException(jmsException.getMessage(), jmsException);
        } finally {
            try {
                // Clean up
                connection.stop();
                producer.close();
                session.close();
                connection.close();
            } catch (JMSException jmsException) {
                throw new MessageException(jmsException.getMessage(), jmsException);
            }
        }
    }

    /**
     * Gets the queue name.
     * 
     * @param queueCategory the queue category
     * @param queueType the queue type
     * @return the queue name
     */
    protected String getQueueName(QueueCategory queueCategory, QueueType queueType) {
        List<MessageQueue> messageQueues = mqConfiguration.getMessageQueues();
        if (messageQueues != null && !messageQueues.isEmpty()) {
            for (MessageQueue messageQueue : messageQueues) {
                if (queueCategory == messageQueue.getQueueCategory()) {
                    if (queueType == QueueType.OUTPUT) {
                        return messageQueue.getOutput();
                    } else if (queueType == QueueType.FAIL) {
                        return messageQueue.getFail();
                    } else if (queueType == QueueType.ERROR) {
                        return messageQueue.getError();
                    }
                }
            }
        }
        return null;
    }

    /**
     * Load mq configuration.
     * 
     * @throws ConfigurationException the configuration exception
     */
    private void loadMqConfiguration() throws ConfigurationException {
        String property = fileSystemProperties.getProperty(FileSystemConstants.CONFIG_MIDDLEWARE_QUEUE_FILE);
        File mqConfigFile = new File(property);
        mqConfiguration = messageQueueConfigReader.getConfiguration(mqConfigFile);
    }

    /**
     * Gets the message queues.
     * 
     * @return the message queues
     */
    public List<MessageQueue> getMessageQueues() {
        return mqConfiguration.getMessageQueues();
    }
}
