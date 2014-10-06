package com.myschool.web.common.util;

import javax.servlet.http.HttpServletRequest;

import com.myschool.common.exception.ServiceException;
import com.myschool.common.util.StringUtil;
import com.octo.captcha.module.servlet.image.SimpleImageCaptchaServlet;

/**
 * The Class JCaptchaUtil.
 */
public class JCaptchaUtil {

    /**
     * Validate captcha.
     * 
     * @param request the request
     * @param userCaptchaResponse the user captcha response
     * @throws ServiceException the service exception
     */
    public static void validateCaptcha(HttpServletRequest request,
            String userCaptchaResponse) throws ServiceException {
        if (StringUtil.isNullOrBlank(userCaptchaResponse)) {
            throw new ServiceException("Captcha Image - Please prove that you are not a Robot");
        }
        boolean captchaPassed = SimpleImageCaptchaServlet.validateResponse(
                request, userCaptchaResponse);
        if (!captchaPassed) {
            // return error to user
            throw new ServiceException("You have entered an invalid value for the captcha");
        }
    }

}
