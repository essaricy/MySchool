package com.myschool.attendance.dto;

import java.io.Serializable;
import java.util.List;

/**
 * The Class AttendanceMonth.
 */
public class AttendanceMonth implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The attendance month id. */
    private int attendanceMonthId;

    /** The year. */
    private int year;

    /** The month number. */
    private int monthNumber;

    /** The month name. */
    private String monthName;

    /** The locked. */
    private boolean locked;

    /** The attendance days. */
    private List<AttendanceDay> attendanceDays;

    /**
     * Gets the attendance month id.
     * 
     * @return the attendance month id
     */
    public int getAttendanceMonthId() {
        return attendanceMonthId;
    }

    /**
     * Sets the attendance month id.
     * 
     * @param attendanceMonthId the new attendance month id
     */
    public void setAttendanceMonthId(int attendanceMonthId) {
        this.attendanceMonthId = attendanceMonthId;
    }

    /**
     * Gets the year.
     * 
     * @return the year
     */
    public int getYear() {
        return year;
    }

    /**
     * Sets the year.
     * 
     * @param year the new year
     */
    public void setYear(int year) {
        this.year = year;
    }

    /**
     * Gets the month number.
     * 
     * @return the month number
     */
    public int getMonthNumber() {
        return monthNumber;
    }

    /**
     * Sets the month number.
     * 
     * @param monthNumber the new month number
     */
    public void setMonthNumber(int monthNumber) {
        this.monthNumber = monthNumber;
    }

    /**
     * Gets the month name.
     * 
     * @return the month name
     */
    public String getMonthName() {
        return monthName;
    }

    /**
     * Sets the month name.
     * 
     * @param monthName the new month name
     */
    public void setMonthName(String monthName) {
        this.monthName = monthName;
    }

    /**
     * Checks if is locked.
     * 
     * @return true, if is locked
     */
    public boolean isLocked() {
        return locked;
    }

    /**
     * Sets the locked.
     * 
     * @param locked the new locked
     */
    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    /**
     * Gets the attendance days.
     * 
     * @return the attendance days
     */
    public List<AttendanceDay> getAttendanceDays() {
        return attendanceDays;
    }

    /**
     * Sets the attendance days.
     * 
     * @param attendanceDays the new attendance days
     */
    public void setAttendanceDays(List<AttendanceDay> attendanceDays) {
        this.attendanceDays = attendanceDays;
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
        retValue.append("AttendanceMonth ( ")
            .append(super.toString()).append(SEPARATOR)
            .append("attendanceMonthId = ").append(this.attendanceMonthId).append(SEPARATOR)
            .append("year = ").append(this.year).append(SEPARATOR)
            .append("monthNumber = ").append(this.monthNumber).append(SEPARATOR)
            .append("monthName = ").append(this.monthName).append(SEPARATOR)
            .append("locked = ").append(this.locked).append(SEPARATOR)
            .append("attendanceDays = ").append(this.attendanceDays).append(SEPARATOR)
            .append(" )\n");
        return retValue.toString();
    }

}
