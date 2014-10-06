package com.myschool.school.dto;

import java.io.Serializable;

import com.myschool.branch.dto.BranchDto;
import com.myschool.branch.dto.DivisionDto;

/**
 * The Class SchoolDto.
 */
public class SchoolDto implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The school id. */
    private int schoolId;

    /** The school name. */
    private String schoolName;

    /** The address. */
    private String address;

    /** The email id. */
    private String emailId;

    /** The branch. */
    private BranchDto branch;

    /** The primaryPhoneNumber. */
    private String primaryPhoneNumber;

    /** The secondaryPhoneNumber. */
    private String secondaryPhoneNumber;

    /** The faxNumber. */
    private String faxNumber;

    /** The mobileNumber. */
    private String mobileNumber;

    /** The map url. */
    private String mapUrl;

    /** The division. */
    private DivisionDto division;

    /**
     * Gets the school id.
     * 
     * @return the school id
     */
    public int getSchoolId() {
        return schoolId;
    }

    /**
     * Sets the school id.
     * 
     * @param schoolId the new school id
     */
    public void setSchoolId(int schoolId) {
        this.schoolId = schoolId;
    }

    /**
     * Gets the school name.
     * 
     * @return the school name
     */
    public String getSchoolName() {
        return schoolName;
    }

    /**
     * Sets the school name.
     * 
     * @param schoolName the new school name
     */
    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    /**
     * Gets the address.
     * 
     * @return the address
     */
    public String getAddress() {
        return address;
    }

    /**
     * Sets the address.
     * 
     * @param address the new address
     */
    public void setAddress(String address) {
        this.address = address;
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
     * Sets the branch.
     * 
     * @param branch the new branch
     */
    public void setBranch(BranchDto branch) {
        this.branch = branch;
    }

    /**
     * Gets the branch.
     * 
     * @return the branch
     */
    public BranchDto getBranch() {
        return branch;
    }

    /**
     * Gets the primary phone number.
     * 
     * @return the primary phone number
     */
    public String getPrimaryPhoneNumber() {
        return primaryPhoneNumber;
    }

    /**
     * Sets the primary phone number.
     * 
     * @param primaryPhoneNumber the new primary phone number
     */
    public void setPrimaryPhoneNumber(String primaryPhoneNumber) {
        this.primaryPhoneNumber = primaryPhoneNumber;
    }

    /**
     * Gets the secondary phone number.
     * 
     * @return the secondary phone number
     */
    public String getSecondaryPhoneNumber() {
        return secondaryPhoneNumber;
    }

    /**
     * Sets the secondary phone number.
     * 
     * @param secondaryPhoneNumber the new secondary phone number
     */
    public void setSecondaryPhoneNumber(String secondaryPhoneNumber) {
        this.secondaryPhoneNumber = secondaryPhoneNumber;
    }

    /**
     * Gets the fax number.
     * 
     * @return the fax number
     */
    public String getFaxNumber() {
        return faxNumber;
    }

    /**
     * Sets the fax number.
     * 
     * @param faxNumber the new fax number
     */
    public void setFaxNumber(String faxNumber) {
        this.faxNumber = faxNumber;
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
     * Gets the map url.
     *
     * @return the map url
     */
    public String getMapUrl() {
        return mapUrl;
    }

    /**
     * Sets the map url.
     *
     * @param mapUrl the new map url
     */
    public void setMapUrl(String mapUrl) {
        this.mapUrl = mapUrl;
    }

    /**
     * Gets the division.
     *
     * @return the division
     */
    public DivisionDto getDivision() {
        return division;
    }

    /**
     * Sets the division.
     *
     * @param division the new division
     */
    public void setDivision(DivisionDto division) {
        this.division = division;
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
        retValue.append("SchoolDto ( ")
            .append(super.toString()).append(SEPARATOR)
            .append("address = ").append(this.address).append(SEPARATOR)
            .append("branch = ").append(this.branch).append(SEPARATOR)
            .append("division = ").append(this.division).append(SEPARATOR)
            .append("emailId = ").append(this.emailId).append(SEPARATOR)
            .append("faxNumber = ").append(this.faxNumber).append(SEPARATOR)
            .append("mapUrl = ").append(this.mapUrl).append(SEPARATOR)
            .append("mobileNumber = ").append(this.mobileNumber).append(SEPARATOR)
            .append("primaryPhoneNumber = ").append(this.primaryPhoneNumber).append(SEPARATOR)
            .append("schoolId = ").append(this.schoolId).append(SEPARATOR)
            .append("schoolName = ").append(this.schoolName).append(SEPARATOR)
            .append("secondaryPhoneNumber = ").append(this.secondaryPhoneNumber).append(SEPARATOR)
            .append(" )\n");
        return retValue.toString();
    }

}
