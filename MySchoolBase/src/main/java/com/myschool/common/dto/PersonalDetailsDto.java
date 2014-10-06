package com.myschool.common.dto;

import java.io.Serializable;

/**
 * The Class PersonalDetailsDto.
 */
public class PersonalDetailsDto implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The first name. */
    private String firstName;

    /** The middle name. */
    private String middleName;

    /** The last name. */
    private String lastName;
    
    /** The gender. */
    private String gender;

    /** The date of birth. */
    private String dateOfBirth;
    
    /** The religion. */
    private String religion;

    /** The caste. */
    private String caste;

    /** The nationality. */
    private String nationality;
    
    /** The mother tongue. */
    private String motherTongue;

    /** The permanent address. */
    private String permanentAddress;

    /** The correspondence address. */
    private String correspondenceAddress;

    /** The mobile number. */
    private String mobileNumber;

    /** The identification marks. */
    private String identificationMarks;

    /** The blood group. */
    private String bloodGroup;

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
     * Gets the date of birth.
     *
     * @return the date of birth
     */
    public String getDateOfBirth() {
        return dateOfBirth;
    }

    /**
     * Sets the date of birth.
     *
     * @param dateOfBirth the new date of birth
     */
    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    /**
     * Gets the religion.
     *
     * @return the religion
     */
    public String getReligion() {
        return religion;
    }

    /**
     * Sets the religion.
     *
     * @param religion the new religion
     */
    public void setReligion(String religion) {
        this.religion = religion;
    }

    /**
     * Gets the caste.
     *
     * @return the caste
     */
    public String getCaste() {
        return caste;
    }

    /**
     * Sets the caste.
     *
     * @param caste the new caste
     */
    public void setCaste(String caste) {
        this.caste = caste;
    }

    /**
     * Gets the nationality.
     *
     * @return the nationality
     */
    public String getNationality() {
        return nationality;
    }

    /**
     * Sets the nationality.
     *
     * @param nationality the new nationality
     */
    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    /**
     * Gets the mother tongue.
     *
     * @return the mother tongue
     */
    public String getMotherTongue() {
        return motherTongue;
    }

    /**
     * Sets the mother tongue.
     *
     * @param motherTongue the new mother tongue
     */
    public void setMotherTongue(String motherTongue) {
        this.motherTongue = motherTongue;
    }

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
     * Gets the correspondence address.
     *
     * @return the correspondence address
     */
    public String getCorrespondenceAddress() {
        return correspondenceAddress;
    }

    /**
     * Sets the correspondence address.
     *
     * @param correspondenceAddress the new correspondence address
     */
    public void setCorrespondenceAddress(String correspondenceAddress) {
        this.correspondenceAddress = correspondenceAddress;
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
     * Gets the identification marks.
     *
     * @return the identification marks
     */
    public String getIdentificationMarks() {
        return identificationMarks;
    }

    /**
     * Sets the identification marks.
     *
     * @param identificationMarks the new identification marks
     */
    public void setIdentificationMarks(String identificationMarks) {
        this.identificationMarks = identificationMarks;
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
     * Constructs a <code>String</code> with all attributes
     * in name = value format.
     *
     * @return a <code>String</code> representation 
     * of this object.
     */
    public String toString() {
        final String SEPARATOR = ", ";
        StringBuilder retValue = new StringBuilder();
        retValue.append("PersonalDetailsDto ( ")
            .append(super.toString()).append(SEPARATOR)
            .append("bloodGroup = ").append(this.bloodGroup).append(SEPARATOR)
            .append("caste = ").append(this.caste).append(SEPARATOR)
            .append("correspondenceAddress = ").append(this.correspondenceAddress).append(SEPARATOR)
            .append("dateOfBirth = ").append(this.dateOfBirth).append(SEPARATOR)
            .append("firstName = ").append(this.firstName).append(SEPARATOR)
            .append("gender = ").append(this.gender).append(SEPARATOR)
            .append("identificationMarks = ").append(this.identificationMarks).append(SEPARATOR)
            .append("lastName = ").append(this.lastName).append(SEPARATOR)
            .append("middleName = ").append(this.middleName).append(SEPARATOR)
            .append("mobileNumber = ").append(this.mobileNumber).append(SEPARATOR)
            .append("motherTongue = ").append(this.motherTongue).append(SEPARATOR)
            .append("nationality = ").append(this.nationality).append(SEPARATOR)
            .append("permanentAddress = ").append(this.permanentAddress).append(SEPARATOR)
            .append("religion = ").append(this.religion).append(SEPARATOR)
            .append(" )\n");
        return retValue.toString();
    }

}
