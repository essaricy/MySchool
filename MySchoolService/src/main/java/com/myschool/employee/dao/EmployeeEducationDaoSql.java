package com.myschool.employee.dao;


/**
 * The Class EmployeeEducationDaoSql.
 */
public class EmployeeEducationDaoSql {

    /** The SELECT_ALL. */
    public static String SELECT_ALL;

    /** The SELECT_BY_ID. */
    public static String SELECT_BY_ID;

    /** The SELECT_BY_EMPLOYEE_ID. */
    public static String SELECT_BY_EMPLOYEE_ID;

    /** The SELECT_BY_EMPLOYEE_NUMBER. */
    public static String SELECT_BY_EMPLOYEE_NUMBER;

    /** The SELECT_BY_EDUCATION. */
    public static String SELECT_BY_EDUCATION;

    /** The INSERT. */
    public static String INSERT;

    /** The UPDATE. */
    public static String UPDATE;

    /** The Constant DELETE. */
    public static final String DELETE;

    static {
        StringBuffer buffer = new StringBuffer();
        buffer.append("SELECT ");
        buffer.append("EMPLOYEE_EDUCATION.EDUCATION_ID, ");
        buffer.append("EMPLOYEE_EDUCATION.EMPLOYEE_ID, ");
        buffer.append("EMPLOYEE_EDUCATION.DEGREE, ");
        buffer.append("EMPLOYEE_EDUCATION.SPECIALIZATION, ");
        buffer.append("EMPLOYEE_EDUCATION.YEAR_OF_GRADUATION, "); 
        buffer.append("EMPLOYEE_EDUCATION.PERCENTAGE, ");
        buffer.append("EMPLOYEE_EDUCATION.COLLEGE, ");
        buffer.append("EMPLOYEE_EDUCATION.UNIVERSITY ");
        buffer.append("FROM ");
        buffer.append("EMPLOYEE_EDUCATION ");
        SELECT_ALL = buffer.toString();
        buffer.setLength(0);

        buffer.append(SELECT_ALL);
        buffer.append("WHERE EDUCATION_ID=?");
        SELECT_BY_ID = buffer.toString();
        buffer.setLength(0);

        buffer.append(SELECT_ALL);
        buffer.append("WHERE EMPLOYEE_ID=?");
        SELECT_BY_EMPLOYEE_ID = buffer.toString();
        buffer.setLength(0);

        buffer.append(SELECT_ALL); 
        buffer.append("WHERE ");
        buffer.append("EMPLOYEE_EDUCATION.EMPLOYEE_ID=");
        buffer.append("(SELECT EMPLOYEE_ID FROM EMPLOYEE WHERE EMPLOYEE_NUMBER=?)");
        SELECT_BY_EMPLOYEE_NUMBER = buffer.toString();
        buffer.setLength(0);

        buffer.append(SELECT_ALL);
        buffer.append("WHERE EMPLOYEE_ID=? ");
        buffer.append("AND DEGREE=? ");
        buffer.append("AND YEAR_OF_GRADUATION=?");
        SELECT_BY_EDUCATION = buffer.toString();
        buffer.setLength(0);

        buffer.append("INSERT INTO ");
        buffer.append("EMPLOYEE_EDUCATION( ");
        buffer.append("EDUCATION_ID, ");
        buffer.append("EMPLOYEE_ID, ");
        buffer.append("DEGREE, ");
        buffer.append("SPECIALIZATION, ");
        buffer.append("YEAR_OF_GRADUATION, "); 
        buffer.append("PERCENTAGE, ");
        buffer.append("COLLEGE, ");
        buffer.append("UNIVERSITY ");
        buffer.append(") VALUES (?, ?, ?, ?, ?, ?, ?, ?) ");
        INSERT = buffer.toString();
        buffer.setLength(0);

        buffer.append("UPDATE EMPLOYEE_EDUCATION ");
        buffer.append("SET DEGREE=?, ");
        buffer.append("SPECIALIZATION=?, ");
        buffer.append("YEAR_OF_GRADUATION=?, "); 
        buffer.append("PERCENTAGE=?, ");
        buffer.append("COLLEGE=?, ");
        buffer.append("UNIVERSITY=? ");
        buffer.append("WHERE EDUCATION_ID=? ");
        UPDATE = buffer.toString();
        buffer.setLength(0);

        buffer.append("DELETE FROM EMPLOYEE_EDUCATION ");
        buffer.append("WHERE EDUCATION_ID=? ");
        DELETE = buffer.toString();
        buffer.setLength(0);
    }

}
