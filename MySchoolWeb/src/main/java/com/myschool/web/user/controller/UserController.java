package com.myschool.web.user.controller;

import java.text.ParseException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.myschool.common.dto.ResultDto;
import com.myschool.common.exception.DataException;
import com.myschool.common.exception.InvalidDataException;
import com.myschool.common.exception.ServiceException;
import com.myschool.common.util.StringUtil;
import com.myschool.common.validator.DataTypeValidator;
import com.myschool.user.assembler.UserDataAssembler;
import com.myschool.user.constants.UserTheme;
import com.myschool.user.dto.ChangePasswordDto;
import com.myschool.user.dto.UserContext;
import com.myschool.user.dto.UserPreference;
import com.myschool.user.dto.UsersDto;
import com.myschool.user.service.UserService;
import com.myschool.user.service.UserTypeService;
import com.myschool.web.application.constants.ApplicationViewNames;
import com.myschool.web.application.constants.WebConstants;
import com.myschool.web.common.util.HttpUtil;
import com.myschool.web.common.util.ViewDelegationController;
import com.myschool.web.common.util.ViewErrorHandler;

/**
 * The Class UserController.
 */
@Controller
@RequestMapping("user")
public class UserController {

    /** The user service. */
    @Autowired
    private UserService userService;

    /** The user type service. */
    @Autowired
    private UserTypeService userTypeService;

    /** The view error handler. */
    @Autowired
    private ViewErrorHandler viewErrorHandler;

    /**
     * View dashboard.
     *
     * @param request the request
     * @param response the response
     * @return the model and view
     * @throws Exception the exception
     */
    @RequestMapping(value="viewDashboard")
    public ModelAndView viewDashboard(HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        return ViewDelegationController.delegateWholePageView(request, ApplicationViewNames.DASH_BOARD);
    }

    /**
     * Users by type.
     * 
     * @param request the request
     * @param response the response
     * @return the model and view
     * @throws Exception the exception
     */
    @RequestMapping(value="usersByType")
    public ModelAndView usersByType(HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        JSONArray data = null;
        try {
            String userTypeId = request.getParameter("UserTypeID");
            System.out.println("userTypeId " + userTypeId);
            if (!StringUtil.isNullOrBlank(userTypeId)) {
                // add users list by user type id
                List<UsersDto> users = userTypeService.getUsers(Integer.parseInt(userTypeId));
                data = UserDataAssembler.create(users);
            }
        }  finally {
            System.out.println("data " + data);
            HttpUtil.wrapAndWriteJson(response, "Users", data);
        }
        return null;
    }

    /**
     * Do update.
     *
     * @param request the request
     * @param response the response
     * @return the model and view
     * @throws Exception the exception
     */
    @RequestMapping(value="changePassword")
    public ModelAndView changePassword(HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        ResultDto result = new ResultDto();
        try {
            ChangePasswordDto changePassword = validateAndGetChangePassword(request);
            result.setSuccessful(userService.changePassword(changePassword));
            result.setStatusMessage("Your password has been changed successfully.");
        } catch (DataException dataException) {
            result.setStatusMessage(viewErrorHandler.getMessage(dataException.getMessage()));
        } catch (ServiceException serviceException) {
            result.setStatusMessage(serviceException.getMessage());
        } finally {
            HttpUtil.writeAsJson(response, result);
        }
        return null;
    }

    /**
     * Change preferences.
     *
     * @param request the request
     * @param response the response
     * @return the model and view
     * @throws Exception the exception
     */
    @RequestMapping(value="changePreferences")
    public ModelAndView changePreferences(HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        ResultDto result = new ResultDto();
        try {
            UserPreference userPreference = validateAndGetUserPreference(request);
            result.setSuccessful(userService.changePreferences(userPreference));
            result.setStatusMessage("Your preferences has been changed successfully.");
            // Update User preferences in the session object.
            UserContext userContext = (UserContext) request.getSession().getAttribute(WebConstants.USER_CONTEXT);
            if (userContext != null) {
                userContext.setUserPreference(userPreference);
            }
        } catch (DataException dataException) {
            result.setStatusMessage(viewErrorHandler.getMessage(dataException.getMessage()));
        } catch (ServiceException serviceException) {
            result.setStatusMessage(serviceException.getMessage());
        } finally {
            HttpUtil.writeAsJson(response, result);
        }
        return null;
    }

    /**
     * Validate and get user preference.
     *
     * @param request the request
     * @return the user preference
     * @throws DataException the data exception
     */
    private UserPreference validateAndGetUserPreference(
            HttpServletRequest request) throws DataException {
        UserPreference userPreference = new UserPreference();

        try {
            String changePreferenceDetailsValue = request.getParameter("ChangePreferenceDetails");
            if (!StringUtil.isNullOrBlank(changePreferenceDetailsValue)) {
                JSONObject jsonObject = new JSONObject(changePreferenceDetailsValue);
                userPreference = UserDataAssembler.createUserPreference(jsonObject);
            }
            UserTheme userTheme = userPreference.getUserTheme();
            if (userTheme == null) {
                throw new DataException("Theme Name is mandatory.");
            }
            viewErrorHandler.validate(String.valueOf(userPreference.getUserId()), "userId", DataTypeValidator.INTEGER, true);
            viewErrorHandler.validate(userTheme.toString(), "themeName", DataTypeValidator.ANY_CHARACTER, true);
            viewErrorHandler.validate(String.valueOf(userPreference.getRecordsPerPage()), "recordsPerPage", DataTypeValidator.INTEGER, true);
            viewErrorHandler.validate(String.valueOf(userPreference.isAllowAds()), "allowAds", DataTypeValidator.TRUE_FALSE, true);
        } catch (ParseException parseException) {
            throw new InvalidDataException(parseException);
        }
        return userPreference;
    }

    /**
     * Validate and get change password.
     *
     * @param request the request
     * @return the change password dto
     * @throws DataException the data exception
     */
    private ChangePasswordDto validateAndGetChangePassword(HttpServletRequest request) throws DataException {
        ChangePasswordDto changePassword = new ChangePasswordDto();

        try {
            String changePasswordDetailsValue = request.getParameter("ChangePasswordDetails");
            if (!StringUtil.isNullOrBlank(changePasswordDetailsValue)) {
                JSONObject jsonObject = new JSONObject(changePasswordDetailsValue);
                changePassword = UserDataAssembler.createChangePassword(jsonObject);
            }
            viewErrorHandler.validate(String.valueOf(changePassword.getUserId()), "userId", DataTypeValidator.INTEGER, true);
            viewErrorHandler.validate(changePassword.getCurrentPassword(), "currentPassword", DataTypeValidator.ANY_CHARACTER, true);
            viewErrorHandler.validate(changePassword.getNewPassword(), "newPassword", DataTypeValidator.ANY_CHARACTER, true);
            viewErrorHandler.validate(changePassword.getConfirmedPassword(), "confirmedPassword", DataTypeValidator.ANY_CHARACTER, true);
        } catch (ParseException parseException) {
            throw new InvalidDataException(parseException);
        }
        return changePassword;
    }

}
