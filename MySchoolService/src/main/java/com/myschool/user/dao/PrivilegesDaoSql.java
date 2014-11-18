package com.myschool.user.dao;

import java.util.List;

import com.myschool.common.util.CollectionUtil;

/**
 * The Class PrivilegesDaoSql.
 */
public class PrivilegesDaoSql {

    /**
     * Builds the default privileges sql.
     *
     * @param uniqueRecord the unique record
     * @return the string
     */
    public static String buildDefaultPrivilegesSql(boolean uniqueRecord) {
        StringBuffer buffer = new StringBuffer();
        buffer.append("SELECT ");
        buffer.append("USER_TYPE_ID, DESCRIPTION, ");
        buffer.append("REF_MODULE.MODULE_ID AS REF_MODULE_MODULE_ID, ");
        buffer.append("REF_MODULE.MODULE_NAME AS REF_MODULE_MODULE_NAME, ");
        buffer.append("REF_MODULE.CAN_ADMIN_ACCESS AS REF_MODULE_CAN_ADMIN_ACCESS, ");
        buffer.append("REF_MODULE.CAN_EMPLOYEE_ACCESS AS REF_MODULE_CAN_EMPLOYEE_ACCESS, ");
        buffer.append("REF_MODULE.CAN_STUDENT_ACCESS AS REF_MODULE_CAN_STUDENT_ACCESS, ");
        buffer.append("REF_MODULE.ACCESS_URL AS REF_MODULE_ACCESS_URL, ");
        buffer.append("FUNCTION.FUNCTION_ID AS FUNCTION_FUNCTION_ID, ");
        buffer.append("FUNCTION.FUNCTION_NAME AS FUNCTION_FUNCTION_NAME, ");
        buffer.append("FUNCTION.ICON_URL AS FUNCTION_ICON_URL, ");
        buffer.append("FUNCTION.ACCESS_URL AS FUNCTION_ACCESS_URL, ");
        buffer.append("CAN_VIEW, CAN_CREATE, CAN_UPDATE, CAN_DELETE ");

        buffer.append("FROM DEFAULT_USER_ACCESS ");
        buffer.append("INNER JOIN REF_USER_TYPE ");
        buffer.append("ON REF_USER_TYPE.USER_TYPE_ID = DEFAULT_USER_ACCESS.REF_USER_TYPE_ID ");
        buffer.append("INNER JOIN FUNCTION ");
        buffer.append("ON FUNCTION.FUNCTION_ID = DEFAULT_USER_ACCESS.FUNCTION_ID ");
        buffer.append("INNER JOIN REF_MODULE ");
        buffer.append("ON REF_MODULE.MODULE_ID = FUNCTION.REF_MODULE_ID ");

        if (uniqueRecord) {
            buffer.append("WHERE REF_USER_TYPE_ID = ? ");
        }
        buffer.append("ORDER BY REF_MODULE.MODULE_ID, FUNCTION.FUNCTION_NAME ");
        return buffer.toString();
    }

    /**
     * Builds the select default privileges sql.
     *
     * @return the string
     */
    public static String buildSelectFunctionsSql() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("SELECT ");
        buffer.append("REF_MODULE.MODULE_ID AS REF_MODULE_MODULE_ID, ");
        buffer.append("REF_MODULE.MODULE_NAME AS REF_MODULE_MODULE_NAME, ");
        buffer.append("REF_MODULE.CAN_ADMIN_ACCESS AS REF_MODULE_CAN_ADMIN_ACCESS, ");
        buffer.append("REF_MODULE.CAN_EMPLOYEE_ACCESS AS REF_MODULE_CAN_EMPLOYEE_ACCESS, ");
        buffer.append("REF_MODULE.CAN_STUDENT_ACCESS AS REF_MODULE_CAN_STUDENT_ACCESS, ");
        buffer.append("REF_MODULE.ACCESS_URL AS REF_MODULE_ACCESS_URL, ");
        buffer.append("FUNCTION.FUNCTION_ID AS FUNCTION_FUNCTION_ID, ");
        buffer.append("FUNCTION.FUNCTION_NAME AS FUNCTION_FUNCTION_NAME, ");
        buffer.append("FUNCTION.ICON_URL AS FUNCTION_ICON_URL, ");
        buffer.append("FUNCTION.ACCESS_URL AS FUNCTION_ACCESS_URL ");
        buffer.append("FROM FUNCTION ");
        buffer.append("INNER JOIN REF_MODULE ");
        buffer.append("ON REF_MODULE.MODULE_ID = FUNCTION.REF_MODULE_ID ");
        buffer.append("ORDER BY REF_MODULE_MODULE_ID, FUNCTION_NAME ");
        return buffer.toString();
    }

    /**
     * Builds the delete default privileges sql.
     *
     * @return the string
     */
    public static String buildDeleteDefaultPrivilegesSql() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("DELETE FROM DEFAULT_USER_ACCESS WHERE REF_USER_TYPE_ID = ? ");
        return buffer.toString();
    }

    /**
     * Builds the create default privileges sql.
     *
     * @return the string
     */
    public static String buildCreateDefaultPrivilegesSql() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("INSERT INTO DEFAULT_USER_ACCESS (REF_USER_TYPE_ID, FUNCTION_ID, CAN_VIEW, CAN_CREATE, CAN_UPDATE, CAN_DELETE) ");
        buffer.append("VALUES (?, ?, ?, ?, ?, ?) ");
        return buffer.toString();
    }

    /**
     * Builds the user privileges sql.
     *
     * @param uniqueRecord the unique record
     * @return the string
     */
    public static String buildUserPrivilegesSql(boolean uniqueRecord) {
        StringBuffer buffer = new StringBuffer();
        buffer.append("SELECT ");
        buffer.append("USERS.USER_ID, ");
        buffer.append("REF_MODULE.MODULE_ID AS REF_MODULE_MODULE_ID, ");
        buffer.append("REF_MODULE.MODULE_NAME AS REF_MODULE_MODULE_NAME, ");
        buffer.append("REF_MODULE.CAN_ADMIN_ACCESS AS REF_MODULE_CAN_ADMIN_ACCESS, ");
        buffer.append("REF_MODULE.CAN_EMPLOYEE_ACCESS AS REF_MODULE_CAN_EMPLOYEE_ACCESS, ");
        buffer.append("REF_MODULE.CAN_STUDENT_ACCESS AS REF_MODULE_CAN_STUDENT_ACCESS, ");
        buffer.append("REF_MODULE.ACCESS_URL AS REF_MODULE_ACCESS_URL, ");
        buffer.append("FUNCTION.FUNCTION_ID AS FUNCTION_FUNCTION_ID, ");
        buffer.append("FUNCTION.FUNCTION_NAME AS FUNCTION_FUNCTION_NAME, ");
        buffer.append("FUNCTION.ICON_URL AS FUNCTION_ICON_URL, ");
        buffer.append("FUNCTION.ACCESS_URL AS FUNCTION_ACCESS_URL, ");
        buffer.append("CAN_VIEW, CAN_CREATE, CAN_UPDATE, CAN_DELETE ");
        buffer.append("FROM USER_ACCESS ");
        buffer.append("INNER JOIN USERS ");
        buffer.append("ON USERS.USER_ID = USER_ACCESS.USER_ID ");
        buffer.append("INNER JOIN FUNCTION ");
        buffer.append("ON FUNCTION.FUNCTION_ID = USER_ACCESS.FUNCTION_ID ");
        buffer.append("INNER JOIN REF_MODULE ");
        buffer.append("ON REF_MODULE.MODULE_ID = FUNCTION.REF_MODULE_ID ");
        if (uniqueRecord) {
            buffer.append("WHERE USERS.USER_ID = ? ");
        }
        buffer.append("ORDER BY REF_MODULE_MODULE_ID, FUNCTION_FUNCTION_NAME ");
        return buffer.toString();
    }

    /**
     * Builds the delete user privileges sql.
     *
     * @return the string
     */
    public static String buildDeleteUserPrivilegesSql() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("DELETE FROM USER_ACCESS WHERE USER_ID = ? ");
        return buffer.toString();
    }

    /**
     * Builds the create user privileges sql.
     *
     * @return the string
     */
    public static String buildCreateUserPrivilegesSql() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("INSERT INTO USER_ACCESS (USER_ID, FUNCTION_ID, CAN_VIEW, CAN_CREATE, CAN_UPDATE, CAN_DELETE) ");
        buffer.append("VALUES (?, ?, ?, ?, ?, ?) ");
        return buffer.toString();
    }

    /**
     * Gets the copy privileges sql.
     * 
     * @return the copy privileges sql
     */
    public static String getCopyPrivilegesSql(Integer copyFromUserId,
            List<Integer> copyToUserIds) {
        return "SELECT copy_user_privileges(" + copyFromUserId + ", '" + CollectionUtil.getSqlArray(copyToUserIds) + "'::INT[]);";
    }

}
