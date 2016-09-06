package com.myschool.util;

import java.util.List;

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
