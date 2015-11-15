package com.myschool.user.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.myschool.application.dto.NumberNameValueDto;
import com.myschool.common.exception.ConnectionException;
import com.myschool.common.exception.DaoException;
import com.myschool.infra.database.agent.DatabaseAgent;
import com.myschool.user.assembler.UsageDataAssembler;
import com.myschool.user.assembler.UserSessionDataAssembler;
import com.myschool.user.constants.UserType;
import com.myschool.user.dto.UsageCount;
import com.myschool.user.dto.UserSession;

/**
 * The Class UsageDaoImpl.
 */
@Repository
public class UsageDaoImpl implements UsageDao {

	/** The database agent. */
	@Autowired
	private DatabaseAgent databaseAgent;

	/* (non-Javadoc)
	 * @see com.myschool.user.dao.UsageDao#getActiveSessions()
	 */
	@Override
	public List<UserSession> getActiveSessions() throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        List<UserSession> userSessions = null;

        try {
            connection = databaseAgent.getConnection();
            preparedStatement = connection.prepareStatement(UsageDaoSql.SELECT_ACTIVE_SESSIONS);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                if (userSessions == null) {
                    userSessions = new ArrayList<UserSession>();
                }
                userSessions.add(UserSessionDataAssembler.create(resultSet));
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
        return userSessions;
    }

	/* (non-Javadoc)
	 * @see com.myschool.user.dao.UsageDao#getUsageCount()
	 */
	@Override
	public List<UsageCount> getUsageCount() throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        List<UsageCount> usageCounts = null;

        try {
            connection = databaseAgent.getConnection();
            preparedStatement = connection.prepareStatement(UsageDaoSql.SELECT_USAGE_COUNT);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                if (usageCounts == null) {
                    usageCounts = new ArrayList<UsageCount>();
                }
                usageCounts.add(UserSessionDataAssembler.createUsageCount(resultSet));
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
        return usageCounts;
    }

	/* (non-Javadoc)
	 * @see com.myschool.user.dao.UsageDao#getLoginsTrend(com.myschool.user.constants.UserType, int)
	 */
	@Override
	public List<NumberNameValueDto> getLoginsTrend(UserType userType,
			int type) throws DaoException {

		String query = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<NumberNameValueDto> monthValues = null;

        try {
            connection = databaseAgent.getConnection();
            if (type == Calendar.MONTH) {
            	query = UsageDaoSql.SELECT_LOGINS_TREND_MONTH_OF_YEAR;
    		} else if (type == Calendar.DAY_OF_YEAR) {
    			query = UsageDaoSql.SELECT_LOGINS_TREND_DAY_OF_YEAR;
    		} else if (type == Calendar.DAY_OF_WEEK) {
    			query = UsageDaoSql.SELECT_LOGINS_TREND_DAY_OF_WEEK;
    		} else if (type == Calendar.HOUR_OF_DAY) {
    			query = UsageDaoSql.SELECT_LOGINS_TREND_HOUR_OF_DAY;
    		} else if (type == Calendar.DAY_OF_MONTH) {
    			query = UsageDaoSql.SELECT_LOGINS_TREND_DAY_OF_MONTH;
    		}
            int userTypeValue = userType.getUserTypeValue();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, userTypeValue);
            resultSet = preparedStatement.executeQuery();
        	while (resultSet.next()) {
        		if (monthValues == null) {
        			monthValues = new ArrayList<NumberNameValueDto>();
        		}
        		monthValues.add(UsageDataAssembler.create(resultSet));
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
        return monthValues;
    }

}
