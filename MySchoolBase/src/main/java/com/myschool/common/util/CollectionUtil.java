package com.myschool.common.util;

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
}
