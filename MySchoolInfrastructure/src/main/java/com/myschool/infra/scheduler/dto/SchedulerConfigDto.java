package com.myschool.infra.scheduler.dto;

import java.io.Serializable;

/**
 * The Class SchedulerConfigDto.
 */
public class SchedulerConfigDto implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The name. */
    private String name;

    /** The class name. */
    private String className;

    /** The year. */
    private String year;

    /** The month. */
    private String month;

    /** The date. */
    private String date;

    /** The hour. */
    private String hour;

    /** The minute. */
    private String minute;

    /** The second. */
    private String second;

    /** The days. */
    private String days;

    /**
     * Gets the name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the class name.
     *
     * @return the class name
     */
    public String getClassName() {
        return className;
    }

    /**
     * Gets the year.
     *
     * @return the year
     */
    public String getYear() {
        return year;
    }

    /**
     * Gets the month.
     *
     * @return the month
     */
    public String getMonth() {
        return month;
    }

    /**
     * Gets the date.
     *
     * @return the date
     */
    public String getDate() {
        return date;
    }

    /**
     * Gets the hour.
     *
     * @return the hour
     */
    public String getHour() {
        return hour;
    }

    /**
     * Gets the minute.
     *
     * @return the minute
     */
    public String getMinute() {
        return minute;
    }

    /**
     * Gets the second.
     *
     * @return the second
     */
    public String getSecond() {
        return second;
    }

    /**
     * Gets the days.
     *
     * @return the days
     */
    public String getDays() {
        return days;
    }

    /**
     * Sets the name.
     *
     * @param name the new name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Sets the class name.
     *
     * @param className the new class name
     */
    public void setClassName(String className) {
        this.className = className;
    }

    /**
     * Sets the year.
     *
     * @param year the new year
     */
    public void setYear(String year) {
        this.year = year;
    }

    /**
     * Sets the month.
     *
     * @param month the new month
     */
    public void setMonth(String month) {
        this.month = month;
    }

    /**
     * Sets the date.
     *
     * @param date the new date
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * Sets the hour.
     *
     * @param hour the new hour
     */
    public void setHour(String hour) {
        this.hour = hour;
    }

    /**
     * Sets the minute.
     *
     * @param minute the new minute
     */
    public void setMinute(String minute) {
        this.minute = minute;
    }

    /**
     * Sets the second.
     *
     * @param second the new second
     */
    public void setSecond(String second) {
        this.second = second;
    }

    /**
     * Sets the days.
     *
     * @param days the new days
     */
    public void setDays(String days) {
        this.days = days;
    }

    /**
     * Constructs a <code>String</code> with all attributes
     * in name = value format.
     *
     * @return a <code>String</code> representation 
     * of this object.
     */
    public String toString() {
        final String SEPARATOR = ", ";
        StringBuilder retValue = new StringBuilder();
        retValue.append("SchedulerConfigDto ( ")
            .append(super.toString()).append(SEPARATOR)
            .append("className = ").append(this.className).append(SEPARATOR)
            .append("date = ").append(this.date).append(SEPARATOR)
            .append("days = ").append(this.days).append(SEPARATOR)
            .append("hour = ").append(this.hour).append(SEPARATOR)
            .append("minute = ").append(this.minute).append(SEPARATOR)
            .append("month = ").append(this.month).append(SEPARATOR)
            .append("name = ").append(this.name).append(SEPARATOR)
            .append("second = ").append(this.second).append(SEPARATOR)
            .append("year = ").append(this.year).append(SEPARATOR)
            .append(" )\n");
        return retValue.toString();
    }

}
