package com.myschool.student.dto;

import java.io.Serializable;
import java.util.List;

import com.myschool.clazz.dto.RegisteredClassDto;
import com.myschool.common.dto.FamilyMemberDto;
import com.myschool.common.dto.PersonalDetailsDto;
import com.myschool.common.dto.TrainingDto;

/**
 * The Class StudentDto.
 */
public class StudentDto implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The student id. */
    private int studentId;

    /** The admission number. */
    private String admissionNumber;

    /** The date of joining. */
    private String dateOfJoining;

    /** The remarks. */
    private String remarks;

    /** The admission status. */
    private AdmissionStatus admissionStatus;

    /** The documents submitted. */
    private List<StudentDocument> documentsSubmitted;

    /** The awareness trainings. */
    private List<TrainingDto> awarenessTrainings;

    /** The registered class dto. */
    private RegisteredClassDto registeredClassDto;

    /** The personal details. */
    private PersonalDetailsDto personalDetails;

    /** The family members. */
    private List<FamilyMemberDto> familyMembers;

    /** The verified. */
    private boolean verified;

    /** The image name. */
    private String imageName;

    /**
     * Gets the student id.
     *
     * @return the student id
     */
    public int getStudentId() {
        return studentId;
    }

    /**
     * Sets the student id.
     *
     * @param studentId the new student id
     */
    public void setStudentId(int studentId) {
        this.studentId = studentId;
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
     * Gets the date of joining.
     *
     * @return the date of joining
     */
    public String getDateOfJoining() {
        return dateOfJoining;
    }

    /**
     * Sets the date of joining.
     *
     * @param dateOfJoining the new date of joining
     */
    public void setDateOfJoining(String dateOfJoining) {
        this.dateOfJoining = dateOfJoining;
    }

    /**
     * Gets the remarks.
     *
     * @return the remarks
     */
    public String getRemarks() {
        return remarks;
    }

    /**
     * Sets the remarks.
     *
     * @param remarks the new remarks
     */
    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    /**
     * Gets the admission status.
     * 
     * @return the admission status
     */
    public AdmissionStatus getAdmissionStatus() {
        return admissionStatus;
    }

    /**
     * Sets the admission status.
     * 
     * @param admissionStatus the new admission status
     */
    public void setAdmissionStatus(AdmissionStatus admissionStatus) {
        this.admissionStatus = admissionStatus;
    }

    /**
     * Gets the documents submitted.
     *
     * @return the documents submitted
     */
    public List<StudentDocument> getDocumentsSubmitted() {
        return documentsSubmitted;
    }

    /**
     * Sets the documents submitted.
     *
     * @param documentsSubmitted the new documents submitted
     */
    public void setDocumentsSubmitted(List<StudentDocument> documentsSubmitted) {
        this.documentsSubmitted = documentsSubmitted;
    }

    /**
     * Gets the awareness trainings.
     *
     * @return the awareness trainings
     */
    public List<TrainingDto> getAwarenessTrainings() {
        return awarenessTrainings;
    }

    /**
     * Sets the awareness trainings.
     *
     * @param awarenessTrainings the new awareness trainings
     */
    public void setAwarenessTrainings(List<TrainingDto> awarenessTrainings) {
        this.awarenessTrainings = awarenessTrainings;
    }

    /**
     * Gets the registered class dto.
     *
     * @return the registered class dto
     */
    public RegisteredClassDto getRegisteredClassDto() {
        return registeredClassDto;
    }

    /**
     * Sets the registered class dto.
     *
     * @param registeredClassDto the new registered class dto
     */
    public void setRegisteredClassDto(RegisteredClassDto registeredClassDto) {
        this.registeredClassDto = registeredClassDto;
    }

    /**
     * Gets the personal details.
     *
     * @return the personal details
     */
    public PersonalDetailsDto getPersonalDetails() {
        return personalDetails;
    }

    /**
     * Sets the personal details.
     *
     * @param personalDetails the new personal details
     */
    public void setPersonalDetails(PersonalDetailsDto personalDetails) {
        this.personalDetails = personalDetails;
    }

    /**
     * Gets the family members.
     *
     * @return the family members
     */
    public List<FamilyMemberDto> getFamilyMembers() {
        return familyMembers;
    }

    /**
     * Sets the family members.
     *
     * @param familyMembers the new family members
     */
    public void setFamilyMembers(List<FamilyMemberDto> familyMembers) {
        this.familyMembers = familyMembers;
    }

    /**
     * Checks if is verified.
     * 
     * @return true, if is verified
     */
    public boolean isVerified() {
        return verified;
    }

    /**
     * Sets the verified.
     * 
     * @param verified the new verified
     */
    public void setVerified(boolean verified) {
        this.verified = verified;
    }

    /**
     * Gets the image name.
     * 
     * @return the image name
     */
    public String getImageName() {
        return imageName;
    }

    /**
     * Sets the image name.
     * 
     * @param imageName the new image name
     */
    public void setImageName(String imageName) {
        this.imageName = imageName;
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
        retValue.append("StudentDto ( ")
            .append(super.toString()).append(SEPARATOR)
            .append("studentId = ").append(this.studentId).append(SEPARATOR)
            .append("admissionNumber = ").append(this.admissionNumber).append(SEPARATOR)
            .append("dateOfJoining = ").append(this.dateOfJoining).append(SEPARATOR)
            .append("remarks = ").append(this.remarks).append(SEPARATOR)
            .append("admissionStatus = ").append(this.admissionStatus).append(SEPARATOR)
            .append("documentsSubmitted = ").append(this.documentsSubmitted).append(SEPARATOR)
            .append("awarenessTrainings = ").append(this.awarenessTrainings).append(SEPARATOR)
            .append("registeredClassDto = ").append(this.registeredClassDto).append(SEPARATOR)
            .append("personalDetails = ").append(this.personalDetails).append(SEPARATOR)
            .append("familyMembers = ").append(this.familyMembers).append(SEPARATOR)
            .append("verified = ").append(this.verified).append(SEPARATOR)
            .append("imageName = ").append(this.imageName).append(SEPARATOR)
            .append(" )\n");
        return retValue.toString();
    }

}
