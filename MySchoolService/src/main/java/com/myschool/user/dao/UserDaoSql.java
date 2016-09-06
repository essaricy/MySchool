package com.myschool.user.dao;

import com.myschool.graph.constant.ToDateType;
import com.myschool.user.constants.UserType;

/**
 * The Class UserDaoSql.
 */
public class UserDaoSql {

    /** The Constant SELECT_ALL. */
    public static final String SELECT_ALL;

    /** The Constant SELECT_BY_ID. */
    public static final String SELECT_BY_ID;

    /** The Constant SELECT_BY_USER. */
    public static final String SELECT_BY_USER;

    /** The Constant SELECT_USER_PREFERENCES_BY_ID. */
    public static final String SELECT_USER_PREFERENCES_BY_ID;

    /** The Constant SELECT_USER_SESSIONS_BY_ID. */
    public static final String SELECT_USER_SESSIONS_BY_ID;

    /** The Constant INSERT. */
    public static final String INSERT;

    /** The Constant UPDATE_PASSWORD_BY_USER. */
    public static final String UPDATE_PASSWORD_BY_USER;

    /** The Constant UPDATE_PASSWORD_BY_ID. */
    public static final String UPDATE_PASSWORD_BY_ID;

    /** The Constant SELECT_ALL_THEMES. */
    public static final String SELECT_ALL_THEMES;

    /** The UPDATE_USER_PREFERENCES_BY_ID. */
    public static final String UPDATE_USER_PREFERENCES_BY_ID;

    /** The SELECT_LOGINS_BY_USER_TYPE. */
    private static final String SELECT_LOGINS_BY_USER_TYPE;

    static {
        StringBuilder builder = new StringBuilder();
        builder.append("SELECT USER_ID, USER_NAME, PASSWORD, REF_USER_TYPE_ID, REF_USER_ID, ");
        builder.append("(CASE REF_USER_TYPE_ID ");
        builder.append("WHEN ").append(UserType.ADMIN.getUserTypeValue()).append(" THEN REF_USER_ID || ' - ADMIN' ");
        builder.append("WHEN ").append(UserType.EMPLOYEE.getUserTypeValue());
        builder.append(" THEN (SELECT EMPLOYEE_NUMBER || ' - ' || FIRST_NAME || ', ' ||LAST_NAME FROM EMPLOYEE ");
        builder.append("WHERE EMPLOYEE.EMPLOYEE_ID = USERS.REF_USER_ID) ");
        builder.append("WHEN ").append(UserType.STUDENT.getUserTypeValue());
        builder.append(" THEN (SELECT ADMISSION_NUMBER || ' - ' || FIRST_NAME || ', ' ||LAST_NAME FROM STUDENT ");
        builder.append("WHERE STUDENT.STUDENT_ID = USERS.REF_USER_ID) ");
        builder.append("END) AS DISPLAY_NAME ");
        builder.append("FROM USERS ");
        SELECT_ALL = builder.toString();
        builder.setLength(0);

        builder.append(SELECT_ALL);
        builder.append("WHERE USER_ID=?");
        SELECT_BY_ID = builder.toString();
        builder.setLength(0);

        builder.append(SELECT_ALL);
        builder.append("WHERE REF_USER_TYPE_ID=? ");
        builder.append("AND REF_USER_ID=?");
        SELECT_BY_USER = builder.toString();
        builder.setLength(0);

        builder.append("INSERT INTO USERS (");
        builder.append("USER_ID, ");
        builder.append("USER_NAME, ");
        builder.append("PASSWORD, ");
        builder.append("REF_USER_TYPE_ID, ");
        builder.append("REF_USER_ID) ");
        builder.append("VALUES (?, ?, ?, ?, ?) ");
        INSERT = builder.toString();
        builder.setLength(0);

        builder.append("UPDATE USERS ");
        builder.append("SET PASSWORD=?");
        builder.append("WHERE ");
        builder.append("REF_USER_TYPE_ID=? ");
        builder.append("AND REF_USER_ID=?");
        UPDATE_PASSWORD_BY_USER = builder.toString();
        builder.setLength(0);

        builder.append("UPDATE ");
        builder.append("USERS ");
        builder.append("SET PASSWORD=? ");
        builder.append("WHERE USER_ID=?");
        UPDATE_PASSWORD_BY_ID = builder.toString();
        builder.setLength(0);

        builder.append("SELECT ");
        builder.append("USER_THEME.CODE AS USER_THEME_CODE, ");
        builder.append("USER_THEME.NAME AS USER_THEME_NAME, ");
        builder.append("RECORDS_PER_PAGE, ");
        builder.append("ALLOW_ADS ");
        builder.append("FROM ");
        builder.append("USER_PREFERENCES ");
        builder.append("INNER JOIN USER_THEME ");
        builder.append("ON USER_THEME.CODE = THEME_NAME ");
        builder.append("WHERE USER_ID=?");
        SELECT_USER_PREFERENCES_BY_ID = builder.toString();
        builder.setLength(0);

        builder.append("UPDATE ");
        builder.append("USER_PREFERENCES ");
        builder.append("SET THEME_NAME=?, ");
        builder.append("RECORDS_PER_PAGE=?, ");
        builder.append("ALLOW_ADS=? ");
        builder.append("WHERE USER_ID=?");
        UPDATE_USER_PREFERENCES_BY_ID = builder.toString();
        builder.setLength(0);

        builder.append("SELECT ");
        builder.append("COUNT(SESSION_ID) AS NUMBER_OF_VISITS, ");
        builder.append("MAX(SESSION_START_TIME) AS LAST_VISIT ");
        builder.append("FROM USER_SESSION ");
        builder.append("WHERE USER_ID=?");
        SELECT_USER_SESSIONS_BY_ID = builder.toString();
        builder.setLength(0);

        builder.append("SELECT ");
        builder.append("COUNT(SESSION_ID) AS TO_DATE_VALUE, ");
        builder.append("MAX(SESSION_START_TIME) AS TO_DATE ");
        builder.append("FROM USER_SESSION ");
        builder.append("WHERE ");
        builder.append("USER_ID IN (SELECT USER_ID FROM USERS WHERE REF_USER_TYPE_ID = ?) ");
        SELECT_LOGINS_BY_USER_TYPE = builder.toString();
        builder.setLength(0);

        builder.append("SELECT ");
        builder.append("CODE, ");
        builder.append("NAME ");
        builder.append("FROM USER_THEME ");
        SELECT_ALL_THEMES = builder.toString();
        builder.setLength(0);
    }

    /**
     * Gets the to date logins sql.
     * 
     * @param toDateType the to date type
     * @return the to date logins sql
     */
    public static String getToDateLoginsSql(ToDateType toDateType) {
        StringBuilder builder = new StringBuilder();
        builder.append(SELECT_LOGINS_BY_USER_TYPE);
        builder.append("AND CAST(SESSION_START_TIME AS DATE) > (CURRENT_DATE - ");
        builder.append(toDateType.getDuration());
        builder.append(") AND CAST(SESSION_START_TIME AS DATE) <= CURRENT_DATE ");
        //builder.append("GROUP BY TO_DATE ");
        builder.append("ORDER BY TO_DATE DESC ");
        return builder.toString();
    }

}
