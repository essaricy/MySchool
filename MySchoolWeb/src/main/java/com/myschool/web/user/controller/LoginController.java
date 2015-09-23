package com.myschool.web.user.controller;

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

import com.myschool.application.service.ImageService;
import com.myschool.application.service.ProfileService;
import com.myschool.common.exception.ServiceException;
import com.myschool.common.util.Encryptor;
import com.myschool.common.util.MessageUtil;
import com.myschool.employee.dto.EmployeeDto;
import com.myschool.student.dto.StudentDto;
import com.myschool.student.service.StudentService;
import com.myschool.user.constants.UserActivityConstant;
import com.myschool.user.constants.UserType;
import com.myschool.user.dto.LoginDto;
import com.myschool.user.dto.UserContext;
import com.myschool.user.service.LoginService;
import com.myschool.user.service.UserService;
import com.myschool.user.util.ContextUtil;
import com.myschool.web.application.constants.ApplicationViewNames;
import com.myschool.web.application.constants.WebConstants;
import com.myschool.web.framework.controller.ViewDelegationController;
import com.myschool.web.framework.handler.ViewErrorHandler;
import com.myschool.web.framework.util.HttpUtil;

/**
 * The Class LoginController.
 */
@Controller
public class LoginController {

	/** The Constant LOGGER. */
	private static final Logger LOGGER = Logger.getLogger(LoginController.class);

	@Autowired
	private UserService userService;

    /** The login service. */
    @Autowired
    private LoginService loginService;

    /** The image service. */
    @Autowired
    private ImageService imageService;

    /** The student service. */
    @Autowired
    private StudentService studentService;

    /** The view error handler. */
    @Autowired
    private ViewErrorHandler viewErrorHandler;

    /** The message util. */
    @Autowired
    private MessageUtil messageUtil;

    @Autowired
    private ProfileService profileService;

    /**
     * Launch login.
     *
     * @param request the request
     * @param response the response
     * @return the model and view
     * @throws Exception the exception
     */
    @RequestMapping(value="launchLogin")
    public ModelAndView launchLogin(HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("features", imageService.getFeatures());
        String errorKey = (String) request.getAttribute(ViewDelegationController.ERROR_KEY);
        if (errorKey != null) {
            map.put(ViewDelegationController.ERROR_KEY, errorKey);
        }
        return ViewDelegationController.delegateWholePageView(request, ApplicationViewNames.LOGIN, map);
    }

    /**
     * Login.
     *
     * @param request the request
     * @param response the response
     * @return the model and view
     * @throws Exception the exception
     */
    @RequestMapping(value="login")
    public ModelAndView login(HttpServletRequest request,
            HttpServletResponse response) {

        Map<String, Object> map = new HashMap<String, Object>();
        ModelAndView modelAndView = null;
        UserContext context = null;

        String loginId = null;
        String password;
        String sessionId = null;
		try {
			HttpSession session = HttpUtil.getExistingSession(request);
			sessionId = session.getId();
			loginId = request.getParameter("loginId");
			password = request.getParameter("password");
			LOGGER.info(sessionId + " trying to use Login ID " + loginId);
			
			LoginDto login = new LoginDto();
			login.setLoginId(loginId);
			login.setPassword(Encryptor.getInstance().encrypt(password));
			LoginDto loginDetails = loginService.login(login);
			if (loginDetails == null) {
				LOGGER.info(MessageFormat.format(UserActivityConstant.USER_LOGIN_NOT_IN_SYSTEM, sessionId, loginId));
				map.put("features", imageService.getFeatures());
	            map.put(ViewDelegationController.ERROR_KEY, "User does not exist in the system");
	            modelAndView = ViewDelegationController.delegateWholePageView(request, ApplicationViewNames.LOGIN, map);
			} else {
				UserType userType = loginDetails.getUserType();
				LOGGER.info(MessageFormat.format(UserActivityConstant.USER_LOGIN_SUCCESS, sessionId, loginId, userType));
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
	        	// Return to dash board screen
	            modelAndView = ViewDelegationController.delegateWholePageView(request, ApplicationViewNames.DASH_BOARD, map);
			}
		} catch (ServiceException serviceException) {
			String message = serviceException.getMessage();
			LOGGER.info(MessageFormat.format(UserActivityConstant.USER_LOGIN_FAILED, sessionId, loginId, message));
			map.put("features", imageService.getFeatures());
            map.put(ViewDelegationController.ERROR_KEY, message);
            modelAndView = ViewDelegationController.delegateWholePageView(request, ApplicationViewNames.LOGIN, map);
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
    @RequestMapping(value="logout")
    public ModelAndView logout(HttpServletRequest request,
            HttpServletResponse response) throws Exception {
    	String loginId = null;
    	String sessionId = null;
        HttpSession session = HttpUtil.getExistingSession(request);
        if (session != null) {
        	sessionId = session.getId();
        	UserContext userContext = (UserContext) session.getAttribute(WebConstants.USER_CONTEXT);
        	if (userContext != null) {
        		loginId = userContext.getLogin().getLoginId();
        	}
        	session.removeAttribute(WebConstants.USER_CONTEXT);
            session.invalidate();
        }
        LOGGER.info(MessageFormat.format(UserActivityConstant.USER_LOGOUT_SUCCESS, sessionId, loginId));
        request.setAttribute(ViewDelegationController.ERROR_KEY, messageUtil.getMessage("logout.done"));
        return launchLogin(request, response);
    }

}
