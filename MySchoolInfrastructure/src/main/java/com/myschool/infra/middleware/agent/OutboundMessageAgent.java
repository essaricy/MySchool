package com.myschool.infra.middleware.agent;

import java.io.File;

import org.springframework.stereotype.Component;

import com.myschool.common.exception.AgentException;
import com.myschool.common.exception.ConfigurationException;
import com.myschool.infra.agent.AbstractAgent;

@Component
public class OutboundMessageAgent extends AbstractAgent {

    @Override
    public void loadConfiguration(File configFile)
            throws ConfigurationException {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void validate() throws AgentException {
        // TODO Auto-generated method stub
        
    }

    public void sendMessage(String message) {
        System.out.println("################## OutboundMessageAgent - Will send message: " + message);
    }

}
