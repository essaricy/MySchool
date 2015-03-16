package com.myschool.academic.dao;

import java.util.HashMap;
import java.util.Map;

import com.myschool.academic.dto.HolidaySearchCriteria;
import com.myschool.common.util.DatabaseUtil;

/**
 * The Class HolidayDaoSql.
 */
public final class HolidayDaoSql {

    /** The SELECT_ALL. */
    public static String SELECT_ALL;

    /** The SELECT_BY_ID. */
    public static String SELECT_BY_ID;

    /** The SELECT_BY_HOLIDAY. */
    public static String SELECT_BY_HOLIDAY;

    /** The INSERT. */
    public static String INSERT;

    /** The UPDATE. */
    public static String UPDATE;

    /** The DELETE. */
    public static String DELETE;

    static {
        StringBuffer buffer = new StringBuffer();
        buffer.append("SELECT ");
        buffer.append("HOLIDAY_ID, HOLIDAY_NAME, START_DATE, END_DATE ");
        buffer.append("FROM HOLIDAYS ");
        SELECT_ALL = buffer.toString();
        buffer.setLength(0);

        buffer.append(SELECT_ALL);
        buffer.append("WHERE HOLIDAY_ID=?");
        SELECT_BY_ID = buffer.toString();
        buffer.setLength(0);

        buffer.append(SELECT_ALL);
        buffer.append("WHERE HOLIDAY_NAME=? ");
        buffer.append("AND START_DATE=? ");
        buffer.append("AND END_DATE=?");
        SELECT_BY_HOLIDAY = buffer.toString();
        buffer.setLength(0);

        buffer.append("INSERT INTO HOLIDAYS (");
        buffer.append("HOLIDAY_ID, HOLIDAY_NAME, START_DATE, END_DATE");
        buffer.append(") VALUES (?, ?, ?, ?)");
        INSERT = buffer.toString();
        buffer.setLength(0);

        buffer.append("UPDATE HOLIDAYS ");
        buffer.append("SET HOLIDAY_NAME=?, ");
        buffer.append("START_DATE=?, ");
        buffer.append("END_DATE=? ");
        buffer.append("WHERE HOLIDAY_ID=?");
        UPDATE = buffer.toString();
        buffer.setLength(0);

        buffer.append("DELETE FROM HOLIDAYS WHERE HOLIDAY_ID=? ");
        DELETE = buffer.toString();
        buffer.setLength(0);
    }

    /**
     * Instantiates a new holiday dao sql.
     */
    private HolidayDaoSql() {
    }

    /**
     * Gets the holidays sql.
     * 
     * @param holidaySearchCriteria the holiday search criteria
     * @return the holidays sql
     */
    public static String getHolidaysSql(
            HolidaySearchCriteria holidaySearchCriteria) {
        if (holidaySearchCriteria == null) {
            return SELECT_ALL;
        } else {
            StringBuffer buffer = new StringBuffer();
            buffer.append(SELECT_ALL);
            Map<String, String> whereClauseMap = new HashMap<String, String>();
            whereClauseMap.put("START_DATE>=(SELECT AY_START_DATE FROM ACADEMICS WHERE ACADEMIC_YEAR_NAME='?') "
                    + "AND END_DATE<=(SELECT AY_END_DATE FROM ACADEMICS WHERE ACADEMIC_YEAR_NAME='?')", holidaySearchCriteria.getAcademicYear());
            whereClauseMap.put("START_DATE>='?'", holidaySearchCriteria.getStartDate());
            whereClauseMap.put("END_DATE<='?'", holidaySearchCriteria.getEndDate());
            buffer.append(DatabaseUtil.getWhereClause(whereClauseMap));
            buffer.append(" ORDER BY START_DATE");
            return buffer.toString();
        }
    }
}
