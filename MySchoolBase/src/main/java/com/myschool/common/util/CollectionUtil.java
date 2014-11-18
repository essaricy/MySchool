package com.myschool.common.util;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;

/**
 * The Class CollectionUtil.
 */
public class CollectionUtil {

    /**
     * To comma seperated string.
     *
     * @param objectArray the object array
     * @return the string
     */
    public static String toCommaSeperatedString(Object[] objectArray) {
        StringBuffer stringBuffer = new StringBuffer();
        if (objectArray != null && objectArray.length !=0) {
            for (Object object : objectArray) {
                if (object != null) {
                    stringBuffer.append(object.toString().toLowerCase()).append(",");
                }
            }
            stringBuffer.setLength(stringBuffer.length() -1);
        }
        return stringBuffer.toString();
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
     */
    public static String getSqlArray(List<? extends Object> list) {
        String sqlArray = null;
        if (list != null && !list.isEmpty()) {
            sqlArray = list.toString().replaceAll("\\[", "{").replaceAll("]", "}");
        }
        return sqlArray;
    }

}
