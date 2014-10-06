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
import com.myschool.employee.assembler.EmployeeEducationDataAssembler;
import com.myschool.employee.dto.EmployeeEducation;
import com.myschool.infra.database.agent.DatabaseAgent;

/**
 * The Class EmployeeEducationDaoImpl.
 */
@Repository
public class EmployeeEducationDaoImpl implements EmployeeEducationDao {

    /** The database agent. */
    @Autowired
    private DatabaseAgent databaseAgent;

    /* (non-Javadoc)
     * @see com.myschool.employee.dao.EmployeeEducationDao#get(int)
     */
    @Override
    public EmployeeEducation get(int employeeEducationId) throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        EmployeeEducation employeeEducation = null;

        try {
            connection = databaseAgent.getConnection();
            preparedStatement = connection.prepareStatement(EmployeeEducationDaoSql.SELECT_BY_ID);
            preparedStatement.setInt(1, employeeEducationId);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                employeeEducation = EmployeeEducationDataAssembler.create(resultSet);
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
        return employeeEducation;
    }

    /* (non-Javadoc)
     * @see com.myschool.employee.dao.EmployeeEducationDao#getByEmployee(int)
     */
    @Override
    public List<EmployeeEducation> getByEmployee(int employeeId) throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        List<EmployeeEducation> employeeEducations = null;

        try {
            connection = databaseAgent.getConnection();
            preparedStatement = connection.prepareStatement(EmployeeEducationDaoSql.SELECT_BY_EMPLOYEE_ID);
            preparedStatement.setInt(1, employeeId);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                if (employeeEducations == null) {
                    employeeEducations = new ArrayList<EmployeeEducation>();
                }
                employeeEducations.add(EmployeeEducationDataAssembler.create(resultSet));
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
        return employeeEducations;
    }

    /* (non-Javadoc)
     * @see com.myschool.employee.dao.EmployeeEducationDao#create(int, com.myschool.employee.dto.EmployeeEducation)
     */
    @Override
    public int create(int employeeId, EmployeeEducation employeeEducation) throws DaoException {
        int nextId = 0;
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = databaseAgent.getConnection();
            preparedStatement = connection.prepareStatement(EmployeeEducationDaoSql.INSERT);
            nextId = databaseAgent.getNextId("EMPLOYEE_EDUCATION", "EDUCATION_ID");
            preparedStatement.setInt(1, nextId);
            preparedStatement.setInt(2, employeeId);
            preparedStatement.setString(3, employeeEducation.getDegree());
            preparedStatement.setString(4, employeeEducation.getSpecialization());
            preparedStatement.setInt(5, employeeEducation.getYearOfGraduation());
            preparedStatement.setInt(6, employeeEducation.getPercentage());
            preparedStatement.setString(7, employeeEducation.getCollege());
            preparedStatement.setString(8, employeeEducation.getUniversity());
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
     * @see com.myschool.employee.dao.EmployeeEducationDao#create(int, java.util.List)
     */
    @Override
    public void create(int employeeId,
            List<EmployeeEducation> employeeEducations) throws DaoException {
        int nextId = 0;
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            if (employeeEducations != null && !employeeEducations.isEmpty()) {
                connection = databaseAgent.getConnection();
                preparedStatement = connection.prepareStatement(EmployeeEducationDaoSql.INSERT);

                for (EmployeeEducation employeeEducation : employeeEducations) {
                    if (nextId == 0) {
                        nextId = databaseAgent.getNextId("EMPLOYEE_EDUCATION", "EDUCATION_ID");
                    } else {
                        nextId++;
                    }
                    preparedStatement.setInt(1, nextId);
                    preparedStatement.setInt(2, employeeId);
                    preparedStatement.setString(3, employeeEducation.getDegree());
                    preparedStatement.setString(4, employeeEducation.getSpecialization());
                    preparedStatement.setInt(5, employeeEducation.getYearOfGraduation());
                    preparedStatement.setInt(6, employeeEducation.getPercentage());
                    preparedStatement.setString(7, employeeEducation.getCollege());
                    preparedStatement.setString(8, employeeEducation.getUniversity());
                    preparedStatement.addBatch();
                }
                preparedStatement.executeBatch();
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
    }

    /* (non-Javadoc)
     * @see com.myschool.employee.dao.EmployeeEducationDao#update(int, com.myschool.employee.dto.EmployeeEducation)
     */
    @Override
    public boolean update(int employeeEducationId,
            EmployeeEducation employeeEducation) throws DaoException {
        boolean updated = false;
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = databaseAgent.getConnection();
            preparedStatement = connection.prepareStatement(EmployeeEducationDaoSql.UPDATE);
            preparedStatement.setString(1, employeeEducation.getDegree());
            preparedStatement.setString(2, employeeEducation.getSpecialization());
            preparedStatement.setInt(3, employeeEducation.getYearOfGraduation());
            preparedStatement.setInt(4, employeeEducation.getPercentage());
            preparedStatement.setString(5, employeeEducation.getCollege());
            preparedStatement.setString(6, employeeEducation.getUniversity());
            preparedStatement.setInt(7, employeeEducationId);
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
     * @see com.myschool.employee.dao.EmployeeEducationDao#get(int, com.myschool.employee.dto.EmployeeEducation)
     */
    @Override
    public EmployeeEducation get(int employeeId,
            EmployeeEducation employeeEducation) throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        EmployeeEducation existingEmployeeEducation = null;

        try {
            connection = databaseAgent.getConnection();
            preparedStatement = connection.prepareStatement(EmployeeEducationDaoSql.SELECT_BY_EDUCATION);
            preparedStatement.setInt(1, employeeId);
            preparedStatement.setString(2, employeeEducation.getDegree());
            preparedStatement.setInt(3, employeeEducation.getYearOfGraduation());
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                existingEmployeeEducation = EmployeeEducationDataAssembler.create(resultSet);
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
        return existingEmployeeEducation;
    }

    /* (non-Javadoc)
     * @see com.myschool.employee.dao.EmployeeEducationDao#delete(int)
     */
    @Override
    public boolean delete(int educationId) throws DaoException {
        boolean deleted = false;
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = databaseAgent.getConnection();
            preparedStatement = connection.prepareStatement(EmployeeEducationDaoSql.DELETE);
            preparedStatement.setInt(1, educationId);
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
     * @see com.myschool.employee.dao.EmployeeEducationDao#getByEmployee(java.lang.String)
     */
    @Override
    public List<EmployeeEducation> getByEmployee(String employeeNumber) throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        List<EmployeeEducation> employeeEducations = null;

        try {
            connection = databaseAgent.getConnection();
            preparedStatement = connection.prepareStatement(EmployeeEducationDaoSql.SELECT_BY_EMPLOYEE_NUMBER);
            preparedStatement.setString(1, employeeNumber);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                if (employeeEducations == null) {
                    employeeEducations = new ArrayList<EmployeeEducation>();
                }
                employeeEducations.add(EmployeeEducationDataAssembler.create(resultSet));
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
        return employeeEducations;
    }

    /* (non-Javadoc)
     * @see com.myschool.employee.dao.EmployeeEducationDao#update(java.util.List)
     */
    @Override
    public void update(List<EmployeeEducation> employeeEducations) throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            if (employeeEducations != null && !employeeEducations.isEmpty()) {
                connection = databaseAgent.getConnection();
                preparedStatement = connection.prepareStatement(EmployeeEducationDaoSql.UPDATE);

                for (EmployeeEducation employeeEducation : employeeEducations) {
                    if (employeeEducation == null || employeeEducation.getEducationId() == 0) {
                        continue;
                    }
                    preparedStatement = connection.prepareStatement(EmployeeEducationDaoSql.UPDATE);
                    preparedStatement.setString(1, employeeEducation.getDegree());
                    preparedStatement.setString(2, employeeEducation.getSpecialization());
                    preparedStatement.setInt(3, employeeEducation.getYearOfGraduation());
                    preparedStatement.setInt(4, employeeEducation.getPercentage());
                    preparedStatement.setString(5, employeeEducation.getCollege());
                    preparedStatement.setString(6, employeeEducation.getUniversity());
                    preparedStatement.setInt(7, employeeEducation.getEducationId());
                    preparedStatement.addBatch();
                }
                preparedStatement.executeBatch();
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
    }

}
