package com.myschool.infra.sms.factory;

import org.springframework.stereotype.Component;

import com.myschool.infra.agent.factory.AgentFactory;

/**
 * A factory for creating SmsServerAgent objects.
 */
@Component
public class SmsServerAgentFactory extends AgentFactory {

    /* (non-Javadoc)
     * @see com.myschool.infra.agent.factory.AgentFactory#getAgentBaseName()
     */
    @Override
    public String getAgentBaseName() {
        return "SMS AGENT";
    }

}
