package com.myschool.employee.dto;

import java.io.Serializable;

/**
 * The Class DesignationDto.
 */
public class DesignationDto implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The designation id. */
    private int designationId;

    /** The designation. */
    private String designation;

    /**
     * Gets the designation id.
     * 
     * @return the designation id
     */
    public int getDesignationId() {
        return designationId;
    }

    /**
     * Sets the designation id.
     * 
     * @param designationId the new designation id
     */
    public void setDesignationId(int designationId) {
        this.designationId = designationId;
    }

    /**
     * Gets the designation.
     * 
     * @return the designation
     */
    public String getDesignation() {
        return designation;
    }

    /**
     * Sets the designation.
     * 
     * @param designation the new designation
     */
    public void setDesignation(String designation) {
        this.designation = designation;
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
        retValue.append("DesignationDto ( ").append(super.toString())
                .append(SEPARATOR).append("designation = ")
                .append(this.designation).append(SEPARATOR)
                .append("designationId = ").append(this.designationId)
                .append(SEPARATOR).append(" )\n");
        return retValue.toString();
    }

}
