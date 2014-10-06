package com.myschool.student.dao;

/**
 * The Class StudentDocumentDaoSql.
 */
public class StudentDocumentDaoSql {

    /** The SELECT_ALL. */
    public static String SELECT_ALL;

    /** The SELECT_BY_ID. */
    public static String SELECT_BY_ID;

    /** The SELECT_BY_STUDENT_ID. */
    public static String SELECT_BY_STUDENT_ID;

    /** The SELECT_BY_ADMISSION_NUMBER. */
    public static String SELECT_BY_ADMISSION_NUMBER;

    /** The SELECT_BY_DOCMENT. */
    public static String SELECT_BY_DOCMENT;

    /** The INSERT. */
    public static String INSERT;

    /** The UPDATE. */
    public static String UPDATE;

    /** The DELETE. */
    public static String DELETE;

    static {
        StringBuffer buffer = new StringBuffer();
        buffer.append("SELECT ");
        buffer.append("STUDENT_DOCUMENT.STUDENT_DOCUMENT_ID, ");
        buffer.append("STUDENT_DOCUMENT.STUDENT_ID, ");
        buffer.append("STUDENT_DOCUMENT.DOCUMENT_NUMBER, ");
        buffer.append("STUDENT_DOCUMENT.DOCUMENT_EXPIRY_DATE, "); 
        buffer.append("STUDENT_DOCUMENT.ISSUED_BY, ");
        buffer.append("DOCUMENT.DOCUMENT_ID AS DOCUMENT_DOCUMENT_ID, ");
        buffer.append("DOCUMENT.DOCUMENT_NAME AS DOCUMENT_NAME, ");
        buffer.append("DOCUMENT.DESCRIPTION AS DOCUMENT_DESCRIPTION, ");
        buffer.append("DOCUMENT.STUDENT_APPLICABLE AS DOCUMENT_STUDENT_APPLICABLE, ");
        buffer.append("DOCUMENT.EMPLOYEE_APPLICABLE AS DOCUMENT_EMPLOYEE_APPLICABLE ");
        buffer.append("FROM STUDENT_DOCUMENT ");
        buffer.append("INNER JOIN DOCUMENT ");
        buffer.append("ON DOCUMENT.DOCUMENT_ID = STUDENT_DOCUMENT.DOCUMENT_ID "); 
        SELECT_ALL = buffer.toString();
        buffer.setLength(0);

        buffer.append(SELECT_ALL); 
        buffer.append("WHERE STUDENT_DOCUMENT_ID=?");
        SELECT_BY_ID = buffer.toString();
        buffer.setLength(0);

        buffer.append(SELECT_ALL); 
        buffer.append("WHERE STUDENT_ID=? ");
        buffer.append("AND STUDENT_DOCUMENT.DOCUMENT_ID=?");
        SELECT_BY_DOCMENT = buffer.toString();
        buffer.setLength(0);

        buffer.append(SELECT_ALL); 
        buffer.append("WHERE ");
        buffer.append("STUDENT_DOCUMENT.STUDENT_ID=");
        buffer.append("(SELECT STUDENT_ID FROM STUDENT WHERE ADMISSION_NUMBER=?)");
        SELECT_BY_ADMISSION_NUMBER = buffer.toString();
        buffer.setLength(0);

        buffer.append(SELECT_ALL); 
        buffer.append("WHERE STUDENT_ID=?");
        SELECT_BY_STUDENT_ID = buffer.toString();
        buffer.setLength(0);

        buffer.append("INSERT INTO ");
        buffer.append("STUDENT_DOCUMENT( ");
        buffer.append("STUDENT_DOCUMENT_ID, ");
        buffer.append("STUDENT_ID, ");
        buffer.append("DOCUMENT_ID, ");
        buffer.append("DOCUMENT_NUMBER, ");
        buffer.append("DOCUMENT_EXPIRY_DATE, ");
        buffer.append("ISSUED_BY ");
        buffer.append(") VALUES (?, ?, ?, ?, ?, ?) ");
        INSERT = buffer.toString();
        buffer.setLength(0);

        buffer.append("UPDATE STUDENT_DOCUMENT ");
        buffer.append("SET DOCUMENT_ID=?, ");
        buffer.append("DOCUMENT_NUMBER=?, ");
        buffer.append("DOCUMENT_EXPIRY_DATE=?, ");
        buffer.append("ISSUED_BY=? ");
        buffer.append("WHERE STUDENT_DOCUMENT_ID=? ");
        UPDATE = buffer.toString();
        buffer.setLength(0);

        buffer.append("DELETE FROM STUDENT_DOCUMENT ");
        buffer.append("WHERE STUDENT_DOCUMENT_ID=? ");
        DELETE = buffer.toString();
        buffer.setLength(0);

    }

    /**
     * Instantiates a new student document dao sql.
     */
    private StudentDocumentDaoSql() {
    }
}
