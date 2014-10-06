package com.myschool.employee.dto;

import java.io.Serializable;

import com.myschool.common.dto.Relationship;
import com.myschool.employee.constant.EmployeeNotificationTo;

/**
 * The Class EmployeeContact.
 */
public class EmployeeContact implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The permanent address. */
    private String permanentAddress;

    /** The present address. */
    private String presentAddress;

    /** The personal mobile number. */
    private String personalMobileNumber;

    /** The personal email id. */
    private String personalEmailId;

    /** The emergency contact number. */
    private String emergencyContactNumber;

    /** The emergency contact relationship. */
    private Relationship emergencyContactRelationship;

    /** The office desk phone number. */
    private String officeDeskPhoneNumber;

    /** The office desk extension. */
    private String officeDeskExtension;

    /** The office mobile number. */
    private String officeMobileNumber;

    /** The office email id. */
    private String officeEmailId;

    /** The employee. */
    private EmployeeDto employee;

    /** The email notification to. */
    private EmployeeNotificationTo emailNotificationTo;

    /** The sms notification to. */
    private EmployeeNotificationTo smsNotificationTo;

    /**
     * Gets the permanent address.
     * 
     * @return the permanent address
     */
    public String getPermanentAddress() {
        return permanentAddress;
    }

    /**
     * Sets the permanent address.
     * 
     * @param permanentAddress the new permanent address
     */
    public void setPermanentAddress(String permanentAddress) {
        this.permanentAddress = permanentAddress;
    }

    /**
     * Gets the present address.
     * 
     * @return the present address
     */
    public String getPresentAddress() {
        return presentAddress;
    }

    /**
     * Sets the present address.
     * 
     * @param presentAddress the new present address
     */
    public void setPresentAddress(String presentAddress) {
        this.presentAddress = presentAddress;
    }

    /**
     * Gets the personal mobile number.
     * 
     * @return the personal mobile number
     */
    public String getPersonalMobileNumber() {
        return personalMobileNumber;
    }

    /**
     * Sets the personal mobile number.
     * 
     * @param personalMobileNumber the new personal mobile number
     */
    public void setPersonalMobileNumber(String personalMobileNumber) {
        this.personalMobileNumber = personalMobileNumber;
    }

    /**
     * Gets the personal email id.
     * 
     * @return the personal email id
     */
    public String getPersonalEmailId() {
        return personalEmailId;
    }

    /**
     * Sets the personal email id.
     * 
     * @param personalEmailId the new personal email id
     */
    public void setPersonalEmailId(String personalEmailId) {
        this.personalEmailId = personalEmailId;
    }

    /**
     * Gets the emergency contact number.
     * 
     * @return the emergency contact number
     */
    public String getEmergencyContactNumber() {
        return emergencyContactNumber;
    }

    /**
     * Sets the emergency contact number.
     * 
     * @param emergencyContactNumber the new emergency contact number
     */
    public void setEmergencyContactNumber(String emergencyContactNumber) {
        this.emergencyContactNumber = emergencyContactNumber;
    }

    /**
     * Gets the emergency contact relationship.
     * 
     * @return the emergency contact relationship
     */
    public Relationship getEmergencyContactRelationship() {
        return emergencyContactRelationship;
    }

    /**
     * Sets the emergency contact relationship.
     * 
     * @param emergencyContactRelationship the new emergency contact
     *            relationship
     */
    public void setEmergencyContactRelationship(
            Relationship emergencyContactRelationship) {
        this.emergencyContactRelationship = emergencyContactRelationship;
    }

    /**
     * Gets the office desk phone number.
     * 
     * @return the office desk phone number
     */
    public String getOfficeDeskPhoneNumber() {
        return officeDeskPhoneNumber;
    }

    /**
     * Sets the office desk phone number.
     * 
     * @param officeDeskPhoneNumber the new office desk phone number
     */
    public void setOfficeDeskPhoneNumber(String officeDeskPhoneNumber) {
        this.officeDeskPhoneNumber = officeDeskPhoneNumber;
    }

    /**
     * Gets the office desk extension.
     * 
     * @return the office desk extension
     */
    public String getOfficeDeskExtension() {
        return officeDeskExtension;
    }

    /**
     * Sets the office desk extension.
     * 
     * @param officeDeskExtension the new office desk extension
     */
    public void setOfficeDeskExtension(String officeDeskExtension) {
        this.officeDeskExtension = officeDeskExtension;
    }

    /**
     * Gets the office mobile number.
     * 
     * @return the office mobile number
     */
    public String getOfficeMobileNumber() {
        return officeMobileNumber;
    }

    /**
     * Sets the office mobile number.
     * 
     * @param officeMobileNumber the new office mobile number
     */
    public void setOfficeMobileNumber(String officeMobileNumber) {
        this.officeMobileNumber = officeMobileNumber;
    }

    /**
     * Gets the office email id.
     * 
     * @return the office email id
     */
    public String getOfficeEmailId() {
        return officeEmailId;
    }

    /**
     * Sets the office email id.
     * 
     * @param officeEmailId the new office email id
     */
    public void setOfficeEmailId(String officeEmailId) {
        this.officeEmailId = officeEmailId;
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
     * Gets the email notification to.
     * 
     * @return the email notification to
     */
    public EmployeeNotificationTo getEmailNotificationTo() {
        return emailNotificationTo;
    }

    /**
     * Sets the email notification to.
     * 
     * @param emailNotificationTo the new email notification to
     */
    public void setEmailNotificationTo(EmployeeNotificationTo emailNotificationTo) {
        this.emailNotificationTo = emailNotificationTo;
    }

    /**
     * Gets the sms notification to.
     * 
     * @return the sms notification to
     */
    public EmployeeNotificationTo getSmsNotificationTo() {
        return smsNotificationTo;
    }

    /**
     * Sets the sms notification to.
     * 
     * @param smsNotificationTo the new sms notification to
     */
    public void setSmsNotificationTo(EmployeeNotificationTo smsNotificationTo) {
        this.smsNotificationTo = smsNotificationTo;
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
        retValue.append("EmployeeContact ( ")
            .append(super.toString()).append(SEPARATOR)
            .append("permanentAddress = ").append(this.permanentAddress).append(SEPARATOR)
            .append("presentAddress = ").append(this.presentAddress).append(SEPARATOR)
            .append("personalMobileNumber = ").append(this.personalMobileNumber).append(SEPARATOR)
            .append("personalEmailId = ").append(this.personalEmailId).append(SEPARATOR)
            .append("emergencyContactNumber = ").append(this.emergencyContactNumber).append(SEPARATOR)
            .append("emergencyContactRelationship = ").append(this.emergencyContactRelationship).append(SEPARATOR)
            .append("officeDeskPhoneNumber = ").append(this.officeDeskPhoneNumber).append(SEPARATOR)
            .append("officeDeskExtension = ").append(this.officeDeskExtension).append(SEPARATOR)
            .append("officeMobileNumber = ").append(this.officeMobileNumber).append(SEPARATOR)
            .append("officeEmailId = ").append(this.officeEmailId).append(SEPARATOR)
            .append("employee = ").append(this.employee).append(SEPARATOR)
            .append("emailNotificationTo = ").append(this.emailNotificationTo).append(SEPARATOR)
            .append("smsNotificationTo = ").append(this.smsNotificationTo).append(SEPARATOR)
            .append(" )\n");
        return retValue.toString();
    }

}
