package com.myschool.attendance.dto;

import java.io.Serializable;

/**
 * The Class Day.
 */
public class Day implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The date. */
    private int date;

    /** The day number in week. */
    private int dayNumberInWeek;

    /** The day short name. */
    private String dayShortName;

    /** The day full name. */
    private String dayFullName;

    /**
     * Gets the date.
     *
     * @return the date
     */
    public int getDate() {
        return date;
    }

    /**
     * Sets the date.
     *
     * @param date the new date
     */
    public void setDate(int date) {
        this.date = date;
    }

    /**
     * Gets the day number in week.
     * 
     * @return the day number in week
     */
    public int getDayNumberInWeek() {
        return dayNumberInWeek;
    }

    /**
     * Sets the day number in week.
     * 
     * @param dayNumberInWeek the new day number in week
     */
    public void setDayNumberInWeek(int dayNumberInWeek) {
        this.dayNumberInWeek = dayNumberInWeek;
    }

    /**
     * Gets the day short name.
     *
     * @return the day short name
     */
    public String getDayShortName() {
        return dayShortName;
    }

    /**
     * Sets the day short name.
     *
     * @param dayShortName the new day short name
     */
    public void setDayShortName(String dayShortName) {
        this.dayShortName = dayShortName;
    }

    /**
     * Gets the day full name.
     *
     * @return the day full name
     */
    public String getDayFullName() {
        return dayFullName;
    }

    /**
     * Sets the day full name.
     *
     * @param dayFullName the new day full name
     */
    public void setDayFullName(String dayFullName) {
        this.dayFullName = dayFullName;
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
        retValue.append("Day ( ")
            .append(super.toString()).append(SEPARATOR)
            .append("date = ").append(this.date).append(SEPARATOR)
            .append("dayNumberInWeek = ").append(this.dayNumberInWeek).append(SEPARATOR)
            .append("dayShortName = ").append(this.dayShortName).append(SEPARATOR)
            .append("dayFullName = ").append(this.dayFullName).append(SEPARATOR)
            .append(" )\n");
        return retValue.toString();
    }

}
