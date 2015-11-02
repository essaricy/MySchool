package com.myschool.attendance.dao;

/**
 * The Class AttendanceCodeDaoSql.
 */
public class AttendanceCodeDaoSql {

    /** The Constant SELECT. */
    private static final String SELECT;

    /** The Constant SELECT_ALL. */
    public static final String SELECT_ALL;

    /** The Constant SELECT_USE_IN_REFERENCE. */
    public static final String SELECT_USE_IN_REFERENCE;

    /** The Constant SELECT_USE_IN_ASSIGNMENT. */
    public static final String SELECT_USE_IN_ASSIGNMENT;

    static {
        StringBuffer buffer = new StringBuffer();
        buffer.append("SELECT ");
        buffer.append("CODE, ");
        buffer.append("SHORT_DESCRIPTION, ");
        buffer.append("LONG_DESCRIPTION, ");
        buffer.append("USE_IN_REFERENCE, ");
        buffer.append("USE_IN_ASSIGNMENT ");
        buffer.append("FROM ");
        buffer.append("ATTENDANCE_CODE ");
        SELECT = buffer.toString();
        buffer.setLength(0);

        buffer.append(SELECT);
        buffer.append("ORDER BY CODE ASC ");
        SELECT_ALL = buffer.toString();
        buffer.setLength(0);

        buffer.append(SELECT);
        buffer.append("WHERE USE_IN_REFERENCE='Y' ");
        buffer.append("ORDER BY CODE ASC ");
        SELECT_USE_IN_REFERENCE = buffer.toString();
        buffer.setLength(0);

        buffer.append(SELECT);
        buffer.append("WHERE USE_IN_ASSIGNMENT='Y' ");
        buffer.append("ORDER BY CODE ASC ");
        SELECT_USE_IN_ASSIGNMENT = buffer.toString();
        buffer.setLength(0);

    }

}
