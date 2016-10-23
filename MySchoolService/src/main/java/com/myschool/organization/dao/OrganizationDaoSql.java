package com.myschool.organization.dao;

public class OrganizationDaoSql {

    public static final String SELECT;

    public static final String UPDATE;

    public static final String SELECT_PREFERENCES;

    public static final String UPDATE_NOTIFICATION_SETTINGS;

    public static final String UPDATE_DISPLAY_SETTINGS;

    public static final String UPDATE_SELF_SERVICE_SETTINGS;

    public static final String UPDATE_DEFAULT_THEME;

    public static final String UPDATE_DEFAULT_GALLERY;

    public static final String SELECT_MANIFEST;

    public static final String ROLL_ACADEMIC_YEAR;

    public static final String UPDATE_AYE_PROCESS;

    static {
        StringBuilder builder = new StringBuilder();
        builder.append("SELECT ");
        builder.append("ID, ");
        builder.append("NAME, ");
        builder.append("ADDRESS, ");
        builder.append("PHONE_NUMBER, ");
        builder.append("FAX_NUMBER ");
        builder.append("FROM ");
        builder.append("ORGANIZATION ");
        SELECT = builder.toString();
        builder.setLength(0);

        builder.append("UPDATE ");
        builder.append("ORGANIZATION ");
        builder.append("SET ADDRESS=?, ");
        builder.append("PHONE_NUMBER=?, ");
        builder.append("FAX_NUMBER=? ");
        UPDATE = builder.toString();
        builder.setLength(0);

        builder.append("SELECT ");
        builder.append("ORGANIZATION_ID, ");
        builder.append("NOTIF_USE_EMAILS, ");
        builder.append("NOTIF_EMAIL_EMPLOYEES, ");
        builder.append("NOTIF_EMAIL_STUDENTS, ");
        builder.append("NOTIF_USE_TEXT, ");
        builder.append("NOTIF_TEXT_EMPLOYEES, ");
        builder.append("NOTIF_TEXT_STUDENTS, ");
        builder.append("USE_MENU_ICONS, ");
        builder.append("DEFAULT_THEME, ");
        builder.append("PINNED_GALLERY, ");
        builder.append("USE_EMPLOYEE_SELF_SUBMIT, ");
        builder.append("USE_STUDENT_SELF_SUBMIT ");
        builder.append("FROM ");
        builder.append("ORGANIZATION_PREFERENCES ");
        SELECT_PREFERENCES = builder.toString();
        builder.setLength(0);

        builder.append("UPDATE ");
        builder.append("ORGANIZATION_PREFERENCES ");
        builder.append("SET NOTIF_USE_EMAILS=?, ");
        builder.append("NOTIF_EMAIL_EMPLOYEES=?, ");
        builder.append("NOTIF_EMAIL_STUDENTS=?, ");
        builder.append("NOTIF_USE_TEXT=?, ");
        builder.append("NOTIF_TEXT_EMPLOYEES=?, ");
        builder.append("NOTIF_TEXT_STUDENTS=? ");
        UPDATE_NOTIFICATION_SETTINGS = builder.toString();
        builder.setLength(0);

        builder.append("UPDATE ");
        builder.append("ORGANIZATION_PREFERENCES ");
        builder.append("SET USE_MENU_ICONS=?, ");
        builder.append("DEFAULT_THEME=?, ");
        builder.append("PINNED_GALLERY=? ");
        UPDATE_DISPLAY_SETTINGS = builder.toString();
        builder.setLength(0);

        builder.append("UPDATE ");
        builder.append("ORGANIZATION_PREFERENCES ");
        builder.append("SET USE_EMPLOYEE_SELF_SUBMIT=?, ");
        builder.append("USE_STUDENT_SELF_SUBMIT=? ");
        UPDATE_SELF_SERVICE_SETTINGS = builder.toString();
        builder.setLength(0);

        builder.append("UPDATE ");
        builder.append("ORGANIZATION_PREFERENCES ");
        builder.append("SET DEFAULT_THEME=? ");
        UPDATE_DEFAULT_THEME = builder.toString();
        builder.setLength(0);

        builder.append("UPDATE ");
        builder.append("ORGANIZATION_PREFERENCES ");
        builder.append("SET PINNED_GALLERY=? ");
        UPDATE_DEFAULT_GALLERY = builder.toString();
        builder.setLength(0);

        builder.append("SELECT ");
        builder.append("ORGANIZATION_ID, ");
        builder.append("CURRENT_AY_NAME, ");
        builder.append("MAP_URL, ");
        builder.append("AYE_IN_PROGRESS ");
        builder.append("FROM ");
        builder.append("ORGANIZATION_MANIFEST ");
        SELECT_MANIFEST = builder.toString();
        builder.setLength(0);

        builder.append("UPDATE ");
        builder.append("ORGANIZATION_MANIFEST ");
        builder.append("SET CURRENT_AY_NAME=(");
        builder.append("SELECT ACADEMIC_YEAR_NAME FROM ACADEMICS ");
        builder.append("WHERE AY_START_DATE > (");
        builder.append("SELECT AY_START_DATE FROM ACADEMICS WHERE ACADEMIC_YEAR_NAME = (");
        builder.append("SELECT CURRENT_AY_NAME FROM ORGANIZATION_MANIFEST)) ORDER BY AY_START_DATE LIMIT 1) ");
        ROLL_ACADEMIC_YEAR = builder.toString();
        builder.setLength(0);

        builder.append("UPDATE ");
        builder.append("ORGANIZATION_MANIFEST ");
        builder.append("SET AYE_IN_PROGRESS=?");
        UPDATE_AYE_PROCESS = builder.toString();
        builder.setLength(0);
    }

    private OrganizationDaoSql() { }

}
