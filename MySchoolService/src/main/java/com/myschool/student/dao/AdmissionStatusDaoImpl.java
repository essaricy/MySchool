package com.myschool.student.dao;

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
import com.myschool.student.assembler.AdmissionStatusDataAssembler;
import com.myschool.student.dao.AdmissionStatusDao;
import com.myschool.student.dao.AdmissionStatusDaoSql;
import com.myschool.student.dto.AdmissionStatus;

/**
 * The Class AdmissionStatusDaoImpl.
 */
@Repository
public class AdmissionStatusDaoImpl implements AdmissionStatusDao {

    /** The database agent. */
    @Autowired
    private DatabaseAgent databaseAgent;

    /* (non-Javadoc)
     * @see com.myschool.student.dao.AdmissionStatusDao#create(com.myschool.student.dto.AdmissionStatus)
     */
    @Override
    public int create(AdmissionStatus admissionStatus)
            throws DaoException {
        int nextId = 0;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = databaseAgent.getConnection();
            preparedStatement = connection.prepareStatement(AdmissionStatusDaoSql.INSERT);
            nextId = databaseAgent.getNextId("ADMISSION_STATUS", "STATUS_ID");
            preparedStatement.setInt(1, nextId);
            preparedStatement.setString(2, admissionStatus.getDescription());
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
     * @see com.myschool.student.dao.AdmissionStatusDao#getAll()
     */
    @Override
    public List<AdmissionStatus> getAll() throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        List<AdmissionStatus> admissionStatusList = null;
        AdmissionStatus admissionStatus = null;

        try {
            connection = databaseAgent.getConnection();
            preparedStatement = connection.prepareStatement(AdmissionStatusDaoSql.SELECT_ALL);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                admissionStatus = AdmissionStatusDataAssembler.create(resultSet);
                if (admissionStatusList == null) {
                    admissionStatusList = new ArrayList<AdmissionStatus>();
                }
                admissionStatusList.add(admissionStatus);
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
        return admissionStatusList;
    }

    /* (non-Javadoc)
     * @see com.myschool.student.dao.AdmissionStatusDao#get(int)
     */
    @Override
    public AdmissionStatus get(int admissionStatusId) throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        AdmissionStatus admissionStatus = null;

        try {
            connection = databaseAgent.getConnection();
            preparedStatement = connection.prepareStatement(AdmissionStatusDaoSql.SELECT_BY_ID);
            preparedStatement.setInt(1, admissionStatusId);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                admissionStatus = AdmissionStatusDataAssembler.create(resultSet);
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
        return admissionStatus;
    }

    /* (non-Javadoc)
     * @see com.myschool.student.dao.AdmissionStatusDao#update(int, com.myschool.student.dto.AdmissionStatus)
     */
    @Override
    public boolean update(int admissionStatusId,
            AdmissionStatus admissionStatus) throws DaoException {
        boolean updated = false;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = databaseAgent.getConnection();
            preparedStatement = connection.prepareStatement(AdmissionStatusDaoSql.UPDATE);
            preparedStatement.setString(1, admissionStatus.getDescription());
            preparedStatement.setInt(2, admissionStatusId);
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
     * @see com.myschool.student.dao.AdmissionStatusDao#delete(int)
     */
    @Override
    public boolean delete(int admissionStatusId) throws DaoException {
        boolean deleted = false;
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = databaseAgent.getConnection();
            preparedStatement = connection.prepareStatement(AdmissionStatusDaoSql.DELETE);
            preparedStatement.setInt(1, admissionStatusId);
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
     * @see com.myschool.student.dao.AdmissionStatusDao#get(java.lang.String)
     */
    @Override
    public AdmissionStatus get(String description) throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        AdmissionStatus admissionStatus = null;

        try {
            connection = databaseAgent.getConnection();
            preparedStatement = connection.prepareStatement(AdmissionStatusDaoSql.SELECT_BY_DESCRIPTION);
            preparedStatement.setString(1, description);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                admissionStatus = AdmissionStatusDataAssembler.create(resultSet);
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
        return admissionStatus;
    }

}
