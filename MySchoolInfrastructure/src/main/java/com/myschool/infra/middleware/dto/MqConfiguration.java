package com.myschool.infra.middleware.dto;

import java.io.Serializable;
import java.util.List;

/**
 * The Class MqConfiguration.
 */
public class MqConfiguration implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The message queues. */
    private List<MessageQueue> messageQueues;

    /**
     * Gets the message queues.
     * 
     * @return the message queues
     */
    public List<MessageQueue> getMessageQueues() {
        return messageQueues;
    }

    /**
     * Sets the message queues.
     * 
     * @param messageQueues the new message queues
     */
    public void setMessageQueues(List<MessageQueue> messageQueues) {
        this.messageQueues = messageQueues;
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
        retValue.append("MqConfiguration ( ")
            .append(super.toString()).append(SEPARATOR)
            .append("messageQueues = ").append(this.messageQueues).append(SEPARATOR)
            .append(" )\n");
        return retValue.toString();
    }

}
