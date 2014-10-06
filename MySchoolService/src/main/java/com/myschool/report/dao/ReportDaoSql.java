package com.myschool.report.dao;

/**
 * The Class ReportDaoSql.
 */
public class ReportDaoSql {

    /** The Constant SELECT_ALL. */
    public static final String SELECT_ALL;

    /** The Constant SELECT_BY_REPORT_KEY. */
    public static final String SELECT_BY_REPORT_KEY;

    /** The Constant SELECT_REPORT_CRITERIA_BY_KEY. */
    public static final String SELECT_REPORT_CRITERIA_BY_KEY;

    static {
        StringBuilder builder = new StringBuilder();
        builder.append("SELECT ");
        builder.append("REPORT_KEY, ");
        builder.append("REPORT_NAME, ");
        builder.append("CAN_ADMIN_VIEW, ");
        builder.append("CAN_EMPLOYEE_VIEW, ");
        builder.append("CAN_STUDENT_VIEW ");
        builder.append("FROM REPORT ");
        SELECT_ALL = builder.toString();
        builder.setLength(0);

        builder.append(SELECT_ALL);
        builder.append("WHERE REPORT_KEY=?");
        SELECT_BY_REPORT_KEY = builder.toString();
        builder.setLength(0);

        builder.append("SELECT ");
        builder.append("CRITERIA_NAME, ");
        builder.append("CONTROL_TYPE, ");
        builder.append("REFERENCE, ");
        builder.append("USE ");
        builder.append("FROM REPORT_CRITERIA ");
        builder.append("WHERE REPORT_KEY=? ");
        builder.append("ORDER BY CRITERIA_NAME ");
        SELECT_REPORT_CRITERIA_BY_KEY = builder.toString();
        builder.setLength(0);

    }

    /**
     * Instantiates a new report dao sql.
     */
    private ReportDaoSql() {}

}
