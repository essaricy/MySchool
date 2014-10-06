package com.myschool.application.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.myschool.application.assembler.IssueDataAssembler;
import com.myschool.application.assembler.StatisticsDataAssembler;
import com.myschool.application.constants.IssueStatus;
import com.myschool.application.dto.DateValueDto;
import com.myschool.application.dto.IssueDto;
import com.myschool.application.dto.IssueSearchCriteriaDto;
import com.myschool.common.exception.ConnectionException;
import com.myschool.common.exception.DaoException;
import com.myschool.graph.constant.ToDateType;
import com.myschool.infra.database.agent.DatabaseAgent;
import com.myschool.user.constants.UserType;

/**
 * The Class IssueDaoImpl.
 */
@Repository
public class IssueDaoImpl implements IssueDao {

    /** The database agent. */
    @Autowired
    private DatabaseAgent databaseAgent;

    /* (non-Javadoc)
     * @see com.myschool.application.dao.IssueDao#create(com.myschool.application.dto.IssueDto)
     */
    @Override
    public int create(IssueDto issue) throws DaoException {
        boolean issueCreated = false;
        int issueId = 0;
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = databaseAgent.getConnection();
            issueId = databaseAgent.getNextId("ISSUE", "ISSUE_ID");
            preparedStatement = connection.prepareStatement(IssueDaoSql.INSERT);
            preparedStatement.setInt(1, issueId);
            preparedStatement.setInt(2, issue.getUserType().getUserTypeValue());
            preparedStatement.setInt(3, IssueStatus.OPEN.getStatusId());
            preparedStatement.setString(4, issue.getSubject());
            preparedStatement.setString(5, issue.getDescription());
            preparedStatement.setString(6, issue.getContactEmailId());
            issueCreated = (preparedStatement.executeUpdate() > 0) ? true : false;
            if (!issueCreated) {
                issueId = 0;
            }
        } catch (SQLException sqlException) {
            throw new DaoException(sqlException.getMessage(), sqlException);
        } catch (ConnectionException connectionException) {
            throw new DaoException(connectionException.getMessage(),
                    connectionException);
        } finally {
            try {
                databaseAgent.releaseResources(connection, preparedStatement);
            } catch (ConnectionException connectionException) {
                throw new DaoException(connectionException.getMessage(),
                        connectionException);
            }
        }
        return issueId;
    }

    /* (non-Javadoc)
     * @see com.myschool.application.dao.IssueDao#getAll(com.myschool.application.dto.IssueSearchCriteriaDto)
     */
    @Override
    public List<IssueDto> getAll(IssueSearchCriteriaDto issueSearchCriteria) throws DaoException {
        List<IssueDto> issues = null;
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            connection = databaseAgent.getConnection();
            statement = connection.createStatement();
            String issuesSearchSql = IssueDaoSql.getIssuesSearchSql(issueSearchCriteria);
            resultSet = statement.executeQuery(issuesSearchSql);
            while (resultSet.next()) {
                if (issues == null) {
                    issues = new ArrayList<IssueDto>();
                }
                issues.add(IssueDataAssembler.createIssue(resultSet));
            }
        } catch (SQLException sqlException) {
            throw new DaoException(sqlException.getMessage(), sqlException);
        } catch (ConnectionException connectionException) {
            throw new DaoException(connectionException.getMessage(),
                    connectionException);
        } finally {
            try {
                databaseAgent.releaseResources(connection, statement, resultSet);
            } catch (ConnectionException connectionException) {
                throw new DaoException(connectionException.getMessage(),
                        connectionException);
            }
        }
        return issues;
    }

    /* (non-Javadoc)
     * @see com.myschool.application.dao.IssueDao#get(int)
     */
    @Override
    public IssueDto get(int issueId) throws DaoException {
        IssueDto issue = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = databaseAgent.getConnection();
            preparedStatement = connection.prepareStatement(IssueDaoSql.SELECT_BY_ID);
            preparedStatement.setInt(1, issueId);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                issue = IssueDataAssembler.createIssue(resultSet);
            }
        } catch (SQLException sqlException) {
            throw new DaoException(sqlException.getMessage(), sqlException);
        } catch (ConnectionException connectionException) {
            throw new DaoException(connectionException.getMessage(),
                    connectionException);
        } finally {
            try {
                databaseAgent.releaseResources(connection, preparedStatement, resultSet);
            } catch (ConnectionException connectionException) {
                throw new DaoException(connectionException.getMessage(),
                        connectionException);
            }
        }
        return issue;
    }

    /* (non-Javadoc)
     * @see com.myschool.application.dao.IssueDao#update(int, com.myschool.application.dto.IssueDto)
     */
    @Override
    public boolean update(int issueId, IssueDto issue) throws DaoException {
        boolean issueUpdated = false;
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = databaseAgent.getConnection();
            preparedStatement = connection.prepareStatement(IssueDaoSql.UPDATE);
            preparedStatement.setInt(1, issue.getIssueStatus().getStatusId());
            preparedStatement.setString(2, issue.getClosedDate());
            preparedStatement.setInt(3, issueId);
            issueUpdated = (preparedStatement.executeUpdate() > 0) ? true : false;
        } catch (SQLException sqlException) {
            throw new DaoException(sqlException.getMessage(), sqlException);
        } catch (ConnectionException connectionException) {
            throw new DaoException(connectionException.getMessage(), connectionException);
        } finally {
            try {
                databaseAgent.releaseResources(connection, preparedStatement);
            } catch (ConnectionException connectionException) {
                throw new DaoException(connectionException.getMessage(),
                        connectionException);
            }
        }
        return issueUpdated;
    }

    /* (non-Javadoc)
     * @see com.myschool.application.dao.IssueDao#getIssuesToDate(com.myschool.user.constants.UserType, com.myschool.graph.constant.ToDateType)
     */
    @Override
    public List<DateValueDto> getIssuesToDate(UserType userType,
            ToDateType toDateType) throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        List<DateValueDto> dateValues = null;

        try {
            connection = databaseAgent.getConnection();
            preparedStatement = connection.prepareStatement(IssueDaoSql.getToDateIssuesSql(toDateType));
            preparedStatement.setInt(1, userType.getUserTypeValue());
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                if (dateValues == null) {
                    dateValues = new ArrayList<DateValueDto>();
                }
                dateValues.add(StatisticsDataAssembler.createDateValue(resultSet));
            }
        } catch (SQLException sqlException) {
            throw new DaoException(sqlException.getMessage(), sqlException);
        } catch (ConnectionException connectionException) {
            throw new DaoException(connectionException.getMessage(),
                    connectionException);
        } finally {
            try {
                databaseAgent.releaseResources(connection, preparedStatement, resultSet);
            } catch (ConnectionException connectionException) {
                throw new DaoException(connectionException.getMessage(), connectionException);
            }
        }
        return dateValues;
    }

}
