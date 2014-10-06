package com.myschool.user.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.myschool.common.exception.ConnectionException;
import com.myschool.common.exception.DaoException;
import com.myschool.infra.database.agent.DatabaseAgent;
import com.myschool.user.assembler.UserDataAssembler;
import com.myschool.user.dto.LoginDto;

/**
 * The Class LoginDaoImpl.
 */
@Repository
public class LoginDaoImpl implements LoginDao {

    /** The database agent. */
    @Autowired
    private DatabaseAgent databaseAgent;

    /* (non-Javadoc)
     * @see com.myschool.user.dao.LoginDao#getLoginDetails(com.myschool.user.dto.LoginDto)
     */
    public LoginDto getLoginDetails(LoginDto login) throws DaoException {

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        LoginDto loginDetails = null;

        try {
            connection = databaseAgent.getConnection();
            preparedStatement = connection.prepareStatement(LoginDaoSql.SELECT_BY_LOGIN);
            preparedStatement.setString(1, login.getLoginId());
            preparedStatement.setString(2, login.getLoginId());
            preparedStatement.setString(3, login.getPassword());
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                loginDetails = new LoginDto();
                loginDetails.setId(resultSet.getInt(SchemaUser.USER_ID));
                loginDetails.setLoginId(resultSet.getString(SchemaUser.USER_NAME));
                loginDetails.setPassword(resultSet.getString(SchemaUser.PASSWORD));
                loginDetails.setUserType(UserDataAssembler.createUserType(resultSet.getInt(SchemaUser.REF_USER_TYPE_ID)));
                loginDetails.setRefUserId(resultSet.getInt(SchemaUser.REF_USER_ID));
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
        return loginDetails;
    }

}
