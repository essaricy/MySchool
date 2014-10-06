package com.myschool.employee.dto;

import java.io.Serializable;

/**
 * The Class EmploymentStatus.
 */
public class EmploymentStatus implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The status id. */
    private int statusId;

    /** The description. */
    private String description;

    /**
     * Gets the status id.
     * 
     * @return the status id
     */
    public int getStatusId() {
        return statusId;
    }

    /**
     * Sets the status id.
     * 
     * @param statusId the new status id
     */
    public void setStatusId(int statusId) {
        this.statusId = statusId;
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
     * Constructs a <code>String</code> with all attributes in name = value
     * format.
     * 
     * @return a <code>String</code> representation of this object.
     */
    public String toString() {
        final String SEPARATOR = ", ";
        StringBuilder retValue = new StringBuilder();
        retValue.append("EmploymentStatus ( ").append(super.toString())
                .append(SEPARATOR).append("statusId = ").append(this.statusId)
                .append(SEPARATOR).append("description = ")
                .append(this.description).append(SEPARATOR).append(" )\n");
        return retValue.toString();
    }

}
