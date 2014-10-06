package com.myschool.infra.captcha.agent;

import org.springframework.stereotype.Component;

import com.myschool.common.exception.AgentException;

/**
 * The Class JCaptchaAgent.
 */
@Component
public class JCaptchaAgent extends CaptchaAgent {

    /* (non-Javadoc)
     * @see com.myschool.infra.captcha.agent.CaptchaAgent#validate()
     */
    @Override
    public void validate() throws AgentException {
        // SimpleImageCaptchaServlet.validateResponse(request, userCaptchaResponse);
    }

}
