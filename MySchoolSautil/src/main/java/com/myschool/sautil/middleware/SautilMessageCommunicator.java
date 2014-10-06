package com.myschool.sautil.middleware;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.Queue;
import javax.jms.Session;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.myschool.common.exception.ConfigurationException;
import com.myschool.common.exception.ConnectionException;
import com.myschool.infra.application.Agents;
import com.myschool.infra.application.constants.CommandName;
import com.myschool.infra.middleware.agent.MiddlewareAgent;
import com.myschool.infra.middleware.constants.QueueCategory;
import com.myschool.infra.middleware.dto.MessageQueue;
import com.myschool.sautil.base.StandAloneUtility;
import com.myschool.sautil.externalize.ExternalizeData;

/**
 * The Class SautilMessageCommunicator.
 */
@Component
public class SautilMessageCommunicator extends StandAloneUtility {

    /** The Constant LOGGER. */
    private static final Logger LOGGER = Logger.getLogger(SautilMessageCommunicator.class);

    /** The agents. */
    @Autowired
    private MiddlewareAgent middlewareAgent;

    /** The agents. */
    @Autowired
    private Agents agents;

    /** The externalize data. */
    @Autowired
    private ExternalizeData externalizeData;

    /** The message processor factory. */
    @Autowired
    private MessageProcessorFactory messageProcessorFactory;

    /** The sautil message communicator shutdown hook. */
    @Autowired
    private SautilMessageCommunicatorShutdownHook sautilMessageCommunicatorShutdownHook;

    /* (non-Javadoc)
     * @see com.myschool.sautil.base.StandAloneUtility#validateParameters()
     */
    @Override
    public void validateParameters() throws ConfigurationException {
    }

    /* (non-Javadoc)
     * @see com.myschool.sautil.base.StandAloneUtility#startProcess()
     */
    @Override
    public void startProcess() throws ConnectionException, JMSException {
        LOGGER.info("Starting SautilMessageCommunicator.");
        Queue queue = null;
        Thread thread = null;
        Session session = null;
        MessageConsumer messageConsumer = null;
        MessageProcessor messageProcessor = null;
        OutputQueueExecutor outputQueueExecutor = null;

        Runtime runtime = Runtime.getRuntime();
        Connection connection = middlewareAgent.getConnection();
        session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        List<MessageQueue> messageQueues = middlewareAgent.getMessageQueues();
        Map<CommandName, StandAloneUtility> standAloneUtilities = getAllStandAloneUtilities();
        if (messageQueues != null && !messageQueues.isEmpty()) {
            try {
                List<OutputQueueExecutor> outputQueueExecutors = new ArrayList<OutputQueueExecutor>();
                for (MessageQueue messageQueue : messageQueues) {
                    // Start output queues.
                    String outputQueueName = messageQueue.getOutput();
                    queue = session.createQueue(outputQueueName);
                    LOGGER.info("Established queue connection to " + queue);
                    messageConsumer = session.createConsumer(queue);

                    QueueCategory queueCategory = messageQueue.getQueueCategory();
                    messageProcessor = messageProcessorFactory.getMessageProcessor(queueCategory);

                    outputQueueExecutor = new OutputQueueExecutor(messageQueue, messageConsumer, messageProcessor, agents, standAloneUtilities);
                    thread = new Thread(outputQueueExecutor);
                    thread.setName("MESSAGE_PROCESSOR_" + outputQueueName);
                    thread.start();
                    outputQueueExecutors.add(outputQueueExecutor);
                    LOGGER.info("Starting listener to message queue: " + outputQueueName);
                }
                sautilMessageCommunicatorShutdownHook.setConnection(connection);
                sautilMessageCommunicatorShutdownHook.setSession(session);
                sautilMessageCommunicatorShutdownHook.setQueueProcessors(outputQueueExecutors);
                runtime.addShutdownHook(sautilMessageCommunicatorShutdownHook);
                LOGGER.info("Registered shutdown hook.");
                connection.start();
            } catch (JMSException jmsException) {
                LOGGER.error(jmsException.getMessage(), jmsException);
            }
        }
        LOGGER.info("Completed SautilMessageCommunicator.");
    }

    /**
     * Gets the all stand alone utilities.
     * 
     * @return the all stand alone utilities
     */
    private Map<CommandName, StandAloneUtility> getAllStandAloneUtilities() {
        Map<CommandName, StandAloneUtility> standAloneUtilities = new HashMap<CommandName, StandAloneUtility>();
        standAloneUtilities.put(CommandName.EXTERNALIZE_DATA, externalizeData);
        return standAloneUtilities;
    }

    /* (non-Javadoc)
     * @see com.myschool.sautil.base.StandAloneUtility#getUsageText()
     */
    @Override
    public String getUsageText() {
        return null;
    }

}
