package com.myschool.employee.dao;

/**
 * The Class DesignationDaoSql.
 */
public class DesignationDaoSql {

    /** The SELECT_ALL. */
    public static String SELECT_ALL;

    /** The SELECT_BY_ID. */
    public static String SELECT_BY_ID;

    /** The INSERT. */
    public static String INSERT;

    /** The UPDATE. */
    public static String UPDATE;

    /** The DELETE. */
    public static String DELETE;

    static {
        StringBuilder builder = new StringBuilder();
        builder.append("SELECT ");
        builder.append("DESIGNATION_ID, ");
        builder.append("DESIGNATION ");
        builder.append("FROM ");
        builder.append("REF_DESIGNATION ");
        SELECT_ALL = builder.toString();
        builder.setLength(0);

        builder.append(SELECT_ALL);
        builder.append("WHERE DESIGNATION_ID=?");
        SELECT_BY_ID = builder.toString();
        builder.setLength(0);

        builder.append("INSERT INTO REF_DESIGNATION (");
        builder.append("DESIGNATION_ID, DESIGNATION");
        builder.append(") VALUES (?, ?)");
        INSERT = builder.toString();
        builder.setLength(0);

        builder.append("UPDATE REF_DESIGNATION ");
        builder.append("SET DESIGNATION=? ");
        builder.append("WHERE DESIGNATION_ID=?");
        UPDATE = builder.toString();
        builder.setLength(0);

        builder.append("DELETE FROM REF_DESIGNATION ");
        builder.append("WHERE DESIGNATION_ID=? ");
        DELETE = builder.toString();
        builder.setLength(0);

    }

    /**
     * Instantiates a new designation dao sql.
     */
    private DesignationDaoSql() {
    }

}
