package com.myschool.infra.sms.agent;

import java.io.File;
import java.io.IOException;
import java.text.MessageFormat;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.myschool.common.dto.ResultDto;
import com.myschool.common.exception.AgentException;
import com.myschool.common.exception.ConfigurationException;
import com.myschool.common.exception.SmsException;
import com.myschool.infra.agent.AbstractAgent;
import com.quasar.core.exception.FileSystemException;
import com.quasar.core.util.PropertiesUtil;

/**
 * The Class SmsServerAgent.
 */
@Component
public abstract class SmsServerAgent extends AbstractAgent {

    /** The Constant LOGGER. */
    private static final Logger LOGGER = Logger.getLogger(SmsServerAgent.class);

    /** The Constant SMS_PROVIDER_URL. */
    private static final String SMS_PROVIDER_URL = "sms.provider.url";

    /** The send sms url. */
    private static String sendSmsUrl;

    /* (non-Javadoc)
     * @see com.myschool.infra.agent.Agent#loadConfiguration(java.io.File)
     */
    @Override
    public void loadConfiguration(File configFile)
            throws ConfigurationException {
        try {
            properties = PropertiesUtil.loadNestedProperties(configFile);
            sendSmsUrl = properties.getProperty(SMS_PROVIDER_URL);
        } catch (FileSystemException fileSystemException) {
            throw new ConfigurationException(fileSystemException.getMessage(), fileSystemException);
        }
    }

    /* (non-Javadoc)
     * @see com.myschool.infra.Agent#validate()
     */
    @Override
    public void validate() throws AgentException {
        // Send test SMS?
    }

    /**
     * Gets the result.
     *
     * @param response the response
     * @return the result
     */
    public abstract ResultDto getResult(String response);

    /**
     * Send sms.
     *
     * @param parameters the parameters
     * @return the string
     * @throws SmsException the sms exception
     */
    public ResultDto sendSms(String... parameters) throws SmsException {
        return sendWebRequest(sendSmsUrl, parameters);
    }

    // TODO move this method to a utility class. Change exception
    /**
     * Send web request.
     *
     * @param url the url
     * @param parameters the parameters
     * @return the result dto
     * @throws SmsException the sms exception
     */
    private ResultDto sendWebRequest(String url, String... parameters) throws SmsException {
        String smsUrl = null;
        String response = null;
        HttpMethod method = null;
        HttpClient httpClient = new HttpClient();
        try {
            smsUrl = MessageFormat.format(url, (Object[])parameters);
            method = new GetMethod(smsUrl);
            httpClient.executeMethod(method);
            LOGGER.debug("Method Status: " + method.getStatusCode());
            byte[] responseBody = method.getResponseBody();
            // Deal with the response.
            // Use caution: ensure correct character encoding and is not binary data
            response = new String(responseBody);
        } catch (HttpException httpException) {
            throw new SmsException(httpException.getMessage(), httpException);
        } catch (IOException ioException) {
            throw new SmsException(ioException.getMessage(), ioException);
        }
        return getResult(response);
    }

}
