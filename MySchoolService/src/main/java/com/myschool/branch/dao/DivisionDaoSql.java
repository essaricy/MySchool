package com.myschool.branch.dao;


/**
 * The Class DivisionSql.
 */
public class DivisionDaoSql {

    /** The Constant SELECT_ALL. */
    public static final String SELECT_ALL;

    /** The Constant SELECT_BY_ID. */
    public static final String SELECT_BY_ID;

    /** The Constant SELECT_BY_CODE. */
    public static final String SELECT_BY_CODE;

    /** The Constant INSERT. */
    public static final String INSERT;

    /** The Constant UPDATE. */
    public static final String UPDATE;

    /** The Constant DELETE. */
    public static final String DELETE;

    static {
        StringBuffer buffer = new StringBuffer();
        buffer.append("SELECT ");
        buffer.append("DIVISION_ID, ");
        buffer.append("DIVISION_CODE, ");
        buffer.append("DESCRIPTION ");
        buffer.append("FROM REF_DIVISION ");
        SELECT_ALL = buffer.toString();
        buffer.setLength(0);

        buffer.append(SELECT_ALL);
        buffer.append("WHERE DIVISION_ID=?");
        SELECT_BY_ID = buffer.toString();
        buffer.setLength(0);

        buffer.append("WHERE DIVISION_CODE=?");
        SELECT_BY_CODE = buffer.toString();
        buffer.setLength(0);

        buffer.append("INSERT INTO REF_DIVISION");
        buffer.append("(DIVISION_ID, DIVISION_CODE, DESCRIPTION) ");
        buffer.append("VALUES(?, ?, ?)");
        INSERT = buffer.toString();
        buffer.setLength(0);

        buffer.append("UPDATE REF_DIVISION ");
        buffer.append("SET DIVISION_CODE=?, ");
        buffer.append("DESCRIPTION=? ");
        buffer.append("WHERE DIVISION_ID=? ");
        UPDATE = buffer.toString();
        buffer.setLength(0);

        buffer.append("DELETE FROM REF_DIVISION WHERE DIVISION_ID=?");
        DELETE = buffer.toString();
        buffer.setLength(0);

    }

    /**
     * Instantiates a new division dao sql.
     */
    private DivisionDaoSql() { }

}
