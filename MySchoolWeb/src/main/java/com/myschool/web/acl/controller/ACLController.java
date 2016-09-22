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

import com.myschool.common.exception.ServiceException;
import com.myschool.common.util.Encryptor;
import com.myschool.common.util.StringUtil;
import com.myschool.employee.dto.EmployeeDto;
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

@Controller
@RequestMapping("acl")
public class ACLController {


    private static final Logger LOGGER = Logger.getLogger(ACLController.class);

    private static final String LOGIN_FAILED = "Username or password is invalid.";

    private static final String LOGOUT_SUCCESS_MESSAGE = "You are successfully logged out.";

    /** The login service. */
    @Autowired
    private LoginService loginService;

    @RequestMapping(value = "admin")
    public ModelAndView admin(HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        System.out.println("@@@@@@@@@@@@@@@@ acl/admin.htm");
        Map<String, Object> map = new HashMap<String, Object>();
        map.put(WebConstants.USER_TYPE, UserType.ADMIN);
        return ViewDelegationController.delegateWholePageView(request, ACLViewNames.USER_LOGIN, map);
    }

    @RequestMapping(value = "employee")
    public ModelAndView employee(HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        System.out.println("@@@@@@@@@@@@@@@@ acl/employee.htm");
        Map<String, Object> map = new HashMap<String, Object>();
        map.put(WebConstants.USER_TYPE, UserType.EMPLOYEE);
        return ViewDelegationController.delegateWholePageView(request, ACLViewNames.USER_LOGIN, map);
    }

    @RequestMapping(value = "student")
    public ModelAndView student(HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        System.out.println("@@@@@@@@@@@@@@@@ acl/student.htm");
        Map<String, Object> map = new HashMap<String, Object>();
        map.put(WebConstants.USER_TYPE, UserType.STUDENT);
        return ViewDelegationController.delegateWholePageView(request, ACLViewNames.USER_LOGIN, map);
    }

    @RequestMapping(value = "forgotPassword")
    public ModelAndView forgotPassword(HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        System.out.println("@@@@@@@@@@@@@@@@ acl/forgotPassword.htm");
        Map<String, Object> map = new HashMap<String, Object>();
        map.put(WebConstants.USER_TYPE, UserType.STUDENT);
        return ViewDelegationController.delegateWholePageView(request, ACLViewNames.FORGOT_PASSWORD, map);
    }

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

    @RequestMapping(value = "signin")
    public ModelAndView in(HttpServletRequest request,
            HttpServletResponse response) {
        System.out.println("/acl/signin.htm reached");
        Map<String, Object> map = new HashMap<String, Object>();
        ModelAndView modelAndView = null;
        UserContext context = null;

        boolean loginSuccessful = false;
        String userTypeValue = null;
        String loginId = null;
        String password;
        String sessionId = null;
        String message = null;
        UserType userType = null;
        try {
            HttpSession session = HttpUtil.getExistingSession(request);
            sessionId = session.getId();

            userTypeValue = request.getParameter("UserType");
            loginId = request.getParameter("LoginId");
            password = request.getParameter("Password");
            LOGGER.info(sessionId + " trying to use Login ID " + loginId);

            userType = UserType.get(userTypeValue);
            System.out.println("userType=" + userType);
            if (userType == null) {
                message = LOGIN_FAILED;
            } else if (StringUtil.isNullOrBlank(loginId)) {
                message = LOGIN_FAILED;
            } else if (StringUtil.isNullOrBlank(password)) {
                message = LOGIN_FAILED;
            } else {
                LoginDto login = new LoginDto();
                login.setLoginId(loginId);
                login.setPassword(Encryptor.getInstance().encrypt(password));
                login.setUserType(userType);
                LoginDto loginDetails = loginService.login(login);
                if (loginDetails == null) {
                    LOGGER.info(MessageFormat.format(UserActivityConstant.USER_LOGIN_NOT_IN_SYSTEM, sessionId, loginId));
                    message = LOGIN_FAILED;
                } else {
                    LOGGER.info(MessageFormat.format(
                            UserActivityConstant.USER_LOGIN_SUCCESS, sessionId,
                            loginId, userType));
                    context = ContextUtil.createUserContext(loginDetails);
                    if (userType == UserType.STUDENT) {
                        StudentDto student = (StudentDto) loginDetails.getUserDetails();
                        session.setAttribute(WebConstants.STUDENT, student);
                        map.put(WebConstants.STUDENT, student);
                    } else if (userType == UserType.EMPLOYEE) {
                        EmployeeDto employee = (EmployeeDto) loginDetails.getUserDetails();
                        session.setAttribute(WebConstants.EMPLOYEE, employee);
                        map.put(WebConstants.EMPLOYEE, employee);
                    }
                    session.setAttribute(WebConstants.USER_CONTEXT, context);
                    map.put(WebConstants.USER_CONTEXT, context);

                    loginSuccessful = true;
                }
            }
        } catch (ServiceException serviceException) {
            message = serviceException.getMessage();
            LOGGER.info(MessageFormat.format(UserActivityConstant.USER_LOGIN_FAILED, sessionId, loginId, message));
        }
        map.put(WebConstants.USER_TYPE, userType);
        if (message != null) {
            System.out.println("Login message = " + message);
            map.put(WebConstants.MESSAGE, message);
        }
        System.out.println("loginSuccessful? " + loginSuccessful);
        if (loginSuccessful) {
            // Return to dash board screen
            modelAndView = ViewDelegationController.delegateWholePageView(
                    request, ApplicationViewNames.DASH_BOARD, map);
        } else if (userType == null) {
            System.out.println("Ideally should not come here. but sending to public dashboard");
            modelAndView = ViewDelegationController.delegateWholePageView(
                    request, WebConstants.PUBLIC_DASHBOARD, map);
        } else {
            System.out.println("sending back to " + userType + " login screen.");
            modelAndView = ViewDelegationController.delegateWholePageView(request, ACLViewNames.USER_LOGIN, map);
        }
        return modelAndView;
    }

    /**
     * Logout.
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
