package com.myschool.infra.middleware.agent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.myschool.infra.application.dto.CommandDto;
import com.myschool.infra.middleware.constants.QueueCategory;
import com.myschool.infra.middleware.constants.QueueType;
import com.myschool.infra.middleware.exception.MessageException;
import com.myschool.infra.oxo.agent.OxoAgent;

@Component
public class MiddlewareMessageHelper {

    @Autowired
    private MiddlewareAgent middlewareAgent;

    /** The oxo agent. */
    @Autowired
    private OxoAgent oxoAgent;

    public void produceCommandMessage(CommandDto command) throws MessageException {
        if (command != null) {
            String xmlMessage = oxoAgent.toXml(command);
            // TODO handle error if the message is null
            if (xmlMessage != null) {
                middlewareAgent.produceMessage(QueueCategory.COMMAND_EXECUTOR, QueueType.OUTPUT, xmlMessage);
            }
        }
    }

}
