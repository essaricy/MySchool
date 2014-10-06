package com.myschool.attendance.dto;

import java.io.Serializable;

/**
 * The Class AttendanceDto.
 */
public class AttendanceDto implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The attendance year. */
    private int attendanceYear;

    /**
     * Gets the attendance year.
     *
     * @return the attendance year
     */
    public int getAttendanceYear() {
        return attendanceYear;
    }

    /**
     * Sets the attendance year.
     *
     * @param attendanceYear the new attendance year
     */
    public void setAttendanceYear(int attendanceYear) {
        this.attendanceYear = attendanceYear;
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
        retValue.append("AttendanceDto ( ")
            .append(super.toString()).append(SEPARATOR)
            .append("attendanceYear = ").append(this.attendanceYear).append(SEPARATOR)
            .append(" )\n");
        return retValue.toString();
    }

}
