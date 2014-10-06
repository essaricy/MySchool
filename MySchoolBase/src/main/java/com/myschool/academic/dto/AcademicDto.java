package com.myschool.academic.dto;

import java.io.Serializable;

/**
 * The Class AcademicDto.
 */
public class AcademicDto implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The academic year name. */
    private String academicYearName;

    /** The academic year start date. */
    private String academicYearStartDate;

    /** The academic year end date. */
    private String academicYearEndDate;

    /**
     * Gets the academic year name.
     *
     * @return the academic year name
     */
    public String getAcademicYearName() {
        return academicYearName;
    }

    /**
     * Sets the academic year name.
     *
     * @param academicYearName the new academic year name
     */
    public void setAcademicYearName(String academicYearName) {
        this.academicYearName = academicYearName;
    }

    /**
     * Gets the academic year start date.
     *
     * @return the academic year start date
     */
    public String getAcademicYearStartDate() {
        return academicYearStartDate;
    }

    /**
     * Sets the academic year start date.
     *
     * @param academicYearStartDate the new academic year start date
     */
    public void setAcademicYearStartDate(String academicYearStartDate) {
        this.academicYearStartDate = academicYearStartDate;
    }

    /**
     * Gets the academic year end date.
     *
     * @return the academic year end date
     */
    public String getAcademicYearEndDate() {
        return academicYearEndDate;
    }

    /**
     * Sets the academic year end date.
     *
     * @param academicYearEndDate the new academic year end date
     */
    public void setAcademicYearEndDate(String academicYearEndDate) {
        this.academicYearEndDate = academicYearEndDate;
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
        retValue.append("AcademicDto ( ")
            .append(super.toString()).append(SEPARATOR)
            .append("academicYearEndDate = ").append(this.academicYearEndDate).append(SEPARATOR)
            .append("academicYearName = ").append(this.academicYearName).append(SEPARATOR)
            .append("academicYearStartDate = ").append(this.academicYearStartDate).append(SEPARATOR)
            .append(" )\n");
        return retValue.toString();
    }

}
