package com.myschool.user.assembler;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.myschool.user.dto.FunctionAccessDto;
import com.myschool.user.dto.ModuleAccessDto;

/**
 * The Class PrivilegesDataAssembler.
 */
public class PrivilegesDataAssembler {

    /**
     * Creates the module access list.
     * 
     * @return the list
     */
    public static List<ModuleAccessDto> createModuleAccessList(JSONArray jsonModules) {
        List<ModuleAccessDto> moduleAccessList = null;
        ModuleAccessDto moduleAccess = null;
        if (jsonModules != null) {
            moduleAccessList = new ArrayList<ModuleAccessDto>();
            for (int index = 0; index < jsonModules.length(); index++) {
                moduleAccess = createModuleAccess((JSONObject) jsonModules.get(index));
                if (moduleAccess != null) {
                    moduleAccessList.add(moduleAccess);
                }
            }
        }
        return moduleAccessList;
    }

    /**
     * Creates the module access.
     * 
     * @param jsonObject the json object
     * @return the module access dto
     */
    private static ModuleAccessDto createModuleAccess(JSONObject jsonObject) {
        ModuleAccessDto moduleAccess = null;
        if (jsonObject != null) {
            moduleAccess = new ModuleAccessDto();
            moduleAccess.setModuleId(jsonObject.getInt("ModuleId"));
            moduleAccess.setAllView(jsonObject.getBoolean("CanView"));
            moduleAccess.setAllCreate(jsonObject.getBoolean("CanCreate"));
            moduleAccess.setAllUpdate(jsonObject.getBoolean("CanUpdate"));
            moduleAccess.setAllDelete(jsonObject.getBoolean("CanDelete"));
            moduleAccess.setFunctionAccess(createFunctionAccessList(jsonObject.getJSONArray("Functions")));
        }
        return moduleAccess;
    }

    /**
     * Creates the function access list.
     * 
     * @param jsonFunctions the json array
     * @return the list
     */
    private static List<FunctionAccessDto> createFunctionAccessList(JSONArray jsonFunctions) {
        List<FunctionAccessDto> functionAccessList = null;
        FunctionAccessDto functionAccess = null;
        if (jsonFunctions != null) {
            functionAccessList = new ArrayList<FunctionAccessDto>();
            for (int index = 0; index < jsonFunctions.length(); index++) {
                functionAccess = createFunctionAccess((JSONObject) jsonFunctions.get(index));
                if (functionAccess != null) {
                    functionAccessList.add(functionAccess);
                }
            }
        }
        return functionAccessList;
    }

    /**
     * Creates the function access.
     * 
     * @param jsonObject the json object
     * @return the function access dto
     */
    private static FunctionAccessDto createFunctionAccess(JSONObject jsonObject) {
        FunctionAccessDto functionAccess = null;
        if (jsonObject != null) {
            functionAccess = new FunctionAccessDto();
            functionAccess.setFunctionId(jsonObject.getInt("FunctionId"));
            functionAccess.setView(jsonObject.getBoolean("CanView"));
            functionAccess.setCreate(jsonObject.getBoolean("CanCreate"));
            functionAccess.setUpdate(jsonObject.getBoolean("CanUpdate"));
            functionAccess.setDelete(jsonObject.getBoolean("CanDelete"));
        }
        return functionAccess;
    }

    /**
     * Creates the.
     * 
     * @param moduleAccessList the module access list
     * @return the jSON array
     */
    public static JSONArray create(List<ModuleAccessDto> moduleAccessList) {
        JSONArray jsonArray = null;
        if (moduleAccessList != null) {
            jsonArray = new JSONArray();
            for (ModuleAccessDto moduleAccess : moduleAccessList) {
                if (moduleAccess != null) {
                    jsonArray.put(createModuleAccess(moduleAccess));
                }
            }
        }
        return jsonArray;
    }

    /**
     * Creates the module access.
     * 
     * @param moduleAccess the module access
     * @return the jSON object
     */
    private static JSONObject createModuleAccess(ModuleAccessDto moduleAccess) {
        JSONObject jsonObject = null;
        if (moduleAccess != null) {
            jsonObject = new JSONObject();
            jsonObject.put("ModuleId", moduleAccess.getModuleId());
            jsonObject.put("ModuleName", moduleAccess.getModuleName());
            jsonObject.put("CanView", moduleAccess.isAllView());
            jsonObject.put("CanCreate", moduleAccess.isAllCreate());
            jsonObject.put("CanUpdate", moduleAccess.isAllUpdate());
            jsonObject.put("CanDelete", moduleAccess.isAllDelete());
            jsonObject.put("Functions", createFunctionAccess(moduleAccess.getFunctionAccess()));
        }
        return jsonObject;
    }

    /**
     * Creates the function access.
     * 
     * @param functionAccessList the function access list
     * @return the jSON array
     */
    private static JSONArray createFunctionAccess(List<FunctionAccessDto> functionAccessList) {
        JSONArray jsonArray = null;
        if (functionAccessList != null) {
            jsonArray = new JSONArray();
            for (FunctionAccessDto functionAccess : functionAccessList) {
                if (functionAccess != null) {
                    jsonArray.put(create(functionAccess));
                }
            }
        }
        return jsonArray;
    }

    /**
     * Creates the.
     * 
     * @param functionAccess the function access
     * @return the jSON object
     */
    private static JSONObject create(FunctionAccessDto functionAccess) {
        JSONObject jsonObject = null;
        if (functionAccess != null) {
            jsonObject = new JSONObject();
            jsonObject.put("FunctionId", functionAccess.getFunctionId());
            jsonObject.put("FunctionName", functionAccess.getFunctionName());
            jsonObject.put("CanView", functionAccess.isView());
            jsonObject.put("CanCreate", functionAccess.isCreate());
            jsonObject.put("CanUpdate", functionAccess.isUpdate());
            jsonObject.put("CanDelete", functionAccess.isDelete());
        }
        return jsonObject;
    }

}
