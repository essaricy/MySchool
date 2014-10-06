package com.myschool.clazz.dao;


/**
 * The Class ClassDaoSql.
 */
public class ClassDaoSql {

    /** The Constant SELECT_ALL. */
    public static final String SELECT_ALL;

    /** The Constant SELECT_BY_ID. */
    public static final String SELECT_BY_ID;

    /** The Constant SELECT_BY_NAME. */
    public static final String SELECT_BY_NAME;

    /** The Constant INSERT. */
    public static final String INSERT;

    /** The Constant UPDATE. */
    public static final String UPDATE;

    /** The Constant DELETE. */
    public static final String DELETE;

    static {
        StringBuffer buffer = new StringBuffer();
        buffer.append("SELECT ");
        buffer.append("CLASS_ID, ");
        buffer.append("CLASS_NAME, ");
        buffer.append("PROMOTION_ORDER ");
        buffer.append("FROM REF_CLASS ");
        SELECT_ALL = buffer.toString();
        buffer.setLength(0);

        buffer.append(SELECT_ALL);
        buffer.append("WHERE CLASS_ID=?");
        SELECT_BY_ID = buffer.toString();
        buffer.setLength(0);

        buffer.append(SELECT_ALL);
        buffer.append("WHERE CLASS_NAME=?");
        SELECT_BY_NAME = buffer.toString();
        buffer.setLength(0);

        buffer.append("INSERT INTO REF_CLASS (");
        buffer.append("CLASS_ID, CLASS_NAME, PROMOTION_ORDER) ");
        buffer.append("VALUES (?, ?, ?) ");
        INSERT = buffer.toString();
        buffer.setLength(0);

        buffer.append("UPDATE REF_CLASS ");
        buffer.append("SET CLASS_NAME=?, ");
        buffer.append("PROMOTION_ORDER= ? ");
        buffer.append("WHERE CLASS_ID=?");
        UPDATE = buffer.toString();
        buffer.setLength(0);

        buffer.append("DELETE FROM REF_CLASS WHERE CLASS_ID=?");
        DELETE = buffer.toString();
        buffer.setLength(0);
    }

    /**
     * Instantiates a new class dao sql.
     */
    private ClassDaoSql() {}

}
