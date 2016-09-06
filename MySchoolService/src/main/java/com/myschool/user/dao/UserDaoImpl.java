package com.myschool.user.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.myschool.application.assembler.StatisticsDataAssembler;
import com.myschool.application.dto.DateValueDto;
import com.myschool.common.exception.ConnectionException;
import com.myschool.common.exception.DaoException;
import com.myschool.common.util.ConversionUtil;
import com.myschool.graph.constant.ToDateType;
import com.myschool.infra.database.agent.DatabaseAgent;
import com.myschool.user.assembler.UserDataAssembler;
import com.myschool.user.assembler.UserSessionDataAssembler;
import com.myschool.user.constants.UserType;
import com.myschool.user.dto.UserActivity;
import com.myschool.user.dto.UserPreference;
import com.myschool.user.dto.UserSession;
import com.myschool.user.dto.UserStatistics;
import com.myschool.user.dto.UserTheme;
import com.myschool.user.dto.UsersDto;

/**
 * The Class UserDaoImpl.
 */
@Repository
public class UserDaoImpl implements UserDao {

    /** The database agent. */
    @Autowired
    private DatabaseAgent databaseAgent;

    /* (non-Javadoc)
     * @see com.myschool.user.dao.UserDao#createUser(com.myschool.user.dto.UsersDto)
     */
    @Override
    public int createUser(UsersDto user) throws DaoException {
        boolean userCreated = false;
        int userId = 0;
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = databaseAgent.getConnection();
            userId = databaseAgent.getNextId("USERS", "USER_ID");
            preparedStatement = connection.prepareStatement(UserDaoSql.INSERT);
            preparedStatement.setInt(1, userId);
            preparedStatement.setString(2, user.getUserName());
            preparedStatement.setString(3, user.getPassword());
            preparedStatement.setInt(4, user.getUserType().getUserTypeValue());
            preparedStatement.setInt(5, user.getRefUserId());
            userCreated = (preparedStatement.executeUpdate() > 0) ? true : false;
            if (!userCreated) {
                userId = 0;
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
        return userId;
    }

    /* (non-Javadoc)
     * @see com.myschool.user.dao.UserDao#restorePassword(com.myschool.user.constants.UserType, int, java.lang.String)
     */
    @Override
    public boolean restorePassword(UserType userType, int refUserId,
            String initialPassword) throws DaoException {

        boolean passwordRestored = false;
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = databaseAgent.getConnection();
            preparedStatement = connection.prepareStatement(UserDaoSql.UPDATE_PASSWORD_BY_USER);
            preparedStatement.setString(1, initialPassword);
            preparedStatement.setInt(2, userType.getUserTypeValue());
            preparedStatement.setInt(3, refUserId);
            passwordRestored = (preparedStatement.executeUpdate() > 0) ? true : false;
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
        return passwordRestored;
    }

    /* (non-Javadoc)
     * @see com.myschool.user.dao.UserDao#getUserPreferences(int)
     */
    @Override
    public UserPreference getUserPreferences(int userId) throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        UserPreference userPreference = null;

        try {
            connection = databaseAgent.getConnection();
            preparedStatement = connection.prepareStatement(UserDaoSql.SELECT_USER_PREFERENCES_BY_ID);
            preparedStatement.setInt(1, userId);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                userPreference = UserDataAssembler.createUserPreference(resultSet);
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
                throw new DaoException(connectionException.getMessage(), connectionException);
            }
        }
        return userPreference;
    }

    /* (non-Javadoc)
     * @see com.myschool.user.dao.UserDao#getUserStatistics(int)
     */
    @Override
    public UserStatistics getUserStatistics(int userId) throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        UserStatistics userStatistics = null;

        try {
            connection = databaseAgent.getConnection();
            preparedStatement = connection.prepareStatement(UserDaoSql.SELECT_USER_SESSIONS_BY_ID);
            preparedStatement.setInt(1, userId);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                userStatistics = UserDataAssembler.createUserStatistics(resultSet);
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
                throw new DaoException(connectionException.getMessage(), connectionException);
            }
        }
        return userStatistics;
    }

    /* (non-Javadoc)
     * @see com.myschool.user.dao.UserDao#getUser(int)
     */
    @Override
    public UsersDto getUser(int userId) throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        UsersDto userDto = null;

        try {
            connection = databaseAgent.getConnection();
            preparedStatement = connection.prepareStatement(UserDaoSql.SELECT_BY_ID);
            preparedStatement.setInt(1, userId);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                userDto = UserDataAssembler.createUser(resultSet);
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
        return userDto;
    }

    /* (non-Javadoc)
     * @see com.myschool.user.dao.UserDao#changePassword(int, java.lang.String)
     */
    @Override
    public boolean changePassword(int userId, String newPassword) throws DaoException {
        boolean passwordChanged = false;
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = databaseAgent.getConnection();
            preparedStatement = connection.prepareStatement(UserDaoSql.UPDATE_PASSWORD_BY_ID);
            preparedStatement.setString(1, newPassword);
            preparedStatement.setInt(2, userId);
            passwordChanged = (preparedStatement.executeUpdate() > 0) ? true : false;
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
        return passwordChanged;
    }

    /* (non-Javadoc)
     * @see com.myschool.user.dao.UserDao#changePreferences(com.myschool.user.dto.UserPreference)
     */
    @Override
    public boolean changePreferences(UserPreference userPreference) throws DaoException {
        boolean preferenceChanged = false;
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = databaseAgent.getConnection();
            preparedStatement = connection.prepareStatement(UserDaoSql.UPDATE_USER_PREFERENCES_BY_ID);
            preparedStatement.setString(1, userPreference.getUserTheme().getCode());
            preparedStatement.setInt(2, userPreference.getRecordsPerPage());
            preparedStatement.setString(3, ConversionUtil.toYN(userPreference.isAllowAds()));
            preparedStatement.setInt(4, userPreference.getUserId());
            preferenceChanged = (preparedStatement.executeUpdate() > 0) ? true : false;
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
        return preferenceChanged;
    }

    /* (non-Javadoc)
     * @see com.myschool.user.dao.UserDao#getUser(com.myschool.user.constants.UserType, int)
     */
    @Override
    public UsersDto getUser(UserType userType, int employeeId) throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        UsersDto userDto = null;

        try {
            connection = databaseAgent.getConnection();
            preparedStatement = connection.prepareStatement(UserDaoSql.SELECT_BY_USER);
            preparedStatement.setInt(1, userType.getUserTypeValue());
            preparedStatement.setInt(2, employeeId);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                userDto = UserDataAssembler.createUser(resultSet);
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
        return userDto;
    }

    /* (non-Javadoc)
     * @see com.myschool.user.dao.UserDao#getLoginsToDate(com.myschool.user.constants.UserType, com.myschool.graph.constant.ToDateType)
     */
    @Override
    public List<DateValueDto> getLoginsToDate(UserType userType, ToDateType toDateType)
            throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        List<DateValueDto> dateValues = null;

        try {
            connection = databaseAgent.getConnection();
            preparedStatement = connection.prepareStatement(UserDaoSql.getToDateLoginsSql(toDateType));
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

	/* (non-Javadoc)
	 * @see com.myschool.user.dao.UserDao#getUserSession(java.lang.String)
	 */
	@Override
	public UserSession getUserSession(String sessionId) throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        UserSession userSession = null;

        try {
            connection = databaseAgent.getConnection();
            preparedStatement = connection.prepareStatement(UserSessionDaoSql.SELECT_BY_ID);
            preparedStatement.setString(1, sessionId);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
            	userSession = UserSessionDataAssembler.create(resultSet);
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
        return userSession;
    }

	/* (non-Javadoc)
	 * @see com.myschool.user.dao.UserDao#create(com.myschool.user.dto.UserSession)
	 */
	@Override
	public int create(UserSession userSession) throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = databaseAgent.getConnection();
            preparedStatement = connection.prepareStatement(UserSessionDaoSql.INSERT);
            preparedStatement.setString(1, userSession.getSessionId());
            preparedStatement.setTimestamp(2, new Timestamp(userSession.getSessionStartTime().getTime()));
            preparedStatement.setString(3, userSession.getDeviceInformation());
            preparedStatement.setString(4, userSession.getBrowserInformation());
            preparedStatement.setString(5, userSession.getIpAddress());
            return preparedStatement.executeUpdate();
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
    }

	/* (non-Javadoc)
	 * @see com.myschool.user.dao.UserDao#create(java.lang.String, java.util.List)
	 */
	@Override
	public boolean create(String sessionId, List<UserActivity> userActivities) throws DaoException {
		boolean created = false;
        int nextId = 0;
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
    		if (userActivities != null && !userActivities.isEmpty()) {
    			connection = databaseAgent.getConnection();
    			preparedStatement = connection.prepareStatement(UserSessionDaoSql.INSERT_USER_ACTIVITY);
    			for (UserActivity userActivity : userActivities) {
    				if (nextId == 0) {
    					nextId = databaseAgent.getNextId("USER_ACTIVITY", "REQUEST_ID");
    				} else {
    					nextId++;
    				}
    				preparedStatement.setInt(1, nextId);
    				preparedStatement.setString(2, sessionId);
    				preparedStatement.setString(3, userActivity.getRequestUrl());
    				preparedStatement.setTimestamp(4, new Timestamp(userActivity.getRequestedTime().getTime()));
    				preparedStatement.setLong(5, userActivity.getServiceLatency());
    				preparedStatement.addBatch();
    			}
    			preparedStatement.executeBatch();
    			created = true;
    		}
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
    	return created;
	}

	/* (non-Javadoc)
	 * @see com.myschool.user.dao.UserDao#update(com.myschool.user.dto.UserSession)
	 */
	@Override
	public boolean update(UserSession userSession) throws DaoException {
        boolean updated = false;
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = databaseAgent.getConnection();
            preparedStatement = connection.prepareStatement(UserSessionDaoSql.UPDATE);
            int userId = userSession.getUserId();
            if (userId == 0) {
            	preparedStatement.setNull(1, Types.INTEGER);
            } else {
            	preparedStatement.setInt(1, userId);
            }
            preparedStatement.setTimestamp(2, new Timestamp(userSession.getSessionStartTime().getTime()));
            Date sessionEndTime = userSession.getSessionEndTime();
            if (sessionEndTime == null) {
            	preparedStatement.setNull(3, Types.TIMESTAMP);
            } else {
            	preparedStatement.setTimestamp(3, new Timestamp(sessionEndTime.getTime()));
            }
            preparedStatement.setString(4, userSession.getDeviceInformation());
            preparedStatement.setString(5, userSession.getBrowserInformation());
            preparedStatement.setString(6, userSession.getIpAddress());
            preparedStatement.setString(7, userSession.getSessionId());
            updated = (preparedStatement.executeUpdate() > 0) ? true : false;
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
        return updated;
    }

    /* (non-Javadoc)
     * @see com.myschool.user.dao.UserDao#getThemes()
     */
    @Override
    public List<UserTheme> getThemes() throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        List<UserTheme> userThemes = null;

        try {
            connection = databaseAgent.getConnection();
            preparedStatement = connection.prepareStatement(UserDaoSql.SELECT_ALL_THEMES);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                if (userThemes == null) {
                    userThemes = new ArrayList<UserTheme>();
                }
                userThemes.add(UserDataAssembler.createUserTheme(resultSet, false));
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
        return userThemes;
    }

}
