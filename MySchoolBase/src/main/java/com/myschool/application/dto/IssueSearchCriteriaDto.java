package com.myschool.application.dto;

import java.io.Serializable;

import com.myschool.application.constants.IssueStatus;
import com.myschool.user.constants.UserType;

/**
 * The Class IssueSearchCriteriaDto.
 */
public class IssueSearchCriteriaDto implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The user type. */
    private UserType userType;

    /** The issue status. */
    private IssueStatus issueStatus;

    /** The subject. */
    private String subject;

    /** The description. */
    private String description;

    /** The contact email id. */
    private String contactEmailId;

    /** The reported date min. */
    private String reportedDateMin;

    /** The reported date max. */
    private String reportedDateMax;

    /** The closed date min. */
    private String closedDateMin;

    /** The closed date max. */
    private String closedDateMax;

    /**
     * Gets the user type.
     * 
     * @return the user type
     */
    public UserType getUserType() {
        return userType;
    }

    /**
     * Sets the user type.
     * 
     * @param userType the new user type
     */
    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    /**
     * Gets the issue status.
     * 
     * @return the issue status
     */
    public IssueStatus getIssueStatus() {
        return issueStatus;
    }

    /**
     * Sets the issue status.
     * 
     * @param issueStatus the new issue status
     */
    public void setIssueStatus(IssueStatus issueStatus) {
        this.issueStatus = issueStatus;
    }

    /**
     * Gets the subject.
     * 
     * @return the subject
     */
    public String getSubject() {
        return subject;
    }

    /**
     * Sets the subject.
     * 
     * @param subject the new subject
     */
    public void setSubject(String subject) {
        this.subject = subject;
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
     * Gets the contact email id.
     * 
     * @return the contact email id
     */
    public String getContactEmailId() {
        return contactEmailId;
    }

    /**
     * Sets the contact email id.
     * 
     * @param contactEmailId the new contact email id
     */
    public void setContactEmailId(String contactEmailId) {
        this.contactEmailId = contactEmailId;
    }

    /**
     * Gets the reported date min.
     * 
     * @return the reported date min
     */
    public String getReportedDateMin() {
        return reportedDateMin;
    }

    /**
     * Sets the reported date min.
     * 
     * @param reportedDateMin the new reported date min
     */
    public void setReportedDateMin(String reportedDateMin) {
        this.reportedDateMin = reportedDateMin;
    }

    /**
     * Gets the reported date max.
     * 
     * @return the reported date max
     */
    public String getReportedDateMax() {
        return reportedDateMax;
    }

    /**
     * Sets the reported date max.
     * 
     * @param reportedDateMax the new reported date max
     */
    public void setReportedDateMax(String reportedDateMax) {
        this.reportedDateMax = reportedDateMax;
    }

    /**
     * Gets the closed date min.
     * 
     * @return the closed date min
     */
    public String getClosedDateMin() {
        return closedDateMin;
    }

    /**
     * Sets the closed date min.
     * 
     * @param closedDateMin the new closed date min
     */
    public void setClosedDateMin(String closedDateMin) {
        this.closedDateMin = closedDateMin;
    }

    /**
     * Gets the closed date max.
     * 
     * @return the closed date max
     */
    public String getClosedDateMax() {
        return closedDateMax;
    }

    /**
     * Sets the closed date max.
     * 
     * @param closedDateMax the new closed date max
     */
    public void setClosedDateMax(String closedDateMax) {
        this.closedDateMax = closedDateMax;
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
        retValue.append("IssueSearchCriteriaDto ( ")
            .append(super.toString()).append(SEPARATOR)
            .append("userType = ").append(this.userType).append(SEPARATOR)
            .append("issueStatus = ").append(this.issueStatus).append(SEPARATOR)
            .append("subject = ").append(this.subject).append(SEPARATOR)
            .append("description = ").append(this.description).append(SEPARATOR)
            .append("contactEmailId = ").append(this.contactEmailId).append(SEPARATOR)
            .append("reportedDateMin = ").append(this.reportedDateMin).append(SEPARATOR)
            .append("reportedDateMax = ").append(this.reportedDateMax).append(SEPARATOR)
            .append("closedDateMin = ").append(this.closedDateMin).append(SEPARATOR)
            .append("closedDateMax = ").append(this.closedDateMax).append(SEPARATOR)
            .append(" )\n");
        return retValue.toString();
    }

}
