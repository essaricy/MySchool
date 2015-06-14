package com.myschool.user.dao;

/**
 * The Class UsageDaoSql.
 */
public class UsageDaoSql {

	/** The Constant SELECT_ACTIVE_SESSIONS. */
	public static final String SELECT_ACTIVE_SESSIONS;

	static {
		StringBuffer buffer = new StringBuffer();
		buffer.append("SELECT ");
		buffer.append("SESSION_ID, ");
		buffer.append("USER_ID, ");
		buffer.append("SESSION_START_TIME, ");
		buffer.append("SESSION_END_TIME, ");
		buffer.append("DEVICE, ");
		buffer.append("BROWSER, ");
		buffer.append("IP_ADDRESS ");
		buffer.append("FROM USER_SESSION ");
		buffer.append("WHERE SESSION_END_TIME IS NULL ");
		buffer.append("ORDER BY SESSION_START_TIME DESC ");
		SELECT_ACTIVE_SESSIONS = buffer.toString();
		buffer.setLength(0);
	}

}
