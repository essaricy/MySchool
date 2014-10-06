package com.myschool.download.dto;

import java.io.File;
import java.io.Serializable;

/**
 * The Class BrochureDto.
 */
public class BrochureDto implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The brochure name. */
    private String brochureName;

    /** The brochure Type. */
    private String brochureType;

    /** The last Updated on. */
    private String lastUpdatedOn;

    /** The brochure file. */
    private File brochureFile;

    /**
     * Gets the brochure name.
     *
     * @return the brochure name
     */
    public String getBrochureName() {
        return brochureName;
    }

    /**
     * Sets the brochure name.
     *
     * @param brochureName the new brochure name
     */
    public void setBrochureName(String brochureName) {
        this.brochureName = brochureName;
    }

    /**
     * Gets the brochure type.
     *
     * @return the brochure type
     */
    public String getBrochureType() {
        return brochureType;
    }

    /**
     * Sets the brochure type.
     *
     * @param brochureType the new brochure type
     */
    public void setBrochureType(String brochureType) {
        this.brochureType = brochureType;
    }

    /**
     * Gets the last updated on.
     *
     * @return the last updated on
     */
    public String getLastUpdatedOn() {
        return lastUpdatedOn;
    }

    /**
     * Sets the last updated on.
     *
     * @param lastUpdatedOn the new last updated on
     */
    public void setLastUpdatedOn(String lastUpdatedOn) {
        this.lastUpdatedOn = lastUpdatedOn;
    }

    /**
     * Gets the brochure file.
     * 
     * @return the brochure file
     */
    public File getBrochureFile() {
        return brochureFile;
    }

    /**
     * Sets the brochure file.
     * 
     * @param brochureFile the new brochure file
     */
    public void setBrochureFile(File brochureFile) {
        this.brochureFile = brochureFile;
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
        retValue.append("BrochureDto ( ")
            .append(super.toString()).append(SEPARATOR)
            .append("brochureName = ").append(this.brochureName).append(SEPARATOR)
            .append("brochureType = ").append(this.brochureType).append(SEPARATOR)
            .append("lastUpdatedOn = ").append(this.lastUpdatedOn).append(SEPARATOR)
            .append("brochureFile = ").append(this.brochureFile).append(SEPARATOR)
            .append(" )\n");
        return retValue.toString();
    }

}
