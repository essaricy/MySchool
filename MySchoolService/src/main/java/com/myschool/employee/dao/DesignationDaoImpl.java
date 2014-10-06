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
import com.myschool.employee.assembler.DesignationDataAssembler;
import com.myschool.employee.dto.DesignationDto;
import com.myschool.infra.database.agent.DatabaseAgent;

/**
 * The Class DesignationDaoImpl.
 */
@Repository
public class DesignationDaoImpl implements DesignationDao {

    /** The database agent. */
    @Autowired
    private DatabaseAgent databaseAgent;

    /* (non-Javadoc)
     * @see com.myschool.employee.dao.DesignationDao#getAll()
     */
    @Override
    public List<DesignationDto> getAll() throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        List<DesignationDto> designations = null;
        DesignationDto designation = null;

        try {
            connection = databaseAgent.getConnection();
            preparedStatement = connection.prepareStatement(DesignationDaoSql.SELECT_ALL);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                designation = DesignationDataAssembler.create(resultSet);
                if (designations == null) {
                    designations = new ArrayList<DesignationDto>();
                }
                designations.add(designation);
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
        return designations;
    }

    /* (non-Javadoc)
     * @see com.myschool.employee.dao.DesignationDao#create(com.myschool.employee.dto.DesignationDto)
     */
    @Override
    public boolean create(DesignationDto designation) throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = databaseAgent.getConnection();
            preparedStatement = connection.prepareStatement(DesignationDaoSql.INSERT);
            preparedStatement.setInt(1, designation.getDesignationId());
            preparedStatement.setString(2, designation.getDesignation());
            return (preparedStatement.executeUpdate() > 0);
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
     * @see com.myschool.employee.dao.DesignationDao#get(int)
     */
    @Override
    public DesignationDto get(int designationId) throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        DesignationDto designation = null;

        try {
            connection = databaseAgent.getConnection();
            preparedStatement = connection.prepareStatement(DesignationDaoSql.SELECT_BY_ID);
            preparedStatement.setInt(1, designationId);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                designation = DesignationDataAssembler.create(resultSet);
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
        return designation;
    }

    /* (non-Javadoc)
     * @see com.myschool.employee.dao.DesignationDao#delete(int)
     */
    @Override
    public boolean delete(int designationId) throws DaoException {
        boolean deleted = false;
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = databaseAgent.getConnection();
            preparedStatement = connection.prepareStatement(DesignationDaoSql.DELETE);
            preparedStatement.setInt(1, designationId);
            deleted = (preparedStatement.executeUpdate() > 0) ? true : false;
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
     * @see com.myschool.employee.dao.DesignationDao#update(int, com.myschool.employee.dto.DesignationDto)
     */
    @Override
    public boolean update(int designationId, DesignationDto designation) throws DaoException {
        boolean updated = false;
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = databaseAgent.getConnection();
            preparedStatement = connection.prepareStatement(DesignationDaoSql.UPDATE);
            preparedStatement.setString(1, designation.getDesignation());
            preparedStatement.setInt(2, designationId);
            updated = (preparedStatement.executeUpdate() > 0);
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

}
