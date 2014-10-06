package com.myschool.branch.dto;

import java.io.Serializable;

/**
 * The Class DivisionDto.
 */
public class DivisionDto implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The division id. */
    private int divisionId;
    
    /** The division code. */
    private String divisionCode;
    
    /** The description. */
    private String description;

    /**
     * Gets the division id.
     *
     * @return the division id
     */
    public int getDivisionId() {
        return divisionId;
    }

    /**
     * Sets the division id.
     *
     * @param divisionId the new division id
     */
    public void setDivisionId(int divisionId) {
        this.divisionId = divisionId;
    }

    /**
     * Gets the division code.
     *
     * @return the division code
     */
    public String getDivisionCode() {
        return divisionCode;
    }

    /**
     * Sets the division code.
     *
     * @param divisionCode the new division code
     */
    public void setDivisionCode(String divisionCode) {
        this.divisionCode = divisionCode;
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
     * Constructs a <code>String</code> with all attributes
     * in name = value format.
     *
     * @return a <code>String</code> representation 
     * of this object.
     */
    public String toString() {
        final String SEPARATOR = ", ";
        StringBuilder retValue = new StringBuilder();
        retValue.append("DivisionDto ( ")
            .append(super.toString()).append(SEPARATOR)
            .append("description = ").append(this.description).append(SEPARATOR)
            .append("divisionCode = ").append(this.divisionCode).append(SEPARATOR)
            .append("divisionId = ").append(this.divisionId).append(SEPARATOR)
            .append(" )\n");
        return retValue.toString();
    }

}
