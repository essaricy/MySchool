package com.myschool.web.application.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.myschool.user.constants.SecurityQuestion;
import com.myschool.user.dto.UserContext;
import com.myschool.web.application.constants.WebConstants;
import com.myschool.web.framework.controller.ViewDelegationController;
import com.myschool.web.framework.handler.ViewErrorHandler;
import com.myschool.web.user.constants.UserViewNames;

/**
 * The Class SettingsController.
 */
@Controller
@RequestMapping("settings")
public class SettingsController {

    /** The resource bundle util. */
    @Autowired
    private ViewErrorHandler viewErrorHandler;

    /**
     * Launch.
     * 
     * @param request the request
     * @param response the response
     * @return the model and view
     * @throws Exception the exception
     */
    @RequestMapping(value="launch")
    public ModelAndView launch(HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        HttpSession session = request.getSession();
        UserContext userContext = (UserContext) session.getAttribute(WebConstants.USER_CONTEXT);
        map.put(WebConstants.USER_CONTEXT, userContext);
        return ViewDelegationController.delegateWholePageView(request, UserViewNames.VIEW_SETTINGS, map);
    }

    /**
     * Launch forgot password.
     *
     * @param request the request
     * @param response the response
     * @return the model and view
     * @throws Exception the exception
     */
    @RequestMapping(value="launchForgotPassword")
    public ModelAndView launchForgotPassword(HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("securityQuestions", SecurityQuestion.values());
        return ViewDelegationController.delegateWholePageView(request, UserViewNames.FORGOT_PASSWORD, map);
    }

    /**
     * Reset password.
     *
     * @param request the request
     * @param response the response
     * @return the model and view
     * @throws Exception the exception
     */
    /*@RequestMapping(value="resetPassword")
    public ModelAndView resetPassword(HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        ResultDto result = new ResultDto();
        Map<String, Object> map = new HashMap<String, Object>();

        try {
            result.setSuccessful(ResultDto.FAILURE);
            map.put("securityQuestions", SecurityQuestion.values());
            ResetPasswordDto resetPassword = validateAndGetResetPassword(request);
            boolean passwordReset = settingsService.resetPassword(resetPassword);
            if (passwordReset) {
                result.setSuccessful(ResultDto.SUCCESS);
                result.setStatusMessage("Your Password has been reset successfully and an email has been sent to your email id.");
            } else {
                result.setStatusMessage("Problem occurred while resettin gyour password. Please contact support for assistance.");
            }
        } catch (DataException dataException) {
            result.setStatusMessage(dataException.getMessage());
        } catch (ServiceException serviceException) {
            result.setStatusMessage(serviceException.getMessage());
        }
        map.put("result", result);
        return ViewDelegationController.delegateWholePageView(request, UserViewNames.FORGOT_PASSWORD, map);
    }*/

    /**
     * Validate and get reset password.
     *
     * @param request the request
     * @return the reset password dto
     * @throws DataException the data exception
     *//*
    private ResetPasswordDto validateAndGetResetPassword(
            HttpServletRequest request) throws DataException {
        String loginName = request.getParameter("loginName");
        String securityQuestion = request.getParameter("securityQuestion");
        String securityAnswer = request.getParameter("securityAnswer");

        viewErrorHandler.validate(loginName, "loginName", DataTypeValidator.ANY_CHARACTER, true);
        viewErrorHandler.validate(securityQuestion, "securityQuestion", DataTypeValidator.ANY_CHARACTER, true);
        viewErrorHandler.validate(securityAnswer, "securityAnswer", DataTypeValidator.ANY_CHARACTER, true);

        ResetPasswordDto resetPassword = new ResetPasswordDto();
        resetPassword.setLoginName(loginName);
        resetPassword.setSecurityQuestion(securityQuestion);
        resetPassword.setSecurityAnswer(securityAnswer);
        return resetPassword;
    }*/

}
