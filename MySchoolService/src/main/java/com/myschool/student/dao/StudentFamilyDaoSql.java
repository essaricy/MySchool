package com.myschool.student.dao;


/**
 * The Class StudentFamilyDaoSql.
 */
public class StudentFamilyDaoSql {

    /** The Constant SELECT_ALL. */
    public static final String SELECT_ALL;

    /** The Constant SELECT_BY_ID. */
    public static final String SELECT_BY_ID;

    /** The Constant INSERT. */
    public static final String INSERT;

    /** The Constant UPDATE. */
    public static final String UPDATE;

    /** The Constant DELETE. */
    public static final String DELETE;

    /** The Constant SELECT_BY_ADMISSION_NUMBER. */
    public static final String SELECT_BY_ADMISSION_NUMBER;

    /** The Constant SELECT_BY_FAMILY_MEMBER. */
    public static final String SELECT_BY_FAMILY_MEMBER;

    static {
        StringBuffer buffer = new StringBuffer();
        buffer.append("SELECT ");
        buffer.append("STUDENT_FAMILY.ID AS FAMILY_MEMBER_ID, ");
        buffer.append("REF_RELATIONSHIP.CODE AS REF_RELATIONSHIP_CODE, ");
        buffer.append("REF_RELATIONSHIP.NAME AS REF_RELATIONSHIP_NAME, ");
        buffer.append("STUDENT_FAMILY.NAME, ");
        buffer.append("STUDENT_FAMILY.OCCUPATION, ");
        buffer.append("STUDENT_FAMILY.MOBILE_NUMBER, ");
        buffer.append("STUDENT_FAMILY.EMAIL_ID, ");
        buffer.append("STUDENT_FAMILY.AVAIL_SMS, ");
        buffer.append("STUDENT_FAMILY.AVAIL_EMAIL ");
        buffer.append("FROM ");
        buffer.append("STUDENT_FAMILY ");
        buffer.append("INNER JOIN REF_RELATIONSHIP ");
        buffer.append("ON REF_RELATIONSHIP.CODE = STUDENT_FAMILY.RELATIONSHIP_CODE ");
        SELECT_ALL = buffer.toString();
        buffer.setLength(0);

        buffer.append(SELECT_ALL);
        buffer.append("WHERE STUDENT_FAMILY.ID=?");
        SELECT_BY_ID = buffer.toString();
        buffer.setLength(0);

        buffer.append("INSERT INTO STUDENT_FAMILY ( ");
        buffer.append("ID, STUDENT_ID, RELATIONSHIP_CODE, NAME, OCCUPATION, ");
        buffer.append("MOBILE_NUMBER, EMAIL_ID, AVAIL_SMS, AVAIL_EMAIL) ");
        buffer.append("VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");
        INSERT = buffer.toString();
        buffer.setLength(0);

        buffer.append("UPDATE STUDENT_FAMILY ");
        buffer.append("SET RELATIONSHIP_CODE=?,");
        buffer.append("NAME=?,");
        buffer.append("OCCUPATION=?,");
        buffer.append("MOBILE_NUMBER=?,");
        buffer.append("EMAIL_ID=?,");
        buffer.append("AVAIL_SMS=?,");
        buffer.append("AVAIL_EMAIL=? ");
        buffer.append("WHERE ID=?");
        UPDATE = buffer.toString();
        buffer.setLength(0);

        buffer.append("DELETE FROM STUDENT_FAMILY WHERE ID=?");
        DELETE = buffer.toString();
        buffer.setLength(0);

        buffer.append(SELECT_ALL);
        buffer.append("WHERE STUDENT_FAMILY.STUDENT_ID=");
        buffer.append("(SELECT STUDENT_ID FROM STUDENT WHERE ADMISSION_NUMBER=?)");
        SELECT_BY_ADMISSION_NUMBER = buffer.toString();
        buffer.setLength(0);

        buffer.append(SELECT_ALL);
        buffer.append("WHERE STUDENT_ID=?");
        buffer.append("AND STUDENT_FAMILY.RELATIONSHIP_CODE=? ");
        buffer.append("AND STUDENT_FAMILY.NAME=?");
        SELECT_BY_FAMILY_MEMBER = buffer.toString();
        buffer.setLength(0);

    }

    /**
     * Instantiates a new student family dao sql.
     */
    private StudentFamilyDaoSql() {}

}
