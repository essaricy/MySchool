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
import com.myschool.common.util.ConversionUtil;
import com.myschool.employee.assembler.EmployeeExperienceDataAssembler;
import com.myschool.employee.dto.EmployeeExperience;
import com.myschool.infra.database.agent.DatabaseAgent;

/**
 * The Class EmployeeExperienceDaoImpl.
 */
@Repository
public class EmployeeExperienceDaoImpl implements EmployeeExperienceDao {

    /** The database agent. */
    @Autowired
    private DatabaseAgent databaseAgent;

    /* (non-Javadoc)
     * @see com.myschool.employee.dao.EmployeeExperienceDao#get(int)
     */
    @Override
    public EmployeeExperience get(int employeeExperienceId) throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        EmployeeExperience employeeExperience = null;

        try {
            connection = databaseAgent.getConnection();
            preparedStatement = connection.prepareStatement(EmployeeExperienceDaoSql.SELECT_BY_ID);
            preparedStatement.setInt(1, employeeExperienceId);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                employeeExperience = EmployeeExperienceDataAssembler.create(resultSet);
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
        return employeeExperience;
    }

    /* (non-Javadoc)
     * @see com.myschool.employee.dao.EmployeeExperienceDao#getByEmployee(int)
     */
    @Override
    public List<EmployeeExperience> getByEmployee(int employeeId)
            throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        List<EmployeeExperience> employeeExperiences = null;

        try {
            connection = databaseAgent.getConnection();
            preparedStatement = connection.prepareStatement(EmployeeExperienceDaoSql.SELECT_BY_EMPLOYEE_ID);
            preparedStatement.setInt(1, employeeId);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                if (employeeExperiences == null) {
                    employeeExperiences = new ArrayList<EmployeeExperience>();
                }
                employeeExperiences.add(EmployeeExperienceDataAssembler.create(resultSet));
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
        return employeeExperiences;
    }

    /* (non-Javadoc)
     * @see com.myschool.employee.dao.EmployeeExperienceDao#create(int, com.myschool.employee.dto.EmployeeExperience)
     */
    @Override
    public int create(int employeeId, EmployeeExperience employeeExperience)
            throws DaoException {
        int nextId = 0;
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = databaseAgent.getConnection();
            preparedStatement = connection.prepareStatement(EmployeeExperienceDaoSql.INSERT);
            nextId = databaseAgent.getNextId("EMPLOYEE_EXPERIENCE", "EXPERIENCE_ID");
            preparedStatement.setInt(1, nextId);
            preparedStatement.setInt(2, employeeId);
            preparedStatement.setString(3, employeeExperience.getEmployer());
            preparedStatement.setString(4, employeeExperience.getJobTitle());
            preparedStatement.setDate(5, ConversionUtil.fromApplicationDateToStorageDate(
                    employeeExperience.getFromDate()));
            preparedStatement.setDate(6, ConversionUtil.fromApplicationDateToStorageDate(
                    employeeExperience.getToDate()));
            preparedStatement.setInt(7, employeeExperience.getExperieceInMonth());
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
                databaseAgent.releaseResources(connection, preparedStatement);
            } catch (ConnectionException connectionException) {
                throw new DaoException(connectionException.getMessage(),
                        connectionException);
            }
        }
        return nextId;
    }

    /* (non-Javadoc)
     * @see com.myschool.employee.dao.EmployeeExperienceDao#create(int, java.util.List)
     */
    @Override
    public void create(int employeeId,
            List<EmployeeExperience> employeeExperiences) throws DaoException {
        int nextId = 0;
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            if (employeeExperiences != null && !employeeExperiences.isEmpty()) {
                connection = databaseAgent.getConnection();
                preparedStatement = connection.prepareStatement(EmployeeExperienceDaoSql.INSERT);

                for (EmployeeExperience employeeExperience : employeeExperiences) {
                    if (nextId == 0) {
                        nextId = databaseAgent.getNextId("EMPLOYEE_EXPERIENCE", "EXPERIENCE_ID");
                    } else {
                        nextId++;
                    }
                    preparedStatement.setInt(1, nextId);
                    preparedStatement.setInt(2, employeeId);
                    preparedStatement.setString(3, employeeExperience.getEmployer());
                    preparedStatement.setString(4, employeeExperience.getJobTitle());
                    preparedStatement.setDate(5, ConversionUtil.fromApplicationDateToStorageDate(
                            employeeExperience.getFromDate()));
                    preparedStatement.setDate(6, ConversionUtil.fromApplicationDateToStorageDate(
                            employeeExperience.getToDate()));
                    preparedStatement.setInt(7, employeeExperience.getExperieceInMonth());
                    preparedStatement.addBatch();
                }
                preparedStatement.executeBatch();
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
    }

    /* (non-Javadoc)
     * @see com.myschool.employee.dao.EmployeeExperienceDao#update(int, com.myschool.employee.dto.EmployeeExperience)
     */
    @Override
    public boolean update(int employeeExperienceId,
            EmployeeExperience employeeExperience) throws DaoException {
        boolean updated = false;
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = databaseAgent.getConnection();
            preparedStatement = connection.prepareStatement(EmployeeExperienceDaoSql.UPDATE);
            preparedStatement.setString(1, employeeExperience.getEmployer());
            preparedStatement.setString(2, employeeExperience.getJobTitle());
            preparedStatement.setDate(3, ConversionUtil.fromApplicationDateToStorageDate(
                    employeeExperience.getFromDate()));
            preparedStatement.setDate(4, ConversionUtil.fromApplicationDateToStorageDate(
                    employeeExperience.getToDate()));
            preparedStatement.setInt(5, employeeExperience.getExperieceInMonth());
            preparedStatement.setInt(6, employeeExperienceId);
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

    /* (non-Javadoc)
     * @see com.myschool.employee.dao.EmployeeExperienceDao#get(int, com.myschool.employee.dto.EmployeeExperience)
     */
    @Override
    public EmployeeExperience get(int employeeId,
            EmployeeExperience employeeExperience) throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        EmployeeExperience existingEmployeeExperience = null;

        try {
            connection = databaseAgent.getConnection();
            preparedStatement = connection.prepareStatement(EmployeeExperienceDaoSql.SELECT_BY_EXPERIENCE);
            preparedStatement.setInt(1, employeeId);
            preparedStatement.setString(2, employeeExperience.getEmployer());
            preparedStatement.setDate(3, ConversionUtil.fromApplicationDateToStorageDate(
                    employeeExperience.getFromDate()));
            preparedStatement.setDate(4, ConversionUtil.fromApplicationDateToStorageDate(
                    employeeExperience.getToDate()));
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                existingEmployeeExperience = EmployeeExperienceDataAssembler.create(resultSet);
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
        return existingEmployeeExperience;
    }

    /* (non-Javadoc)
     * @see com.myschool.employee.dao.EmployeeExperienceDao#delete(int)
     */
    @Override
    public boolean delete(int experienceId) throws DaoException {
        boolean deleted = false;
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = databaseAgent.getConnection();
            preparedStatement = connection.prepareStatement(EmployeeExperienceDaoSql.DELETE);
            preparedStatement.setInt(1, experienceId);
            deleted = (preparedStatement.executeUpdate() > 0);
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
     * @see com.myschool.employee.dao.EmployeeExperienceDao#getByEmployee(java.lang.String)
     */
    @Override
    public List<EmployeeExperience> getByEmployee(String employeeNumber) throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        List<EmployeeExperience> employeeExperiences = null;

        try {
            connection = databaseAgent.getConnection();
            preparedStatement = connection.prepareStatement(EmployeeExperienceDaoSql.SELECT_BY_EMPLOYEE_NUMBER);
            preparedStatement.setString(1, employeeNumber);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                if (employeeExperiences == null) {
                    employeeExperiences = new ArrayList<EmployeeExperience>();
                }
                employeeExperiences.add(EmployeeExperienceDataAssembler.create(resultSet));
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
        return employeeExperiences;
    }

    /* (non-Javadoc)
     * @see com.myschool.employee.dao.EmployeeExperienceDao#update(java.util.List)
     */
    @Override
    public void update(List<EmployeeExperience> employeeExperiences) throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            if (employeeExperiences != null && !employeeExperiences.isEmpty()) {
                connection = databaseAgent.getConnection();
                preparedStatement = connection.prepareStatement(EmployeeExperienceDaoSql.UPDATE);

                for (EmployeeExperience employeeExperience : employeeExperiences) {
                    if (employeeExperience == null || employeeExperience.getExperienceId() == 0) {
                        continue;
                    }
                    preparedStatement.setString(1, employeeExperience.getEmployer());
                    preparedStatement.setString(2, employeeExperience.getJobTitle());
                    preparedStatement.setDate(3, ConversionUtil.fromApplicationDateToStorageDate(
                            employeeExperience.getFromDate()));
                    preparedStatement.setDate(4, ConversionUtil.fromApplicationDateToStorageDate(
                            employeeExperience.getToDate()));
                    preparedStatement.setInt(5, employeeExperience.getExperieceInMonth());
                    preparedStatement.setInt(6, employeeExperience.getExperienceId());
                    preparedStatement.addBatch();
                }
                preparedStatement.executeBatch();
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
    }

}
