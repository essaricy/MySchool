package com.myschool.report.dto;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.myschool.report.constants.ReportKey;

/**
 * The Class ReportCriteria.
 */
public class ReportCriteria implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The report key. */
    private ReportKey reportKey;

    /** The report name. */
    private String reportName;

    /** The report criteria tokens. */
    private List<ReportCriteriaToken> reportCriteriaTokens;

    /** The report criteria values. */
    private Map<ReportCriteriaToken, String> reportCriteriaValues;

    /**
     * Gets the report key.
     * 
     * @return the report key
     */
    public ReportKey getReportKey() {
        return reportKey;
    }

    /**
     * Sets the report key.
     * 
     * @param reportKey the new report key
     */
    public void setReportKey(ReportKey reportKey) {
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
     * Gets the report criteria values.
     * 
     * @return the report criteria values
     */
    public Map<ReportCriteriaToken, String> getReportCriteriaValues() {
        return reportCriteriaValues;
    }

    /**
     * Sets the report criteria values.
     * 
     * @param reportCriteriaValues the report criteria values
     */
    public void setReportCriteriaValues(Map<ReportCriteriaToken, String> reportCriteriaValues) {
        this.reportCriteriaValues = reportCriteriaValues;
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
        retValue.append("ReportCriteria ( ")
            .append(super.toString()).append(SEPARATOR)
            .append("reportKey = ").append(this.reportKey).append(SEPARATOR)
            .append("reportName = ").append(this.reportName).append(SEPARATOR)
            .append("reportCriteriaTokens = ").append(this.reportCriteriaTokens).append(SEPARATOR)
            .append("reportCriteriaValues = ").append(this.reportCriteriaValues).append(SEPARATOR)
            .append(" )\n");
        return retValue.toString();
    }

}
