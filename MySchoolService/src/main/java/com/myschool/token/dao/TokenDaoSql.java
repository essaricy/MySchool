package com.myschool.token.dao;

/**
 * The Class TokenDaoSql.
 */
public class TokenDaoSql {

    /** The Constant SELECT_ALL. */
    public static final String SELECT_ALL;

    /** The Constant SELECT_BY_ID. */
    public static final String SELECT_BY_ID;

    /** The Constant INSERT. */
    public static final String INSERT;

    /** The Constant DISPOSE. */
    public static final String DISPOSE;

    static {
        StringBuffer buffer = new StringBuffer();

        buffer.append("SELECT ");
        buffer.append("TOKEN_ID, ");
        buffer.append("CONSUMED_BY, ");
        buffer.append("NECESSITY, ");
        buffer.append("GENERATED_ON, ");
        buffer.append("DISPOSED_ON, ");
        buffer.append("VALIDITY ");
        buffer.append("FROM TOKEN ");
        SELECT_ALL = buffer.toString();
        buffer.setLength(0);

        buffer.append(SELECT_ALL);
        buffer.append("WHERE TOKEN_ID=?");
        SELECT_BY_ID = buffer.toString();
        buffer.setLength(0);

        buffer.append("INSERT INTO TOKEN(");
        buffer.append("TOKEN_ID, CONSUMED_BY, NECESSITY, ");
        buffer.append("GENERATED_ON, VALIDITY ");
        buffer.append(")VALUES ( ?, ?, ?, ?, ?)");
        INSERT = buffer.toString();
        buffer.setLength(0);

        buffer.append("UPDATE TOKEN ");
        buffer.append("SET DISPOSED_ON=CURRENT_DATE, ");
        buffer.append("WHERE TOKEN_ID=? ");
        DISPOSE = buffer.toString();
        buffer.setLength(0);
    }

}
