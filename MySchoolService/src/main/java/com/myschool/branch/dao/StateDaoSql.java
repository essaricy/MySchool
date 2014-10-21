package com.myschool.branch.dao;


/**
 * The Class StateDaoSql.
 */
public class StateDaoSql {

    /** The Constant SELECT_ALL. */
    public static final String SELECT_ALL;

    /** The Constant SELECT_BY_STATE. */
    public static final String SELECT_BY_STATE;

    /** The Constant INSERT. */
    public static final String INSERT;

    static {
        StringBuffer buffer = new StringBuffer();
        buffer.append("SELECT ");
        buffer.append("REF_STATE.STATE_ID AS STATE_ID, ");
        buffer.append("REF_STATE.STATE_NAME AS STATE_NAME ");
        buffer.append("FROM ");
        buffer.append("REF_STATE ");
        SELECT_ALL = buffer.toString();
        buffer.setLength(0);

        buffer.append(SELECT_ALL);
        buffer.append("WHERE REF_STATE.STATE_NAME=?");
        SELECT_BY_STATE = buffer.toString();
        buffer.setLength(0);

        buffer.append("INSERT INTO REF_STATE ");
        buffer.append("(STATE_ID, STATE_NAME) ");
        buffer.append("VALUES (?, ?)");
        INSERT = buffer.toString();
        buffer.setLength(0);

    }

    /**
     * Instantiates a new state dao sql.
     */
    private StateDaoSql() {}

}
