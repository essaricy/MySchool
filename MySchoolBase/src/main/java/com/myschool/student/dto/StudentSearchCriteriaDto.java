package com.myschool.student.dto;

import java.io.Serializable;

import com.myschool.common.constants.RecordStatus;

/**
 * The Class StudentSearchCriteriaDto.
 */
public class StudentSearchCriteriaDto implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The branch id. */
    private int branchId;

    /** The division id. */
    private int divisionId;

    /** The school id. */
    private int schoolId;

    /** The class id. */
    private int classId;

    /** The medium id. */
    private int mediumId;

    /** The section id. */
    private int sectionId;

    /** The admission number. */
    private String admissionNumber;

    /** The student name. */
    private String studentName;

    /** The gender. */
    private String gender;

    /** The religion. */
    private String religion;

    /** The blood group. */
    private String bloodGroup;

    /** The date of birth min. */
    private String dateOfBirthMin;

    /** The date of birth max. */
    private String dateOfBirthMax;

    /** The date of joining min. */
    private String dateOfJoiningMin;

    /** The date of joining max. */
    private String dateOfJoiningMax;

    private RecordStatus recordStatus;

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
     * Gets the division id.
     *
     * @return the division id
     */
    public int getDivisionId() {
        return divisionId;
    }

    /**
     * Sets the division id.
     *
     * @param divisionId the new division id
     */
    public void setDivisionId(int divisionId) {
        this.divisionId = divisionId;
    }

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
     * Gets the class id.
     *
     * @return the class id
     */
    public int getClassId() {
        return classId;
    }

    /**
     * Sets the class id.
     *
     * @param classId the new class id
     */
    public void setClassId(int classId) {
        this.classId = classId;
    }

    /**
     * Gets the medium id.
     *
     * @return the medium id
     */
    public int getMediumId() {
        return mediumId;
    }

    /**
     * Sets the medium id.
     *
     * @param mediumId the new medium id
     */
    public void setMediumId(int mediumId) {
        this.mediumId = mediumId;
    }

    /**
     * Gets the section id.
     *
     * @return the section id
     */
    public int getSectionId() {
        return sectionId;
    }

    /**
     * Sets the section id.
     *
     * @param sectionId the new section id
     */
    public void setSectionId(int sectionId) {
        this.sectionId = sectionId;
    }

    /**
     * Gets the admission number.
     *
     * @return the admission number
     */
    public String getAdmissionNumber() {
        return admissionNumber;
    }

    /**
     * Sets the admission number.
     *
     * @param admissionNumber the new admission number
     */
    public void setAdmissionNumber(String admissionNumber) {
        this.admissionNumber = admissionNumber;
    }

    /**
     * Gets the student name.
     *
     * @return the student name
     */
    public String getStudentName() {
        return studentName;
    }

    /**
     * Sets the student name.
     *
     * @param studentName the new student name
     */
    public void setStudentName(String studentName) {
        this.studentName = studentName;
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
     * Gets the date of joining min.
     * 
     * @return the date of joining min
     */
    public String getDateOfJoiningMin() {
        return dateOfJoiningMin;
    }

    /**
     * Sets the date of joining min.
     * 
     * @param dateOfJoiningMin the new date of joining min
     */
    public void setDateOfJoiningMin(String dateOfJoiningMin) {
        this.dateOfJoiningMin = dateOfJoiningMin;
    }

    /**
     * Gets the date of joining max.
     * 
     * @return the date of joining max
     */
    public String getDateOfJoiningMax() {
        return dateOfJoiningMax;
    }

    /**
     * Sets the date of joining max.
     * 
     * @param dateOfJoiningMax the new date of joining max
     */
    public void setDateOfJoiningMax(String dateOfJoiningMax) {
        this.dateOfJoiningMax = dateOfJoiningMax;
    }

    /**
     * @return the recordStatus
     */
    public RecordStatus getRecordStatus() {
        return recordStatus;
    }

    /**
     * @param recordStatus the recordStatus to set
     */
    public void setRecordStatus(RecordStatus recordStatus) {
        this.recordStatus = recordStatus;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("StudentSearchCriteriaDto [branchId=").append(branchId)
                .append(", divisionId=").append(divisionId)
                .append(", schoolId=").append(schoolId).append(", classId=")
                .append(classId).append(", mediumId=").append(mediumId)
                .append(", sectionId=").append(sectionId)
                .append(", admissionNumber=").append(admissionNumber)
                .append(", studentName=").append(studentName)
                .append(", gender=").append(gender).append(", religion=")
                .append(religion).append(", bloodGroup=").append(bloodGroup)
                .append(", dateOfBirthMin=").append(dateOfBirthMin)
                .append(", dateOfBirthMax=").append(dateOfBirthMax)
                .append(", dateOfJoiningMin=").append(dateOfJoiningMin)
                .append(", dateOfJoiningMax=").append(dateOfJoiningMax)
                .append(", recordStatus=").append(recordStatus).append("]");
        return builder.toString();
    }

}
