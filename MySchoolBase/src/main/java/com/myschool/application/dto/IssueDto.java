package com.myschool.application.dto;

import java.io.Serializable;

import com.myschool.application.constants.IssueStatus;
import com.myschool.user.constants.UserType;

/**
 * The Class IssueDto.
 */
public class IssueDto implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The issue id. */
    private int issueId;

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

    /** The reported date. */
    private String reportedDate;

    /** The closed date. */
    private String closedDate;

    /**
     * Gets the issue id.
     *
     * @return the issue id
     */
    public int getIssueId() {
        return issueId;
    }

    /**
     * Sets the issue id.
     *
     * @param issueId the new issue id
     */
    public void setIssueId(int issueId) {
        this.issueId = issueId;
    }

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
     * Gets the reported date.
     *
     * @return the reported date
     */
    public String getReportedDate() {
        return reportedDate;
    }

    /**
     * Sets the reported date.
     *
     * @param reportedDate the new reported date
     */
    public void setReportedDate(String reportedDate) {
        this.reportedDate = reportedDate;
    }

    /**
     * Gets the closed date.
     *
     * @return the closed date
     */
    public String getClosedDate() {
        return closedDate;
    }

    /**
     * Sets the closed date.
     *
     * @param closedDate the new closed date
     */
    public void setClosedDate(String closedDate) {
        this.closedDate = closedDate;
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
        retValue.append("IssueDto ( ")
            .append(super.toString()).append(SEPARATOR)
            .append("issueId = ").append(this.issueId).append(SEPARATOR)
            .append("userType = ").append(this.userType).append(SEPARATOR)
            .append("issueStatus = ").append(this.issueStatus).append(SEPARATOR)
            .append("subject = ").append(this.subject).append(SEPARATOR)
            .append("description = ").append(this.description).append(SEPARATOR)
            .append("contactEmailId = ").append(this.contactEmailId).append(SEPARATOR)
            .append("reportedDate = ").append(this.reportedDate).append(SEPARATOR)
            .append("closedDate = ").append(this.closedDate).append(SEPARATOR)
            .append(" )\n");
        return retValue.toString();
    }

}
