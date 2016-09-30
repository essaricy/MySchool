package com.myschool.infra.captcha.agent;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.MessageFormat;

import javax.net.ssl.HttpsURLConnection;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.google.api.client.http.HttpMethods;
import com.myschool.captcha.assembler.CaptchaDataAssembler;
import com.myschool.captcha.dto.CaptchaVerificationResult;
import com.myschool.common.exception.AgentException;
import com.myschool.common.exception.ConfigurationException;
import com.myschool.common.exception.FileSystemException;
import com.myschool.common.util.PropertiesUtil;
import com.myschool.infra.captcha.constants.CaptchaConstants;

/**
 * The Class GoogleNoCaptchaReCaptchaAgent.
 */
@Component
public class GoogleNoCaptchaReCaptchaAgent extends CaptchaAgent {

    /** The Constant LOGGER. */
    private static final Logger LOGGER = Logger.getLogger(GoogleNoCaptchaReCaptchaAgent.class);

    /** The user agent. */
    private final String USER_AGENT = "Mozilla/5.0";

    /** The client key. */
    private String clientKey;

    /** The server key. */
    private String serverKey;

    /** The verify url. */
    private String verifyUrl;

    /* (non-Javadoc)
     * @see com.myschool.infra.agent.Agent#loadConfiguration(java.io.File)
     */
    @Override
    public void loadConfiguration(File configFile)
            throws ConfigurationException {
        try {
            LOGGER.info("Loading configuration");
            properties = PropertiesUtil.loadProperties(configFile);

            clientKey = properties.getProperty(CaptchaConstants.KEY_CLIENT);
            LOGGER.info("clientKey=" + clientKey);
            serverKey = properties.getProperty(CaptchaConstants.KEY_SERVER);
            LOGGER.info("serverKey=" + serverKey);
            verifyUrl = properties.getProperty(CaptchaConstants.VERIFY_URL);
            LOGGER.info("verifyUrl=" + verifyUrl);

        } catch (FileSystemException fileSystemException) {
            throw new ConfigurationException(fileSystemException.getMessage(), fileSystemException);
        }
    }

    /* (non-Javadoc)
     * @see com.myschool.infra.captcha.agent.CaptchaAgent#validate()
     */
    @Override
    public void validate() throws AgentException {
        LOGGER.info("validate");
    }

    /* (non-Javadoc)
     * @see com.myschool.infra.captcha.agent.CaptchaAgent#isValid(java.lang.String)
     */
    @Override
    public boolean isValid(String captchaResponse) {
        CaptchaVerificationResult result = sendPost(verifyUrl, serverKey, captchaResponse);
        return (result != null && result.isSuccess());
    }

    /**
     * Send post.
     *
     * @param url the url
     * @param secret the secret
     * @param captchaResponse the captcha response
     * @return the captcha verification result
     */
    private CaptchaVerificationResult sendPost(String url, String secret, String captchaResponse) {
        CaptchaVerificationResult result = null;
        BufferedReader bufferedReader = null;
        DataOutputStream dataOutputStream = null;
        try {
            LOGGER.info("request - " + captchaResponse);
            URL obj = new URL(url);
            HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();

            //add request header
            con.setRequestMethod(HttpMethods.POST);
            con.setRequestProperty("User-Agent", USER_AGENT);
            con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

            String urlParameters = "secret={0}&response={1}";
            String data = MessageFormat.format(urlParameters, secret, captchaResponse);

            // Send post request
            con.setDoOutput(true);
            dataOutputStream = new DataOutputStream(con.getOutputStream());
            dataOutputStream.writeBytes(data);
            dataOutputStream.flush();

            int responseCode = con.getResponseCode();
            LOGGER.info("responseCode - " + responseCode);

            bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = bufferedReader.readLine()) != null) {
                response.append(inputLine);
            }
            //print result
            LOGGER.info("response - " + response);
            result = CaptchaDataAssembler.create(response.toString());
        } catch (Exception exception) {
            LOGGER.error(exception.getMessage(), exception);
        } finally {
            IOUtils.closeQuietly(dataOutputStream);
            IOUtils.closeQuietly(bufferedReader);
        }
        return result;
    }

    /* (non-Javadoc)
     * @see com.myschool.infra.captcha.agent.CaptchaAgent#getClientKey()
     */
    @Override
    public String getClientKey() {
        return clientKey;
    }

}
