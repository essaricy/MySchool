package com.myschool.infra.captcha.agent;

import org.springframework.stereotype.Component;

import com.myschool.common.exception.AgentException;
import com.myschool.infra.agent.AbstractAgent;

/**
 * The Class CaptchaAgent.
 */
@Component
public abstract class CaptchaAgent extends AbstractAgent {

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

    /**
     * Gets the client key.
     *
     * @return the client key
     */
    public abstract String getClientKey();

}
