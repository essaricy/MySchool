package com.myschool.infra.captcha.agent;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.google.api.client.http.HttpMethods;
import com.myschool.captcha.assembler.CaptchaDataAssembler;
import com.myschool.captcha.dto.CaptchaVerificationResult;
import com.myschool.common.exception.AgentException;
import com.myschool.common.exception.ConfigurationException;
import com.myschool.infra.captcha.constants.CaptchaConstants;
import com.quasar.core.exception.FileSystemException;
import com.quasar.core.util.PropertiesUtil;
import com.quasar.core.util.StringUtil;

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

    private Map<String, CaptchaVerificationResult> resultsCache;

    /* (non-Javadoc)
     * @see com.myschool.infra.agent.Agent#loadConfiguration(java.io.File)
     */
    @Override
    public void loadConfiguration(File configFile)
            throws ConfigurationException {
        try {
            LOGGER.info("Loading configuration");
            properties = PropertiesUtil.loadProperties(configFile);
            resultsCache = new HashMap<String, CaptchaVerificationResult>() {

                private static final long serialVersionUID = 1L;

                @Override
                public CaptchaVerificationResult put(String key,
                        CaptchaVerificationResult value) {
                    // TODO: ensure this list does not grow huge. Remove older items, (say more than 1hr) periodically.
                    return super.put(key, value);
                }
            };

            clientKey = properties.getProperty(CaptchaConstants.KEY_CLIENT);
            serverKey = properties.getProperty(CaptchaConstants.KEY_SERVER);
            verifyUrl = properties.getProperty(CaptchaConstants.VERIFY_URL);

            if (StringUtil.isNullOrBlank(clientKey)) {
                throw new ConfigurationException("Missing '" + CaptchaConstants.KEY_CLIENT + "' in the config file.");
            }
            if (StringUtil.isNullOrBlank(serverKey)) {
                throw new ConfigurationException("Missing '" + CaptchaConstants.KEY_SERVER + "' in the config file.");
            }
            if (StringUtil.isNullOrBlank(verifyUrl)) {
                throw new ConfigurationException("Missing '" + CaptchaConstants.VERIFY_URL + "' in the config file.");
            }
            LOGGER.info("Loaded configuration");
        } catch (FileSystemException fileSystemException) {
            throw new ConfigurationException(fileSystemException.getMessage(), fileSystemException);
        }
    }

    /* (non-Javadoc)
     * @see com.myschool.infra.captcha.agent.CaptchaAgent#validate()
     */
    @Override
    public void validate() throws AgentException {
        //LOGGER.info("validate");
    }

    /* (non-Javadoc)
     * @see com.myschool.infra.captcha.agent.CaptchaAgent#isValid(java.lang.String)
     */
    @Override
    public synchronized boolean isValid(String captchaResponse) {
        boolean valid = false;
        CaptchaVerificationResult result = null;
        LOGGER.info("Enter - " + captchaResponse);
        if (resultsCache.containsKey(captchaResponse)) {
            result = resultsCache.get(captchaResponse);
            LOGGER.info("CachedResponse - " + result);
        } else {
            result = sendPost(verifyUrl, serverKey, captchaResponse);
            if (result != null) {
                resultsCache.put(captchaResponse, result);
            }
        }
        valid = (result != null && result.isSuccess());
        LOGGER.info("Exit - " + valid);
        return valid;
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
            //LOGGER.info("request - " + captchaResponse);
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

            //int responseCode = con.getResponseCode();
            //LOGGER.info("responseCode - " + responseCode);

            bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = bufferedReader.readLine()) != null) {
                response.append(inputLine);
            }
            //print result
            LOGGER.info("GoogleResponse - " + response);
            result = CaptchaDataAssembler.create(response.toString());
        } catch (Exception exception) {
            LOGGER.error("Error - " + exception.getMessage(), exception);
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
