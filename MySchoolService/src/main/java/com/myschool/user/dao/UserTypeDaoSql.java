package com.myschool.user.dao;

import com.myschool.user.constants.UserType;

/**
 * The Class UserTypeDaoSql.
 */
public class UserTypeDaoSql {

    /**
     * Builds the select sql.
     *
     * @param uniqueRecord the unique record
     * @return the string
     */
    public static String buildSelectSql(boolean uniqueRecord) {
        StringBuffer buffer = new StringBuffer();
        buffer.append("SELECT USER_TYPE_ID, DESCRIPTION ");
        buffer.append("FROM REF_USER_TYPE ");
        if (uniqueRecord) {
            buffer.append("WHERE USER_TYPE_ID = ? ");
        }
        return buffer.toString();
    }

    /**
     * Builds the select users sql.
     *
     * @return the string
     */
    public static String buildSelectUsersSql() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("SELECT USER_ID, USER_NAME, PASSWORD, REF_USER_TYPE_ID, REF_USER_ID, ");
        buffer.append("(CASE REF_USER_TYPE_ID ");
        buffer.append("WHEN ").append(UserType.ADMIN.getUserTypeValue()).append(" THEN REF_USER_ID || ' - ADMIN' ");
        buffer.append("WHEN ").append(UserType.EMPLOYEE.getUserTypeValue());
        buffer.append(" THEN (SELECT EMPLOYEE_NUMBER || ' - ' || FIRST_NAME || ', ' ||LAST_NAME FROM EMPLOYEE ");
        buffer.append("WHERE EMPLOYEE.EMPLOYEE_ID = USERS.REF_USER_ID) ");
        buffer.append("WHEN ").append(UserType.STUDENT.getUserTypeValue());
        buffer.append(" THEN (SELECT ADMISSION_NUMBER || ' - ' || FIRST_NAME || ', ' ||LAST_NAME FROM STUDENT ");
        buffer.append("WHERE STUDENT.STUDENT_ID = USERS.REF_USER_ID) ");
        buffer.append("END) AS DISPLAY_NAME ");
        buffer.append("FROM USERS ");
        buffer.append("WHERE REF_USER_TYPE_ID = ? ");
        return buffer.toString();
    }

}
