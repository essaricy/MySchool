package com.myschool.application.dto;

import java.io.Serializable;

/**
 * The Class OrganizationProfileDto.
 */
public class OrganizationProfileDto implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The organization name. */
    private String organizationName;

    /** The current academic year. */
    private String currentAcademicYear;

    /** The address. */
    private String address;

    /** The phone number. */
    private String phoneNumber;

    /** The fax number. */
    private String faxNumber;

    /** The map url. */
    private String mapUrl;

    /**
     * Gets the organization name.
     *
     * @return the organization name
     */
    public String getOrganizationName() {
        return organizationName;
    }

    /**
     * Sets the organization name.
     *
     * @param organizationName the new organization name
     */
    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    /**
     * Gets the current academic year.
     *
     * @return the current academic year
     */
    public String getCurrentAcademicYear() {
        return currentAcademicYear;
    }

    /**
     * Sets the current academic year.
     *
     * @param currentAcademicYear the new current academic year
     */
    public void setCurrentAcademicYear(String currentAcademicYear) {
        this.currentAcademicYear = currentAcademicYear;
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
     * Constructs a <code>String</code> with all attributes
     * in name = value format.
     *
     * @return a <code>String</code> representation 
     * of this object.
     */
    public String toString() {
        final String SEPARATOR = ", ";
        StringBuilder retValue = new StringBuilder();
        retValue.append("OrganizationProfileDto ( ")
            .append(super.toString()).append(SEPARATOR)
            .append("organizationName = ").append(this.organizationName).append(SEPARATOR)
            .append("currentAcademicYear = ").append(this.currentAcademicYear).append(SEPARATOR)
            .append("address = ").append(this.address).append(SEPARATOR)
            .append("phoneNumber = ").append(this.phoneNumber).append(SEPARATOR)
            .append("faxNumber = ").append(this.faxNumber).append(SEPARATOR)
            .append("mapUrl = ").append(this.mapUrl).append(SEPARATOR)
            .append(" )\n");
        return retValue.toString();
    }

}
