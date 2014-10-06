package com.myschool.report.dto;

/**
 * The Enum ReportCriteriaToken.
 */
public class ReportCriteriaToken {

    /** The criteria name. */
    private String criteriaName;

    /** The control type. */
    private String controlType;

    /** The reference. */
    private String reference;

    /** The use. */
    private String use;

    /**
     * Gets the criteria name.
     * 
     * @return the criteria name
     */
    public String getCriteriaName() {
        return criteriaName;
    }

    /**
     * Sets the criteria name.
     * 
     * @param criteriaName the new criteria name
     */
    public void setCriteriaName(String criteriaName) {
        this.criteriaName = criteriaName;
    }

    /**
     * Gets the control type.
     * 
     * @return the control type
     */
    public String getControlType() {
        return controlType;
    }

    /**
     * Sets the control type.
     * 
     * @param controlType the new control type
     */
    public void setControlType(String controlType) {
        this.controlType = controlType;
    }

    /**
     * Gets the reference.
     * 
     * @return the reference
     */
    public String getReference() {
        return reference;
    }

    /**
     * Sets the reference.
     * 
     * @param reference the new reference
     */
    public void setReference(String reference) {
        this.reference = reference;
    }

    /**
     * Gets the use.
     * 
     * @return the use
     */
    public String getUse() {
        return use;
    }

    /**
     * Sets the use.
     * 
     * @param use the new use
     */
    public void setUse(String use) {
        this.use = use;
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
        retValue.append("ReportCriteriaToken ( ")
            .append(super.toString()).append(SEPARATOR)
            .append("criteriaName = ").append(this.criteriaName).append(SEPARATOR)
            .append("controlType = ").append(this.controlType).append(SEPARATOR)
            .append("reference = ").append(this.reference).append(SEPARATOR)
            .append("use = ").append(this.use).append(SEPARATOR)
            .append(" )\n");
        return retValue.toString();
    }

}
