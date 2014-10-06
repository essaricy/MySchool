package com.myschool.employee.dto;

import java.io.Serializable;

/**
 * The Class EmployeeExperience.
 */
public class EmployeeExperience implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The experience id. */
    private int experienceId;

    /** The employer. */
    private String employer;

    /** The job title. */
    private String jobTitle;

    /** The from date. */
    private String fromDate;

    /** The to date. */
    private String toDate;

    /** The experiece in month. */
    private int experieceInMonth;

    /** The employee. */
    private EmployeeDto employee;

    /**
     * Gets the experience id.
     * 
     * @return the experience id
     */
    public int getExperienceId() {
        return experienceId;
    }

    /**
     * Sets the experience id.
     * 
     * @param experienceId the new experience id
     */
    public void setExperienceId(int experienceId) {
        this.experienceId = experienceId;
    }

    /**
     * Gets the employer.
     * 
     * @return the employer
     */
    public String getEmployer() {
        return employer;
    }

    /**
     * Sets the employer.
     * 
     * @param employer the new employer
     */
    public void setEmployer(String employer) {
        this.employer = employer;
    }

    /**
     * Gets the job title.
     * 
     * @return the job title
     */
    public String getJobTitle() {
        return jobTitle;
    }

    /**
     * Sets the job title.
     * 
     * @param jobTitle the new job title
     */
    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    /**
     * Gets the from date.
     * 
     * @return the from date
     */
    public String getFromDate() {
        return fromDate;
    }

    /**
     * Sets the from date.
     * 
     * @param fromDate the new from date
     */
    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    /**
     * Gets the to date.
     * 
     * @return the to date
     */
    public String getToDate() {
        return toDate;
    }

    /**
     * Sets the to date.
     * 
     * @param toDate the new to date
     */
    public void setToDate(String toDate) {
        this.toDate = toDate;
    }

    /**
     * Gets the experiece in month.
     * 
     * @return the experiece in month
     */
    public int getExperieceInMonth() {
        return experieceInMonth;
    }

    /**
     * Sets the experiece in month.
     * 
     * @param experieceInMonth the new experiece in month
     */
    public void setExperieceInMonth(int experieceInMonth) {
        this.experieceInMonth = experieceInMonth;
    }

    /**
     * Gets the employee.
     * 
     * @return the employee
     */
    public EmployeeDto getEmployee() {
        return employee;
    }

    /**
     * Sets the employee.
     * 
     * @param employee the new employee
     */
    public void setEmployee(EmployeeDto employee) {
        this.employee = employee;
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
        retValue.append("EmployeeExperience ( ")
            .append(super.toString()).append(SEPARATOR)
            .append("experienceId = ").append(this.experienceId).append(SEPARATOR)
            .append("employer = ").append(this.employer).append(SEPARATOR)
            .append("jobTitle = ").append(this.jobTitle).append(SEPARATOR)
            .append("fromDate = ").append(this.fromDate).append(SEPARATOR)
            .append("toDate = ").append(this.toDate).append(SEPARATOR)
            .append("experieceInMonth = ").append(this.experieceInMonth).append(SEPARATOR)
            .append("employee = ").append(this.employee).append(SEPARATOR)
            .append(" )\n");
        return retValue.toString();
    }

}
