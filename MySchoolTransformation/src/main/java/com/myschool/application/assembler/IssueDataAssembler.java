package com.myschool.application.assembler;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.json.JSONArray;
import org.json.JSONObject;

import com.myschool.application.constants.IssueStatus;
import com.myschool.application.dto.IssueDto;
import com.myschool.application.dto.IssueSearchCriteriaDto;
import com.myschool.common.util.JsonUtil;
import com.myschool.user.assembler.UserDataAssembler;
import com.myschool.user.constants.UserType;

/**
 * The Class IssueDataAssembler.
 */
public class IssueDataAssembler {

    /**
     * Creates the issue.
     *
     * @param resultSet the result set
     * @return the issue dto
     * @throws SQLException the sQL exception
     */
    public static IssueDto createIssue(ResultSet resultSet) throws SQLException {
        IssueDto issue = new IssueDto();
        issue.setClosedDate(resultSet.getString("CLOSED_DATE"));
        issue.setContactEmailId(resultSet.getString("CONTACT_EMAIL_ID"));
        issue.setDescription(resultSet.getString("DESCRIPTION"));
        issue.setIssueId(resultSet.getInt("ISSUE_ID"));
        issue.setIssueStatus(getIssueStatus(resultSet.getInt("ISSUE_STATUS_ID")));
        issue.setSubject(resultSet.getString("ISSUE_SUBJECT"));
        issue.setReportedDate(resultSet.getString("REPORTED_DATE"));
        issue.setUserType(UserDataAssembler.createUserType(resultSet.getInt("USER_TYPE_ID")));
        return issue;
    }

    /**
     * Gets the issue status.
     *
     * @param issueStatusId the issue status id
     * @return the issue status
     */
    public static IssueStatus getIssueStatus(int issueStatusId) {
        IssueStatus issueStatus = null;
        if (issueStatusId == IssueStatus.OPEN.getStatusId()) {
            issueStatus = IssueStatus.OPEN;
        } else if (issueStatusId == IssueStatus.CLOSED.getStatusId()) {
            issueStatus = IssueStatus.CLOSED;
        }
        return issueStatus;
    }

    /**
     * Creates the.
     * 
     * @param issue the issue
     * @return the jSON array
     */
    public static JSONArray create(IssueDto issue) {
        JSONArray row = new JSONArray();
        row.put(issue.getIssueId());
        UserType userType = issue.getUserType();
        if (userType == null) {
            row.put("");
            row.put("");
        } else {
            row.put(userType.getUserTypeValue());
            row.put(userType.toString());
        }
        IssueStatus issueStatus = issue.getIssueStatus();
        row.put(issueStatus.getStatusId());
        row.put(issueStatus.toString());

        row.put(issue.getReportedDate());
        row.put(issue.getClosedDate());
        row.put(issue.getSubject());
        row.put(issue.getDescription());
        row.put(issue.getContactEmailId());
        return row;
    }

    /**
     * Creates the issue search criteria.
     * 
     * @param issueSearchCriteriaData the issue search criteria json
     * @return the issue search criteria
     */
    public static IssueSearchCriteriaDto createIssueSearchCriteria(
            JSONObject issueSearchCriteriaData) {
        IssueSearchCriteriaDto issueSearchCriteria = null;
        if (issueSearchCriteriaData != null) {
            issueSearchCriteria = new IssueSearchCriteriaDto();

            issueSearchCriteria.setUserType(UserType.getByValue(JsonUtil.getIntValue(issueSearchCriteriaData, "UserTypeID")));
            issueSearchCriteria.setIssueStatus(IssueStatus.get(JsonUtil.getIntValue(issueSearchCriteriaData, "StatusID")));

            issueSearchCriteria.setReportedDateMin(JsonUtil.getStringValue(issueSearchCriteriaData, "ReportedDateMin"));
            issueSearchCriteria.setReportedDateMax(JsonUtil.getStringValue(issueSearchCriteriaData, "ReportedDateMax"));
            issueSearchCriteria.setClosedDateMin(JsonUtil.getStringValue(issueSearchCriteriaData, "ClosedDateMin"));
            issueSearchCriteria.setClosedDateMax(JsonUtil.getStringValue(issueSearchCriteriaData, "ClosedDateMax"));
            issueSearchCriteria.setSubject(JsonUtil.getStringValue(issueSearchCriteriaData, "Subject"));
            issueSearchCriteria.setDescription(JsonUtil.getStringValue(issueSearchCriteriaData, "Description"));
        }
        return issueSearchCriteria;
    }

    /**
     * Creates the.
     * 
     * @param issueData the issue data
     * @return the issue dto
     */
    public static IssueDto create(JSONObject issueData) {
        IssueDto issue = new IssueDto();
        issue.setUserType(UserType.getByValue(JsonUtil.getIntValue(issueData, "UserTypeID")));
        //issue.setIssueStatus(IssueStatus.get(JsonUtil.getIntValue(issueData, "StatusID")));

        issue.setReportedDate(JsonUtil.getStringValue(issueData, "ReportedDate"));
        issue.setClosedDate(JsonUtil.getStringValue(issueData, "ClosedDate"));
        issue.setSubject(JsonUtil.getStringValue(issueData, "Subject"));
        issue.setDescription(JsonUtil.getStringValue(issueData, "Description"));
        return issue;
    }
}
