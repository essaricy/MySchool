package com.myschool.captcha.assembler;

import java.text.ParseException;

import org.json.JSONObject;

import com.myschool.captcha.dto.CaptchaVerificationResult;

/**
 * The Class CaptchaDataAssembler.
 */
public class CaptchaDataAssembler {

    /**
     * Creates the.
     *
     * @param result the result
     * @return the captcha verification result
     * @throws ParseException the parse exception
     */
    public static CaptchaVerificationResult create(String result) throws ParseException {
        CaptchaVerificationResult captchaVerificationResult = null;
        JSONObject captchaResultJson = new JSONObject(result);
        if (captchaResultJson != null) {
            captchaVerificationResult = new CaptchaVerificationResult();
            captchaVerificationResult.setSuccess(captchaResultJson.getBoolean("success"));
            captchaVerificationResult.setTimestamp(captchaResultJson.getString("challenge_ts"));
        }
        System.out.println("captchaVerificationResult=" + captchaVerificationResult);
        return captchaVerificationResult;
    }

}
