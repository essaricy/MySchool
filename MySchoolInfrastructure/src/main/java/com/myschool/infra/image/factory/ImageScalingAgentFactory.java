package com.myschool.infra.image.factory;

import org.springframework.stereotype.Component;

import com.myschool.infra.agent.factory.AgentFactory;

/**
 * A factory for creating ImageScalingAgentFactory objects.
 */
@Component
public class ImageScalingAgentFactory extends AgentFactory {

    /* (non-Javadoc)
     * @see com.myschool.infra.agent.factory.AgentFactory#getAgentBaseName()
     */
    @Override
    public String getAgentBaseName() {
        return "IMAGE SCALING AGENT";
    }

}
