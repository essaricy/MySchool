package com.myschool.user.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.myschool.common.exception.ConnectionException;
import com.myschool.common.exception.DaoException;
import com.myschool.infra.database.agent.DatabaseAgent;
import com.myschool.user.assembler.UserDataAssembler;
import com.myschool.user.dto.UserTypeDto;
import com.myschool.user.dto.UsersDto;

/**
 * The Class UserTypeDaoImpl.
 */
@Repository
public class UserTypeDaoImpl implements UserTypeDao {

    @Autowired
    private DatabaseAgent databaseAgent;

    /**
     * Gets the all.
     *
     * @return the all
     * @throws DaoException the dao exception
     */
    public List<UserTypeDto> getAll() throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        UserTypeDto userType = null;
        List<UserTypeDto> userTypes = null;

        try {
            connection = databaseAgent.getConnection();
            String query = UserTypeDaoSql.buildSelectSql(false);
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                userType = UserDataAssembler.createUserType(resultSet);
                while (userTypes == null) {
                    userTypes = new ArrayList<UserTypeDto>();
                }
                userTypes.add(userType);
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
        return userTypes;
    }

    /**
     * Gets the users.
     *
     * @param userTypeId the user type id
     * @return the users
     * @throws DaoException the dao exception
     */
    public List<UsersDto> getUsers(int userTypeId) throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        UsersDto user = null;
        List<UsersDto> users = null;

        try {
            connection = databaseAgent.getConnection();
            String query = UserTypeDaoSql.buildSelectUsersSql();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, userTypeId);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                user = UserDataAssembler.createUser(resultSet);
                while (users == null) {
                    users = new ArrayList<UsersDto>();
                }
                users.add(user);
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
        return users;
    }

}
