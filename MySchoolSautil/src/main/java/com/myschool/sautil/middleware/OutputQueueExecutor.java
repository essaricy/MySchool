package com.myschool.sautil.middleware;

import java.util.Map;

import javax.jms.JMSException;
import javax.jms.MessageConsumer;

import com.myschool.common.exception.ConfigurationException;
import com.myschool.infra.application.Agents;
import com.myschool.infra.application.constants.CommandName;
import com.myschool.infra.middleware.dto.MessageQueue;
import com.myschool.sautil.base.StandAloneUtility;

/**
 * The Class OutputQueueExecutor.
 */
public class OutputQueueExecutor implements Runnable {

    /** The agents. */
    private Agents agents;

    /** The message queue. */
    private MessageQueue messageQueue;

    /** The message consumer. */
    private MessageConsumer messageConsumer;

    /** The message processor. */
    private MessageProcessor messageProcessor;

    /** The stand alone utilities. */
    private Map<CommandName, StandAloneUtility> standAloneUtilities;

    /**
     * Instantiates a new output queue executor.
     * 
     * @param messageQueue the message queue
     * @param messageConsumer the message consumer
     * @param messageProcessor the message processor
     * @param agents the agents
     * @param standAloneUtilities the stand alone utilities
     */
    public OutputQueueExecutor(MessageQueue messageQueue,
            MessageConsumer messageConsumer, MessageProcessor messageProcessor,
            Agents agents,
            Map<CommandName, StandAloneUtility> standAloneUtilities) {
        this.agents = agents;
        this.messageConsumer = messageConsumer;
        this.messageProcessor = messageProcessor;
        this.messageQueue = messageQueue;
        this.standAloneUtilities = standAloneUtilities;
    }

    /* (non-Javadoc)
     * @see java.lang.Runnable#run()
     */
    @Override
    public void run() {
        try {
            if (messageQueue == null) {
                throw new ConfigurationException("messageQueue is null.");
            }
            if (messageConsumer == null) {
                throw new ConfigurationException("messageConsumer is null.");
            }
            if (messageProcessor == null) {
                throw new ConfigurationException("messageProcessor is null.");
            }
            messageProcessor.setMessageQueue(messageQueue);
            messageProcessor.setAgents(agents);
            messageProcessor.setStandAloneUtilities(standAloneUtilities);
            messageConsumer.setMessageListener(messageProcessor);
            lockDestination();
        } catch (JMSException jmsException) {
            jmsException.printStackTrace();
        } catch (InterruptedException interruptedException) {
            interruptedException.printStackTrace();
        } catch (ConfigurationException configurationException) {
            configurationException.printStackTrace();
        }
    }

    /**
     * Lock destination.
     * 
     * @throws InterruptedException the interrupted exception
     */
    private void lockDestination() throws InterruptedException {
        synchronized (messageProcessor) {
            messageProcessor.wait(); 
        }
    }

    /**
     * Unlock destination.
     */
    private void unlockDestination() {
        synchronized (messageProcessor) {
            messageProcessor.notify();
        }
    }

    /**
     * Stop execution.
     * 
     * @throws JMSException the jMS exception
     */
    public void stopExecution() throws JMSException {
        unlockDestination();
        if (messageConsumer != null) {
            messageConsumer.close();
        }
    }

}
