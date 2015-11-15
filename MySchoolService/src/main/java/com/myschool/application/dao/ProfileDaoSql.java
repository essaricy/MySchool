package com.myschool.application.dao;


/**
 * The Class ProfileDaoSql.
 */
public class ProfileDaoSql {
    
    /** The SELECT_ORGANIZATION_PROFILE. */
    public static String SELECT_ORGANIZATION_PROFILE;

    /** The UPDATE_ORGANIZATION_PROFILE. */
    public static String UPDATE_ORGANIZATION_PROFILE;

    /** The SELECT_MYSCHOOL_PROFILE. */
    public static String SELECT_MYSCHOOL_PROFILE;

    /** The UPDATE_MYSCHOOL_PROFILE. */
    public static String UPDATE_MYSCHOOL_PROFILE;

    /** The UPDATE_MYSCHOOL_PROFILE_PIN_GALLERY. */
    public static String UPDATE_MYSCHOOL_PROFILE_PIN_GALLERY;

    /** The UPDATE_AYE_STATUS. */
    public static String UPDATE_AYE_STATUS;

    static {
        StringBuilder builder = new StringBuilder();
        builder.append("SELECT ");
        builder.append("ORGANIZATION_NAME, ");
        builder.append("CURRENT_AY_NAME, ");
        builder.append("ADDRESS, ");
        builder.append("PHONE_NUMBER, ");
        builder.append("FAX_NUMBER, ");
        builder.append("MAP_URL ");
        builder.append("FROM ");
        builder.append("ORGANIZATION_PROFILE ");
        SELECT_ORGANIZATION_PROFILE = builder.toString();
        builder.setLength(0);

        builder.append("UPDATE ORGANIZATION_PROFILE ");
        builder.append("SET ADDRESS=?, ");
        builder.append("PHONE_NUMBER=?, ");
        builder.append("FAX_NUMBER=? ");
        UPDATE_ORGANIZATION_PROFILE = builder.toString();
        builder.setLength(0);

        builder.append("SELECT ");
        builder.append("EMAIL_ACTIVE, ");
        builder.append("EMAIL_EMPLOYEES, ");
        builder.append("EMAIL_STUDENTS, ");
        builder.append("SMS_ACTIVE, ");
        builder.append("SMS_EMPLOYEES, ");
        builder.append("SMS_STUDENTS, ");
        builder.append("USE_MENU_ICONS, ");
        builder.append("AYE_IN_PROGRESS, ");
        builder.append("USE_EMPLOYEE_SELF_SUBMIT, ");
        builder.append("USE_STUDENT_SELF_SUBMIT, ");
        builder.append("PINNED_GALLERY ");
        builder.append("FROM ");
        builder.append("MYSCHOOL_PROFILE ");
        SELECT_MYSCHOOL_PROFILE = builder.toString();
        builder.setLength(0);

        builder.append("UPDATE MYSCHOOL_PROFILE ");
        builder.append("SET EMAIL_ACTIVE=?, ");
        builder.append("EMAIL_EMPLOYEES=?, ");
        builder.append("EMAIL_STUDENTS=?, ");
        builder.append("SMS_ACTIVE=?, ");
        builder.append("SMS_EMPLOYEES=?, ");
        builder.append("SMS_STUDENTS=?, ");
        builder.append("USE_MENU_ICONS=?, ");
        builder.append("USE_EMPLOYEE_SELF_SUBMIT=?, ");
        builder.append("USE_STUDENT_SELF_SUBMIT=? ");
        UPDATE_MYSCHOOL_PROFILE = builder.toString();
        builder.setLength(0);

        builder.append("UPDATE MYSCHOOL_PROFILE ");
        builder.append("SET PINNED_GALLERY=? ");
        UPDATE_MYSCHOOL_PROFILE_PIN_GALLERY = builder.toString();

        builder.append("UPDATE MYSCHOOL_PROFILE ");
        builder.append("SET AYE_IN_PROGRESS=? ");
        UPDATE_AYE_STATUS = builder.toString();
        builder.setLength(0);
    }

}
