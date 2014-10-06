package com.myschool.academic.dto;

import java.io.Serializable;

/**
 * The Class HolidaySearchCriteria.
 */
public class HolidaySearchCriteria implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The academic year. */
    private String academicYear;

    /** The start date. */
    private String startDate;

    /** The end date. */
    private String endDate;

    /**
     * Gets the academic year.
     * 
     * @return the academic year
     */
    public String getAcademicYear() {
        return academicYear;
    }

    /**
     * Sets the academic year.
     * 
     * @param academicYear the new academic year
     */
    public void setAcademicYear(String academicYear) {
        this.academicYear = academicYear;
    }

    /**
     * Gets the start date.
     * 
     * @return the start date
     */
    public String getStartDate() {
        return startDate;
    }

    /**
     * Sets the start date.
     * 
     * @param startDate the new start date
     */
    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    /**
     * Gets the end date.
     * 
     * @return the end date
     */
    public String getEndDate() {
        return endDate;
    }

    /**
     * Sets the end date.
     * 
     * @param endDate the new end date
     */
    public void setEndDate(String endDate) {
        this.endDate = endDate;
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
        retValue.append("HolidaySearchCriteria ( ")
            .append(super.toString()).append(SEPARATOR)
            .append("academicYear = ").append(this.academicYear).append(SEPARATOR)
            .append("startDate = ").append(this.startDate).append(SEPARATOR)
            .append("endDate = ").append(this.endDate).append(SEPARATOR)
            .append(" )\n");
        return retValue.toString();
    }

}
