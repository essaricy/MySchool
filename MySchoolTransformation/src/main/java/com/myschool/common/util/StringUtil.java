package com.myschool.common.util;

import java.util.ArrayList;
import java.util.List;

/**
 * The Class StringUtil.
 */
public class StringUtil {

    /**
     * Gets the statement boolean.
     *
     * @param gender the gender
     * @return the statement boolean
     */
    public static String parseMaleFemale(String gender) {
        String parsedGender = null;
        if (gender != null) {
            if (gender.equalsIgnoreCase("Male")
                    || gender.equalsIgnoreCase("M")) {
                parsedGender = "M";
            } else if (gender.equalsIgnoreCase("Female")
                    || gender.equalsIgnoreCase("F")) {
                parsedGender = "F";
            }
        }
        return parsedGender;
    }

    /**
     *
     * @param value the value
     * @return true, if is blank
     */
    public static boolean isNullOrBlank(String value) {
        boolean blank = false;
        if (value == null || value.trim().equals("")) {
            blank = true;
        }
        return blank;
    }

    /**
     * Checks if is empty.
     *
     * @param value the value
     * @return true, if is empty
     */
    public static boolean isEmpty(String value) {
        boolean blank = false;
        if (value != null && value.trim().equals("")) {
            blank = true;
        }
        return blank;
    }

    /**
     * Checks if is number.
     * 
     * @param value the value
     * @return true, if is number
     */
    public static boolean isNumber(String value) {
        if (value == null) {
            return false;
        } else {
            if (isNullOrBlank(value)) {
                return false;
            }
            for (int index = 0; index < value.length(); index++) {
                if (!Character.isDigit(value.charAt(index))) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Gets the value.
     *
     * @param value the value
     * @return the value
     */
    public static String getValue(String value) {
        String newValue = null;
        if (value != null && !value.trim().equals("") && !value.trim().equalsIgnoreCase("null")) {
            newValue = value.trim();
        }
        return newValue;
    }

    public static String getJsonValue(String value) {
        if (StringUtil.isNullOrBlank(value)) {
            return "";
        }
        return value;
    }

    /**
     * To collections of integers.
     *
     * @param commaSeparatedValues the comma separated values
     * @return the list
     */
    public static List<Integer> toCollectionsOfIntegers(String commaSeparatedValues) {
        List<Integer> integers = null;
        if (commaSeparatedValues != null) {
            String[] split = commaSeparatedValues.split(",");
            if (split != null && split.length != 0) {
                integers = new ArrayList<Integer>();
                for (String value : split) {
                    integers.add(Integer.parseInt(value));
                }
            }
        }
        return integers;
    }
}
