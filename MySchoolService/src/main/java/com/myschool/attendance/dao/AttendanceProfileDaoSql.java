package com.myschool.attendance.dao;


/**
 * The Class AttendanceProfileDaoSql.
 */
public class AttendanceProfileDaoSql {

    /** The Constant SELECT. */
    private static final String SELECT;

    /** The Constant SELECT_ALL. */
    public static final String SELECT_ALL;

    /** The Constant SELECT_BY_ID. */
    public static final String SELECT_BY_ID;

    public static final String SELECT_BY_NAME;

    /** The Constant SELECT_BY_ACADEMIC. */
    public static final String SELECT_BY_ACADEMIC;

    /** The Constant INSERT. */
    public static final String INSERT;

    /** The Constant UPDATE. */
    public static final String UPDATE;

    /** The Constant DELETE. */
    public static final String DELETE;

    static {
        StringBuffer buffer = new StringBuffer();
        buffer.append("SELECT ");
        buffer.append("PROFILE_ID, ");
        buffer.append("PROFILE_NAME, ");
        buffer.append("ACADEMICS.ACADEMIC_YEAR_NAME AS ACADEMICS_ACADEMIC_YEAR_NAME, ");
        buffer.append("ACADEMICS.AY_START_DATE AS ACADEMICS_AY_START_DATE, ");
        buffer.append("ACADEMICS.AY_END_DATE AS ACADEMICS_AY_END_DATE ");
        buffer.append("FROM ");
        buffer.append("ATTENDANCE_PROFILE ");
        buffer.append("INNER JOIN ACADEMICS ");
        buffer.append("ON EFFECTIVE_ACADEMIC=ACADEMICS.ACADEMIC_YEAR_NAME ");
        SELECT = buffer.toString();
        buffer.setLength(0);

        buffer.append(SELECT);
        buffer.append("ORDER BY ACADEMICS_AY_START_DATE DESC, PROFILE_NAME ASC ");
        SELECT_ALL = buffer.toString();
        buffer.setLength(0);

        buffer.append(SELECT);
        buffer.append("WHERE PROFILE_ID=?");
        SELECT_BY_ID = buffer.toString();
        buffer.setLength(0);

        buffer.append(SELECT);
        buffer.append("WHERE PROFILE_NAME=?");
        SELECT_BY_NAME = buffer.toString();
        buffer.setLength(0);

        buffer.append(SELECT);
        buffer.append("WHERE EFFECTIVE_ACADEMIC=?");
        SELECT_BY_ACADEMIC = buffer.toString();
        buffer.setLength(0);

        buffer.append("INSERT INTO ATTENDANCE_PROFILE (");
        buffer.append("PROFILE_ID, PROFILE_NAME, EFFECTIVE_ACADEMIC");
        buffer.append(") VALUES(?, ?, ?)");
        INSERT = buffer.toString();
        buffer.setLength(0);

        buffer.append("UPDATE ATTENDANCE_PROFILE ");
        buffer.append("SET PROFILE_NAME=?,");
        buffer.append("EFFECTIVE_ACADEMIC=? ");
        buffer.append("WHERE PROFILE_ID=?");
        UPDATE = buffer.toString();
        buffer.setLength(0);

        buffer.append("DELETE FROM ATTENDANCE_PROFILE WHERE PROFILE_ID=?");
        DELETE = buffer.toString();
        buffer.setLength(0);

    }

}
