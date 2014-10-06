package com.myschool.employee.dao;

/**
 * The Class EmployeeContactDaoSql.
 */
public class EmployeeContactDaoSql {

    /** The SELECT. */
    private static String SELECT;

    /** The SELECT_BY_EMPLOYEE_ID. */
    public static String SELECT_BY_EMPLOYEE_ID;

    /** The INSERT. */
    public static String INSERT;

    /** The UPDATE. */
    public static String UPDATE;

    static {
        StringBuffer buffer = new StringBuffer();
        buffer.append("SELECT ");
        buffer.append("EMPLOYEE_ID, ");
        buffer.append("PERMANENT_ADDRESS, ");
        buffer.append("PRESENT_ADDRESS, ");
        buffer.append("PERSONAL_MOBILE_NUMBER, "); 
        buffer.append("PERSONAL_EMAIL_ID, ");
        buffer.append("EMERGENCY_CONTACT_NUMBER, ");
        buffer.append("REF_RELATIONSHIP.CODE AS REF_RELATIONSHIP_CODE, ");
        buffer.append("REF_RELATIONSHIP.NAME AS REF_RELATIONSHIP_NAME, ");
        buffer.append("OFFICE_DESK_PHONE_NUMBER, ");
        buffer.append("OFFICE_DESK_EXTENSION, ");
        buffer.append("OFFICE_MOBILE_NUMBER,  ");
        buffer.append("OFFICE_EMAIL_ID, ");
        buffer.append("EMAIL_NOTIFICATION_TO, ");
        buffer.append("SMS_NOTIFICATION_TO ");
        buffer.append("FROM EMPLOYEE_CONTACT ");
        buffer.append("INNER JOIN REF_RELATIONSHIP ");
        buffer.append("ON REF_RELATIONSHIP.CODE = EMPLOYEE_CONTACT.EMERGENCY_CONTACT_RELATIONSHIP ");
        SELECT = buffer.toString();
        buffer.setLength(0);

        buffer.append(SELECT);
        buffer.append("WHERE EMPLOYEE_ID=?");
        SELECT_BY_EMPLOYEE_ID = buffer.toString();
        buffer.setLength(0);

        buffer.append("INSERT INTO EMPLOYEE_CONTACT( ");
        buffer.append("EMPLOYEE_ID, ");
        buffer.append("PERMANENT_ADDRESS, ");
        buffer.append("PRESENT_ADDRESS, ");
        buffer.append("PERSONAL_MOBILE_NUMBER, "); 
        buffer.append("PERSONAL_EMAIL_ID, ");
        buffer.append("EMERGENCY_CONTACT_NUMBER, ");
        buffer.append("EMERGENCY_CONTACT_RELATIONSHIP, "); 
        buffer.append("OFFICE_DESK_PHONE_NUMBER, ");
        buffer.append("OFFICE_DESK_EXTENSION, ");
        buffer.append("OFFICE_MOBILE_NUMBER,  ");
        buffer.append("OFFICE_EMAIL_ID, ");
        buffer.append("EMAIL_NOTIFICATION_TO, ");
        buffer.append("SMS_NOTIFICATION_TO ");
        buffer.append(") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ");
        INSERT = buffer.toString();
        buffer.setLength(0);

        buffer.append("UPDATE EMPLOYEE_CONTACT ");
        buffer.append("SET PERMANENT_ADDRESS=?, ");
        buffer.append("PRESENT_ADDRESS=?, ");
        buffer.append("PERSONAL_MOBILE_NUMBER=?, "); 
        buffer.append("PERSONAL_EMAIL_ID=?, ");
        buffer.append("EMERGENCY_CONTACT_NUMBER=?, ");
        buffer.append("EMERGENCY_CONTACT_RELATIONSHIP=?, "); 
        buffer.append("OFFICE_DESK_PHONE_NUMBER=?, ");
        buffer.append("OFFICE_DESK_EXTENSION=?, ");
        buffer.append("OFFICE_MOBILE_NUMBER=?,  ");
        buffer.append("OFFICE_EMAIL_ID=?, ");
        buffer.append("EMAIL_NOTIFICATION_TO=?, ");
        buffer.append("SMS_NOTIFICATION_TO=? ");
        buffer.append("WHERE EMPLOYEE_ID=? ");
        UPDATE = buffer.toString();
        buffer.setLength(0);
    }

    /**
     * Instantiates a new employee contact dao sql.
     */
    private EmployeeContactDaoSql() {
    }
}
