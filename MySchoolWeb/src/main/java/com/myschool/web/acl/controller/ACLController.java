package com.myschool.web.acl.controller;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.myschool.acl.constant.SigninSecurityLevel;
import com.myschool.acl.dto.SigninSecurity;
import com.myschool.common.exception.InsufficientInputException;
import com.myschool.common.exception.ServiceException;
import com.myschool.common.util.Encryptor;
import com.myschool.common.util.StringUtil;
import com.myschool.employee.dto.EmployeeDto;
import com.myschool.infra.captcha.agent.CaptchaAgent;
import com.myschool.student.dto.StudentDto;
import com.myschool.user.constants.UserActivityConstant;
import com.myschool.user.constants.UserType;
import com.myschool.user.dto.LoginDto;
import com.myschool.user.dto.UserContext;
import com.myschool.user.service.LoginService;
import com.myschool.user.util.ContextUtil;
import com.myschool.web.acl.constants.ACLViewNames;
import com.myschool.web.application.constants.ApplicationViewNames;
import com.myschool.web.application.constants.WebConstants;
import com.myschool.web.framework.controller.ViewDelegationController;
import com.myschool.web.framework.util.HttpUtil;

/**
 * The Class ACLController.
 */
@Controller
@RequestMapping("acl")
public class ACLController {


    /** The Constant LOGGER. */
    private static final Logger LOGGER = Logger.getLogger(ACLController.class);

    /** The Constant LOGIN_FAILED. */
    private static final String LOGIN_FAILED = "Username or password is invalid.";

    /** The Constant LOGOUT_SUCCESS_MESSAGE. */
    private static final String LOGOUT_SUCCESS_MESSAGE = "You are successfully logged out.";

    /** The login service. */
    @Autowired
    private LoginService loginService;

    /** The captcha agent. */
    @Autowired
    private CaptchaAgent captchaAgent;

    /**
     * Admin.
     *
     * @param request the request
     * @param response the response
     * @return the model and view
     * @throws Exception the exception
     */
    @RequestMapping(value = "admin")
    public ModelAndView admin(HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        System.out.println("@@@@@@@@@@@@@@@@ acl/admin.htm");
        Map<String, Object> map = new HashMap<String, Object>();
        map.put(WebConstants.USER_TYPE, UserType.ADMIN);
        return ViewDelegationController.delegateWholePageView(request, ACLViewNames.USER_LOGIN, map);
    }

    /**
     * Employee.
     *
     * @param request the request
     * @param response the response
     * @return the model and view
     * @throws Exception the exception
     */
    @RequestMapping(value = "employee")
    public ModelAndView employee(HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        System.out.println("@@@@@@@@@@@@@@@@ acl/employee.htm");
        Map<String, Object> map = new HashMap<String, Object>();
        map.put(WebConstants.USER_TYPE, UserType.EMPLOYEE);
        return ViewDelegationController.delegateWholePageView(request, ACLViewNames.USER_LOGIN, map);
    }

    /**
     * Student.
     *
     * @param request the request
     * @param response the response
     * @return the model and view
     * @throws Exception the exception
     */
    @RequestMapping(value = "student")
    public ModelAndView student(HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        System.out.println("@@@@@@@@@@@@@@@@ acl/student.htm");
        Map<String, Object> map = new HashMap<String, Object>();
        map.put(WebConstants.USER_TYPE, UserType.STUDENT);
        return ViewDelegationController.delegateWholePageView(request, ACLViewNames.USER_LOGIN, map);
    }

    /**
     * Forgot password.
     *
     * @param request the request
     * @param response the response
     * @return the model and view
     * @throws Exception the exception
     */
    @RequestMapping(value = "forgotPassword")
    public ModelAndView forgotPassword(HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        System.out.println("@@@@@@@@@@@@@@@@ acl/forgotPassword.htm");
        Map<String, Object> map = new HashMap<String, Object>();
        map.put(WebConstants.USER_TYPE, UserType.STUDENT);
        return ViewDelegationController.delegateWholePageView(request, ACLViewNames.FORGOT_PASSWORD, map);
    }

    /**
     * Assistance.
     *
     * @param request the request
     * @param response the response
     * @return the model and view
     * @throws Exception the exception
     */
    @RequestMapping(value = "assistance")
    public ModelAndView assistance(HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        System.out.println("@@@@@@@@@@@@@@@@ acl/assistance.htm");
        String userTypeValue = request.getParameter(WebConstants.USER_TYPE);
        System.out.println("userTypeValue=" + userTypeValue);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put(WebConstants.USER_TYPE, UserType.get(userTypeValue));
        return ViewDelegationController.delegateWholePageView(request, ACLViewNames.ASSISTANCE, map);
    }

    /**
     * Signin.
     *
     * @param request the request
     * @param response the response
     * @return the model and view
     */
    @RequestMapping(value = "signin")
    public ModelAndView signin(HttpServletRequest request,
            HttpServletResponse response) {
        System.out.println("/acl/signin.htm reached");

        String loginId = null;
        String sessionId = null;
        Map<String, Object> map = new HashMap<String, Object>();

        ModelAndView modelAndView = null;
        UserType userType = null;
        HttpSession session = null;

        try {
            session = HttpUtil.getExistingSession(request);
            sessionId = session.getId();

            loginId = request.getParameter("LoginId");
            String userTypeValue = request.getParameter(WebConstants.USER_TYPE);
            String password = request.getParameter("Password");
            String captchaResponse = request.getParameter(WebConstants.CAPTCHA_RESPONSE);
            LOGGER.info(sessionId + " trying to use Login ID " + loginId);
            System.out.println("captchaResponse=" + captchaResponse);

            // Check if all fields have data
            userType = UserType.get(userTypeValue);
            System.out.println("userType=" + userType);
            if (userType == null || StringUtil.isNullOrBlank(loginId) || StringUtil.isNullOrBlank(password)) {
                throw new InsufficientInputException(LOGIN_FAILED);
            }

            // Validate CAPTHCA, if used
            SigninSecurity signinSecurity = (SigninSecurity) session.getAttribute(WebConstants.SIGNIN_SECURITY);
            SigninSecurityLevel currentSecurityLevel = signinSecurity.getCurrentSecurityLevel();
            System.out.println("signinSecurity=" + signinSecurity);
            if (currentSecurityLevel == SigninSecurityLevel.USE_CAPTCHA) {
                if (StringUtil.isNullOrBlank(captchaResponse)
                        || !captchaAgent.isValid(captchaResponse)) {
                    throw new InsufficientInputException("You are required to answer to CAPTCHA.");
                }
            }

            // Check if this user account is present in the system.
            LoginDto login = new LoginDto();
            login.setLoginId(loginId);
            login.setPassword(Encryptor.getInstance().encrypt(password));
            login.setUserType(userType);
            LoginDto loginDetails = loginService.login(login);
            if (loginDetails == null) {
                throw new ServiceException(LOGIN_FAILED);
            }
            // User account is present in our system
            System.out.println("loginSuccessful");
            LOGGER.info(MessageFormat.format(UserActivityConstant.SIGNUP_SUCCESS, sessionId, loginId, userType));
            UserContext context = ContextUtil.createUserContext(loginDetails);
            if (userType == UserType.STUDENT) {
                StudentDto student = (StudentDto) loginDetails.getUserDetails();
                session.setAttribute(WebConstants.STUDENT, student);
                map.put(WebConstants.STUDENT, student);
            } else if (userType == UserType.EMPLOYEE) {
                EmployeeDto employee = (EmployeeDto) loginDetails.getUserDetails();
                session.setAttribute(WebConstants.EMPLOYEE, employee);
                map.put(WebConstants.EMPLOYEE, employee);
            }
            map.put(WebConstants.USER_CONTEXT, context);
            session.setAttribute(WebConstants.USER_CONTEXT, context);

            trackLoginAttempt(session, true);
            // Send the user to dashboard page.
            modelAndView = ViewDelegationController.delegateWholePageView(request, ApplicationViewNames.DASH_BOARD, map);
        } catch (InsufficientInputException insufficientInputException) {
            String message = insufficientInputException.getMessage();
            map.put(WebConstants.MESSAGE, message);
            map.put(WebConstants.USER_TYPE, userType);

            LOGGER.info(MessageFormat.format(UserActivityConstant.SIGNUP_FAILED, sessionId, loginId, message));
            modelAndView = ViewDelegationController.delegateWholePageView(request, ACLViewNames.USER_LOGIN, map);
        } catch (ServiceException serviceException) {
            String message = serviceException.getMessage();

            trackLoginAttempt(session, false);
            map.put(WebConstants.MESSAGE, message);
            map.put(WebConstants.USER_TYPE, userType);

            LOGGER.info(MessageFormat.format(UserActivityConstant.SIGNUP_FAILED, sessionId, loginId, message));
            modelAndView = ViewDelegationController.delegateWholePageView(request, ACLViewNames.USER_LOGIN, map);
        }
        return modelAndView;
    }

    /**
     * Track login attempt.
     *
     * @param session the session
     * @param success the success
     */
    private void trackLoginAttempt(HttpSession session, boolean success) {
        System.out.println("trackLoginAttempt, success=" + success);
        int numberOfFailedAttempts = 0;
        SigninSecurity signinSecurity = (SigninSecurity) session.getAttribute(WebConstants.SIGNIN_SECURITY);
        if (success) {
            signinSecurity.setCurrentSecurityLevel(SigninSecurityLevel.CREDENTIALS);
        } else {
            numberOfFailedAttempts = signinSecurity.getNumberOfFailedAttempts();
            ++numberOfFailedAttempts;
            if (numberOfFailedAttempts >= 2) {
                signinSecurity.setCurrentSecurityLevel(SigninSecurityLevel.USE_CAPTCHA);
            }
            signinSecurity.setLastFailedAttempt(System.currentTimeMillis());
        }
        signinSecurity.setNumberOfFailedAttempts(numberOfFailedAttempts);
        //session.getAttribute(WebConstants.SIGNIN_SECURITY)
        System.out.println("signinSecurity=" + signinSecurity);
    }

    /**
     * Signout.
     *
     * @param request the request
     * @param response the response
     * @return the model and view
     * @throws Exception the exception
     */
    @RequestMapping(value = "signout")
    public ModelAndView signout(HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        System.out.println("/acl/signout.htm reached");

        String loginId = null;
        String sessionId = null;
        UserType userType = null;

        ModelAndView modelAndView = null;
        Map<String, Object> map = new HashMap<String, Object>();
        HttpSession session = HttpUtil.getExistingSession(request);
        if (session != null) {
            sessionId = session.getId();
            UserContext userContext = (UserContext) session.getAttribute(WebConstants.USER_CONTEXT);
            if (userContext != null) {
                loginId = userContext.getLogin().getLoginId();
                userType = userContext.getUserType();

            }
            session.removeAttribute(WebConstants.USER_CONTEXT);
            session.invalidate();
        }

        map.put(WebConstants.USER_TYPE, userType);
        map.put(WebConstants.MESSAGE, LOGOUT_SUCCESS_MESSAGE);
        System.out.println("Logout message = " + LOGOUT_SUCCESS_MESSAGE);
        LOGGER.info(MessageFormat.format(UserActivityConstant.USER_LOGOUT_SUCCESS, sessionId, loginId));

        if (userType == null) {
            System.out.println("Ideally should not come here. but sending to public dashboard");
            modelAndView = ViewDelegationController.delegateWholePageView(
                    request, WebConstants.PUBLIC_DASHBOARD, map);
        } else {
            System.out.println("sending back to " + userType + " login screen.");
            modelAndView = ViewDelegationController.delegateWholePageView(request, ACLViewNames.USER_LOGIN, map);
        }
        return modelAndView;
    }

}
