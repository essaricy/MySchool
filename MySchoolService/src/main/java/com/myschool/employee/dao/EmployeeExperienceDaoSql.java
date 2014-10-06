package com.myschool.employee.dao;

/**
 * The Class EmployeeExperienceDaoSql.
 */
public class EmployeeExperienceDaoSql {

    /** The SELECT_ALL. */
    public static String SELECT_ALL;

    /** The SELECT_BY_ID. */
    public static String SELECT_BY_ID;

    /** The SELECT_BY_EMPLOYEE_ID. */
    public static String SELECT_BY_EMPLOYEE_ID;

    /** The SELECT_BY_EMPLOYEE_NUMBER. */
    public static String SELECT_BY_EMPLOYEE_NUMBER;

    /** The SELECT_BY_EDUCATION. */
    public static String SELECT_BY_EXPERIENCE;

    /** The INSERT. */
    public static String INSERT;

    /** The UPDATE. */
    public static String UPDATE;

    /** The DELETE. */
    public static String DELETE;

    static {
        StringBuffer buffer = new StringBuffer();
        buffer.append("SELECT ");
        buffer.append("EMPLOYEE_EXPERIENCE.EXPERIENCE_ID, ");
        buffer.append("EMPLOYEE_EXPERIENCE.EMPLOYEE_ID, ");
        buffer.append("EMPLOYEE_EXPERIENCE.EMPLOYER, ");
        buffer.append("EMPLOYEE_EXPERIENCE.JOB_TITLE, ");
        buffer.append("EMPLOYEE_EXPERIENCE.FROM_DATE, ");
        buffer.append("EMPLOYEE_EXPERIENCE.TO_DATE, ");
        buffer.append("EMPLOYEE_EXPERIENCE.EXPERIENCE_IN_MONTHS ");
        buffer.append("FROM EMPLOYEE_EXPERIENCE ");
        SELECT_ALL = buffer.toString();
        buffer.setLength(0);

        buffer.append(SELECT_ALL);
        buffer.append("WHERE EXPERIENCE_ID=?");
        SELECT_BY_ID = buffer.toString();
        buffer.setLength(0);

        buffer.append(SELECT_ALL);
        buffer.append("WHERE EMPLOYEE_ID=?");
        SELECT_BY_EMPLOYEE_ID = buffer.toString();
        buffer.setLength(0);

        buffer.append(SELECT_ALL); 
        buffer.append("WHERE ");
        buffer.append("EMPLOYEE_EXPERIENCE.EMPLOYEE_ID=");
        buffer.append("(SELECT EMPLOYEE_ID FROM EMPLOYEE WHERE EMPLOYEE_NUMBER=?)");
        SELECT_BY_EMPLOYEE_NUMBER = buffer.toString();
        buffer.setLength(0);

        buffer.append(SELECT_ALL);
        buffer.append("WHERE EMPLOYEE_ID=? ");
        buffer.append("AND EMPLOYER=? ");
        buffer.append("AND FROM_DATE=? ");
        buffer.append("AND TO_DATE=?");
        SELECT_BY_EXPERIENCE = buffer.toString();
        buffer.setLength(0);

        buffer.append("INSERT INTO ");
        buffer.append("EMPLOYEE_EXPERIENCE( ");
        buffer.append("EXPERIENCE_ID, ");
        buffer.append("EMPLOYEE_ID, ");
        buffer.append("EMPLOYER, ");
        buffer.append("JOB_TITLE, ");
        buffer.append("FROM_DATE, ");
        buffer.append("TO_DATE, ");
        buffer.append("EXPERIENCE_IN_MONTHS ");
        buffer.append(") VALUES (?, ?, ?, ?, ?, ?, ?) ");
        INSERT = buffer.toString();
        buffer.setLength(0);

        buffer.append("UPDATE ");
        buffer.append("EMPLOYEE_EXPERIENCE ");
        buffer.append("SET EMPLOYER=?, ");
        buffer.append("JOB_TITLE=?, ");
        buffer.append("FROM_DATE=?, ");
        buffer.append("TO_DATE=?, ");
        buffer.append("EXPERIENCE_IN_MONTHS=? ");
        buffer.append("WHERE EXPERIENCE_ID=?");
        UPDATE = buffer.toString();
        buffer.setLength(0);

        buffer.append("DELETE FROM EMPLOYEE_EXPERIENCE ");
        buffer.append("WHERE EXPERIENCE_ID=?");
        DELETE = buffer.toString();
        buffer.setLength(0);
    }

}
