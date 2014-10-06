package com.myschool.branch.dao;

/**
 * The Class RegionDaoSql.
 */
public class RegionDaoSql {

    /** The Constant SELECT_ALL. */
    public static final String SELECT_ALL;

    /** The Constant SELECT_BY_ID. */
    public static final String SELECT_BY_ID;

    /** The Constant SELECT_BY_REGION_NAME. */
    public static final String SELECT_BY_REGION_NAME;

    /** The Constant SELECT_BY_STATE_ID. */
    public static final String SELECT_BY_STATE_ID;

    /** The Constant SELECT_BY_REGION_NAME_STATE_ID. */
    public static final String SELECT_BY_REGION_NAME_STATE_ID;

    /** The Constant INSERT. */
    public static final String INSERT;

    static {
        StringBuffer buffer = new StringBuffer();
        buffer.append("SELECT ");
        buffer.append("REF_REGION.REGION_ID, ");
        buffer.append("REF_REGION.REGION_NAME, ");
        buffer.append("REF_STATE.STATE_ID AS REF_STATE_STATE_ID, ");
        buffer.append("REF_STATE.STATE_NAME REF_STATE_STATE_NAME ");
        buffer.append("FROM REF_REGION ");
        buffer.append("INNER JOIN REF_STATE ");
        buffer.append("ON REF_STATE.STATE_ID = REF_REGION.REF_STATE_ID ");
        SELECT_ALL = buffer.toString();
        buffer.setLength(0);

        buffer.append(SELECT_ALL);
        buffer.append("WHERE REGION_ID=?");
        SELECT_BY_ID = buffer.toString();
        buffer.setLength(0);

        buffer.append(SELECT_ALL);
        buffer.append("WHERE REGION_NAME=? ");
        SELECT_BY_REGION_NAME = buffer.toString();
        buffer.setLength(0);

        buffer.append(SELECT_ALL);
        buffer.append("WHERE REF_STATE.STATE_ID=? ");
        SELECT_BY_STATE_ID = buffer.toString();
        buffer.setLength(0);

        buffer.append(SELECT_ALL);
        buffer.append("WHERE REGION_NAME=? ");
        buffer.append("AND STATE_ID=?");
        SELECT_BY_REGION_NAME_STATE_ID = buffer.toString();
        buffer.setLength(0);

        buffer.append("INSERT INTO REF_REGION (");
        buffer.append("REGION_ID, REGION_NAME, REF_STATE_ID");
        buffer.append(")VALUES (?, ?, ?)");
        INSERT = buffer.toString();
        buffer.setLength(0);
    }

    /**
     * Instantiates a new region dao sql.
     */
    private RegionDaoSql() { }

}
