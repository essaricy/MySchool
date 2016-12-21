package com.myschool.employee.dao;

/**
 * The Class EmployeeSubjectDaoSql.
 */
public final class EmployeeSubjectDaoSql {

    /** The SELECT_ALL. */
    public static String SELECT_ALL;

    /** The SELECT_BY_ID. */
    public static String SELECT_BY_ID;

    /** The SELECT_BY_EMPLOYEE_ID. */
    public static String SELECT_BY_EMPLOYEE_ID;

    /** The SELECT_BY_EMPLOYEE_NUMBER. */
    public static String SELECT_BY_EMPLOYEE_NUMBER;

    public static String SELECT_BY_EMPLOYEE_SUBJECT;

    /** The INSERT. */
    public static String INSERT;

    /** The UPDATE. */
    public static String UPDATE;

    /** The DELETE. */
    public static String DELETE;

    static {
        StringBuffer buffer = new StringBuffer();
        buffer.append("SELECT ");
        // Employee Subject
        buffer.append("EMPLOYEE_SUBJECT.EMPLOYEE_SUBJECT_ID, ");
        buffer.append("EMPLOYEE_SUBJECT.EMPLOYEE_ID, ");
        // Registered Subject
        buffer.append("SUBJECT.SUBJECT_ID AS SUBJECT_SUBJECT_ID, ");
        // Master Subject
        buffer.append("REF_SUBJECT.SUBJECT_ID AS REF_SUBJECT_SUBJECT_ID, ");
        buffer.append("REF_SUBJECT.SUBJECT_NAME AS REF_SUBJECT_SUBJECT_NAME, ");
        // Registered Class
        buffer.append("CLASS.CLASS_ID AS CLASS_CLASS_ID, ");
        // Master Class
        buffer.append("REF_CLASS.CLASS_ID AS REF_CLASS_CLASS_ID, ");
        buffer.append("REF_CLASS.CLASS_NAME AS REF_CLASS_CLASS_NAME, ");
        // Medium
        buffer.append("REF_MEDIUM.MEDIUM_ID AS REF_MEDIUM_MEDIUM_ID, ");
        buffer.append("REF_MEDIUM.DESCRIPTION AS REF_MEDIUM_DESCRIPTION, ");
        // Section
        buffer.append("REF_SECTION.SECTION_ID AS REF_SECTION_SECTION_ID, ");
        buffer.append("REF_SECTION.SECTION_NAME AS REF_SECTION_SECTION_NAME, ");
        // School
        buffer.append("SCHOOL.SCHOOL_ID AS SCHOOL_SCHOOL_ID, ");
        buffer.append("SCHOOL.BRANCH_ID AS SCHOOL_BRANCH_ID, ");
        buffer.append("SCHOOL.REF_DIVISION_ID AS SCHOOL_REF_DIVISION_ID, ");
        buffer.append("SCHOOL.ADDRESS AS SCHOOL_ADDRESS, ");
        buffer.append("SCHOOL.PRIMARY_PHONE_NUMBER AS SCHOOL_PRIMARY_PHONE_NUMBER, ");
        buffer.append("SCHOOL.SECONDARY_PHONE_NUMBER AS SCHOOL_SECONDARY_PHONE_NUMBER, ");
        buffer.append("SCHOOL.MOBILE_NUMBER AS SCHOOL_MOBILE_NUMBER, ");
        buffer.append("SCHOOL.FAX_NUMBER AS SCHOOL_FAX_NUMBER, ");
        buffer.append("SCHOOL.EMAIL_ID AS SCHOOL_EMAIL_ID, ");
        buffer.append("SCHOOL.SCHOOL_NAME AS SCHOOL_SCHOOL_NAME, ");
        buffer.append("SCHOOL.MAP_URL AS SCHOOL_MAP_URL, ");
        // Branch
        buffer.append("BRANCH.BRANCH_ID AS BRANCH_BRANCH_ID, "); 
        buffer.append("BRANCH.BRANCH_CODE AS BRANCH_BRANCH_CODE, "); 
        buffer.append("BRANCH.DESCRIPTION AS BRANCH_DESCRIPTION, ");
        buffer.append("BRANCH.ADDRESS AS BRANCH_ADDRESS, ");
        buffer.append("BRANCH.REF_REGION_ID AS BRANCH_REF_REGION_ID, ");
        buffer.append("BRANCH.PHONE_NUMBER AS BRANCH_PHONE_NUMBER, ");
        buffer.append("BRANCH.EMAIL_ID AS BRANCH_EMAIL_ID, ");
        buffer.append("BRANCH.MAP_URL AS BRANCH_MAP_URL, ");
        // Region
        buffer.append("REF_REGION.REGION_ID AS REF_REGION_REGION_ID, ");
        buffer.append("REF_REGION.REGION_NAME AS REF_REGION_REGION_NAME, ");
        // Division
        buffer.append("REF_DIVISION.DIVISION_ID AS REF_DIVISION_DIVISION_ID, ");
        buffer.append("REF_DIVISION.DIVISION_CODE AS REF_DIVISION_DIVISION_CODE, ");
        buffer.append("REF_DIVISION.DESCRIPTION AS REF_DIVISION_DESCRIPTION ");
        buffer.append("FROM ");
        buffer.append("EMPLOYEE_SUBJECT ");
        buffer.append("INNER JOIN SUBJECT ON SUBJECT.SUBJECT_ID = EMPLOYEE_SUBJECT.SUBJECT_ID ");
        buffer.append("INNER JOIN REF_SUBJECT ON REF_SUBJECT.SUBJECT_ID = SUBJECT.REF_SUBJECT_ID ");
        buffer.append("INNER JOIN CLASS ON CLASS.CLASS_ID = SUBJECT.CLASS_ID ");
        buffer.append("INNER JOIN SCHOOL ON CLASS.SCHOOL_ID = SCHOOL.SCHOOL_ID ");
        buffer.append("INNER JOIN REF_CLASS ON REF_CLASS.CLASS_ID = CLASS.REF_CLASS_ID ");
        buffer.append("INNER JOIN REF_MEDIUM ON REF_MEDIUM.MEDIUM_ID = CLASS.REF_MEDIUM_ID ");
        buffer.append("INNER JOIN REF_SECTION ON REF_SECTION.SECTION_ID = CLASS.REF_SECTION_ID ");
        buffer.append("INNER JOIN BRANCH ON BRANCH.BRANCH_ID= SCHOOL.BRANCH_ID ");
        buffer.append("INNER JOIN REF_REGION ON REF_REGION.REGION_ID = BRANCH.REF_REGION_ID ");
        buffer.append("INNER JOIN REF_DIVISION ON REF_DIVISION.DIVISION_ID = SCHOOL.REF_DIVISION_ID ");
        SELECT_ALL = buffer.toString();
        buffer.setLength(0);

        buffer.append(SELECT_ALL);
        buffer.append("WHERE EMPLOYEE_ID=?");
        SELECT_BY_EMPLOYEE_ID = buffer.toString();
        buffer.setLength(0);

        buffer.append(SELECT_ALL); 
        buffer.append("WHERE ");
        buffer.append("EMPLOYEE_SUBJECT.EMPLOYEE_ID=");
        buffer.append("(SELECT EMPLOYEE_ID FROM EMPLOYEE WHERE EMPLOYEE_NUMBER=?)");
        SELECT_BY_EMPLOYEE_NUMBER = buffer.toString();
        buffer.setLength(0);

        buffer.append(SELECT_ALL); 
        buffer.append("WHERE ");
        buffer.append("EMPLOYEE_SUBJECT.EMPLOYEE_ID=? ");
        buffer.append("AND EMPLOYEE_SUBJECT.SUBJECT_ID=? ");
        SELECT_BY_EMPLOYEE_SUBJECT = buffer.toString();
        buffer.setLength(0);

        buffer.append(SELECT_ALL);
        buffer.append("WHERE EMPLOYEE_SUBJECT_ID=?");
        SELECT_BY_ID = buffer.toString();
        buffer.setLength(0);

        buffer.append("INSERT INTO EMPLOYEE_SUBJECT (EMPLOYEE_SUBJECT_ID, EMPLOYEE_ID, SUBJECT_ID) VALUES (?, ?, ?)");
        INSERT = buffer.toString();
        buffer.setLength(0);

        buffer.append("UPDATE ");
        buffer.append("EMPLOYEE_SUBJECT ");
        buffer.append("SET SUBJECT_ID=? ");
        buffer.append("WHERE EMPLOYEE_SUBJECT_ID=? ");
        UPDATE = buffer.toString();
        buffer.setLength(0);

        buffer.append("DELETE FROM EMPLOYEE_SUBJECT WHERE EMPLOYEE_SUBJECT_ID=?");
        DELETE = buffer.toString();
        buffer.setLength(0);
    }

    /**
     * Instantiates a new employee subject dao sql.
     */
    private EmployeeSubjectDaoSql() {
    }

}
