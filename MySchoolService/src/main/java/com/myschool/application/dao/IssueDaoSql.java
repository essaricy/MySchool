package com.myschool.application.dao;

import java.util.HashMap;
import java.util.Map;

import com.myschool.application.constants.IssueStatus;
import com.myschool.application.dto.IssueSearchCriteriaDto;
import com.myschool.common.util.StringUtil;
import com.myschool.graph.constant.ToDateType;
import com.myschool.infra.database.util.DatabaseUtil;
import com.myschool.user.constants.UserType;


/**
 * The Class IssueDaoSql.
 */
public class IssueDaoSql {

    /** The Constant SELECT_ALL. */
    public static final String SELECT_ALL;

    /** The Constant SELECT_BY_ID. */
    public static final String SELECT_BY_ID;

    /** The Constant INSERT. */
    public static final String INSERT;

    /** The Constant UPDATE. */
    public static final String UPDATE;

    /** The SELECT_ISSUES_BY_USER_TYPE. */
    private static String SELECT_ISSUES_BY_USER_TYPE;

    static {
        StringBuffer buffer = new StringBuffer();
        buffer.append("SELECT ");
        buffer.append("ISSUE.ISSUE_ID, ");
        buffer.append("ISSUE.USER_TYPE_ID, ");
        buffer.append("ISSUE.ISSUE_STATUS_ID, ");
        buffer.append("ISSUE.ISSUE_SUBJECT, ");
        buffer.append("ISSUE.DESCRIPTION, ");
        buffer.append("ISSUE.CONTACT_EMAIL_ID, ");
        buffer.append("ISSUE.REPORTED_DATE, ");
        buffer.append("ISSUE.CLOSED_DATE ");
        buffer.append("FROM ISSUE ");
        buffer.append("INNER JOIN REF_USER_TYPE ");
        buffer.append("ON REF_USER_TYPE.USER_TYPE_ID = ISSUE.USER_TYPE_ID ");
        buffer.append("INNER JOIN ISSUE_STATUS ");
        buffer.append("ON ISSUE_STATUS.STATUS_ID = ISSUE.ISSUE_STATUS_ID ");
        SELECT_ALL = buffer.toString();
        buffer.setLength(0);

        buffer.append(SELECT_ALL);
        buffer.append("WHERE ISSUE_ID=?");
        SELECT_BY_ID = buffer.toString();
        buffer.setLength(0);

        buffer.append("INSERT INTO ISSUE (");
        buffer.append("ISSUE_ID, USER_TYPE_ID, ISSUE_STATUS_ID, ISSUE_SUBJECT, DESCRIPTION, CONTACT_EMAIL_ID, REPORTED_DATE");
        buffer.append(") VALUES (?, ?, ?, ?, ?, ?, CURRENT_DATE)");
        INSERT = buffer.toString();
        buffer.setLength(0);

        buffer.append("UPDATE ISSUE ");
        buffer.append("ISSUE_STATUS_ID=?, ");
        buffer.append("CLOSED_DATE=?");
        buffer.append("WHERE ISSUE_ID=?");
        UPDATE = buffer.toString();
        buffer.setLength(0);

        buffer.append("SELECT ");
        buffer.append("CAST(REPORTED_DATE AS DATE) AS TO_DATE, ");
        buffer.append("COUNT(REPORTED_DATE) AS TO_DATE_VALUE ");
        buffer.append("FROM ISSUE ");
        buffer.append("WHERE ");
        buffer.append("USER_TYPE_ID=? ");
        SELECT_ISSUES_BY_USER_TYPE = buffer.toString();
        buffer.setLength(0);

    }

    /**
     * Gets the issues search sql.
     * 
     * @param issueSearchCriteria the issue search criteria
     * @return the issues search sql
     */
    public static String getIssuesSearchSql(IssueSearchCriteriaDto issueSearchCriteria) {
        StringBuffer queryBuffer = new StringBuffer();
        queryBuffer.append(SELECT_ALL);
        if (issueSearchCriteria != null) {
            Map<String, String> whereClauseMap = new HashMap<String, String>();
            issueSearchCriteria.getClosedDateMax();
            issueSearchCriteria.getClosedDateMin();
            issueSearchCriteria.getReportedDateMax();
            issueSearchCriteria.getReportedDateMin();

            UserType userType = issueSearchCriteria.getUserType();
            if (userType != null) {
                int userTypeId = userType.getUserTypeValue();
                if (userTypeId != 0) {
                    whereClauseMap.put("ISSUE.USER_TYPE_ID=?", String.valueOf(userTypeId));
                }   
            }
            IssueStatus issueStatus = issueSearchCriteria.getIssueStatus();
            if (issueStatus != null) {
                int statusId = issueStatus.getStatusId();
                if (statusId != 0) {
                    whereClauseMap.put("ISSUE.ISSUE_STATUS_ID=?", String.valueOf(statusId));
                }   
            }
            whereClauseMap.put("ISSUE.CONTACT_EMAIL_ID LIKE'%?%'", issueSearchCriteria.getContactEmailId());
            whereClauseMap.put("ISSUE.ISSUE_SUBJECT LIKE'%?%'", issueSearchCriteria.getSubject());
            whereClauseMap.put("ISSUE.DESCRIPTION LIKE'%?%'", issueSearchCriteria.getDescription());

            // Filter by reported date
            String reportedDateMin = issueSearchCriteria.getReportedDateMin();
            String reportedDateMax = issueSearchCriteria.getReportedDateMax();
            boolean filterByReportedDate = (!StringUtil.isNullOrBlank(reportedDateMin)
                    && !StringUtil.isNullOrBlank(reportedDateMax));
            if (filterByReportedDate) {
                whereClauseMap.put("ISSUE.REPORTED_DATE>='?'", reportedDateMin);
                whereClauseMap.put("ISSUE.REPORTED_DATE<='?'", reportedDateMax);
            }

            // Filter by closed date
            String closedDateMin = issueSearchCriteria.getClosedDateMin();
            String closedDateMax = issueSearchCriteria.getClosedDateMax();
            boolean filterByClosedDate = (!StringUtil.isNullOrBlank(closedDateMin)
                    && !StringUtil.isNullOrBlank(closedDateMax));
            if (filterByClosedDate) {
                whereClauseMap.put("ISSUE.CLOSED_DATE>='?'", closedDateMin);
                whereClauseMap.put("ISSUE.CLOSED_DATE>='?'", closedDateMax);
            }
            queryBuffer.append(DatabaseUtil.getWhereClause(whereClauseMap));
        }
        queryBuffer.append(" ORDER BY ISSUE.REPORTED_DATE DESC ");
        return queryBuffer.toString();
    }

    /**
     * Gets the to date issues sql.
     * 
     * @param toDateType the to date type
     * @return the to date issues sql
     */
    public static String getToDateIssuesSql(ToDateType toDateType) {
        StringBuilder builder = new StringBuilder();
        builder.append(SELECT_ISSUES_BY_USER_TYPE);
        builder.append("AND REPORTED_DATE > (CURRENT_DATE - ");
        builder.append(toDateType.getDuration());
        builder.append(") AND REPORTED_DATE <= CURRENT_DATE ");
        builder.append("GROUP BY TO_DATE ");
        builder.append("ORDER BY TO_DATE DESC ");
        return builder.toString();
    }

}
