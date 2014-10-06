package com.myschool.sautil.middleware;

import org.springframework.stereotype.Component;

import com.myschool.infra.middleware.constants.QueueCategory;

/**
 * A factory for creating MessageProcessor objects.
 */
@Component
public class MessageProcessorFactory {

    /**
     * Gets the message processor.
     * 
     * @param queueCategory the queue category
     * @return the message processor
     */
    public MessageProcessor getMessageProcessor(QueueCategory queueCategory) {
        if (queueCategory == QueueCategory.COMMAND_EXECUTOR) {
            return new CommandMessageProcessor();
        }
        return null;
    }

}
