package com.quasar.core.util;

import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

import com.quasar.core.exception.InvalidDataException;

/**
 * The Class DateUtil.
 */
public class DateUtil {

    /** The Constant MONTH_SHORT_NAME_FORMAT. */
    public static final SimpleDateFormat MONTH_SHORT_NAME_FORMAT = new SimpleDateFormat("MMM");

    /** The Constant MONTH_SHORT_NAME_YEAR_FORMAT. */
    public static final SimpleDateFormat MONTH_SHORT_NAME_YEAR_FORMAT = new SimpleDateFormat("MMM yyyy");

    /** The Constant MONTH_FULL_NAME_FORMAT. */
    public static final SimpleDateFormat MONTH_FULL_NAME_FORMAT = new SimpleDateFormat("MMMMMMMMMMMMM");
    
    /** The Constant DAY_SHORT_NAME_FORMAT. */
    public static final SimpleDateFormat DAY_SHORT_NAME_FORMAT = new SimpleDateFormat("EEE");
    
    /** The Constant DAY_FULL_NAME_FORMAT. */
    public static final SimpleDateFormat DAY_FULL_NAME_FORMAT = new SimpleDateFormat("EEEEEEEEEE");

    /** The Constant MONTH_NAMES. */
    public static final String[] MONTH_NAMES = new DateFormatSymbols().getMonths();

    /**
     * Check date overlap.
     * 
     * @param fromDateString1 the from date string1
     * @param toDateString1 the to date string1
     * @param fromDateString2 the from date string2
     * @param toDateString2 the to date string2
     * @throws InvalidDataException the invalid data exception
     */
    public static void checkDateOverlap(String fromDateString1,
            String toDateString1, String fromDateString2, String toDateString2)
            throws InvalidDataException {

        Date fromDate1 = ConversionUtil.fromApplicationDate(fromDateString1);
        Date toDate1 = ConversionUtil.fromApplicationDate(toDateString1);
        Date fromDate2 = ConversionUtil.fromApplicationDate(fromDateString2);
        Date toDate2 = ConversionUtil.fromApplicationDate(toDateString2);

        if (fromDate1.equals(fromDate2)) {
            throw new InvalidDataException("'Start Date' is overlapping with another 'Start Date'");
        }
        if (toDate1.equals(toDate2)) {
            throw new InvalidDataException("'End Date' is overlapping with another 'End Date'");
        }
        // From date to check cannot be after the to date to check
        if (fromDate1.after(toDate1)) {
            throw new InvalidDataException("'Start Date' is overlapping with 'End Date'");
        }
        // From date cannot be in between from date to check and to date to check
        if (fromDate1.before(fromDate2) && toDate1.after(fromDate2)) {
            throw new InvalidDataException("Given Date Ranges are overlapping");
        }
        // From date cannot be in between from date to check and to date to check
        if (fromDate1.before(toDate2) && toDate1.after(toDate2)) {
            throw new InvalidDataException("Given Date Ranges are overlapping");
        }
        // From date to check and to date to check cannot be in between the given date range.
        if (fromDate1.after(fromDate2) && toDate1.before(toDate2)) {
            throw new InvalidDataException("'Start Date' and 'End Date' fall between another 'Start Date' and 'End Date'");
        }
        // From date to check and to date to check overlaps the given date range.
        if (fromDate1.before(fromDate2) && toDate1.after(toDate2)) {
            throw new InvalidDataException("'Start Date' and 'End Date' overlap with another 'Start Date' and 'End Date'");
        }
    }

    /**
     * Gets the date.
     *
     * @param date the date
     * @param month the month
     * @param year the year
     * @return the date
     */
    public static Date getDate(int date, int month, int year) {
        Calendar calendar = new GregorianCalendar();
        calendar.set(Calendar.DAY_OF_MONTH, date);
        calendar.set(Calendar.MONTH, month -1);
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    /**
     * Gets the current date.
     *
     * @return the current date
     */
    public static Date getCurrentDate() {
        return Calendar.getInstance().getTime();
    }

    /**
     * Checks if is date in range.
     * 
     * @param holidayDate the holiday date
     * @param startDate the start date
     * @param endDate the end date
     * @return true, if is date in range
     * @throws InvalidDataException the invalid data exception
     */
    public static boolean isDateInRange(Date holidayDate, Date startDate,
            Date endDate) throws InvalidDataException {
        if (holidayDate == null || startDate == null || endDate == null) {
            throw new InvalidDataException("Cannot compare empty dates");
        }
        return (holidayDate.equals(startDate) || holidayDate.equals(endDate) || (holidayDate.after(startDate) && holidayDate.before(endDate)));
    }

    /**
     * Gets the current date ignore hours.
     *
     * @return the current date ignore hours
     */
    public static Date getCurrentDateIgnoreHours() {
        Calendar calendar = new GregorianCalendar();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    /**
     * Gets the current calendar ignore hours.
     *
     * @return the current calendar ignore hours
     */
    public static Calendar getNewCalendarIgnoreHours() {
        Calendar calendar = new GregorianCalendar();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar;
    }

    /**
     * Gets the year from application date.
     * 
     * @param dateString the date string
     * @return the year from application date
     */
    public static int getYearFromApplicationDate(String dateString) {
        if (!StringUtil.isNullOrBlank(dateString)) {
            return Integer.parseInt(dateString.substring(6, 10));
        }
        return 0;
    }

    /**
     * Gets the year from storage date.
     * 
     * @param dateString the date string
     * @return the year from storage date
     */
    public static int getYearFromStorageDate(String dateString) {
        if (!StringUtil.isNullOrBlank(dateString)) {
            return Integer.parseInt(dateString.substring(0, 4));
        }
        return 0;
    }

    /**
     * Gets the readable duration.
     * 
     * @param duration the duration
     * @return the readable duration
     */
    public static String getReadableDuration(long duration) {
        return String.format("%d Minute, %d Seconds",
                TimeUnit.MILLISECONDS.toMinutes(duration),
                TimeUnit.MILLISECONDS.toSeconds(duration)
                        - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS
                                .toMinutes(duration)));
    }

    /**
     * Check from date to date sequence.
     * 
     * @param fromDateString the from date string
     * @param toDateString the to date string
     * @throws InvalidDataException the invalid data exception
     */
    public static void checkFromDateToDateSequence(String fromDateString,
            String toDateString) throws InvalidDataException {
        if (fromDateString != null && toDateString != null) {
            Date fromDate = ConversionUtil.fromApplicationDate(fromDateString);
            Date toDate = ConversionUtil.fromApplicationDate(toDateString);
            if (fromDate.after(toDate)) {
                throw new InvalidDataException("'From Date' cannot be before 'To Date'");
            }
        }
    }

    /**
     * Date diff in months.
     * 
     * @param fromDateString the from date string
     * @param toDateString the to date string
     * @return the int
     * @throws InvalidDataException the invalid data exception
     */
    public static int dateDiffInMonths(String fromDateString,
            String toDateString) throws InvalidDataException {
        Date fromDate = ConversionUtil.fromApplicationDate(fromDateString);
        Date toDate = ConversionUtil.fromApplicationDate(toDateString);
        return dateDiffInMonths(fromDate, toDate);
    }

    /**
     * Date diff in months.
     * 
     * @param fromDate the from date
     * @param toDate the to date
     * @return the int
     * @throws InvalidDataException the invalid data exception
     */
    public static int dateDiffInMonths(Date fromDate,
            Date toDate) throws InvalidDataException {
        if (fromDate.after(toDate)) {
            throw new InvalidDataException("'From Date' cannot be before 'To Date'");
        }
        long diffInMillis = toDate.getTime() - fromDate.getTime();
        return (int) (diffInMillis/(1000L * 60 * 60 * 24 * 30));
    }

    /**
     * Date diff in month numbers.
     * 
     * @param fromDate the from date
     * @param toDate the to date
     * @return the int
     * @throws InvalidDataException the invalid data exception
     */
    public static int dateDiffInMonthNumbers(Date fromDate,
            Date toDate) throws InvalidDataException {
        if (fromDate.after(toDate)) {
            throw new InvalidDataException("'From Date' cannot be before 'To Date'");
        }
        Calendar sDate = Calendar.getInstance();
        Calendar eDate = Calendar.getInstance();
        sDate.setTime(fromDate);
        eDate.setTime(toDate);
        int difInMonths = ((eDate.get(Calendar.YEAR) - sDate.get(Calendar.YEAR)) * 12)
                + (eDate.get(Calendar.MONTH) - sDate.get(Calendar.MONTH))+1;
        return difInMonths;
    }

    /**
     * Checks if is past date.
     * 
     * @param dateString the date string
     * @return true, if is past date
     * @throws InvalidDataException the invalid data exception
     */
    public static boolean isPastDate(String dateString) throws InvalidDataException {
        Date date = ConversionUtil.fromApplicationDate(dateString);
        return date.before(getCurrentDateIgnoreHours());
    }

    /**
     * Checks if is past date.
     * 
     * @param date the date
     * @return true, if is past date
     * @throws InvalidDataException the invalid data exception
     */
    public static boolean isPastDate(Date date) throws InvalidDataException {
        if (date != null){
            return date.before(getCurrentDateIgnoreHours());
        }
        return false;
    }

    /**
     * Checks if is future date.
     * 
     * @param dateString the date string
     * @return true, if is future date
     * @throws InvalidDataException the invalid data exception
     */
    public static boolean isFutureDate(String dateString) throws InvalidDataException {
        Date date = ConversionUtil.fromApplicationDate(dateString);
        return date.after(getCurrentDateIgnoreHours());
    }

    /**
     * Gets the number of dates between.
     * 
     * @param compareDate the compare date
     * @param compateToDate the compate to date
     * @return the number of dates between
     */
    public static int getNumberOfDatesBetween(Date compareDate, Date compateToDate){
        if (compareDate != null && compateToDate != null) {
            Calendar refCalendar = new GregorianCalendar();
            refCalendar.setTime(compareDate);
            Calendar compareCalendar = new GregorianCalendar();
            compareCalendar.setTime(compateToDate);
            return getNumberOfDatesBetween(refCalendar, compareCalendar);
        }
        return 0;
    }

    /**
     * Gets the number of dates between.
     * 
     * @param compareCalendar the compare calendar
     * @param compareToCalendar the compare to calendar
     * @return the number of dates between
     */
    public static int getNumberOfDatesBetween(Calendar compareCalendar, Calendar compareToCalendar) {
        int daysDiff = 0;
        if (compareCalendar != null && compareToCalendar != null) {
            if (compareCalendar.getTime().equals(compareToCalendar.getTime())) {
                daysDiff = 0;
            } else {
                // Decide whether to traverse backward or forward
                boolean forward = (compareCalendar.before(compareToCalendar));
                // Stop when same year and same day of the year is reached
                while (compareCalendar.get(Calendar.DAY_OF_YEAR) != compareToCalendar.get(Calendar.DAY_OF_YEAR)
                        || compareCalendar.get(Calendar.YEAR) != compareToCalendar.get(Calendar.YEAR)) {
                    if (forward) {
                        // Go one more day forward
                        compareCalendar.set(Calendar.DAY_OF_MONTH, compareCalendar.get(Calendar.DAY_OF_MONTH) + 1);
                        daysDiff++;
                    } else {
                        // Go one more day backward
                        compareCalendar.set(Calendar.DAY_OF_MONTH, compareCalendar.get(Calendar.DAY_OF_MONTH) - 1);
                        daysDiff--;
                    }
                }
            }
        }
        return daysDiff;
    }

    /**
     * Gets the month name.
     * 
     * @param monthNumber the month number
     * @return the month name
     */
    public static String getMonthName(int monthNumber) {
        if (monthNumber > 0 && monthNumber < MONTH_NAMES.length) {
            return MONTH_NAMES[monthNumber-1];
        }
        return null;
    }

    public static long getSecondsFromOffset(String offset) {
        /*
        * notation examples = 3d, 30s
        * s    seconds
        * m    minutes
        * h    hours
        * d    days
        * M    months
        * y    years
        * */
        long seconds = 0;
        if (offset != null) {
                if (Pattern.matches("\\d{1}s", offset) || Pattern.matches("\\d{2}s", offset)) {
                        seconds = Long.parseLong(offset.substring(0, offset.indexOf("s")));
                } else if (Pattern.matches("\\d{1}m", offset) || Pattern.matches("\\d{2}m", offset)) {
                        seconds = Long.parseLong(offset.substring(0, offset.indexOf("m"))) * 60;
                } else if (Pattern.matches("\\d{1}h", offset) || Pattern.matches("\\d{2}h", offset)) {
                        seconds = Long.parseLong(offset.substring(0, offset.indexOf("h"))) * 60 * 60;
                } else if (Pattern.matches("\\d{1}d", offset) || Pattern.matches("\\d{2}d", offset)) {
                        seconds = Long.parseLong(offset.substring(0, offset.indexOf("d"))) * 60 * 60 * 24;
                } else if (Pattern.matches("\\d{1}M", offset) || Pattern.matches("\\d{2}M", offset)) {
                        seconds = Long.parseLong(offset.substring(0, offset.indexOf("M"))) * 60 * 60 * 24 * 30;
                } else if (Pattern.matches("\\d{1}y", offset) || Pattern.matches("\\d{2}y", offset)) {
                        seconds = Long.parseLong(offset.substring(0, offset.indexOf("y"))) * 60 * 60 * 24 * 30 * 365;
                }
        }
        return seconds;
    }

    public static long getMillisFromOffset(String offset) {
        long value = getSecondsFromOffset(offset);
        return value * 1000;
    }

    public static long getMinutesFromOffset(String offset) {
        long value = getSecondsFromOffset(offset);
        return (value==0)?value:value/60;
    }

    public static long getHoursFromOffset(String offset) {
        long value = getMinutesFromOffset(offset);
        return (value==0)?value:value/60;
    }

}
