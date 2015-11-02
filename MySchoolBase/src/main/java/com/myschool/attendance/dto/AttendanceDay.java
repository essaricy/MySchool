package com.myschool.attendance.dto;

import java.io.Serializable;

/**
 * The Class AttendanceDay.
 */
public class AttendanceDay implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The date. */
    private int date;

    /** The day name. */
    private String dayName;

    /** The day number in week. */
    private int dayNumberInWeek;

    /** The reference. */
    private AttendanceCode reference;

    /** The assigned. */
    private AttendanceCode assigned;

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
     * Gets the day name.
     * 
     * @return the day name
     */
    public String getDayName() {
        return dayName;
    }

    /**
     * Sets the day name.
     * 
     * @param dayName the new day name
     */
    public void setDayName(String dayName) {
        this.dayName = dayName;
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
     * Gets the reference.
     * 
     * @return the reference
     */
    public AttendanceCode getReference() {
        return reference;
    }

    /**
     * Sets the reference.
     * 
     * @param reference the new reference
     */
    public void setReference(AttendanceCode reference) {
        this.reference = reference;
    }

    /**
     * Gets the assigned.
     * 
     * @return the assigned
     */
    public AttendanceCode getAssigned() {
        return assigned;
    }

    /**
     * Sets the assigned.
     * 
     * @param assigned the new assigned
     */
    public void setAssigned(AttendanceCode assigned) {
        this.assigned = assigned;
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
        retValue.append("AttendanceDay ( ")
            .append(super.toString()).append(SEPARATOR)
            .append("date = ").append(this.date).append(SEPARATOR)
            .append("dayName = ").append(this.dayName).append(SEPARATOR)
            .append("dayNumberInWeek = ").append(this.dayNumberInWeek).append(SEPARATOR)
            .append("reference = ").append(this.reference).append(SEPARATOR)
            .append("assigned = ").append(this.assigned).append(SEPARATOR)
            .append(" )\n");
        return retValue.toString();
    }

}
