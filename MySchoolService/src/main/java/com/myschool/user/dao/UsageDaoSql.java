package com.myschool.user.dao;

import java.text.MessageFormat;

/**
 * The Class UsageDaoSql.
 */
public class UsageDaoSql {

	/** The Constant SELECT_ACTIVE_SESSIONS. */
	public static final String SELECT_ACTIVE_SESSIONS;
	
	/** The Constant SELECT_USAGE_COUNT. */
	public static final String SELECT_USAGE_COUNT;

	/** The Constant SELECT_LOGINS_TREND_MONTH_OF_YEAR. */
	public static final String SELECT_LOGINS_TREND_MONTH_OF_YEAR;

	/** The Constant SELECT_LOGINS_TREND_DAY_OF_YEAR. */
	public static final String SELECT_LOGINS_TREND_DAY_OF_YEAR;

	/** The Constant SELECT_LOGINS_TREND_DAY_OF_WEEK. */
	public static final String SELECT_LOGINS_TREND_DAY_OF_WEEK;

	/** The Constant SELECT_LOGINS_TREND_HOUR_OF_DAY. */
	public static final String SELECT_LOGINS_TREND_HOUR_OF_DAY;

	/** The Constant SELECT_LOGINS_TREND_DAY_OF_MONTH. */
	public static final String SELECT_LOGINS_TREND_DAY_OF_MONTH;

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

		buffer.append("SELECT USERS.USER_ID, ");
		buffer.append("USERS.USER_NAME, ");
		buffer.append("NULL AS PASSWORD, ");
		buffer.append("USERS.REF_USER_TYPE_ID, ");
		buffer.append("NULL AS DISPLAY_NAME, ");
		buffer.append("COUNT(USERS.USER_ID) AS NUMBER_OF_VISITS, ");
		buffer.append("MAX(USER_SESSION.SESSION_START_TIME) AS LAST_VISIT ");
		buffer.append("FROM USER_SESSION ");
		buffer.append("INNER JOIN USERS ");
		buffer.append("ON USERS.USER_ID=USER_SESSION.USER_ID ");
		buffer.append("INNER JOIN REF_USER_TYPE ");
		buffer.append("ON REF_USER_TYPE.USER_TYPE_ID=USERS.REF_USER_TYPE_ID ");
		buffer.append("GROUP BY USERS.USER_ID ");
		buffer.append("ORDER BY NUMBER_OF_VISITS DESC ");
		SELECT_USAGE_COUNT = buffer.toString();
		buffer.setLength(0);

		buffer.append("SELECT ");
		buffer.append("{0} AS SEQ_NUMBER, ");
		buffer.append("{1} AS DESCRIPTION, ");
		buffer.append("COUNT(SESSION_ID) AS VALUE ");
		buffer.append("FROM USER_SESSION ");
		buffer.append("WHERE USER_ID IN (SELECT USER_ID FROM USERS WHERE REF_USER_TYPE_ID = ?) "); 
		buffer.append("GROUP BY SEQ_NUMBER, DESCRIPTION ");
		buffer.append("ORDER BY SEQ_NUMBER");
		String trend_base_query = buffer.toString();
		buffer.setLength(0);

		SELECT_LOGINS_TREND_MONTH_OF_YEAR = MessageFormat.format(trend_base_query, "DATE_PART('MONTH', SESSION_START_TIME)", "TO_CHAR(SESSION_START_TIME, 'MONTH')");
		SELECT_LOGINS_TREND_DAY_OF_YEAR = MessageFormat.format(trend_base_query, "DATE_PART('DOY', SESSION_START_TIME)", "NULL");
		SELECT_LOGINS_TREND_DAY_OF_WEEK = MessageFormat.format(trend_base_query, "DATE_PART('ISODOW', SESSION_START_TIME)", "TO_CHAR(SESSION_START_TIME, 'DAY')");
		SELECT_LOGINS_TREND_HOUR_OF_DAY = MessageFormat.format(trend_base_query, "DATE_PART('HOUR', SESSION_START_TIME)", "NULL");
		SELECT_LOGINS_TREND_DAY_OF_MONTH = MessageFormat.format(trend_base_query, "EXTRACT(DAY FROM SESSION_START_TIME)", "NULL");
	}

}
