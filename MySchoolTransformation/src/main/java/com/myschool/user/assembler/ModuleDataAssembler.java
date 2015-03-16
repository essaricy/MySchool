package com.myschool.user.assembler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.myschool.application.dto.FunctionDto;
import com.myschool.application.dto.ModuleDto;
import com.myschool.common.util.ConversionUtil;
import com.myschool.user.dto.FunctionAccessDto;
import com.myschool.user.dto.ModuleAccessDto;
import com.myschool.user.dto.UserAccessDto;
import com.myschool.user.dto.UserContext;

/**
 * The Class ModuleDataAssembler.
 */
public class ModuleDataAssembler {

    /**
     * Gets the privileges.
     * 
     * @param userAccessList the user access list
     * @param allFunctions the all functions
     * @return the privileges
     */
    public static List<ModuleAccessDto> getPrivileges(
            List<UserAccessDto> userAccessList, List<FunctionDto> allFunctions) {

        ModuleDto module = null;
        ModuleAccessDto moduleAccess = null;
        List<ModuleAccessDto> moduleAccessList = null;

        if (userAccessList == null || userAccessList.isEmpty()) {
            moduleAccessList = new ArrayList<ModuleAccessDto>();
            for (FunctionDto function : allFunctions) {
                module = function.getModule();
                // Module access is not present already in the list. Create one.
                moduleAccess = getModuleAccess(moduleAccessList, module);
                if (moduleAccess == null) {
                    moduleAccess = new ModuleAccessDto();
                    moduleAccess.setModuleId(module.getModuleId());
                    moduleAccess.setModuleName(module.getModuleName());
                    moduleAccess.setAccessUrl(module.getAccessUrl());
                    moduleAccess.setAdminAccess(module.isAdminAccess());
                    moduleAccess.setEmployeeAccess(module.isEmployeeAccess());
                    moduleAccess.setStudentAccess(module.isStudentAccess());
                    moduleAccessList.add(moduleAccess);
                }
                setFunctionAccess(moduleAccess, function);
            }
        } else {
            moduleAccessList = createModuleAccessList(userAccessList);
        }
        return modifyModuleAccessList(moduleAccessList);
    }

    /**
     * Creates the module access list.
     *
     * @param userAccessList the user access list
     * @return the list
     */
    private static List<ModuleAccessDto> createModuleAccessList(List<UserAccessDto> userAccessList) {
        ModuleDto module = null;
        FunctionDto function = null;
        ModuleAccessDto moduleAccess = null;

        List<ModuleAccessDto> moduleAccessList = new ArrayList<ModuleAccessDto>();

        for (UserAccessDto userAccess : userAccessList) {
            function = userAccess.getFunction();
            module = function.getModule();
            // Module access is not present already in the list. Create one.
            moduleAccess = getModuleAccess(moduleAccessList, module);
            if (moduleAccess == null) {
                moduleAccess = new ModuleAccessDto();
                moduleAccess.setModuleId(module.getModuleId());
                moduleAccess.setModuleName(module.getModuleName());
                moduleAccess.setAdminAccess(module.isAdminAccess());
                moduleAccess.setEmployeeAccess(module.isEmployeeAccess());
                moduleAccess.setStudentAccess(module.isStudentAccess());
                moduleAccess.setAccessUrl(module.getAccessUrl());
                moduleAccessList.add(moduleAccess);
            }
            setFunctionAccess(moduleAccess, function, userAccess);
        }
        return moduleAccessList;
    }

    /**
     * Sets the function access.
     *
     * @param moduleAccess the module access
     * @param function the function
     */
    private static void setFunctionAccess(ModuleAccessDto moduleAccess, FunctionDto function) {
        setFunctionAccess(moduleAccess, function, null);
    }

    /**
     * Sets the function access.
     *
     * @param moduleAccess the module access
     * @param function the function
     * @param userAccess the user access
     */
    private static void setFunctionAccess(ModuleAccessDto moduleAccess,
            FunctionDto function, UserAccessDto userAccess) {
        FunctionAccessDto functionAccess = null;
        if (moduleAccess != null) {
            List<FunctionAccessDto> functionAccessList = moduleAccess.getFunctionAccess();
            if (functionAccessList == null) {
                functionAccessList = new ArrayList<FunctionAccessDto>();
            }
            if (function != null) {
                functionAccess = new FunctionAccessDto();
                functionAccess.setFunctionId(function.getFunctionId());
                functionAccess.setFunctionName(function.getFunctionName());
                functionAccess.setAccessUrl(function.getAccessUrl());
                functionAccess.setIconUrl(function.getIconUrl());
                if (userAccess != null) {
                    functionAccess.setView(userAccess.isView());
                    functionAccess.setCreate(userAccess.isCreate());
                    functionAccess.setUpdate(userAccess.isUpdate());
                    functionAccess.setDelete(userAccess.isDelete());
                }
                functionAccessList.add(functionAccess);
            }
            moduleAccess.setFunctionAccess(functionAccessList);
        }
    }

    /**
     * Contains.
     *
     * @param moduleAccessList the module access list
     * @param module the module
     * @return true, if successful
     */
    private static ModuleAccessDto getModuleAccess(
            List<ModuleAccessDto> moduleAccessList, ModuleDto module) {
        ModuleAccessDto moduleAccessDto = null;
        for (ModuleAccessDto moduleAccess : moduleAccessList) {
            if (moduleAccess.getModuleId() == module.getModuleId()) {
                moduleAccessDto = moduleAccess;
                break;
            }
        }
        return moduleAccessDto;
    }

    /**
     * Modify module access list.
     *
     * @param moduleAccessList the module access list
     * @return the list
     */
    private static List<ModuleAccessDto> modifyModuleAccessList(
            List<ModuleAccessDto> moduleAccessList) {
        boolean allView = true;
        boolean allCreate = true;
        boolean allUpdate = true;
        boolean allDelete = true;

        List<FunctionAccessDto> functionAccessList = null;

        if (moduleAccessList != null) {
            for (ModuleAccessDto moduleAccess : moduleAccessList) {
                allView = true;
                allCreate = true;
                allUpdate = true;
                allDelete = true;

                if (moduleAccess != null) {
                    functionAccessList = moduleAccess.getFunctionAccess();
                    if (functionAccessList == null) {
                        moduleAccess.setAllCreate(false);
                        moduleAccess.setAllDelete(false);
                        moduleAccess.setAllUpdate(false);
                        moduleAccess.setAllView(false);
                        moduleAccess.setModuleAccessible(false);
                    } else {
                        for (FunctionAccessDto functionAccess : functionAccessList) {
                            if (functionAccess != null) {
                                // If at least one function is accessible then the module is also accessible.
                                // The least access is view.
                                if (!moduleAccess.isModuleAccessible()
                                        //|| functionAccess.isCreate()
                                        //|| functionAccess.isDelete()
                                        //|| functionAccess.isUpdate()
                                        && functionAccess.isView()) {
                                    moduleAccess.setModuleAccessible(true);
                                }
                                if (!functionAccess.isCreate()) {
                                    allCreate = false;
                                }
                                if (!functionAccess.isDelete()) {
                                    allDelete = false;
                                }
                                if (!functionAccess.isUpdate()) {
                                    allUpdate = false;
                                }
                                if (!functionAccess.isView()) {
                                    allView = false;
                                }
                            }
                        }
                        moduleAccess.setAllCreate(allCreate);
                        moduleAccess.setAllDelete(allDelete);
                        moduleAccess.setAllUpdate(allUpdate);
                        moduleAccess.setAllView(allView);
                    }
                }
            }
        }
        return moduleAccessList;
    }

    /**
     * Gets the page access details.
     *
     * @param userContext the user context
     * @param requestedUrl the requested url
     * @return the page access details
     */
    public static UserAccessDto getPageAccessDetails(UserContext userContext, String requestedUrl) {
        String accessUrl = null;
        UserAccessDto userAccess = new UserAccessDto();

        if (userContext != null && requestedUrl != null) {
            List<ModuleAccessDto> moduleAccessList = userContext.getModuleAccess();
            if (moduleAccessList != null) {
                for (ModuleAccessDto moduleAccess : moduleAccessList) {
                    if (moduleAccess != null) {
                        List<FunctionAccessDto> functionAccessList = moduleAccess.getFunctionAccess();
                        if (functionAccessList != null) {
                            for (FunctionAccessDto functionAccess : functionAccessList) {
                                if (functionAccess != null) {
                                    accessUrl = functionAccess.getAccessUrl();
                                    if (requestedUrl.equals(accessUrl)) {
                                        userAccess.setCreate(functionAccess.isCreate());
                                        userAccess.setDelete(functionAccess.isDelete());
                                        userAccess.setUpdate(functionAccess.isUpdate());
                                        userAccess.setView(functionAccess.isView());
                                        break;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return userAccess;
    }

    /**
     * Creates the function.
     *
     * @param resultSet the result set
     * @param aliased the full name
     * @return the function dto
     * @throws SQLException the sQL exception
     */
    public static FunctionDto createFunction(ResultSet resultSet, boolean aliased) throws SQLException {
        FunctionDto functionDto = new FunctionDto();
        if (aliased) {
            functionDto.setFunctionId(resultSet.getInt("FUNCTION_FUNCTION_ID"));
            functionDto.setFunctionName(resultSet.getString("FUNCTION_FUNCTION_NAME"));
            functionDto.setIconUrl(resultSet.getString("FUNCTION_ICON_URL"));
            functionDto.setAccessUrl(resultSet.getString("FUNCTION_ACCESS_URL"));
        } else {
            functionDto.setFunctionId(resultSet.getInt("FUNCTION_ID"));
            functionDto.setFunctionName(resultSet.getString("FUNCTION_NAME"));
            functionDto.setIconUrl(resultSet.getString("ICON_URL"));
            functionDto.setAccessUrl(resultSet.getString("ACCESS_URL"));
        }
        functionDto.setModule(createModule(resultSet, true));
        return functionDto;
    }

    /**
     * Creates the module.
     *
     * @param resultSet the result set
     * @param aliased the aliased
     * @return the module dto
     * @throws SQLException the sQL exception
     */
    private static ModuleDto createModule(ResultSet resultSet, boolean aliased) throws SQLException {
        ModuleDto moduleDto = new ModuleDto();
        if (aliased) {
            moduleDto.setModuleId(resultSet.getInt("REF_MODULE_MODULE_ID"));
            moduleDto.setModuleName(resultSet.getString("REF_MODULE_MODULE_NAME"));
            moduleDto.setAccessUrl(resultSet.getString("REF_MODULE_ACCESS_URL"));
            moduleDto.setAdminAccess(ConversionUtil.toBoolean(resultSet.getString("REF_MODULE_CAN_ADMIN_ACCESS")));
            moduleDto.setEmployeeAccess(ConversionUtil.toBoolean(resultSet.getString("REF_MODULE_CAN_EMPLOYEE_ACCESS")));
            moduleDto.setStudentAccess(ConversionUtil.toBoolean(resultSet.getString("REF_MODULE_CAN_STUDENT_ACCESS")));
        } else {
            moduleDto.setModuleId(resultSet.getInt("MODULE_ID"));
            moduleDto.setModuleName(resultSet.getString("MODULE_NAME"));
            moduleDto.setAccessUrl(resultSet.getString("ACCESS_URL"));
            moduleDto.setAdminAccess(ConversionUtil.toBoolean(resultSet.getString("CAN_ADMIN_ACCESS")));
            moduleDto.setEmployeeAccess(ConversionUtil.toBoolean(resultSet.getString("CAN_EMPLOYEE_ACCESS")));
            moduleDto.setStudentAccess(ConversionUtil.toBoolean(resultSet.getString("CAN_STUDENT_ACCESS")));
        }
        return moduleDto;
    }

}
