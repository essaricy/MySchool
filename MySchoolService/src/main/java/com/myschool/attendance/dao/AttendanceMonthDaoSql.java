package com.myschool.attendance.dao;

/**
 * The Class AttendanceMonthDaoSql.
 */
public class AttendanceMonthDaoSql {

    /** The Constant SELECT_ALL. */
    public static final String SELECT_ALL;

    /** The Constant SELECT_BY_PROFILE_ID. */
    public static final String SELECT_BY_PROFILE_ID;

    /** The Constant SELECT_BY_PROFILE_MONTH. */
    public static final String SELECT_BY_PROFILE_MONTH;

    /** The Constant SELECT_BY_ID. */
    public static final String SELECT_BY_ID;

    /** The Constant INSERT. */
    public static final String INSERT;

    /** The Constant UPDATE. */
    public static final String UPDATE;

    static {
        StringBuffer buffer = new StringBuffer();
        buffer.append("SELECT ");
        buffer.append("ATTENDANCE_MONTH_ID, ");
        buffer.append("ATTENDANCE_PROFILE_ID, ");
        buffer.append("ATTENDANCE_YEAR, ");
        buffer.append("MONTH, ");
        buffer.append(getAttendanceDaysQueryPart());
        buffer.append(" FROM ");
        buffer.append("ATTENDANCE_MONTH ");
        SELECT_ALL = buffer.toString();
        buffer.setLength(0);

        buffer.append(SELECT_ALL);
        buffer.append("WHERE ATTENDANCE_PROFILE_ID=? ");
        buffer.append("ORDER BY ATTENDANCE_YEAR ASC, MONTH ASC ");
        SELECT_BY_PROFILE_ID = buffer.toString();
        buffer.setLength(0);

        buffer.append(SELECT_ALL);
        buffer.append("WHERE ATTENDANCE_PROFILE_ID=? ");
        buffer.append("AND MONTH=? ");
        buffer.append("ORDER BY ATTENDANCE_YEAR ASC, MONTH ASC");
        SELECT_BY_PROFILE_MONTH = buffer.toString();
        buffer.setLength(0);

        buffer.append(SELECT_ALL);
        buffer.append("WHERE ATTENDANCE_MONTH_ID=? ");
        buffer.append("ORDER BY ATTENDANCE_YEAR ASC, MONTH ASC");
        SELECT_BY_ID = buffer.toString();
        buffer.setLength(0);

        buffer.append("INSERT INTO ATTENDANCE_MONTH (");
        buffer.append("ATTENDANCE_MONTH_ID, ");
        buffer.append("ATTENDANCE_PROFILE_ID, ");
        buffer.append("ATTENDANCE_YEAR, ");
        buffer.append("MONTH, ");
        buffer.append(getAttendanceDaysQueryPart());
        buffer.append(") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
        INSERT = buffer.toString();
        buffer.setLength(0);

        buffer.append("UPDATE ATTENDANCE_MONTH ");
        buffer.append("SET ATTENDANCE_YEAR=?, ");
        buffer.append("MONTH=?, ");
        buffer.append("DAY_1=?, DAY_2=?, DAY_3=?, DAY_4=?, DAY_5=?, ");
        buffer.append("DAY_6=?, DAY_7=?, DAY_8=?, DAY_9=?, DAY_10=?, ");
        buffer.append("DAY_11=?, DAY_12=?, DAY_13=?, DAY_14=?, DAY_15=?, ");
        buffer.append("DAY_16=?, DAY_17=?, DAY_18=?, DAY_19=?, DAY_20=?, ");
        buffer.append("DAY_21=?, DAY_22=?, DAY_23=?, DAY_24=?, DAY_25=?, ");
        buffer.append("DAY_26=?, DAY_27=?, DAY_28=?, DAY_29=?, DAY_30=?, DAY_31=? ");
        buffer.append("WHERE ATTENDANCE_MONTH_ID=?");
        UPDATE = buffer.toString();
        buffer.setLength(0);

    }

    /**
     * Gets the attendance days query part.
     * 
     * @return the attendance days query part
     */
    private static String getAttendanceDaysQueryPart() {
        StringBuffer buffer = new StringBuffer();
        for (int date = 1; date <= 31; date++) {
            buffer.append("DAY_").append(date);
            if (date != 31) {
                buffer.append(", ");
            }
        }
        return buffer.toString();
    }

}
