package com.myschool.web.application.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.myschool.common.dto.ResultDto;
import com.myschool.common.exception.ServiceException;
import com.myschool.common.util.CollectionUtil;
import com.myschool.common.util.StringUtil;
import com.myschool.user.assembler.PrivilegesDataAssembler;
import com.myschool.user.constants.UserType;
import com.myschool.user.dto.ModuleAccessDto;
import com.myschool.user.dto.UserContext;
import com.myschool.user.dto.UsersDto;
import com.myschool.user.service.PrivilegesService;
import com.myschool.user.service.UserService;
import com.myschool.user.service.UserTypeService;
import com.myschool.web.application.constants.WebConstants;
import com.myschool.web.framework.controller.ViewDelegationController;
import com.myschool.web.framework.util.HttpUtil;
import com.myschool.web.user.constants.UserViewNames;

/**
 * The Class PrivilegesController.
 */
@Controller
@RequestMapping("privileges")
public class PrivilegesController {

    /** The privileges service. */
    @Autowired
    private PrivilegesService privilegesService;

    /** The user type service. */
    @Autowired
    private UserTypeService userTypeService;

    /** The user service. */
    @Autowired
    private UserService userService;

    /**
     * Default privileges.
     * 
     * @param request the request
     * @param response the response
     * @return the model and view
     * @throws Exception the exception
     */
    @RequestMapping(value="defaultPrivileges")
    public ModelAndView defaultPrivileges(HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("UserTypes", userTypeService.getAll());
        String userTypeIdString = request.getParameter("UserTypeID");
        if (!StringUtil.isNullOrBlank(userTypeIdString)) {
            List<ModuleAccessDto> defaultPrivileges = privilegesService.getDefaultPrivileges(Integer.parseInt(userTypeIdString));
            map.put("Privileges", defaultPrivileges);
            map.put("UserTypeID", userTypeIdString);
            if (defaultPrivileges != null) {
                map.put("numberOfModules", defaultPrivileges.size());
            }
        }
        return ViewDelegationController.delegateWholePageView(request, UserViewNames.DEFAULT_PRIVILEGES, map);
    }

    /**
     * Json default privileges.
     * 
     * @param request the request
     * @param response the response
     * @return the model and view
     * @throws Exception the exception
     */
    @RequestMapping(value="jsonDefaultPrivileges")
    public ModelAndView jsonDefaultPrivileges(HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        JSONObject privileges = new JSONObject();
        try {
            String userTypeIdString = request.getParameter("UserTypeID");
            if (!StringUtil.isNullOrBlank(userTypeIdString)) {
                List<ModuleAccessDto> defaultPrivileges = privilegesService.getDefaultPrivileges(Integer.parseInt(userTypeIdString));
                privileges.put("Modules", PrivilegesDataAssembler.create(defaultPrivileges));
            }
        } finally {
            HttpUtil.wrapAndWriteJson(response, "Privileges", privileges);
        }
        return null;
    }

    /**
     * User privileges.
     * 
     * @param request the request
     * @param response the response
     * @return the model and view
     * @throws Exception the exception
     */
    @RequestMapping(value="userPrivileges")
    public ModelAndView userPrivileges(HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("UserTypes", userTypeService.getAll());

        String userTypeId = request.getParameter("UserTypeID");
        if (!StringUtil.isNullOrBlank(userTypeId)) {
            // add users list by user type id
            List<UsersDto> users = userTypeService.getUsers(Integer.parseInt(userTypeId));
            map.put("UserTypeID", userTypeId);
            map.put("Users", users);

            String userId = request.getParameter("UserID");
            if (!StringUtil.isEmpty(userId) && !StringUtil.isEmpty(userTypeId)) {
                List<ModuleAccessDto> userPrivileges = privilegesService.getUserPrivileges(
                        Integer.parseInt(userId), Integer.parseInt(userTypeId));
                map.put("UserID", userId);
                map.put("Privileges", userPrivileges);
            }
        }
        return ViewDelegationController.delegateWholePageView(request, UserViewNames.USER_PRIVILEGES, map);
    }

    /**
     * Json user privileges.
     * 
     * @param request the request
     * @param response the response
     * @return the model and view
     * @throws Exception the exception
     */
    @RequestMapping(value="jsonUserPrivileges")
    public ModelAndView jsonUserPrivileges(HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        JSONObject privileges = new JSONObject();
        try {
            String userIdString = request.getParameter("UserID");
            String userTypeIdString = request.getParameter("UserTypeID");
            if (!StringUtil.isNullOrBlank(userIdString) && !StringUtil.isNullOrBlank(userTypeIdString)) {
                List<ModuleAccessDto> defaultPrivileges = privilegesService.getUserPrivileges(Integer.parseInt(userIdString), Integer.parseInt(userTypeIdString));
                privileges.put("Modules", PrivilegesDataAssembler.create(defaultPrivileges));
            }
        } finally {
            HttpUtil.wrapAndWriteJson(response, "Privileges", privileges);
        }
        return null;
    }

    /**
     * Json save default privileges.
     * 
     * @param request the request
     * @param response the response
     * @return the model and view
     * @throws Exception the exception
     */
    @RequestMapping(value="jsonSaveDefaultPrivileges")
    public ModelAndView jsonSaveDefaultPrivileges(HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        ResultDto result = new ResultDto();
        List<ModuleAccessDto> moduleAccessList = null;
        try {
            String privilegesDataVal = request.getParameter("PrivilegesData");
            if (!StringUtil.isNullOrBlank(privilegesDataVal)) {
                JSONObject privilegesData = new JSONObject(privilegesDataVal);
                JSONArray jsonModules = privilegesData.getJSONArray("Modules");
                int userTypeId = privilegesData.getInt("UserTypeID");
                if (userTypeId > 0) {
                    moduleAccessList = PrivilegesDataAssembler.createModuleAccessList(jsonModules);
                    if (moduleAccessList != null && !moduleAccessList.isEmpty()) {
                        result.setSuccessful(privilegesService.saveDefaultPrivileges(userTypeId, moduleAccessList));
                        result.setStatusMessage("Default Privileges have been updated successfully.");
                        // Update the information that is present in the session
                        HttpSession session = HttpUtil.getExistingSession(request);
                        UserContext context = (UserContext) session.getAttribute(WebConstants.USER_CONTEXT);
                        if (context != null && context.getLogin() != null) {
                            context.setModuleAccess(privilegesService.getUserPrivileges(context.getLogin().getId(), userTypeId));
                            session.setAttribute(WebConstants.USER_CONTEXT, context);
                        }
                    }
                }
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            result.setStatusMessage(exception.getMessage());
        } finally {
            HttpUtil.writeAsJson(response, result);
        }
        return null;
    }


    /**
     * Json user save.
     *
     * @param request the request
     * @param response the response
     * @return the model and view
     * @throws Exception the exception
     */
    @RequestMapping(value="jsonSaveUserPrivileges")
    public ModelAndView jsonSaveUserPrivileges(HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        ResultDto result = new ResultDto();
        List<ModuleAccessDto> moduleAccessList = null;
        try {
            String privilegesDataVal = request.getParameter("PrivilegesData");
            if (!StringUtil.isNullOrBlank(privilegesDataVal)) {
                JSONObject privilegesData = new JSONObject(privilegesDataVal);
                JSONArray jsonModules = privilegesData.getJSONArray("Modules");
                int userTypeId = privilegesData.getInt("UserTypeID");
                int userId = privilegesData.getInt("UserID");
                if (userTypeId > 0 && userId > 0) {
                    moduleAccessList = PrivilegesDataAssembler.createModuleAccessList(jsonModules);
                    if (moduleAccessList != null && !moduleAccessList.isEmpty()) {
                        result.setSuccessful(privilegesService.saveUserPrivileges(userId, moduleAccessList));
                        result.setStatusMessage("User Privileges have been updated successfully.");
                        // Update the information that is present in the session
                        HttpSession session = HttpUtil.getExistingSession(request);
                        UserContext context = (UserContext) session.getAttribute(WebConstants.USER_CONTEXT);
                        if (context != null && context.getLogin() != null && context.getLogin().getId() == userId) {
                            context.setModuleAccess(privilegesService.getUserPrivileges(userId, userTypeId));
                            session.setAttribute(WebConstants.USER_CONTEXT, context);
                        }
                    }
                }
            }
        } catch (ServiceException serviceException) {
            result.setStatusMessage(serviceException.getMessage());
        } finally {
            HttpUtil.writeAsJson(response, result);
        }
        return null;
    }

    /**
     * Restore user privileges.
     *
     * @param request the request
     * @param response the response
     * @return the model and view
     * @throws Exception the exception
     */
    @RequestMapping(value="restoreToDefaultPrivileges")
    public ModelAndView restoreToDefaultPrivileges(HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        ResultDto result = new ResultDto();
        try {
            String userIdVal = request.getParameter("UserID");
            if (!StringUtil.isNullOrBlank(userIdVal)) {
                int userId = Integer.parseInt(userIdVal.trim());
                privilegesService.deleteUserPrivileges(userId);
                result.setSuccessful(ResultDto.SUCCESS);
            }
        } catch (ServiceException serviceException) {
            result.setStatusMessage(serviceException.getMessage());
        } finally {
            HttpUtil.writeAsJson(response, result);
        }
        return null;
    }

    /**
     * Launch copy user privileges.
     * 
     * @param request the request
     * @param response the response
     * @return the model and view
     * @throws Exception the exception
     */
    @RequestMapping(value="launchCopyUserPrivileges")
    public ModelAndView launchCopyUserPrivileges(HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        UsersDto userDto = null;
        List<UsersDto> users = null;
        Map<String, Object> map = new HashMap<String, Object>();
        String userTypeIDVal = request.getParameter("UserTypeID");
        if (StringUtil.isNullOrBlank(userTypeIDVal)) {
            String userIDVal = request.getParameter("UserID");
            if (!StringUtil.isNullOrBlank(userIDVal)) {
                userDto = userService.get(Integer.parseInt(userIDVal));
                if (userDto != null) {
                    UserType userType = userDto.getUserType();
                    if (userType != null) {
                        users = userTypeService.getUsers(userType.getUserTypeValue());
                    }
                }
            }
        } else {
            users = userTypeService.getUsers(Integer.parseInt(userTypeIDVal));
        }
        map.put("UserDetails", userDto);
        map.put("Users", users);
        return ViewDelegationController.delegateModelPageView(request, UserViewNames.COPY_USER_PRIVILEGES, map);
    }

    /**
     * Copy user privileges.
     * 
     * @param request the request
     * @param response the response
     * @return the model and view
     * @throws Exception the exception
     */
    @RequestMapping(value="copyUserPrivileges")
    public ModelAndView copyUserPrivileges(HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        ResultDto result = new ResultDto();
        try {
            String copyUserPrivilegesData = request.getParameter("CopyUserPrivilegesData");
            if (!StringUtil.isNullOrBlank(copyUserPrivilegesData)) {
                JSONObject copyUserPrivileges = new JSONObject(copyUserPrivilegesData);
                Integer copyFrom = copyUserPrivileges.getInt("CopyFromID");
                List<Integer> copyTo = CollectionUtil.toIntegerList(copyUserPrivileges.getJSONArray("CopyToIDs"));
                privilegesService.copyUserPrivileges(copyFrom, copyTo);
                result.setSuccessful(ResultDto.SUCCESS);
                result.setStatusMessage("Privileges have been copied successfully.");
            }
        } catch (ServiceException serviceException) {
            result.setStatusMessage(serviceException.getMessage());
        } finally {
            HttpUtil.writeAsJson(response, result);
        }
        return null;
    }

}
