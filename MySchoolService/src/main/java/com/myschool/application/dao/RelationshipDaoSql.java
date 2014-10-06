package com.myschool.application.dao;

/**
 * The Class RelationshipDaoSql.
 */
public class RelationshipDaoSql {

    /** The SELECT_ALL. */
    public static String SELECT_ALL;

    /** The SELECT_BY_CODE. */
    public static String SELECT_BY_CODE;

    /** The SELECT_BY_NAME. */
    public static String SELECT_BY_NAME;

    /** The INSERT. */
    public static String INSERT;

    /** The UPDATE. */
    public static String UPDATE;

    /** The DELETE. */
    public static String DELETE;

    static {
        StringBuffer buffer = new StringBuffer();
        buffer.append("SELECT ");
        buffer.append("CODE, NAME ");
        buffer.append("FROM REF_RELATIONSHIP ");
        SELECT_ALL = buffer.toString();
        buffer.setLength(0);

        buffer.append(SELECT_ALL);
        buffer.append("WHERE CODE=?");
        SELECT_BY_CODE = buffer.toString();
        buffer.setLength(0);

        buffer.append(SELECT_ALL);
        buffer.append("WHERE NAME=?");
        SELECT_BY_NAME = buffer.toString();
        buffer.setLength(0);

        buffer.append("INSERT INTO REF_RELATIONSHIP (CODE, NAME) VALUES (?, ?)");
        INSERT = buffer.toString();
        buffer.setLength(0);

        buffer.append("UPDATE REF_RELATIONSHIP SET NAME=? WHERE CODE=?");
        UPDATE = buffer.toString();
        buffer.setLength(0);

        buffer.append("DELETE FROM REF_RELATIONSHIP WHERE CODE=?");
        DELETE = buffer.toString();
        buffer.setLength(0);
    }

    /**
     * Instantiates a new relationship dao sql.
     */
    private RelationshipDaoSql() {
    }

}
