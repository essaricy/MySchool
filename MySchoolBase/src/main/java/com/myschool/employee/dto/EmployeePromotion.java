package com.myschool.employee.dto;

import java.io.Serializable;

/**
 * The Class EmployeePromotion.
 */
public class EmployeePromotion implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The promotion id. */
    private int promotionId;

    /** The prior designation. */
    private DesignationDto priorDesignation;

    /** The current designation. */
    private DesignationDto currentDesignation;

    /** The effective from. */
    private String effectiveFrom;

    /** The employee. */
    private EmployeeDto employee;

    /**
     * Gets the promotion id.
     * 
     * @return the promotion id
     */
    public int getPromotionId() {
        return promotionId;
    }

    /**
     * Sets the promotion id.
     * 
     * @param promotionId the new promotion id
     */
    public void setPromotionId(int promotionId) {
        this.promotionId = promotionId;
    }

    /**
     * Gets the prior designation.
     * 
     * @return the prior designation
     */
    public DesignationDto getPriorDesignation() {
        return priorDesignation;
    }

    /**
     * Sets the prior designation.
     * 
     * @param priorDesignation the new prior designation
     */
    public void setPriorDesignation(DesignationDto priorDesignation) {
        this.priorDesignation = priorDesignation;
    }

    /**
     * Gets the current designation.
     * 
     * @return the current designation
     */
    public DesignationDto getCurrentDesignation() {
        return currentDesignation;
    }

    /**
     * Sets the current designation.
     * 
     * @param currentDesignation the new current designation
     */
    public void setCurrentDesignation(DesignationDto currentDesignation) {
        this.currentDesignation = currentDesignation;
    }

    /**
     * Gets the effective from.
     * 
     * @return the effective from
     */
    public String getEffectiveFrom() {
        return effectiveFrom;
    }

    /**
     * Sets the effective from.
     * 
     * @param effectiveFrom the new effective from
     */
    public void setEffectiveFrom(String effectiveFrom) {
        this.effectiveFrom = effectiveFrom;
    }

    /**
     * Gets the employee.
     * 
     * @return the employee
     */
    public EmployeeDto getEmployee() {
        return employee;
    }

    /**
     * Sets the employee.
     * 
     * @param employee the new employee
     */
    public void setEmployee(EmployeeDto employee) {
        this.employee = employee;
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
        retValue.append("EmployeePromotion ( ")
            .append(super.toString()).append(SEPARATOR)
            .append("promotionId = ").append(this.promotionId).append(SEPARATOR)
            .append("priorDesignation = ").append(this.priorDesignation).append(SEPARATOR)
            .append("currentDesignation = ").append(this.currentDesignation).append(SEPARATOR)
            .append("effectiveFrom = ").append(this.effectiveFrom).append(SEPARATOR)
            .append("employee = ").append(this.employee).append(SEPARATOR)
            .append(" )\n");
        return retValue.toString();
    }

}
