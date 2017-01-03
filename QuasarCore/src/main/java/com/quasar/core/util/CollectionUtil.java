package com.quasar.core.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;

public class CollectionUtil {

    public static String toString(Object[] objectArray) {
        return StringUtils.join(objectArray, ",");
    }

    /**
     * To integer list.
     * 
     * @param jsonArray the json array
     * @return the list
     */
    public static List<Integer> toIntegerList(JSONArray jsonArray) {
        List<Integer> list = null;
        if (jsonArray != null) {
            list = new ArrayList<Integer>();
            for (int index = 0; index < jsonArray.length(); index++) {
                list.add(jsonArray.getInt(index));
            }
        }
        return list;
    }

    /**
     * Gets the sql array.
     * 
     * @param list the list
     * @return the sql array
     * @deprecated move to database util
     */
    public static String getSqlArray(List<? extends Object> list) {
        String sqlArray = null;
        if (list != null && !list.isEmpty()) {
            sqlArray = list.toString().replaceAll("\\[", "{").replaceAll("]", "}");
        }
        return sqlArray;
    }

    /**
     * Contains.
     * 
     * @param text the text
     * @param list the list
     * @return true, if successful
     */
    public static boolean contains(String text, List<String> list) {
        boolean contains = false;
        if (text != null && list != null && !list.isEmpty()) {
            for (String listValue : list) {
                if (text.equals(listValue)) {
                    contains = true;
                    break;
                }
            }
        }
        return contains;
    }

    /**
     * Contains ignore case.
     * 
     * @param text the text
     * @param list the list
     * @return true, if successful
     */
    public static boolean containsIgnoreCase(String text, List<String> list) {
        boolean contains = false;
        if (text != null && list != null && !list.isEmpty()) {
            for (String listValue : list) {
                if (text.equalsIgnoreCase(listValue)) {
                    contains = true;
                    break;
                }
            }
        }
        return contains;
    }

}
