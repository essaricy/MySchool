package com.myschool.common.dto;

import java.io.Serializable;

/**
 * The Class FamilyMemberDto.
 */
public class FamilyMemberDto implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The family member id. */
    private int familyMemberId;

    /** The relationship. */
    private Relationship relationship;

    /** The name. */
    private String name;

    /** The occupation. */
    private String occupation;

    /** The mobile number. */
    private String mobileNumber;

    /** The email id. */
    private String emailId;

    /** The avail sms. */
    private boolean availSMS;

    /** The avail email. */
    private boolean availEmail;

    /**
     * Gets the family member id.
     *
     * @return the family member id
     */
    public int getFamilyMemberId() {
        return familyMemberId;
    }

    /**
     * Sets the family member id.
     *
     * @param familyMemberId the new family member id
     */
    public void setFamilyMemberId(int familyMemberId) {
        this.familyMemberId = familyMemberId;
    }

    /**
     * Gets the relationship.
     *
     * @return the relationship
     */
    public Relationship getRelationship() {
        return relationship;
    }

    /**
     * Sets the relationship.
     *
     * @param relationship the new relationship
     */
    public void setRelationship(Relationship relationship) {
        this.relationship = relationship;
    }

    /**
     * Gets the name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name.
     *
     * @param name the new name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the occupation.
     *
     * @return the occupation
     */
    public String getOccupation() {
        return occupation;
    }

    /**
     * Sets the occupation.
     *
     * @param occupation the new occupation
     */
    public void setOccupation(String occupation) {
        this.occupation = occupation;
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
     * Checks if is avail sms.
     *
     * @return true, if is avail sms
     */
    public boolean isAvailSMS() {
        return availSMS;
    }

    /**
     * Sets the avail sms.
     *
     * @param availSMS the new avail sms
     */
    public void setAvailSMS(boolean availSMS) {
        this.availSMS = availSMS;
    }

    /**
     * Checks if is avail email.
     *
     * @return true, if is avail email
     */
    public boolean isAvailEmail() {
        return availEmail;
    }

    /**
     * Sets the avail email.
     *
     * @param availEmail the new avail email
     */
    public void setAvailEmail(boolean availEmail) {
        this.availEmail = availEmail;
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
        retValue.append("FamilyMemberDto ( ")
            .append(super.toString()).append(SEPARATOR)
            .append("availEmail = ").append(this.availEmail).append(SEPARATOR)
            .append("availSMS = ").append(this.availSMS).append(SEPARATOR)
            .append("emailId = ").append(this.emailId).append(SEPARATOR)
            .append("familyMemberId = ").append(this.familyMemberId).append(SEPARATOR)
            .append("mobileNumber = ").append(this.mobileNumber).append(SEPARATOR)
            .append("name = ").append(this.name).append(SEPARATOR)
            .append("occupation = ").append(this.occupation).append(SEPARATOR)
            .append("relationship = ").append(this.relationship).append(SEPARATOR)
            .append(" )\n");
        return retValue.toString();
    }

}
