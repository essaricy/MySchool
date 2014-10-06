package com.myschool.attendance.dto;

import java.io.Serializable;
import java.util.List;


/**
 * The Class YearAttendance.
 */
public class YearAttendance extends AttendanceDto implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The number of absents. */
    private int numberOfAbsents;

    /** The number of presents. */
    private int numberOfPresents;

    /** The number of general holidays. */
    private int numberOfGeneralHolidays;

    /** The number of declared holidays. */
    private int numberOfDeclaredHolidays;

    /** The number of holidays. */
    private int numberOfHolidays;

    /** The number of leaves. */
    private int numberOfLeaves;

    /** The year. */
    private Year year;

    /** The month attendances. */
    private List<MonthAttendance> monthAttendances;

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
     * Gets the number of holidays.
     *
     * @return the number of holidays
     */
    public int getNumberOfHolidays() {
        return numberOfHolidays;
    }

    /**
     * Sets the number of holidays.
     *
     * @param numberOfHolidays the new number of holidays
     */
    public void setNumberOfHolidays(int numberOfHolidays) {
        this.numberOfHolidays = numberOfHolidays;
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
     * Gets the year.
     *
     * @return the year
     */
    public Year getYear() {
        return year;
    }

    /**
     * Sets the year.
     *
     * @param year the new year
     */
    public void setYear(Year year) {
        this.year = year;
    }

    /**
     * Gets the month attendances.
     *
     * @return the month attendances
     */
    public List<MonthAttendance> getMonthAttendances() {
        return monthAttendances;
    }

    /**
     * Sets the month attendances.
     *
     * @param monthAttendances the new month attendances
     */
    public void setMonthAttendances(List<MonthAttendance> monthAttendances) {
        this.monthAttendances = monthAttendances;
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
        retValue.append("YearAttendance ( ")
            .append(super.toString()).append(SEPARATOR)
            .append("monthAttendances = ").append(this.monthAttendances).append(SEPARATOR)
            .append("numberOfAbsents = ").append(this.numberOfAbsents).append(SEPARATOR)
            .append("numberOfDeclaredHolidays = ").append(this.numberOfDeclaredHolidays).append(SEPARATOR)
            .append("numberOfGeneralHolidays = ").append(this.numberOfGeneralHolidays).append(SEPARATOR)
            .append("numberOfHolidays = ").append(this.numberOfHolidays).append(SEPARATOR)
            .append("numberOfLeaves = ").append(this.numberOfLeaves).append(SEPARATOR)
            .append("numberOfPresents = ").append(this.numberOfPresents).append(SEPARATOR)
            .append("year = ").append(this.year).append(SEPARATOR)
            .append(" )\n");
        return retValue.toString();
    }

}
