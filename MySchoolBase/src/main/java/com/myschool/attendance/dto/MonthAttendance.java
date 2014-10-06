package com.myschool.attendance.dto;

import java.io.Serializable;
import java.util.List;


/**
 * The Class MonthAttendance.
 */
public class MonthAttendance extends AttendanceDto implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The month attendance id. */
    private int monthAttendanceId;

    /** The number of general holidays. */
    private int numberOfGeneralHolidays;

    /** The number of declared holidays. */
    private int numberOfDeclaredHolidays;
    
    /** The number of leaves. */
    private int numberOfLeaves;
    
    /** The number of half days. */
    private int numberOfHalfDays;
    
    /** The number of presents. */
    private int numberOfPresents;
    
    /** The number of absents. */
    private int numberOfAbsents;

    /** The month. */
    private Month month;

    /** The day attendances. */
    private List<DayAttendance> dayAttendances;

    /**
     * Gets the month attendance id.
     * 
     * @return the month attendance id
     */
    public int getMonthAttendanceId() {
        return monthAttendanceId;
    }

    /**
     * Sets the month attendance id.
     * 
     * @param monthAttendanceId the new month attendance id
     */
    public void setMonthAttendanceId(int monthAttendanceId) {
        this.monthAttendanceId = monthAttendanceId;
    }

    /**
     * Gets the number of general holidays.
     * 
     * @return the number of general holidays
     */
    public int getNumberOfGeneralHolidays() {
        return numberOfGeneralHolidays;
    }

    /**
     * Sets the number of general holidays.
     * 
     * @param numberOfGeneralHolidays the new number of general holidays
     */
    public void setNumberOfGeneralHolidays(int numberOfGeneralHolidays) {
        this.numberOfGeneralHolidays = numberOfGeneralHolidays;
    }

    /**
     * Gets the number of declared holidays.
     * 
     * @return the number of declared holidays
     */
    public int getNumberOfDeclaredHolidays() {
        return numberOfDeclaredHolidays;
    }

    /**
     * Sets the number of declared holidays.
     * 
     * @param numberOfDeclaredHolidays the new number of declared holidays
     */
    public void setNumberOfDeclaredHolidays(int numberOfDeclaredHolidays) {
        this.numberOfDeclaredHolidays = numberOfDeclaredHolidays;
    }

    /**
     * Gets the number of leaves.
     * 
     * @return the number of leaves
     */
    public int getNumberOfLeaves() {
        return numberOfLeaves;
    }

    /**
     * Sets the number of leaves.
     * 
     * @param numberOfLeaves the new number of leaves
     */
    public void setNumberOfLeaves(int numberOfLeaves) {
        this.numberOfLeaves = numberOfLeaves;
    }

    /**
     * Gets the number of half days.
     * 
     * @return the number of half days
     */
    public int getNumberOfHalfDays() {
        return numberOfHalfDays;
    }

    /**
     * Sets the number of half days.
     * 
     * @param numberOfHalfDays the new number of half days
     */
    public void setNumberOfHalfDays(int numberOfHalfDays) {
        this.numberOfHalfDays = numberOfHalfDays;
    }

    /**
     * Gets the number of presents.
     * 
     * @return the number of presents
     */
    public int getNumberOfPresents() {
        return numberOfPresents;
    }

    /**
     * Sets the number of presents.
     * 
     * @param numberOfPresents the new number of presents
     */
    public void setNumberOfPresents(int numberOfPresents) {
        this.numberOfPresents = numberOfPresents;
    }

    /**
     * Gets the number of absents.
     * 
     * @return the number of absents
     */
    public int getNumberOfAbsents() {
        return numberOfAbsents;
    }

    /**
     * Sets the number of absents.
     * 
     * @param numberOfAbsents the new number of absents
     */
    public void setNumberOfAbsents(int numberOfAbsents) {
        this.numberOfAbsents = numberOfAbsents;
    }

    /**
     * Gets the month.
     * 
     * @return the month
     */
    public Month getMonth() {
        return month;
    }

    /**
     * Sets the month.
     * 
     * @param month the new month
     */
    public void setMonth(Month month) {
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
        retValue.append("MonthAttendance ( ")
            .append(super.toString()).append(SEPARATOR)
            .append("monthAttendanceId = ").append(this.monthAttendanceId).append(SEPARATOR)
            .append("numberOfGeneralHolidays = ").append(this.numberOfGeneralHolidays).append(SEPARATOR)
            .append("numberOfDeclaredHolidays = ").append(this.numberOfDeclaredHolidays).append(SEPARATOR)
            .append("numberOfLeaves = ").append(this.numberOfLeaves).append(SEPARATOR)
            .append("numberOfHalfDays = ").append(this.numberOfHalfDays).append(SEPARATOR)
            .append("numberOfPresents = ").append(this.numberOfPresents).append(SEPARATOR)
            .append("numberOfAbsents = ").append(this.numberOfAbsents).append(SEPARATOR)
            .append("month = ").append(this.month).append(SEPARATOR)
            .append("dayAttendances = ").append(this.dayAttendances).append(SEPARATOR)
            .append(" )\n");
        return retValue.toString();
    }

}
