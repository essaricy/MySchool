package com.myschool.sautil.middleware;

import java.util.Map;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.apache.log4j.Logger;

import com.myschool.infra.application.Agents;
import com.myschool.infra.application.constants.CommandName;
import com.myschool.infra.middleware.dto.MessageQueue;
import com.myschool.infra.oxo.agent.OxoAgent;
import com.myschool.sautil.base.StandAloneUtility;

/**
 * The Class MessageProcessor.
 */
public abstract class MessageProcessor implements MessageListener {

    /** The Constant LOGGER. */
    private static final Logger LOGGER = Logger.getLogger(MessageProcessor.class);

    /** The agents. */
    private Agents agents;

    /** The message queue. */
    protected MessageQueue messageQueue;

    /** The stand alone utilities. */
    protected Map<CommandName, StandAloneUtility> standAloneUtilities;

    /**
     * Gets the message queue.
     * 
     * @return the message queue
     */
    public MessageQueue getMessageQueue() {
        return messageQueue;
    }

    /**
     * Sets the message queue.
     * 
     * @param messageQueue the new message queue
     */
    public void setMessageQueue(MessageQueue messageQueue) {
        this.messageQueue = messageQueue;
    }

    /**
     * Sets the agents.
     * 
     * @param agents the new agents
     */
    public void setAgents(Agents agents) {
        this.agents = agents;
    }

    /**
     * Sets the stand alone utilities.
     * 
     * @param standAloneUtilities the stand alone utilities
     */
    public void setStandAloneUtilities(
            Map<CommandName, StandAloneUtility> standAloneUtilities) {
        this.standAloneUtilities = standAloneUtilities;
    }

    /* (non-Javadoc)
     * @see javax.jms.MessageListener#onMessage(javax.jms.Message)
     */
    @Override
    public void onMessage(Message message) {
        try {
            if (message instanceof TextMessage) {
                TextMessage textMessage = (TextMessage) message;
                OxoAgent oxoAgent = agents.getOxoAgent();
                Object object = oxoAgent.toObject(textMessage.getText());
                processObject(object);
            } else {
                LOGGER.error("Unsupported message type: " + message);
            }
        } catch (Exception exception) {
            LOGGER.error("Exception occurred while processing the message: " + exception.getMessage(), exception);
        }
    }

    /**
     * Process object.
     * 
     * @param object the object
     * @throws Exception the exception
     */
    protected abstract void processObject(Object object) throws Exception;

}
