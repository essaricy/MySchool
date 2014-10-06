package com.myschool.academic.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.myschool.common.exception.ConnectionException;
import com.myschool.common.exception.DaoException;
import com.myschool.infra.database.agent.DatabaseAgent;

/**
 * The Class AyeProcessDaoImpl.
 */
@Repository

public class AyeProcessDaoImpl implements AyeProcessDao {

    /** The database agent. */
    @Autowired
    private DatabaseAgent databaseAgent;

    /* (non-Javadoc)
     * @see com.myschool.academic.dao.AyeProcessDao#setNextAcademicYear(java.lang.String)
     */
    @Override
    public boolean setNextAcademicYear(String academicYearName) throws DaoException {
        boolean updated = false;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = databaseAgent.getConnection();
            String query = AyeProcessDaoSql.buildInsertSql(academicYearName);
            preparedStatement = connection.prepareStatement(query);
            updated = preparedStatement.executeUpdate() == 0;
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
        return updated;
    }

}
