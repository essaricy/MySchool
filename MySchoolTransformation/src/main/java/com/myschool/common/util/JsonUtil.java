package com.myschool.common.util;

import org.json.JSONObject;

/**
 * The Class JsonUtil.
 */
public class JsonUtil {

    /**
     * Gets the string value.
     * 
     * @param jsonObject the json object
     * @param paramName the param name
     * @return the string value
     */
    public static String getStringValue(JSONObject jsonObject, String paramName) {
        String paramValue = jsonObject.getString(paramName);
        if (!StringUtil.isNullOrBlank(paramValue)) {
            return paramValue.trim();
        }
        return null;
    }

    /**
     * Gets the int value.
     * 
     * @param jsonObject the json object
     * @param paramName the param name
     * @return the int value
     */
    public static int getIntValue(JSONObject jsonObject, String paramName) {
        return getIntValue(jsonObject, paramName, 0);
    }

    /**
     * Gets the int value.
     * 
     * @param jsonObject the json object
     * @param paramName the param name
     * @param defaultValue the default value
     * @return the int value
     */
    public static int getIntValue(JSONObject jsonObject,
            String paramName, int defaultValue) {
        String paramValue = jsonObject.getString(paramName);
        if (!StringUtil.isNullOrBlank(paramValue)) {
            return Integer.parseInt(paramValue.trim());
        }
        return defaultValue;
    }

}
