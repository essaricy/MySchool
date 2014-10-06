package com.myschool.employee.dao;

/**
 * The Class EmploymentStatusDaoSql.
 */
public class EmploymentStatusDaoSql {

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
        buffer.append("EMPLOYMENT_STATUS ");
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

        buffer.append("INSERT INTO EMPLOYMENT_STATUS(STATUS_ID, DESCRIPTION ) VALUES (?, ?) ");
        INSERT = buffer.toString();
        buffer.setLength(0);

        buffer.append("UPDATE EMPLOYMENT_STATUS ");
        buffer.append("SET DESCRIPTION=? ");
        buffer.append("WHERE STATUS_ID=?");
        UPDATE = buffer.toString();
        buffer.setLength(0);

        buffer.append("DELETE "); 
        buffer.append("FROM EMPLOYMENT_STATUS ");
        buffer.append("WHERE STATUS_ID=?");
        DELETE = buffer.toString();
        buffer.setLength(0);
    }

    /**
     * Instantiates a new employment status dao sql.
     */
    private EmploymentStatusDaoSql() { }

}
