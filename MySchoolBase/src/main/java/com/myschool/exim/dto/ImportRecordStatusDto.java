package com.myschool.exim.dto;

import java.io.Serializable;

/**
 * The Class ImportRecordStatusDto.
 */
public class ImportRecordStatusDto implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The Constant STATUS_UNPROCESSED. */
    public static final int STATUS_UNPROCESSED = 0;

    /** The Constant STATUS_ADDED. */
    public static final int STATUS_ADDED = 1;

    /** The Constant STATUS_UPDATED. */
    public static final int STATUS_UPDATED = 2;

    /** The Constant STATUS_DELETED. */
    public static final int STATUS_DELETED = 3;

    /** The Constant STATUS_INVALID_DATA. */
    public static final int STATUS_INVALID_DATA = 4;

    /** The Constant STATUS_FAILED. */
    public static final int STATUS_FAILED = 5;

    /** The Constant ACTION_CODE_SKIP. */
    public static final int ACTION_CODE_SKIP = 6;

    /** The import record. */
    private String importRecord;

    /** The status code. */
    private int statusCode;

    /** The error description. */
    private String statusDescription;

    /** The content. */
    private Object content;

    /** The action code. */
    private int actionCode;

    /**
     * Gets the action code.
     *
     * @return the action code
     */
    public int getActionCode() {
        return actionCode;
    }

    /**
     * Sets the action code.
     *
     * @param actionCode the new action code
     */
    public void setActionCode(int actionCode) {
        this.actionCode = actionCode;
    }

    /**
     * Gets the content.
     *
     * @return the content
     */
    public Object getContent() {
        return content;
    }

    /**
     * Sets the content.
     *
     * @param content the new content
     */
    public void setContent(Object content) {
        this.content = content;
    }

    /**
     * Gets the import record.
     *
     * @return the import record
     */
    public String getImportRecord() {
        return importRecord;
    }

    /**
     * Sets the import record.
     *
     * @param importRecord the new import record
     */
    public void setImportRecord(String importRecord) {
        this.importRecord = importRecord;
    }

    /**
     * Checks if is status code.
     *
     * @return true, if is status code
     */
    public int getStatusCode() {
        return statusCode;
    }

    /**
     * Sets the status code.
     *
     * @param statusCode the new status code
     */
    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    /**
     * Gets the status description.
     *
     * @return the status description
     */
    public String getStatusDescription() {
        return statusDescription;
    }

    /**
     * Sets the status description.
     *
     * @param statusDescription the new status description
     */
    public void setStatusDescription(String statusDescription) {
        this.statusDescription = statusDescription;
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
        retValue.append("ImportRecordStatusDto ( ")
            .append(super.toString()).append(SEPARATOR)
            .append("actionCode = ").append(this.actionCode).append(SEPARATOR)
            .append("content = ").append(this.content).append(SEPARATOR)
            .append("importRecord = ").append(this.importRecord).append(SEPARATOR)
            .append("statusCode = ").append(this.statusCode).append(SEPARATOR)
            .append("statusDescription = ").append(this.statusDescription).append(SEPARATOR)
            .append(" )\n");
        return retValue.toString();
    }

}
