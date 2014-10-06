package com.myschool.attendance.dto;

import java.io.Serializable;


/**
 * The Class DayAttendance.
 */
public class DayAttendance extends AttendanceDto implements Serializable {

    public static final int INVALID = -99999;

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The days in difference. */
    private int daysInDifference;

    /** The day. */
    private Day day;

    /** The attendance code. */
    private AttendanceCode attendanceCode;

    /**
     * Gets the days in difference.
     * 
     * @return the days in difference
     */
    public int getDaysInDifference() {
        return daysInDifference;
    }

    /**
     * Sets the days in difference.
     * 
     * @param daysInDifference the new days in difference
     */
    public void setDaysInDifference(int daysInDifference) {
        this.daysInDifference = daysInDifference;
    }

    /**
     * Gets the day.
     * 
     * @return the day
     */
    public Day getDay() {
        return day;
    }

    /**
     * Sets the day.
     * 
     * @param day the new day
     */
    public void setDay(Day day) {
        this.day = day;
    }

    /**
     * Gets the attendance code.
     * 
     * @return the attendance code
     */
    public AttendanceCode getAttendanceCode() {
        return attendanceCode;
    }

    /**
     * Sets the attendance code.
     * 
     * @param attendanceCode the new attendance code
     */
    public void setAttendanceCode(AttendanceCode attendanceCode) {
        this.attendanceCode = attendanceCode;
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
        retValue.append("DayAttendance ( ")
            .append(super.toString()).append(SEPARATOR)
            .append("daysInDifference = ").append(this.daysInDifference).append(SEPARATOR)
            .append("day = ").append(this.day).append(SEPARATOR)
            .append("attendanceCode = ").append(this.attendanceCode).append(SEPARATOR)
            .append(" )\n");
        return retValue.toString();
    }

}
