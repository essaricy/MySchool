package com.myschool.student.dao;

/**
 * The Class AdmissionStatusDaoSql.
 */
public class AdmissionStatusDaoSql {

    /** The Constant SELECT_ALL. */
    public static final String SELECT_ALL;

    /** The Constant SELECT_BY_ID. */
    public static final String SELECT_BY_ID;

    /** The Constant INSERT. */
    public static final String INSERT;

    /** The Constant SELECT_BY_DESCRIPTION. */
    public static final String SELECT_BY_DESCRIPTION;

    /** The Constant UPDATE. */
    public static final String UPDATE;

    /** The Constant DELETE. */
    public static final String DELETE;

    static {
        StringBuffer buffer = new StringBuffer();
        buffer.append("SELECT "); 
        buffer.append("STATUS_ID, ");
        buffer.append("DESCRIPTION ");
        buffer.append("FROM ");
        buffer.append("ADMISSION_STATUS ");
        SELECT_ALL = buffer.toString();
        buffer.setLength(0);

        buffer.append(SELECT_ALL);
        buffer.append("WHERE STATUS_ID=?");
        SELECT_BY_ID = buffer.toString();
        buffer.setLength(0);

        buffer.append(SELECT_ALL);
        buffer.append("WHERE DESCRIPTION=?");
        SELECT_BY_DESCRIPTION = buffer.toString();
        buffer.setLength(0);

        buffer.append("INSERT INTO ADMISSION_STATUS(STATUS_ID, DESCRIPTION ) VALUES (?, ?) ");
        INSERT = buffer.toString();
        buffer.setLength(0);

        buffer.append("UPDATE ADMISSION_STATUS ");
        buffer.append("SET DESCRIPTION=? ");
        buffer.append("WHERE STATUS_ID=?");
        UPDATE = buffer.toString();
        buffer.setLength(0);

        buffer.append("DELETE "); 
        buffer.append("FROM ADMISSION_STATUS ");
        buffer.append("WHERE STATUS_ID=?");
        DELETE = buffer.toString();
        buffer.setLength(0);
    }

    /**
     * Instantiates a new admission status dao sql.
     */
    private AdmissionStatusDaoSql() { }

}
