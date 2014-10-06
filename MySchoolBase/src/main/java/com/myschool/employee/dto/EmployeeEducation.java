package com.myschool.employee.dto;

import java.io.Serializable;

/**
 * The Class EmployeeEducation.
 */
public class EmployeeEducation implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The education id. */
    private int educationId;

    /** The degree. */
    private String degree;

    /** The specialization. */
    private String specialization;

    /** The year of graduation. */
    private int yearOfGraduation;

    /** The percentage. */
    private int percentage;

    /** The college. */
    private String college;

    /** The university. */
    private String university;

    /** The employee. */
    private EmployeeDto employee;

    /**
     * Gets the education id.
     * 
     * @return the education id
     */
    public int getEducationId() {
        return educationId;
    }

    /**
     * Sets the education id.
     * 
     * @param educationId the new education id
     */
    public void setEducationId(int educationId) {
        this.educationId = educationId;
    }

    /**
     * Gets the degree.
     * 
     * @return the degree
     */
    public String getDegree() {
        return degree;
    }

    /**
     * Sets the degree.
     * 
     * @param degree the new degree
     */
    public void setDegree(String degree) {
        this.degree = degree;
    }

    /**
     * Gets the specialization.
     * 
     * @return the specialization
     */
    public String getSpecialization() {
        return specialization;
    }

    /**
     * Sets the specialization.
     * 
     * @param specialization the new specialization
     */
    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    /**
     * Gets the year of graduation.
     * 
     * @return the year of graduation
     */
    public int getYearOfGraduation() {
        return yearOfGraduation;
    }

    /**
     * Sets the year of graduation.
     * 
     * @param yearOfGraduation the new year of graduation
     */
    public void setYearOfGraduation(int yearOfGraduation) {
        this.yearOfGraduation = yearOfGraduation;
    }

    /**
     * Gets the percentage.
     * 
     * @return the percentage
     */
    public int getPercentage() {
        return percentage;
    }

    /**
     * Sets the percentage.
     * 
     * @param percentage the new percentage
     */
    public void setPercentage(int percentage) {
        this.percentage = percentage;
    }

    /**
     * Gets the college.
     * 
     * @return the college
     */
    public String getCollege() {
        return college;
    }

    /**
     * Sets the college.
     * 
     * @param college the new college
     */
    public void setCollege(String college) {
        this.college = college;
    }

    /**
     * Gets the university.
     * 
     * @return the university
     */
    public String getUniversity() {
        return university;
    }

    /**
     * Sets the university.
     * 
     * @param university the new university
     */
    public void setUniversity(String university) {
        this.university = university;
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
        retValue.append("EmployeeEducation ( ")
            .append(super.toString()).append(SEPARATOR)
            .append("educationId = ").append(this.educationId).append(SEPARATOR)
            .append("degree = ").append(this.degree).append(SEPARATOR)
            .append("specialization = ").append(this.specialization).append(SEPARATOR)
            .append("yearOfGraduation = ").append(this.yearOfGraduation).append(SEPARATOR)
            .append("percentage = ").append(this.percentage).append(SEPARATOR)
            .append("college = ").append(this.college).append(SEPARATOR)
            .append("university = ").append(this.university).append(SEPARATOR)
            .append("employee = ").append(this.employee).append(SEPARATOR)
            .append(" )\n");
        return retValue.toString();
    }

}
