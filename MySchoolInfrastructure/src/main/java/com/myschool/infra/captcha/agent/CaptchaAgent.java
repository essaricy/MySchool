package com.myschool.infra.captcha.agent;

import java.io.File;

import org.springframework.stereotype.Component;

import com.myschool.common.exception.AgentException;
import com.myschool.common.exception.ConfigurationException;
import com.myschool.infra.agent.AbstractAgent;

/**
 * The Class CaptchaAgent.
 */
@Component
public abstract class CaptchaAgent extends AbstractAgent {

    /* (non-Javadoc)
     * @see com.myschool.infra.agent.Agent#loadConfiguration(java.io.File)
     */
    @Override
    public void loadConfiguration(File configFile)
            throws ConfigurationException {
        // TODO load captcha config file.
    }

    /* (non-Javadoc)
     * @see com.myschool.infra.Agent#validate()
     */
    @Override
    public abstract void validate() throws AgentException;

    /**
     * Checks if is valid.
     *
     * @param captchaResponse the captcha response
     * @return true, if is valid
     */
    public abstract boolean isValid(String captchaResponse);

}
