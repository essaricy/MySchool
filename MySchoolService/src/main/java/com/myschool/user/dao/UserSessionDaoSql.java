package com.myschool.user.dao;


/**
 * The Class UserSessionDaoSql.
 */
public class UserSessionDaoSql {

	/** The Constant SELECT_ALL. */
	public static final String SELECT_ALL;
	
	/** The Constant SELECT_BY_ID. */
	public static final String SELECT_BY_ID;
	
	/** The Constant INSERT. */
	public static final String INSERT;

	/** The Constant UPDATE. */
	public static final String UPDATE;

	/** The Constant INSERT_USER_ACTIVITY. */
	public static final String INSERT_USER_ACTIVITY;

	static {
		StringBuilder builder = new StringBuilder();
		builder.append("SELECT ");
		builder.append("SESSION_ID,");
		builder.append("USER_ID,");
		builder.append("SESSION_START_TIME,");
		builder.append("SESSION_END_TIME,");
		builder.append("DEVICE,");
		builder.append("BROWSER,");
		builder.append("IP_ADDRESS ");
		builder.append("FROM USER_SESSION ");
		SELECT_ALL = builder.toString();
        builder.setLength(0);

        builder.append(SELECT_ALL);
		builder.append("WHERE ");
		builder.append("SESSION_ID=?");
		SELECT_BY_ID = builder.toString();
        builder.setLength(0);

        builder.append("INSERT INTO USER_SESSION (");
        builder.append("SESSION_ID, SESSION_START_TIME, DEVICE, BROWSER, IP_ADDRESS");
        builder.append(")VALUES (?, ?, ?, ?, ?)");
        INSERT = builder.toString();
        builder.setLength(0);

        builder.append("UPDATE USER_SESSION ");
        builder.append("SET USER_ID=?, ");
        builder.append("SESSION_START_TIME=?, ");
        builder.append("SESSION_END_TIME=?, ");
        builder.append("DEVICE=?, ");
        builder.append("BROWSER=?, ");
        builder.append("IP_ADDRESS=? ");
        builder.append("WHERE SESSION_ID=?");
        UPDATE = builder.toString();
        builder.setLength(0);

        builder.append("INSERT INTO USER_ACTIVITY (REQUEST_ID, SESSION_ID, REQUEST_URL, REQUEST_TIME, SERVED_TIME) VALUES (?, ?, ?, ?, ?)");
        INSERT_USER_ACTIVITY = builder.toString();
        builder.setLength(0);
	}

}
