package com.myschool.common.util;

import org.json.JSONArray;
import org.json.JSONObject;

import com.quasar.core.util.StringUtil;

/**
 * The Class JsonUtil.
 */
public class JsonUtil {

    /**
     * Gets the string.
     * 
     * @param jsonObject the json object
     * @param key the key
     * @return the string
     */
    public static String getString(JSONObject jsonObject, String key) {
        String value = null;
        if (jsonObject.has(key)) {
            value = jsonObject.getString(key);
            if (!StringUtil.isNullOrBlank(value)) {
                return value.trim();
            }
        }
        return value;
    }

    /**
     * Gets the int.
     * 
     * @param jsonObject the json object
     * @param key the key
     * @return the int
     */
    public static int getInt(JSONObject jsonObject, String key) {
        return getIntValue(jsonObject, key, 0);
    }

    /**
     * Gets the int value.
     * 
     * @param jsonObject the json object
     * @param key the key
     * @param defaultValue the default value
     * @return the int value
     */
    public static int getIntValue(JSONObject jsonObject,
            String key, int defaultValue) {
        String value = null;
        if (jsonObject.has(key)) {
            value = jsonObject.getString(key);
            if (!StringUtil.isNullOrBlank(value)) {
                return Integer.parseInt(value.trim());
            }
        }
        return defaultValue;
    }

    /**
     * Gets the array.
     * 
     * @param jsonObject the json object
     * @param key the key
     * @return the array
     */
    public static JSONArray getArray(JSONObject jsonObject,
            String key) {
        if (jsonObject.has(key)) {
            Object object = jsonObject.get(key);
            if (object instanceof JSONArray) {
                return (JSONArray) object;
            }
        }
        return null;
    }

    /**
     * Gets the object.
     * 
     * @param jsonObject the json object
     * @param key the key
     * @return the object
     */
    public static JSONObject getObject(JSONObject jsonObject,
            String key) {
        if (jsonObject.has(key)) {
            return jsonObject.getJSONObject(key);
        }
        return null;
    }

}
