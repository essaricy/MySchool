package com.myschool.common.dto;

import java.io.Serializable;

/**
 * The Class Person.
 */
public class Person implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The first name. */
    private String firstName;
    
    /** The middle name. */
    private String middleName;
    
    /** The last name. */
    private String lastName;
    
    /** The email id. */
    private String emailId;
    
    /** The mobile number. */
    private String mobileNumber;

    /**
     * Gets the first name.
     *
     * @return the first name
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets the first name.
     *
     * @param firstName the new first name
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Gets the middle name.
     *
     * @return the middle name
     */
    public String getMiddleName() {
        return middleName;
    }

    /**
     * Sets the middle name.
     *
     * @param middleName the new middle name
     */
    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    /**
     * Gets the last name.
     *
     * @return the last name
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets the last name.
     *
     * @param lastName the new last name
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Gets the email id.
     *
     * @return the email id
     */
    public String getEmailId() {
        return emailId;
    }

    /**
     * Sets the email id.
     *
     * @param emailId the new email id
     */
    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    /**
     * Gets the mobile number.
     *
     * @return the mobile number
     */
    public String getMobileNumber() {
        return mobileNumber;
    }

    /**
     * Sets the mobile number.
     *
     * @param mobileNumber the new mobile number
     */
    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
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
        retValue.append("Person ( ")
            .append(super.toString()).append(SEPARATOR)
            .append("emailId = ").append(this.emailId).append(SEPARATOR)
            .append("firstName = ").append(this.firstName).append(SEPARATOR)
            .append("lastName = ").append(this.lastName).append(SEPARATOR)
            .append("middleName = ").append(this.middleName).append(SEPARATOR)
            .append("mobileNumber = ").append(this.mobileNumber).append(SEPARATOR)
            .append(" )\n");
        return retValue.toString();
    }

}
