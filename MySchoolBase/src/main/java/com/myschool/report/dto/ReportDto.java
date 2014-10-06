package com.myschool.report.dto;

import java.io.Serializable;
import java.util.Hashtable;
import java.util.List;

/**
 * The Class ReportDto.
 */
public class ReportDto implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The report key. */
    private String reportKey;

    /** The report name. */
    private String reportName;

    /** The can admin view. */
    private boolean canAdminView;

    /** The can employee view. */
    private boolean canEmployeeView;

    /** The can student view. */
    private boolean canStudentView;

    /** The report criteria tokens. */
    private List<ReportCriteriaToken> reportCriteriaTokens;

    /** The report parameters. */
    private Hashtable<String, Object> reportParameters;

    /**
     * Gets the report key.
     * 
     * @return the report key
     */
    public String getReportKey() {
        return reportKey;
    }

    /**
     * Sets the report key.
     * 
     * @param reportKey the new report key
     */
    public void setReportKey(String reportKey) {
        this.reportKey = reportKey;
    }

    /**
     * Gets the report name.
     * 
     * @return the report name
     */
    public String getReportName() {
        return reportName;
    }

    /**
     * Sets the report name.
     * 
     * @param reportName the new report name
     */
    public void setReportName(String reportName) {
        this.reportName = reportName;
    }

    /**
     * Checks if is can admin view.
     * 
     * @return true, if is can admin view
     */
    public boolean isCanAdminView() {
        return canAdminView;
    }

    /**
     * Sets the can admin view.
     * 
     * @param canAdminView the new can admin view
     */
    public void setCanAdminView(boolean canAdminView) {
        this.canAdminView = canAdminView;
    }

    /**
     * Checks if is can employee view.
     * 
     * @return true, if is can employee view
     */
    public boolean isCanEmployeeView() {
        return canEmployeeView;
    }

    /**
     * Sets the can employee view.
     * 
     * @param canEmployeeView the new can employee view
     */
    public void setCanEmployeeView(boolean canEmployeeView) {
        this.canEmployeeView = canEmployeeView;
    }

    /**
     * Checks if is can student view.
     * 
     * @return true, if is can student view
     */
    public boolean isCanStudentView() {
        return canStudentView;
    }

    /**
     * Sets the can student view.
     * 
     * @param canStudentView the new can student view
     */
    public void setCanStudentView(boolean canStudentView) {
        this.canStudentView = canStudentView;
    }

    /**
     * Gets the report criteria tokens.
     * 
     * @return the report criteria tokens
     */
    public List<ReportCriteriaToken> getReportCriteriaTokens() {
        return reportCriteriaTokens;
    }

    /**
     * Sets the report criteria tokens.
     * 
     * @param reportCriteriaTokens the new report criteria tokens
     */
    public void setReportCriteriaTokens(
            List<ReportCriteriaToken> reportCriteriaTokens) {
        this.reportCriteriaTokens = reportCriteriaTokens;
    }

    /**
     * Gets the report parameters.
     * 
     * @return the report parameters
     */
    public Hashtable<String, Object> getReportParameters() {
        return reportParameters;
    }

    /**
     * Sets the report parameters.
     * 
     * @param reportParameters the report parameters
     */
    public void setReportParameters(Hashtable<String, Object> reportParameters) {
        this.reportParameters = reportParameters;
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
        retValue.append("ReportDto ( ")
            .append(super.toString()).append(SEPARATOR)
            .append("reportKey = ").append(this.reportKey).append(SEPARATOR)
            .append("reportName = ").append(this.reportName).append(SEPARATOR)
            .append("canAdminView = ").append(this.canAdminView).append(SEPARATOR)
            .append("canEmployeeView = ").append(this.canEmployeeView).append(SEPARATOR)
            .append("canStudentView = ").append(this.canStudentView).append(SEPARATOR)
            .append("reportCriteriaTokens = ").append(this.reportCriteriaTokens).append(SEPARATOR)
            .append("reportParameters = ").append(this.reportParameters).append(SEPARATOR)
            .append(" )\n");
        return retValue.toString();
    }

}
