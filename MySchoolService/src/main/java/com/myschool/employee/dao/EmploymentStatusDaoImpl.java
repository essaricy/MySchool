package com.myschool.employee.dao;

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
import com.myschool.employee.assembler.EmploymentStatusDataAssembler;
import com.myschool.employee.dto.EmploymentStatus;
import com.myschool.infra.database.agent.DatabaseAgent;

/**
 * The Class EmploymentStatusDaoImpl.
 */
@Repository
public class EmploymentStatusDaoImpl implements EmploymentStatusDao {

    /** The database agent. */
    @Autowired
    private DatabaseAgent databaseAgent;

    /* (non-Javadoc)
     * @see com.myschool.employee.dao.EmploymentStatusDao#create(com.myschool.employee.dto.EmploymentStatus)
     */
    @Override
    public int create(EmploymentStatus employmentStatus)
            throws DaoException {
        int nextId = 0;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = databaseAgent.getConnection();
            preparedStatement = connection.prepareStatement(EmploymentStatusDaoSql.INSERT);
            nextId = databaseAgent.getNextId("EMPLOYMENT_STATUS", "STATUS_ID");
            preparedStatement.setInt(1, nextId);
            preparedStatement.setString(2, employmentStatus.getDescription());
            if (preparedStatement.executeUpdate() == 0) {
                nextId = 0;
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
        return nextId;
    }

    /* (non-Javadoc)
     * @see com.myschool.employee.dao.EmploymentStatusDao#getAll()
     */
    @Override
    public List<EmploymentStatus> getAll() throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        List<EmploymentStatus> employmentStatusList = null;
        EmploymentStatus employmentStatus = null;

        try {
            connection = databaseAgent.getConnection();
            preparedStatement = connection.prepareStatement(EmploymentStatusDaoSql.SELECT_ALL);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                employmentStatus = EmploymentStatusDataAssembler.create(resultSet);
                if (employmentStatusList == null) {
                    employmentStatusList = new ArrayList<EmploymentStatus>();
                }
                employmentStatusList.add(employmentStatus);
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
        return employmentStatusList;
    }

    /* (non-Javadoc)
     * @see com.myschool.employee.dao.EmploymentStatusDao#get(int)
     */
    @Override
    public EmploymentStatus get(int employmentStatusId) throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        EmploymentStatus employmentStatus = null;

        try {
            connection = databaseAgent.getConnection();
            preparedStatement = connection.prepareStatement(EmploymentStatusDaoSql.SELECT_BY_ID);
            preparedStatement.setInt(1, employmentStatusId);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                employmentStatus = EmploymentStatusDataAssembler.create(resultSet);
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
        return employmentStatus;
    }

    /* (non-Javadoc)
     * @see com.myschool.employee.dao.EmploymentStatusDao#update(int, com.myschool.employee.dto.EmploymentStatus)
     */
    @Override
    public boolean update(int employmentStatusId,
            EmploymentStatus employmentStatus) throws DaoException {
        boolean updated = false;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = databaseAgent.getConnection();
            preparedStatement = connection.prepareStatement(EmploymentStatusDaoSql.UPDATE);
            preparedStatement.setString(1, employmentStatus.getDescription());
            preparedStatement.setInt(2, employmentStatusId);
            updated = preparedStatement.executeUpdate() > 0;
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

    /* (non-Javadoc)
     * @see com.myschool.employee.dao.EmploymentStatusDao#delete(int)
     */
    @Override
    public boolean delete(int employmentStatusId)
            throws DaoException {
        boolean deleted = false;
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = databaseAgent.getConnection();
            preparedStatement = connection.prepareStatement(EmploymentStatusDaoSql.DELETE);
            preparedStatement.setInt(1, employmentStatusId);
            deleted = preparedStatement.executeUpdate() > 0;
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
        return deleted;
    }

    /* (non-Javadoc)
     * @see com.myschool.employee.dao.EmploymentStatusDao#get(java.lang.String)
     */
    @Override
    public EmploymentStatus get(String description) throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        EmploymentStatus employmentStatus = null;

        try {
            connection = databaseAgent.getConnection();
            preparedStatement = connection.prepareStatement(EmploymentStatusDaoSql.SELECT_BY_DESCRIPTION);
            preparedStatement.setString(1, description);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                employmentStatus = EmploymentStatusDataAssembler.create(resultSet);
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
        return employmentStatus;
    }

}
