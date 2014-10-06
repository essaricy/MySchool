package com.myschool.sautil.middleware;

import java.util.List;

import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.Session;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

/**
 * The Class SautilMessageCommunicatorShutdownHook.
 */
@Component
public class SautilMessageCommunicatorShutdownHook extends Thread {

    private static final Logger LOGGER = Logger.getLogger(SautilMessageCommunicatorShutdownHook.class);

    /** The connection. */
    private Connection connection;

    /** The session. */
    private Session session;

    /** The queue processors. */
    private List<OutputQueueExecutor> queueProcessors;

    /**
     * Gets the connection.
     * 
     * @return the connection
     */
    public Connection getConnection() {
        return connection;
    }

    /**
     * Sets the connection.
     * 
     * @param connection the new connection
     */
    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    /**
     * Gets the session.
     * 
     * @return the session
     */
    public Session getSession() {
        return session;
    }

    /**
     * Sets the session.
     * 
     * @param session the new session
     */
    public void setSession(Session session) {
        this.session = session;
    }

    /**
     * Gets the queue processors.
     * 
     * @return the queue processors
     */
    public List<OutputQueueExecutor> getQueueProcessors() {
        return queueProcessors;
    }

    /**
     * Sets the queue processors.
     * 
     * @param queueProcessors the new queue processors
     */
    public void setQueueProcessors(
            List<OutputQueueExecutor> queueProcessors) {
        this.queueProcessors = queueProcessors;
    }

    /* (non-Javadoc)
     * @see java.lang.Thread#run()
     */
    @Override
    public void run() {
        try {
            LOGGER.info("SautilMessageCommunicatorShutdownHook called.");
            for (OutputQueueExecutor queueProcessor : queueProcessors) {
                queueProcessor.stopExecution();
            }
            session.close();
            LOGGER.info("Session on all message queues has been closed.");
            connection.close();
            LOGGER.info("Connection to the message queues has been closed.");
        } catch (JMSException jmsException) {
            jmsException.printStackTrace();
        }
    }

}
