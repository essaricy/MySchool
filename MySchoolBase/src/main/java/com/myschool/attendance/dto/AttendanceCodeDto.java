package com.myschool.attendance.dto;

import java.io.Serializable;

/**
 * The Class AttendanceCodeDto.
 */
public class AttendanceCodeDto implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The code. */
    private String code;

    /** The short description. */
    private String shortDescription;

    /** The long description. */
    private String longDescription;

    /** The use in reference. */
    private boolean useInReference;

    /** The use in assignment. */
    private boolean useInAssignment;

    /**
     * Gets the code.
     * 
     * @return the code
     */
    public String getCode() {
        return code;
    }

    /**
     * Sets the code.
     * 
     * @param code the new code
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * Gets the short description.
     * 
     * @return the short description
     */
    public String getShortDescription() {
        return shortDescription;
    }

    /**
     * Sets the short description.
     * 
     * @param shortDescription the new short description
     */
    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    /**
     * Gets the long description.
     * 
     * @return the long description
     */
    public String getLongDescription() {
        return longDescription;
    }

    /**
     * Sets the long description.
     * 
     * @param longDescription the new long description
     */
    public void setLongDescription(String longDescription) {
        this.longDescription = longDescription;
    }

    /**
     * Checks if is use in reference.
     * 
     * @return true, if is use in reference
     */
    public boolean isUseInReference() {
        return useInReference;
    }

    /**
     * Sets the use in reference.
     * 
     * @param useInReference the new use in reference
     */
    public void setUseInReference(boolean useInReference) {
        this.useInReference = useInReference;
    }

    /**
     * Checks if is use in assignment.
     * 
     * @return true, if is use in assignment
     */
    public boolean isUseInAssignment() {
        return useInAssignment;
    }

    /**
     * Sets the use in assignment.
     * 
     * @param useInAssignment the new use in assignment
     */
    public void setUseInAssignment(boolean useInAssignment) {
        this.useInAssignment = useInAssignment;
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
        retValue.append("AttendanceCodeDto ( ")
            .append(super.toString()).append(SEPARATOR)
            .append("code = ").append(this.code).append(SEPARATOR)
            .append("shortDescription = ").append(this.shortDescription).append(SEPARATOR)
            .append("longDescription = ").append(this.longDescription).append(SEPARATOR)
            .append("useInReference = ").append(this.useInReference).append(SEPARATOR)
            .append("useInAssignment = ").append(this.useInAssignment).append(SEPARATOR)
            .append(" )\n");
        return retValue.toString();
    }

}
