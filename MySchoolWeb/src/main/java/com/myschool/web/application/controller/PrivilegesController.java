package com.myschool.web.application.controller;

import java.util.ArrayList;
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
import com.myschool.common.util.StringUtil;
import com.myschool.user.dto.FunctionAccessDto;
import com.myschool.user.dto.ModuleAccessDto;
import com.myschool.user.dto.UserContext;
import com.myschool.user.dto.UsersDto;
import com.myschool.user.service.PrivilegesService;
import com.myschool.user.service.UserTypeService;
import com.myschool.web.application.constants.WebConstants;
import com.myschool.web.common.util.HttpUtil;
import com.myschool.web.common.util.ViewDelegationController;
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

    /**
     * List.
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
        map.put("userTypes", userTypeService.getAll());
        String userTypeIdString = request.getParameter("userTypeId");
        int userTypeId = 1;
        if (userTypeIdString != null) {
            userTypeId = Integer.parseInt(request.getParameter("userTypeId"));
        }

        List<ModuleAccessDto> defaultPrivileges = privilegesService.getDefaultPrivileges(userTypeId);
        map.put("privileges", defaultPrivileges);
        map.put("userTypeId", userTypeIdString);
        if (defaultPrivileges != null) {
            map.put("numberOfModules", defaultPrivileges.size());
        }
        return ViewDelegationController.delegateWholePageView(request, UserViewNames.DEFAULT_PRIVILEGES, map);
    }

    /**
     * List.
     *
     * @param request the request
     * @param response the response
     * @return the model and view
     * @throws Exception the exception
     */
    @RequestMapping(value="userList")
    public ModelAndView userList(HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        return ViewDelegationController.delegateWholePageView(request, UserViewNames.USER_PRIVILEGES);
    }

    /**
     * Json default save.
     *
     * @param request the request
     * @param response the response
     * @return the model and view
     * @throws Exception the exception
     */
    @RequestMapping(value="jsonDefaultSave")
    public ModelAndView jsonDefaultSave(HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        int moduleId = 0;
        int functionId = 0;
        
        List<ModuleAccessDto> moduleAccessList = null;
        List<FunctionAccessDto> functionAccessList = null;
        
        JSONObject functionsObject = null;
        JSONArray functions = null;
        JSONArray functionArray = null;

        ModuleAccessDto moduleAccess = null;
        FunctionAccessDto functionAccess = null;

        int userTypeId = Integer.parseInt(request.getParameter("userTypeId"));
        String privilegesData = request.getParameter("privilegesData");
        JSONObject jsonObject = new JSONObject(privilegesData);
        JSONObject modulesObject = jsonObject.getJSONObject("modules");

        ResultDto result = new ResultDto();

        try {
            if (modulesObject != null) {
                JSONArray modules = modulesObject.names();
                if (modules != null) {
                    moduleAccessList = new ArrayList<ModuleAccessDto>();
                    for (int moduleIndex=0; moduleIndex<modules.length(); moduleIndex++) {
                        moduleId = modules.getInt(moduleIndex);
                        moduleAccess = new ModuleAccessDto();
                        moduleAccess.setModuleId(moduleId);
                        functionsObject = modulesObject.getJSONObject(String.valueOf(moduleId));

                        if (functionsObject != null) {
                            functionAccessList = new ArrayList<FunctionAccessDto>();
                            for (int functionIndex=0; functionIndex<functionsObject.length(); functionIndex++) {
                                functions = functionsObject.names();
                                functionId = functions.getInt(functionIndex);
                                functionArray = functionsObject.getJSONArray(String.valueOf(functionId));
                                functionAccess = new FunctionAccessDto();
                                functionAccess.setFunctionId(functionId);
                                functionAccess.setView(functionArray.getBoolean(0));
                                functionAccess.setCreate(functionArray.getBoolean(1));
                                functionAccess.setUpdate(functionArray.getBoolean(2));
                                functionAccess.setDelete(functionArray.getBoolean(3));
                                functionAccessList.add(functionAccess);
                            }
                        }
                        moduleAccessList.add(moduleAccess);
                        moduleAccess.setFunctionAccess(functionAccessList);
                        result.setSuccessful(privilegesService.saveDefaultPrivileges(userTypeId, moduleAccessList));

                        // Update the information that is present in the session
                        HttpSession session = request.getSession();
                        UserContext context = (UserContext) session.getAttribute(WebConstants.USER_CONTEXT);
                        if (context != null) {
                            context.setModuleAccess(privilegesService.getUserPrivileges(context.getLoginId(), userTypeId));
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
        //return defaultPrivileges(request, response);
    }

    /**
     * List.
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
        map.put("userTypes", userTypeService.getAll());

        String userTypeId = request.getParameter("userTypeId");
        if (userTypeId != null && !StringUtil.isEmpty(userTypeId)) {
            // add users list by user type id
            List<UsersDto> users = userTypeService.getUsers(Integer.parseInt(userTypeId));
            map.put("userTypeId", userTypeId);
            map.put("users", users);

            String userId = request.getParameter("userId");
            if (!StringUtil.isEmpty(userId) && !StringUtil.isEmpty(userTypeId)) {
                List<ModuleAccessDto> userPrivileges = privilegesService.getUserPrivileges(
                        Integer.parseInt(userId), Integer.parseInt(userTypeId));
                map.put("userId", userId);
                map.put("privileges", userPrivileges);
            }
        }
        return ViewDelegationController.delegateWholePageView(request, UserViewNames.USER_PRIVILEGES, map);
    }

    /**
     * Json user save.
     *
     * @param request the request
     * @param response the response
     * @return the model and view
     * @throws Exception the exception
     */
    @RequestMapping(value="jsonUserSave")
    public ModelAndView jsonUserSave(HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        int moduleId = 0;
        int functionId = 0;

        List<ModuleAccessDto> moduleAccessList = null;
        List<FunctionAccessDto> functionAccessList = null;

        JSONObject functionsObject = null;
        JSONArray functions = null;
        JSONArray functionArray = null;

        ModuleAccessDto moduleAccess = null;
        FunctionAccessDto functionAccess = null;

        int userId = Integer.parseInt(request.getParameter("userId"));
        String privilegesData = request.getParameter("privilegesData");
        JSONObject jsonObject = new JSONObject(privilegesData);
        JSONObject modulesObject = jsonObject.getJSONObject("modules");

        ResultDto result = new ResultDto();

        try {
            if (modulesObject != null){
                JSONArray modules = modulesObject.names();
                if (modules != null) {
                    moduleAccessList = new ArrayList<ModuleAccessDto>();
                    for (int moduleIndex=0; moduleIndex<modules.length(); moduleIndex++) {
                        moduleId = modules.getInt(moduleIndex);
                        moduleAccess = new ModuleAccessDto();
                        moduleAccess.setModuleId(moduleId);
                        functionsObject = modulesObject.getJSONObject(String.valueOf(moduleId));

                        if (functionsObject != null) {
                            functionAccessList = new ArrayList<FunctionAccessDto>();
                            for (int functionIndex=0; functionIndex<functionsObject.length(); functionIndex++) {
                                functions = functionsObject.names();
                                functionId = functions.getInt(functionIndex);
                                functionArray = functionsObject.getJSONArray(String.valueOf(functionId));
                                functionAccess = new FunctionAccessDto();
                                functionAccess.setFunctionId(functionId);
                                functionAccess.setView(functionArray.getBoolean(0));
                                functionAccess.setCreate(functionArray.getBoolean(1));
                                functionAccess.setUpdate(functionArray.getBoolean(2));
                                functionAccess.setDelete(functionArray.getBoolean(3));
                                functionAccessList.add(functionAccess);
                            }
                        }
                        moduleAccessList.add(moduleAccess);
                        moduleAccess.setFunctionAccess(functionAccessList);
                        
                        result.setSuccessful(privilegesService.saveUserPrivileges(userId, moduleAccessList));
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
    @RequestMapping(value="restoreUserPrivileges")
    public ModelAndView restoreUserPrivileges(HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        ResultDto result = new ResultDto();
        try {
            int userId = Integer.parseInt(request.getParameter("userId"));
            privilegesService.deleteUserPrivileges(userId);
            result.setSuccessful(ResultDto.SUCCESS);
        } catch (ServiceException serviceException) {
            result.setStatusMessage(serviceException.getMessage());
        } finally {
            HttpUtil.writeAsJson(response, result);
        }
        return null;
    }

}
