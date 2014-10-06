package com.myschool.attendance.dto;

import java.io.Serializable;
import java.util.List;

import com.myschool.clazz.dto.RegisteredClassDto;

/**
 * The Class ReferenceAttendanceDto.
 */
public class ReferenceAttendanceDto implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The reference attendance id. */
    private int referenceAttendanceId;

    /** The registered class. */
    private RegisteredClassDto registeredClass;

    /** The year. */
    private int year;

    /** The month. */
    private int month;

    /** The day attendances. */
    private List<DayAttendance> dayAttendances;

    /**
     * Gets the reference attendance id.
     *
     * @return the reference attendance id
     */
    public int getReferenceAttendanceId() {
        return referenceAttendanceId;
    }

    /**
     * Sets the reference attendance id.
     *
     * @param referenceAttendanceId the new reference attendance id
     */
    public void setReferenceAttendanceId(int referenceAttendanceId) {
        this.referenceAttendanceId = referenceAttendanceId;
    }

    /**
     * Gets the registered class.
     *
     * @return the registered class
     */
    public RegisteredClassDto getRegisteredClass() {
        return registeredClass;
    }

    /**
     * Sets the registered class.
     *
     * @param registeredClass the new registered class
     */
    public void setRegisteredClass(RegisteredClassDto registeredClass) {
        this.registeredClass = registeredClass;
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
     * Gets the month.
     *
     * @return the month
     */
    public int getMonth() {
        return month;
    }

    /**
     * Sets the month.
     *
     * @param month the new month
     */
    public void setMonth(int month) {
        this.month = month;
    }

    /**
     * Gets the day attendances.
     *
     * @return the day attendances
     */
    public List<DayAttendance> getDayAttendances() {
        return dayAttendances;
    }

    /**
     * Sets the day attendances.
     *
     * @param dayAttendances the new day attendances
     */
    public void setDayAttendances(List<DayAttendance> dayAttendances) {
        this.dayAttendances = dayAttendances;
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
        retValue.append("ReferenceAttendanceDto ( ")
            .append(super.toString()).append(SEPARATOR)
            .append("dayAttendances = ").append(this.dayAttendances).append(SEPARATOR)
            .append("month = ").append(this.month).append(SEPARATOR)
            .append("referenceAttendanceId = ").append(this.referenceAttendanceId).append(SEPARATOR)
            .append("registeredClass = ").append(this.registeredClass).append(SEPARATOR)
            .append("year = ").append(this.year).append(SEPARATOR)
            .append(" )\n");
        return retValue.toString();
    }

}
