package com.myschool.branch.dto;

import java.io.Serializable;

/**
 * The Class BranchDto.
 */
public class BranchDto implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The branch id. */
    private int branchId;

    /** The branch code. */
    private String branchCode;

    /** The description. */
    private String description;

    /** The address. */
    private String address;

    /** The email id. */
    private String emailId;

    /** The phone number. */
    private String phoneNumber;

    /** The map url. */
    private String mapUrl;

    /** The region. */
    private RegionDto region;

    /**
     * Gets the branch id.
     *
     * @return the branch id
     */
    public int getBranchId() {
        return branchId;
    }

    /**
     * Sets the branch id.
     *
     * @param branchId the new branch id
     */
    public void setBranchId(int branchId) {
        this.branchId = branchId;
    }

    /**
     * Gets the branch code.
     *
     * @return the branch code
     */
    public String getBranchCode() {
        return branchCode;
    }

    /**
     * Sets the branch code.
     *
     * @param branchCode the new branch code
     */
    public void setBranchCode(String branchCode) {
        this.branchCode = branchCode;
    }

    /**
     * Gets the description.
     *
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description.
     *
     * @param description the new description
     */
    public void setDescription(String description) {
        this.description = description;
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
     * Gets the phone number.
     *
     * @return the phone number
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Sets the phone number.
     *
     * @param phoneNumber the new phone number
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
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
     * Gets the region.
     *
     * @return the region
     */
    public RegionDto getRegion() {
        return region;
    }

    /**
     * Sets the region.
     *
     * @param region the new region
     */
    public void setRegion(RegionDto region) {
        this.region = region;
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
        retValue.append("BranchDto ( ")
            .append(super.toString()).append(SEPARATOR)
            .append("address = ").append(this.address).append(SEPARATOR)
            .append("branchCode = ").append(this.branchCode).append(SEPARATOR)
            .append("branchId = ").append(this.branchId).append(SEPARATOR)
            .append("description = ").append(this.description).append(SEPARATOR)
            .append("emailId = ").append(this.emailId).append(SEPARATOR)
            .append("mapUrl = ").append(this.mapUrl).append(SEPARATOR)
            .append("phoneNumber = ").append(this.phoneNumber).append(SEPARATOR)
            .append("region = ").append(this.region).append(SEPARATOR)
            .append(" )\n");
        return retValue.toString();
    }

}
