package com.myschool.common.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.myschool.application.constants.ApplicationConstants;

/**
 * The Class ConversionUtil.
 */
public class ConversionUtil {

    /** The Constant STORAGE_DATE_FORMAT. */
    private static final String STORAGE_DATE_FORMAT = "yyyy-MM-dd";

    /** The Constant APPLICATION_DATE_FORMAT. */
    private static final String APPLICATION_DATE_FORMAT = "dd/MM/yyyy";

    /** The Constant APPLICATION_DATE_FORMAT_DD_MM. */
    private static final String APPLICATION_DATE_FORMAT_DD_MM = "dd/MM";

    /** The Constant REPORT_DATE_FORMAT. */
    private static final String REPORT_DATE_FORMAT = "MMM dd, yyyy hh:mm a";

    /** The Constant STORAGE_SIMPLE_DATE_FORMAT. */
    private static final SimpleDateFormat STORAGE_SIMPLE_DATE_FORMAT = new SimpleDateFormat(STORAGE_DATE_FORMAT);

    /** The Constant APPLICATION_SIMPLE_DATE_FORMAT. */
    private static final SimpleDateFormat APPLICATION_SIMPLE_DATE_FORMAT = new SimpleDateFormat(APPLICATION_DATE_FORMAT);

    /** The Constant APPLICATION_DD_MM_SIMPLE_DATE_FORMAT. */
    private static final SimpleDateFormat APPLICATION_DD_MM_SIMPLE_DATE_FORMAT = new SimpleDateFormat(APPLICATION_DATE_FORMAT_DD_MM);

    /** The Constant REPORT_SIMPLE_DATE_FORMAT. */
    private static final SimpleDateFormat REPORT_SIMPLE_DATE_FORMAT = new SimpleDateFormat(REPORT_DATE_FORMAT);

    /**
     * To yn.
     * 
     * @param boolValue the bool value
     * @return the string
     */
    public static String toYN(boolean boolValue) {
        return (boolValue) ? ApplicationConstants.Y : ApplicationConstants.N;
    }

    /**
     * To boolean.
     * 
     * @param stringValue the string value
     * @return true, if successful
     */
    public static boolean toBoolean(String stringValue) {
        return (stringValue != null && (stringValue.equalsIgnoreCase(ApplicationConstants.TRUE)
                || stringValue.equalsIgnoreCase(ApplicationConstants.Y)
                || stringValue.equalsIgnoreCase(ApplicationConstants.ON)
                || stringValue.equalsIgnoreCase(ApplicationConstants.YES)));
    }

    /**
     * To application date.
     * 
     * @param timeInMillis the time in millis
     * @return the string
     */
    public static String toApplicationDate(long timeInMillis) {
        return APPLICATION_SIMPLE_DATE_FORMAT.format(new Date(timeInMillis));
    }

    /**
     * To application date no year.
     * 
     * @param timeInMillis the time in millis
     * @return the string
     */
    public static String toApplicationDateNoYear(long timeInMillis) {
        return APPLICATION_DD_MM_SIMPLE_DATE_FORMAT.format(new Date(timeInMillis));
    }

    /**
     * To storage date.
     * 
     * @param timeInMillis the time in millis
     * @return the string
     */
    public static String toStorageDate(long timeInMillis) {
        return STORAGE_SIMPLE_DATE_FORMAT.format(new Date(timeInMillis));
    }

    /**
     * From application date to storage date.
     * 
     * @param dateString the date string
     * @return the java.sql. date
     */
    public static java.sql.Date fromApplicationDateToStorageDate(String dateString) {
        Date date = fromApplicationDate(dateString);
        if (date != null) {
            return new java.sql.Date(date.getTime());
        }
        return null;
    }

    /**
     * From application date.
     * 
     * @param dateString the date string
     * @return the date
     */
    public static Date fromApplicationDate(String dateString) {
        return toDate(dateString, APPLICATION_DATE_FORMAT);
    }

    /**
     * From storage date.
     * 
     * @param dateString the date string
     * @return the date
     */
    public static Date fromStorageDate(String dateString) {
        return toDate(dateString, STORAGE_DATE_FORMAT);
    }

    /**
     * To date.
     * 
     * @param dateString the date string
     * @param inDateFormat the in date format
     * @return the date
     */
    private static Date toDate(String dateString, String inDateFormat) {
        Date convertedDate = null;
        if (!StringUtil.isNullOrBlank(dateString) && !StringUtil.isNullOrBlank(inDateFormat)) {
            if (inDateFormat.equals(APPLICATION_DATE_FORMAT)) {
                int date = Integer.parseInt(dateString.substring(0, 2));
                int month = Integer.parseInt(dateString.substring(3, 5));
                int year = Integer.parseInt(dateString.substring(6, 10));
                convertedDate = DateUtil.getDate(date, month, year);
            } else if (inDateFormat.equals(STORAGE_DATE_FORMAT)) {
                int year = Integer.parseInt(dateString.substring(0, 4));
                int month = Integer.parseInt(dateString.substring(5, 7));
                int date = Integer.parseInt(dateString.substring(8, 10));
                convertedDate = DateUtil.getDate(date, month, year);
            }
        }
        return convertedDate;
    }

    /**
     * From storage date.
     * 
     * @param timeInMillis the time in millis
     * @return the string
     */
    public static String fromStorageDate(long timeInMillis) {
        return toStringDate(timeInMillis, STORAGE_DATE_FORMAT);
    }

    /**
     * From application date.
     * 
     * @param timeInMillis the time in millis
     * @return the string
     */
    public static String fromApplicationDate(long timeInMillis) {
        return toStringDate(timeInMillis, APPLICATION_DATE_FORMAT);
    }

    /**
     * To string date.
     * 
     * @param timeInMillis the time in millis
     * @param outDateFormat the out date format
     * @return the string
     */
    private static String toStringDate(long timeInMillis, String outDateFormat) {
        Date date = new Date(timeInMillis);
        if (outDateFormat == APPLICATION_DATE_FORMAT) {
            return APPLICATION_SIMPLE_DATE_FORMAT.format(date);
        } else if (outDateFormat == STORAGE_DATE_FORMAT) {
            return STORAGE_SIMPLE_DATE_FORMAT.format(date);
        }
        return null;
    }

    /**
     * To storage date from application date.
     * 
     * @param dateString the date string
     * @return the string
     */
    public static String toStorageDateFromApplicationDate(String dateString) {
        return toStringDate(dateString, APPLICATION_DATE_FORMAT, STORAGE_DATE_FORMAT);
    }

    /**
     * To application date from storage date.
     * 
     * @param dateString the date string
     * @return the string
     */
    public static String toApplicationDateFromStorageDate(String dateString) {
        return toStringDate(dateString, STORAGE_DATE_FORMAT, APPLICATION_DATE_FORMAT);
    }

    /**
     * To string date.
     * 
     * @param dateString the date string
     * @param inDateFormat the in date format
     * @param outDateFormat the out date format
     * @return the string
     */
    private static String toStringDate(String dateString, String inDateFormat,
            String outDateFormat) {
        String convertedDate = null;
        Date date = toDate(dateString, inDateFormat);
        if (date != null) {
            if (outDateFormat == APPLICATION_DATE_FORMAT) {
                convertedDate = APPLICATION_SIMPLE_DATE_FORMAT.format(date);
            } else if (outDateFormat == STORAGE_DATE_FORMAT) {
                convertedDate = STORAGE_SIMPLE_DATE_FORMAT.format(date);
            }
        }
        return convertedDate;
    }

    /**
     * To report date.
     * 
     * @param date the date
     * @return the string
     */
    public static String toReportDate(Date date) {
        if (date != null) {
            return REPORT_SIMPLE_DATE_FORMAT.format(date);
        }
        return null;
    }

}
