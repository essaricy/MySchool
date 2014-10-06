package com.myschool.user.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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
import com.myschool.user.constants.UserType;
import com.myschool.user.dto.UserPreference;
import com.myschool.user.dto.UserStatistics;
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
     * @see com.myschool.user.dao.UserDao#updateUserStatistics(int)
     */
    @Override
    public void updateUserStatistics(int userId) throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = databaseAgent.getConnection();
            preparedStatement = connection.prepareStatement(UserDaoSql.INSERT_USER_STATISTICS);
            preparedStatement.setInt(1, userId);
            preparedStatement.executeUpdate();
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
            preparedStatement = connection.prepareStatement(UserDaoSql.SELECT_USER_STATISTICS_BY_ID);
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
            preparedStatement.setString(1, userPreference.getUserTheme().toString());
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

}
