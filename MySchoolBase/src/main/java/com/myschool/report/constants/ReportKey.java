package com.myschool.report.constants;

/**
 * The Enum ReportKey.
 */
public enum ReportKey {

    /** The BRANCH_REPORT. */
    BRANCH_REPORT,

    /** The DESIGNATION_REPORT. */
    DESIGNATION_REPORT,

    /** The DIVISION_REPORT. */
    DIVISION_REPORT,

    /** The MASTER_DOCUMENT_REPORT. */
    MASTER_DOCUMENT_REPORT,

    /** The EMPLOYEE_REPORT. */
    EMPLOYEE_REPORT,

    /** The EXAM_GRADE_REPORT. */
    EXAM_GRADE_REPORT,

    /** The HOLIDAY_REPORT. */
    HOLIDAY_REPORT,

    /** The MASTER_CLASS_REPORT. */
    MASTER_CLASS_REPORT,

    /** The RELATIONSHIP_CODE_REPORT. */
    RELATIONSHIP_CODE_REPORT,

    /** The SCHOOL_REPORT. */
    SCHOOL_REPORT,

    /** The STUDENT_REPORT. */
    STUDENT_REPORT;

    /**
     * Gets the.
     * 
     * @param reportKey the report key
     * @return the report constants
     */
    public static ReportKey get(String reportKey) {
        if (reportKey != null) {
            for (ReportKey reportName : values()) {
                if (reportKey.equals(reportName.toString())) {
                    return reportName;
                }
            }
        }
        return null;
    }

}
