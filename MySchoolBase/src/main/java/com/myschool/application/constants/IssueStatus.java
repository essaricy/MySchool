package com.myschool.application.constants;


/**
 * The Enum IssueStatus.
 */
public enum IssueStatus {
    
    /** The OPEN. */
    OPEN(1),
    
    /** The CLOSED. */
    CLOSED(2);

    /** The status id. */
    private int statusId;

    /**
     * Instantiates a new issue status.
     *
     * @param statusId the status id
     */
    private IssueStatus(int statusId) {
        this.statusId = statusId;
    }

    /**
     * Gets the status id.
     *
     * @return the status id
     */
    public int getStatusId() {
        return statusId;
    }

    /**
     * 
     * @param statusId the status id
     */
    public static IssueStatus get(int statusId) {
        IssueStatus gotIssueStatus = null;
        for (IssueStatus issueStatus : values()) {
            if (issueStatus.getStatusId() == statusId) {
                gotIssueStatus = issueStatus;
                break;
            }
        }
        return gotIssueStatus;
    }

}
