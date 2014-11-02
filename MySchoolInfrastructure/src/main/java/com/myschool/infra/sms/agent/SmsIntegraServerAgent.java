package com.myschool.infra.sms.agent;

import org.springframework.stereotype.Component;

import com.myschool.common.dto.ResultDto;

/**
 * The Class SmsIntegraServerAgent.
 */
@Component
public class SmsIntegraServerAgent extends SmsServerAgent {

    /* (non-Javadoc)
     * @see com.myschool.infra.sms.agent.SmsServerAgent#getResult(java.lang.String)
     */
    @Override
    public ResultDto getResult(String response) {
        ResultDto result = new ResultDto();
        result.setSuccessful(ResultDto.FAILURE);
        if (response != null && response.indexOf("100") != -1) {
            result.setSuccessful(ResultDto.SUCCESS);
        }
        result.setStatusMessage(response);
        return result;
    }

}
