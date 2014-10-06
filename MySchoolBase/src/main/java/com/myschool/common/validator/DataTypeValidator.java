package com.myschool.common.validator;

import java.util.regex.Pattern;

import com.myschool.common.exception.DataException;

/**
 * The Class DataTypes.
 */
public class DataTypeValidator {

    /** The Constant INTEGER. */
    public static final String INTEGER = "INTEGER";
    
    /** The Constant DECIMAL. */
    public static final String DECIMAL = "DECIMAL";
    
    /** The Constant ANY_CHARACTER. */
    public static final String ANY_CHARACTER = "ANY_CHARACTER";
    
    /** The Constant ALPHABETS. */
    public static final String ALPHABETS = "ALPHABETS";
    
    /** The Constant ALPHANUMERIC. */
    public static final String ALPHANUMERIC = "ALPHANUMERIC";
    
    /** The Constant NAME. */
    public static final String NAME = "NAME";
    
    /** The Constant GENDER. */
    public static final String GENDER = "GENDER";
    
    /** The Constant PHONE_NUMBER. */
    public static final String PHONE_NUMBER = "PHONE_NUMBER";
    
    /** The Constant EMAIL_ID. */
    public static final String EMAIL_ID = "EMAIL_ID";
    
    /** The Constant WEB_ADDRESS. */
    public static final String WEB_ADDRESS = "WEB_ADDRESS";
    
    /** The Constant DATE. */
    public static final String DATE = "DATE";
    
    /** The Constant TIME_STAMP. */
    public static final String TIME_STAMP = "TIME_STAMP";
    
    /** The Constant SENTENCE. */
    public static final String SENTENCE = "SENTENCE";
    
    /** The Constant OCCUPATION. */
    public static final String OCCUPATION = "OCCUPATION";
    
    /** The Constant YES_NO. */
    public static final String YES_NO = "YES_NO";
    
    /** The Constant TRUE_FALSE. */
    public static final String TRUE_FALSE = "TRUE_FALSE";
    
    /** The Constant SINGLE_DIGIT. */
    public static final String SINGLE_DIGIT = "SINGLE_DIGIT";

    /** The Constant AA_DATA. */
    public static final String AA_DATA = "aaData";

    /**
     * Gets the reg ex.
     *
     * @param dataType the data type
     * @return the reg ex
     * @throws DataException the data exception
     */
    public static String getRegEx(String dataType) throws DataException {
        String regEx = null;
        if (dataType != null) {
            if (dataType.equals(INTEGER)) {
                regEx = "^(\\d)*\\d*$";
            } else if (dataType.equals(DECIMAL)) {
                // Allows max 8 digits and 2 fractions.
                regEx = "^[-+]?\\d+(\\.\\d+)?$";
            } else if (dataType.equals(ANY_CHARACTER)) {
                regEx = "(.|\\n|\\r)*";
            } else if (dataType.equals(ALPHABETS)) {
                regEx = "[a-zA-Z]*";
            } else if (dataType.equals(ALPHANUMERIC)) {
                regEx = "^[0-9a-zA-Z]+$";
            } else if (dataType.equals(NAME)) {
                regEx = "([a-zA-Z| |'|])*";
            } else if (dataType.equals(GENDER)) {
                // Matches F, M, female, male, Female, Male
                regEx = "^(?:m|M|male|Male|f|F|female|Female)$";
            } else if (dataType.equals(PHONE_NUMBER)) {
                regEx = "(^(\\+?\\-? *[0-9]+)([,0-9 ]*)([0-9 ])*$)|(^ *$)";
            } else if (dataType.equals(EMAIL_ID)) {
                // matches correct@email.com | sample@yahoo.co.in | sam_ple@yahoo.com sam.ple@yahho.com
                regEx = "^[a-zA-Z]+[a-zA-Z0-9_.-]*@([a-zA-Z0-9]+){1}(\\.[a-zA-Z0-9]+){1,2}";
            } else if (dataType.equals(WEB_ADDRESS)) {
                // matches www.domain.com | http://www.blah.ru | https://192.168.0.2:80/users/~fname.lname/file.ext
                regEx = "(?<http>(http:[/][/]|www.)([a-z]|[A-Z]|[0-9]|[/.]|[~]|[-])*)";
            } else if (dataType.equals(DATE)) {
                // matches 4/1/2001 | 12/12/2001 | 55/5/3434
                regEx = "^\\d{1,2}\\/\\d{1,2}\\/\\d{4}$";
            } else if (dataType.equals(TIME_STAMP)) {
                // matches 02:04 | 16:56 | 23:59
                regEx = "^([0-1][0-9]|[2][0-3]):([0-5][0-9])$";
            } else if (dataType.equals(SENTENCE)) {
                regEx = "([a-zA-Z| |-|.|0-9])*";
            } else if (dataType.equals(OCCUPATION)) {
                regEx = "([a-zA-Z| |-|.|0-9])*";
            } else if (dataType.equals(YES_NO)) {
                // matches Y, N, no, yes, Yes, No
                regEx = "^(?:y|Y|yes|Yes|n|N|no|No)$";
            } else if (dataType.equals(TRUE_FALSE)) {
                // matches TRUE, True, true, FALSE, False, false
                regEx = "^(?:TRUE|True|true|FALSE|False|false)$";
            } else if (dataType.equals(SINGLE_DIGIT)) {
                regEx = "^[123456789]$";
            } else {
                throw new DataException("Invalid Data Type (" + dataType + ").");
            }
        }
        return regEx;
    }

    /**
     * Validate.
     *
     * @param text the text
     * @param dataType the data type
     * @param filedName the filed name
     * @return the string
     * @throws DataException the data exception
     */
    public static String validate(String text, String dataType, String filedName) throws DataException {
        if (text != null) {
            String regEx = getRegEx(dataType);
            if (!Pattern.matches(regEx, text)) {
                throw new DataException("Field (" + filedName + ") contains invalid data");
            }
        }
        return text;
    }

    /**
     * Validate.
     *
     * @param text the text
     * @param dataType the data type
     * @return the string
     * @throws DataException the data exception
     */
    public static boolean validate(String text, String dataType) throws DataException {
        if (text != null) {
            String regEx = getRegEx(dataType);
            if (!Pattern.matches(regEx, text)) {
                return false;
            }
        }
        return true;
    }

}
