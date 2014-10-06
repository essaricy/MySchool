package com.myschool.clazz.dto;

import java.io.Serializable;

/**
 * The Class SectionDto.
 */
public class SectionDto implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The section id. */
    private int sectionId;

    /** The sectionName. */
    private String sectionName;

    /**
     * Gets the section id.
     * 
     * @return the section id
     */
    public int getSectionId() {
        return sectionId;
    }

    /**
     * Sets the section id.
     * 
     * @param sectionId the new section id
     */
    public void setSectionId(int sectionId) {
        this.sectionId = sectionId;
    }

    /**
     * Gets the section name.
     * 
     * @return the section name
     */
    public String getSectionName() {
        return sectionName;
    }

    /**
     * Sets the section name.
     *
     * @param sectionName the new section name
     */
    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
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
        retValue.append("SectionDto ( ")
            .append(super.toString()).append(SEPARATOR)
            .append("sectionId = ").append(this.sectionId).append(SEPARATOR)
            .append("sectionName = ").append(this.sectionName).append(SEPARATOR)
            .append(" )\n");
        return retValue.toString();
    }

}
