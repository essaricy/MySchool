package com.myschool.clazz.dto;

import java.io.Serializable;

/**
 * The Class MediumDto.
 */
public class MediumDto implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The medium id. */
    private int mediumId;

    /** The description. */
    private String description;

    /**
     * Gets the medium id.
     * 
     * @return the medium id
     */
    public int getMediumId() {
        return mediumId;
    }

    /**
     * Sets the medium id.
     * 
     * @param mediumId the new medium id
     */
    public void setMediumId(int mediumId) {
        this.mediumId = mediumId;
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
        retValue.append("MediumDto ( ")
            .append(super.toString()).append(SEPARATOR)
            .append("description = ").append(this.description).append(SEPARATOR)
            .append("mediumId = ").append(this.mediumId).append(SEPARATOR)
            .append(" )\n");
        return retValue.toString();
    }

}
