package com.myschool.attendance.dto;

import java.io.Serializable;
import java.util.List;

/**
 * The Class Month.
 */
public class Month implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The number. */
    private int number;

    /** The short name. */
    private String shortName;

    /** The full name. */
    private String fullName;

    /** The days. */
    private List<Day> days;

    /**
     * Gets the number.
     *
     * @return the number
     */
    public int getNumber() {
        return number;
    }

    /**
     * Sets the number.
     *
     * @param number the new number
     */
    public void setNumber(int number) {
        this.number = number;
    }

    /**
     * Gets the short name.
     *
     * @return the short name
     */
    public String getShortName() {
        return shortName;
    }

    /**
     * Sets the short name.
     *
     * @param shortName the new short name
     */
    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    /**
     * Gets the full name.
     *
     * @return the full name
     */
    public String getFullName() {
        return fullName;
    }

    /**
     * Sets the full name.
     *
     * @param fullName the new full name
     */
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    /**
     * Gets the days.
     *
     * @return the days
     */
    public List<Day> getDays() {
        return days;
    }

    /**
     * Sets the days.
     *
     * @param days the new days
     */
    public void setDays(List<Day> days) {
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
        retValue.append("Month ( ")
            .append(super.toString()).append(SEPARATOR)
            .append("days = ").append(this.days).append(SEPARATOR)
            .append("fullName = ").append(this.fullName).append(SEPARATOR)
            .append("number = ").append(this.number).append(SEPARATOR)
            .append("shortName = ").append(this.shortName).append(SEPARATOR)
            .append(" )\n");
        return retValue.toString();
    }

}
