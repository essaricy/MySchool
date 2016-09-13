package com.myschool.employee.dto;

import java.io.Serializable;
import java.util.List;

import com.myschool.application.dto.ImageAccessDto;
import com.myschool.branch.dto.BranchDto;

/**
 * The Class EmployeeDto.
 */
public class EmployeeDto implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The employee id. */
    private int employeeId;

    /** The employee number. */
    private String employeeNumber;

    /** The image name. */
    private String imageName;

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

    /** The blood group. */
    private String bloodGroup;

    /** The nationality. */
    private String nationality;

    /** The marital status. */
    private String maritalStatus;

    /** The wedding day. */
    private String weddingDay;

    /** The employed at branch. */
    private BranchDto employedAtBranch;

    /** The designation. */
    private DesignationDto designation;

    /** The employment status. */
    private EmploymentStatus employmentStatus;

    /** The employment start date. */
    private String employmentStartDate;

    /** The employment end date. */
    private String employmentEndDate;

    /** The reporting to. */
    private EmployeeDto reportingTo;

    /** The remarks. */
    private String remarks;

    /** The employee contact. */
    private EmployeeContact employeeContact;

    /** The employee documents. */
    private List<EmployeeDocument> employeeDocuments;

    /** The employee educations. */
    private List<EmployeeEducation> employeeEducations;

    /** The employee experiences. */
    private List<EmployeeExperience> employeeExperiences;

    /** The employee promotions. */
    private List<EmployeePromotion> employeePromotions;

    /** The employee subjects. */
    private List<EmployeeSubjectDto> employeeSubjects;

    /** The verify. */
    private boolean verify;

    /** The verified. */
    private boolean verified;

    /** The image access. */
    private ImageAccessDto imageAccess;

    /**
     * Gets the employee id.
     * 
     * @return the employee id
     */
    public int getEmployeeId() {
        return employeeId;
    }

    /**
     * Sets the employee id.
     * 
     * @param employeeId the new employee id
     */
    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

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
     * Gets the marital status.
     * 
     * @return the marital status
     */
    public String getMaritalStatus() {
        return maritalStatus;
    }

    /**
     * Sets the marital status.
     * 
     * @param maritalStatus the new marital status
     */
    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    /**
     * Gets the wedding day.
     * 
     * @return the wedding day
     */
    public String getWeddingDay() {
        return weddingDay;
    }

    /**
     * Sets the wedding day.
     * 
     * @param weddingDay the new wedding day
     */
    public void setWeddingDay(String weddingDay) {
        this.weddingDay = weddingDay;
    }

    /**
     * Gets the employed at branch.
     * 
     * @return the employed at branch
     */
    public BranchDto getEmployedAtBranch() {
        return employedAtBranch;
    }

    /**
     * Sets the employed at branch.
     * 
     * @param employedAtBranch the new employed at branch
     */
    public void setEmployedAtBranch(BranchDto employedAtBranch) {
        this.employedAtBranch = employedAtBranch;
    }

    /**
     * Gets the designation.
     * 
     * @return the designation
     */
    public DesignationDto getDesignation() {
        return designation;
    }

    /**
     * Sets the designation.
     * 
     * @param designation the new designation
     */
    public void setDesignation(DesignationDto designation) {
        this.designation = designation;
    }

    /**
     * Gets the employment status.
     * 
     * @return the employment status
     */
    public EmploymentStatus getEmploymentStatus() {
        return employmentStatus;
    }

    /**
     * Sets the employment status.
     * 
     * @param employmentStatus the new employment status
     */
    public void setEmploymentStatus(EmploymentStatus employmentStatus) {
        this.employmentStatus = employmentStatus;
    }

    /**
     * Gets the employment start date.
     * 
     * @return the employment start date
     */
    public String getEmploymentStartDate() {
        return employmentStartDate;
    }

    /**
     * Sets the employment start date.
     * 
     * @param employmentStartDate the new employment start date
     */
    public void setEmploymentStartDate(String employmentStartDate) {
        this.employmentStartDate = employmentStartDate;
    }

    /**
     * Gets the employment end date.
     * 
     * @return the employment end date
     */
    public String getEmploymentEndDate() {
        return employmentEndDate;
    }

    /**
     * Sets the employment end date.
     * 
     * @param employmentEndDate the new employment end date
     */
    public void setEmploymentEndDate(String employmentEndDate) {
        this.employmentEndDate = employmentEndDate;
    }

    /**
     * Gets the reporting to.
     * 
     * @return the reporting to
     */
    public EmployeeDto getReportingTo() {
        return reportingTo;
    }

    /**
     * Sets the reporting to.
     * 
     * @param reportingTo the new reporting to
     */
    public void setReportingTo(EmployeeDto reportingTo) {
        this.reportingTo = reportingTo;
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
     * Gets the employee contact.
     * 
     * @return the employee contact
     */
    public EmployeeContact getEmployeeContact() {
        return employeeContact;
    }

    /**
     * Sets the employee contact.
     * 
     * @param employeeContact the new employee contact
     */
    public void setEmployeeContact(EmployeeContact employeeContact) {
        this.employeeContact = employeeContact;
    }

    /**
     * Gets the employee documents.
     * 
     * @return the employee documents
     */
    public List<EmployeeDocument> getEmployeeDocuments() {
        return employeeDocuments;
    }

    /**
     * Sets the employee documents.
     * 
     * @param employeeDocuments the new employee documents
     */
    public void setEmployeeDocuments(List<EmployeeDocument> employeeDocuments) {
        this.employeeDocuments = employeeDocuments;
    }

    /**
     * Gets the employee educations.
     * 
     * @return the employee educations
     */
    public List<EmployeeEducation> getEmployeeEducations() {
        return employeeEducations;
    }

    /**
     * Sets the employee educations.
     * 
     * @param employeeEducations the new employee educations
     */
    public void setEmployeeEducations(List<EmployeeEducation> employeeEducations) {
        this.employeeEducations = employeeEducations;
    }

    /**
     * Gets the employee experiences.
     * 
     * @return the employee experiences
     */
    public List<EmployeeExperience> getEmployeeExperiences() {
        return employeeExperiences;
    }

    /**
     * Sets the employee experiences.
     * 
     * @param employeeExperiences the new employee experiences
     */
    public void setEmployeeExperiences(
            List<EmployeeExperience> employeeExperiences) {
        this.employeeExperiences = employeeExperiences;
    }

    /**
     * Gets the employee promotions.
     * 
     * @return the employee promotions
     */
    public List<EmployeePromotion> getEmployeePromotions() {
        return employeePromotions;
    }

    /**
     * Sets the employee promotions.
     * 
     * @param employeePromotions the new employee promotions
     */
    public void setEmployeePromotions(List<EmployeePromotion> employeePromotions) {
        this.employeePromotions = employeePromotions;
    }

    /**
     * Gets the employee subjects.
     * 
     * @return the employee subjects
     */
    public List<EmployeeSubjectDto> getEmployeeSubjects() {
        return employeeSubjects;
    }

    /**
     * Sets the employee subjects.
     * 
     * @param employeeSubjects the new employee subjects
     */
    public void setEmployeeSubjects(List<EmployeeSubjectDto> employeeSubjects) {
        this.employeeSubjects = employeeSubjects;
    }

    /**
     * Checks if is verify.
     *
     * @return the verify
     */
    public boolean isVerify() {
        return verify;
    }

    /**
     * Sets the verify.
     *
     * @param verify the verify to set
     */
    public void setVerify(boolean verify) {
        this.verify = verify;
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
     * Gets the image access.
     *
     * @return the imageAccess
     */
    public ImageAccessDto getImageAccess() {
        return imageAccess;
    }

    /**
     * Sets the image access.
     *
     * @param imageAccess the imageAccess to set
     */
    public void setImageAccess(ImageAccessDto imageAccess) {
        this.imageAccess = imageAccess;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        final int maxLen = 10;
        StringBuilder builder = new StringBuilder();
        builder.append("EmployeeDto [employeeId=").append(employeeId)
                .append(", employeeNumber=").append(employeeNumber)
                .append(", imageName=").append(imageName).append(", firstName=")
                .append(firstName).append(", middleName=").append(middleName)
                .append(", lastName=").append(lastName).append(", gender=")
                .append(gender).append(", dateOfBirth=").append(dateOfBirth)
                .append(", bloodGroup=").append(bloodGroup)
                .append(", nationality=").append(nationality)
                .append(", maritalStatus=").append(maritalStatus)
                .append(", weddingDay=").append(weddingDay)
                .append(", employedAtBranch=").append(employedAtBranch)
                .append(", designation=").append(designation)
                .append(", employmentStatus=").append(employmentStatus)
                .append(", employmentStartDate=").append(employmentStartDate)
                .append(", employmentEndDate=").append(employmentEndDate)
                .append(", reportingTo=").append(reportingTo)
                .append(", remarks=").append(remarks)
                .append(", employeeContact=").append(employeeContact)
                .append(", employeeDocuments=")
                .append(employeeDocuments != null
                        ? employeeDocuments.subList(0,
                                Math.min(employeeDocuments.size(), maxLen))
                        : null)
                .append(", employeeEducations=")
                .append(employeeEducations != null
                        ? employeeEducations.subList(0,
                                Math.min(employeeEducations.size(), maxLen))
                        : null)
                .append(", employeeExperiences=")
                .append(employeeExperiences != null
                        ? employeeExperiences.subList(0,
                                Math.min(employeeExperiences.size(), maxLen))
                        : null)
                .append(", employeePromotions=")
                .append(employeePromotions != null
                        ? employeePromotions.subList(0,
                                Math.min(employeePromotions.size(), maxLen))
                        : null)
                .append(", employeeSubjects=")
                .append(employeeSubjects != null
                        ? employeeSubjects.subList(0,
                                Math.min(employeeSubjects.size(), maxLen))
                        : null)
                .append(", verify=").append(verify).append(", verified=")
                .append(verified).append(", imageAccess=").append(imageAccess)
                .append("]");
        return builder.toString();
    }

}
