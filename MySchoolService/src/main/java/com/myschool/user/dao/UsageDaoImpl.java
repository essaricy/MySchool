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
import com.myschool.user.assembler.UserSessionDataAssembler;
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

}
