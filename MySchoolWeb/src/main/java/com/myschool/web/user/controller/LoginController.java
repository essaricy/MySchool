package com.myschool.web.user.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.myschool.application.dto.MySchoolProfileDto;
import com.myschool.application.dto.OrganizationProfileDto;
import com.myschool.application.service.ImageService;
import com.myschool.application.service.ProfileService;
import com.myschool.common.util.Encryptor;
import com.myschool.common.util.MessageUtil;
import com.myschool.employee.dto.EmployeeDto;
import com.myschool.student.dto.StudentDto;
import com.myschool.student.service.StudentService;
import com.myschool.user.constants.UserType;
import com.myschool.user.dto.LoginDto;
import com.myschool.user.dto.UserContext;
import com.myschool.user.service.LoginService;
import com.myschool.user.util.ContextUtil;
import com.myschool.web.application.constants.ApplicationViewNames;
import com.myschool.web.application.constants.WebConstants;
import com.myschool.web.common.util.ViewDelegationController;
import com.myschool.web.common.util.ViewErrorHandler;

/**
 * The Class LoginController.
 */
@Controller
public class LoginController {

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
        HttpSession session = request.getSession();
        OrganizationProfileDto organizationProfile = profileService.getOrganizationProfile();
        MySchoolProfileDto mySchoolProfile = profileService.getMySchoolProfile();
        session.setAttribute(WebConstants.ORGANIZATION_PROFILE, organizationProfile);
        session.setAttribute(WebConstants.MYSCHOOL_PROFILE, mySchoolProfile);
        map.put(WebConstants.MYSCHOOL_PROFILE, mySchoolProfile);
        map.put(WebConstants.ORGANIZATION_PROFILE, organizationProfile);
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
            HttpServletResponse response) throws Exception {

        Map<String, Object> map = new HashMap<String, Object>();
        ModelAndView modelAndView = null;
        UserContext context = null;

        String loginId;
        String password;
        try {
            loginId = request.getParameter("loginId");
            password = request.getParameter("password");

            LoginDto login = new LoginDto();
            login.setLoginId(loginId);
            login.setPassword(Encryptor.getInstance().encrypt(password));
            LoginDto loginDetails = loginService.login(login);

            HttpSession session = request.getSession();
            UserType userType = loginDetails.getUserType();
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
            MySchoolProfileDto mySchoolProfile = profileService.getMySchoolProfile();
            session.setAttribute(WebConstants.USER_CONTEXT, context);
            session.setAttribute(WebConstants.MYSCHOOL_PROFILE, mySchoolProfile);
            map.put(WebConstants.USER_CONTEXT, context);
            map.put(WebConstants.MYSCHOOL_PROFILE, mySchoolProfile);
            modelAndView = ViewDelegationController.delegateWholePageView(request, ApplicationViewNames.DASH_BOARD, map);
        } catch (Exception exception) {
            map.put("features", imageService.getFeatures());
            map.put(ViewDelegationController.ERROR_KEY, exception.getMessage());
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
        HttpSession session = request.getSession();
        if (session != null) {
            session.invalidate();
        }
        request.setAttribute(ViewDelegationController.ERROR_KEY, messageUtil.getMessage("logout.done"));
        return launchLogin(request, response);
    }

}