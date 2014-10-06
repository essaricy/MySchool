package com.myschool.common.dto;

import java.io.Serializable;

/**
 * The Class StatusDto.
 */
public class StatusDto implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The Constant SUCCESSFULL. */
    public static final int SUCCESSFUL = 0;

    /** The Constant FAILED. */
    public static final int FAILED = 1;

    /** The status code. */
    private int statusCode;

    /** The status description. */
    private String statusDescription;

    /**
     * Gets the status code.
     *
     * @return the status code
     */
    public int getStatusCode() {
        return statusCode;
    }

    /**
     * Sets the status code.
     *
     * @param importStatusCode the new status code
     */
    public void setStatusCode(int importStatusCode) {
        this.statusCode = importStatusCode;
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
        retValue.append("StatusDto ( ")
            .append(super.toString()).append(SEPARATOR)
            .append("statusCode = ").append(this.statusCode).append(SEPARATOR)
            .append("statusDescription = ").append(this.statusDescription).append(SEPARATOR)
            .append(" )\n");
        return retValue.toString();
    }

}
