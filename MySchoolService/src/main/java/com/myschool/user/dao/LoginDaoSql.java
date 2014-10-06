package com.myschool.user.dao;

import com.myschool.user.constants.UserType;

/**
 * The Class LoginSql.
 */
public class LoginDaoSql {

    /** The Constant SELECT_ALL. */
    public static final String SELECT_ALL;

    /** The Constant SELECT_BY_LOGIN. */
    public static final String SELECT_BY_LOGIN;

    static{
        StringBuilder builder = new StringBuilder();
        builder.append("SELECT ");
        builder.append("USER_ID, ");
        builder.append("PASSWORD, ");
        builder.append("CASE WHEN REF_USER_TYPE_ID = ").append(UserType.ADMIN.getUserTypeValue()).append(" THEN ");
        builder.append("'ADMIN' ");
        builder.append("WHEN REF_USER_TYPE_ID = ").append(UserType.EMPLOYEE.getUserTypeValue()).append(" THEN ");
        builder.append("(SELECT FIRST_NAME ||  ', ' || LAST_NAME FROM EMPLOYEE WHERE EMPLOYEE_ID = REF_USER_ID) ");
        builder.append("WHEN REF_USER_TYPE_ID = ").append(UserType.STUDENT.getUserTypeValue()).append(" THEN ");
        builder.append("(SELECT FIRST_NAME || ' ' || MIDDLE_NAME || ', ' || LAST_NAME FROM STUDENT WHERE STUDENT_ID = REF_USER_ID) ");
        builder.append("END AS USER_NAME, ");
        builder.append("REF_USER_TYPE_ID, ");
        builder.append("REF_USER_ID ");
        builder.append("FROM ");
        builder.append("USERS ");
        SELECT_ALL = builder.toString();
        builder.setLength(0);

        builder.append(SELECT_ALL);
        builder.append("WHERE ");
        builder.append("(USER_NAME=? OR USER_NAME=?) AND PASSWORD=?");
        SELECT_BY_LOGIN = builder.toString();
        builder.setLength(0);
    }

    /**
     * Instantiates a new login dao sql.
     */
    private LoginDaoSql() {}

}
