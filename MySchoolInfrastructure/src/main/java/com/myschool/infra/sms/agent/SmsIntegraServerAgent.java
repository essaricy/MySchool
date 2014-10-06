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
        ResultDto resultDto = new ResultDto();
        resultDto.setSuccessful(ResultDto.FAILURE);
        if (response != null && response.indexOf("100") != -1) {
            resultDto.setSuccessful(ResultDto.SUCCESS);
        }
        resultDto.setStatusMessage(response);
        return resultDto;
    }

}
