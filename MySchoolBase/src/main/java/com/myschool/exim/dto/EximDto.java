package com.myschool.exim.dto;

import java.io.Serializable;

import com.myschool.exim.constants.EximPolicy;

/**
 * The Class EximDto.
 */
public class EximDto implements Serializable {
    
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The exim policy. */
    private EximPolicy eximPolicy;

    /** The description. */
    private String description;
    
    /** The can import. */
    private boolean canImport;
    
    /** The can export. */
    private boolean canExport;

    /**
     * Gets the exim policy.
     * 
     * @return the exim policy
     */
    public EximPolicy getEximPolicy() {
        return eximPolicy;
    }

    /**
     * Sets the exim policy.
     * 
     * @param eximPolicy the new exim policy
     */
    public void setEximPolicy(EximPolicy eximPolicy) {
        this.eximPolicy = eximPolicy;
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
     * Checks if is can import.
     *
     * @return true, if is can import
     */
    public boolean isCanImport() {
        return canImport;
    }

    /**
     * Sets the can import.
     *
     * @param canImport the new can import
     */
    public void setCanImport(boolean canImport) {
        this.canImport = canImport;
    }

    /**
     * Checks if is can export.
     *
     * @return true, if is can export
     */
    public boolean isCanExport() {
        return canExport;
    }

    /**
     * Sets the can export.
     *
     * @param canExport the new can export
     */
    public void setCanExport(boolean canExport) {
        this.canExport = canExport;
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
        retValue.append("EximDto ( ")
            .append(super.toString()).append(SEPARATOR)
            .append("eximPolicy = ").append(this.eximPolicy).append(SEPARATOR)
            .append("description = ").append(this.description).append(SEPARATOR)
            .append("canImport = ").append(this.canImport).append(SEPARATOR)
            .append("canExport = ").append(this.canExport).append(SEPARATOR)
            .append(" )\n");
        return retValue.toString();
    }

}
