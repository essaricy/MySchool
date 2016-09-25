package com.myschool.infra.captcha.agent;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.MessageFormat;

import javax.net.ssl.HttpsURLConnection;

import org.springframework.stereotype.Component;

import com.myschool.captcha.assembler.CaptchaDataAssembler;
import com.myschool.captcha.dto.CaptchaVerificationResult;
import com.myschool.common.exception.AgentException;

/**
 * The Class GoogleNoCaptchaReCaptchaAgent.
 */
@Component
public class GoogleNoCaptchaReCaptchaAgent extends CaptchaAgent {

    private final String USER_AGENT = "Mozilla/5.0";

    // TODO: Load key from the configuration file
    private String secret = "6LeZRQcUAAAAAKEaTxRq15lE0MY1jshHcySsvbn3";

    /* (non-Javadoc)
     * @see com.myschool.infra.captcha.agent.CaptchaAgent#validate()
     */
    @Override
    public void validate() throws AgentException {
        // TODO load the config file.
    }

    /* (non-Javadoc)
     * @see com.myschool.infra.captcha.agent.CaptchaAgent#isValid(java.lang.String)
     */
    @Override
    public boolean isValid(String captchaResponse) {
        System.out.println("isValid called with " + captchaResponse);
        CaptchaVerificationResult result = sendPost(secret, captchaResponse);
        return (result != null && result.isSuccess());
    }

    private CaptchaVerificationResult sendPost(String secret, String captchaResponse) {
        CaptchaVerificationResult result = null;
        try {
            String url = "https://www.google.com/recaptcha/api/siteverify";
            URL obj = new URL(url);
            HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();

            System.out.println("Sending post request to " + url);

            //add request header
            con.setRequestMethod("POST");
            con.setRequestProperty("User-Agent", USER_AGENT);
            con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

            String urlParameters = "secret={0}&response={1}";
            String data = MessageFormat.format(urlParameters, secret, captchaResponse);

            // Send post request
            con.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
            wr.writeBytes(data);
            wr.flush();
            wr.close();

            int responseCode = con.getResponseCode();
            /*System.out.println("\nSending 'POST' request to URL : " + url);
            System.out.println("Post parameters : " + data);*/
            System.out.println("Response Code : " + responseCode);

            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            //print result
            System.out.println("response=" + response.toString());
            result = CaptchaDataAssembler.create(response.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
