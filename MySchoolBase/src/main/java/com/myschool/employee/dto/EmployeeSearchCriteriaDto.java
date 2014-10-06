package com.myschool.employee.dto;

import java.io.Serializable;

/**
 * The Class EmployeeSearchCriteriaDto.
 */
public class EmployeeSearchCriteriaDto implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The employee number. */
    private String employeeNumber;

    /** The employee name. */
    private String employeeName;

    /** The designation id. */
    private int designationId;

    /** The reporting to employee number. */
    private String reportingToEmployeeNumber;

    /** The employed at branch code. */
    private String employedAtBranchCode;

    /** The employment status id. */
    private int employmentStatusId;

    /** The gender. */
    private String gender;

    /** The blood group. */
    private String bloodGroup;

    /** The experience in months min. */
    private int experienceInMonthsMin = -1;

    /** The experience in months max. */
    private int experienceInMonthsMax = -1;

    /** The date of birth min. */
    private String dateOfBirthMin;

    /** The date of birth max. */
    private String dateOfBirthMax;

    /** The employment start date min. */
    private String employmentStartDateMin;

    /** The employment start date max. */
    private String employmentStartDateMax;

    /** The verified status. */
    private String verifiedStatus;

    /**
     * Gets the employee number.
     * 
     * @return the employee number
     */
    public String getEmployeeNumber() {
        return employeeNumber;
    }

    /**
     * Sets the employee number.
     * 
     * @param employeeNumber the new employee number
     */
    public void setEmployeeNumber(String employeeNumber) {
        this.employeeNumber = employeeNumber;
    }

    /**
     * Gets the employee name.
     * 
     * @return the employee name
     */
    public String getEmployeeName() {
        return employeeName;
    }

    /**
     * Sets the employee name.
     * 
     * @param employeeName the new employee name
     */
    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    /**
     * Gets the designation id.
     * 
     * @return the designation id
     */
    public int getDesignationId() {
        return designationId;
    }

    /**
     * Sets the designation id.
     * 
     * @param designationId the new designation id
     */
    public void setDesignationId(int designationId) {
        this.designationId = designationId;
    }

    /**
     * Gets the reporting to employee number.
     * 
     * @return the reporting to employee number
     */
    public String getReportingToEmployeeNumber() {
        return reportingToEmployeeNumber;
    }

    /**
     * Sets the reporting to employee number.
     * 
     * @param reportingToEmployeeNumber the new reporting to employee number
     */
    public void setReportingToEmployeeNumber(String reportingToEmployeeNumber) {
        this.reportingToEmployeeNumber = reportingToEmployeeNumber;
    }

    /**
     * Gets the employed at branch code.
     * 
     * @return the employed at branch code
     */
    public String getEmployedAtBranchCode() {
        return employedAtBranchCode;
    }

    /**
     * Sets the employed at branch code.
     * 
     * @param employedAtBranchCode the new employed at branch code
     */
    public void setEmployedAtBranchCode(String employedAtBranchCode) {
        this.employedAtBranchCode = employedAtBranchCode;
    }

    /**
     * Gets the employment status id.
     * 
     * @return the employment status id
     */
    public int getEmploymentStatusId() {
        return employmentStatusId;
    }

    /**
     * Sets the employment status id.
     * 
     * @param employmentStatusId the new employment status id
     */
    public void setEmploymentStatusId(int employmentStatusId) {
        this.employmentStatusId = employmentStatusId;
    }

    /**
     * Gets the gender.
     * 
     * @return the gender
     */
    public String getGender() {
        return gender;
    }

    /**
     * Sets the gender.
     * 
     * @param gender the new gender
     */
    public void setGender(String gender) {
        this.gender = gender;
    }

    /**
     * Gets the blood group.
     * 
     * @return the blood group
     */
    public String getBloodGroup() {
        return bloodGroup;
    }

    /**
     * Sets the blood group.
     * 
     * @param bloodGroup the new blood group
     */
    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    /**
     * Gets the experience in months min.
     * 
     * @return the experience in months min
     */
    public int getExperienceInMonthsMin() {
        return experienceInMonthsMin;
    }

    /**
     * Sets the experience in months min.
     * 
     * @param experienceInMonthsMin the new experience in months min
     */
    public void setExperienceInMonthsMin(int experienceInMonthsMin) {
        this.experienceInMonthsMin = experienceInMonthsMin;
    }

    /**
     * Gets the experience in months max.
     * 
     * @return the experience in months max
     */
    public int getExperienceInMonthsMax() {
        return experienceInMonthsMax;
    }

    /**
     * Sets the experience in months max.
     * 
     * @param experienceInMonthsMax the new experience in months max
     */
    public void setExperienceInMonthsMax(int experienceInMonthsMax) {
        this.experienceInMonthsMax = experienceInMonthsMax;
    }

    /**
     * Gets the date of birth min.
     * 
     * @return the date of birth min
     */
    public String getDateOfBirthMin() {
        return dateOfBirthMin;
    }

    /**
     * Sets the date of birth min.
     * 
     * @param dateOfBirthMin the new date of birth min
     */
    public void setDateOfBirthMin(String dateOfBirthMin) {
        this.dateOfBirthMin = dateOfBirthMin;
    }

    /**
     * Gets the date of birth max.
     * 
     * @return the date of birth max
     */
    public String getDateOfBirthMax() {
        return dateOfBirthMax;
    }

    /**
     * Sets the date of birth max.
     * 
     * @param dateOfBirthMax the new date of birth max
     */
    public void setDateOfBirthMax(String dateOfBirthMax) {
        this.dateOfBirthMax = dateOfBirthMax;
    }

    /**
     * Gets the employment start date min.
     * 
     * @return the employment start date min
     */
    public String getEmploymentStartDateMin() {
        return employmentStartDateMin;
    }

    /**
     * Sets the employment start date min.
     * 
     * @param employmentStartDateMin the new employment start date min
     */
    public void setEmploymentStartDateMin(String employmentStartDateMin) {
        this.employmentStartDateMin = employmentStartDateMin;
    }

    /**
     * Gets the employment start date max.
     * 
     * @return the employment start date max
     */
    public String getEmploymentStartDateMax() {
        return employmentStartDateMax;
    }

    /**
     * Sets the employment start date max.
     * 
     * @param employmentStartDateMax the new employment start date max
     */
    public void setEmploymentStartDateMax(String employmentStartDateMax) {
        this.employmentStartDateMax = employmentStartDateMax;
    }

    /**
     * Gets the verified status.
     * 
     * @return the verified status
     */
    public String getVerifiedStatus() {
        return verifiedStatus;
    }

    /**
     * Sets the verified status.
     * 
     * @param verifiedStatus the new verified status
     */
    public void setVerifiedStatus(String verifiedStatus) {
        this.verifiedStatus = verifiedStatus;
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
        retValue.append("EmployeeSearchCriteriaDto ( ")
            .append(super.toString()).append(SEPARATOR)
            .append("employeeNumber = ").append(this.employeeNumber).append(SEPARATOR)
            .append("employeeName = ").append(this.employeeName).append(SEPARATOR)
            .append("designationId = ").append(this.designationId).append(SEPARATOR)
            .append("reportingToEmployeeNumber = ").append(this.reportingToEmployeeNumber).append(SEPARATOR)
            .append("employedAtBranchCode = ").append(this.employedAtBranchCode).append(SEPARATOR)
            .append("employmentStatusId = ").append(this.employmentStatusId).append(SEPARATOR)
            .append("gender = ").append(this.gender).append(SEPARATOR)
            .append("bloodGroup = ").append(this.bloodGroup).append(SEPARATOR)
            .append("experienceInMonthsMin = ").append(this.experienceInMonthsMin).append(SEPARATOR)
            .append("experienceInMonthsMax = ").append(this.experienceInMonthsMax).append(SEPARATOR)
            .append("dateOfBirthMin = ").append(this.dateOfBirthMin).append(SEPARATOR)
            .append("dateOfBirthMax = ").append(this.dateOfBirthMax).append(SEPARATOR)
            .append("employmentStartDateMin = ").append(this.employmentStartDateMin).append(SEPARATOR)
            .append("employmentStartDateMax = ").append(this.employmentStartDateMax).append(SEPARATOR)
            .append("verifiedStatus = ").append(this.verifiedStatus).append(SEPARATOR)
            .append(" )\n");
        return retValue.toString();
    }

}
