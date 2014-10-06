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
import com.myschool.employee.assembler.EmployeeSubjectDataAssembler;
import com.myschool.employee.dto.EmployeeSubjectDto;
import com.myschool.infra.database.agent.DatabaseAgent;

/**
 * The Class EmployeeSubjectDaoImpl.
 */
@Repository
public class EmployeeSubjectDaoImpl implements EmployeeSubjectDao {

    /** The database agent. */
    @Autowired
    private DatabaseAgent databaseAgent;

    /* (non-Javadoc)
     * @see com.myschool.employee.dao.EmployeeSubjectDao#getByEmployee(int)
     */
    @Override
    public List<EmployeeSubjectDto> getByEmployee(int employeeId) throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        List<EmployeeSubjectDto> employeeSubjects = null;

        try {
            connection = databaseAgent.getConnection();
            String query = EmployeeSubjectDaoSql.SELECT_BY_EMPLOYEE_ID;
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, employeeId);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                if (employeeSubjects == null) {
                    employeeSubjects = new ArrayList<EmployeeSubjectDto>();
                }
                employeeSubjects.add(EmployeeSubjectDataAssembler.create(resultSet));
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
        return employeeSubjects;
    }

    /* (non-Javadoc)
     * @see com.myschool.employee.dao.EmployeeSubjectDao#create(int, com.myschool.employee.dto.EmployeeSubjectDto)
     */
    @Override
    public int create(int employeeId, EmployeeSubjectDto employeeSubject)
            throws DaoException {
        int nextId = 0;
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = databaseAgent.getConnection();
            nextId = databaseAgent.getNextId("EMPLOYEE_SUBJECT", "EMPLOYEE_SUBJECT_ID");
            preparedStatement = connection.prepareStatement(EmployeeSubjectDaoSql.INSERT);
            preparedStatement.setInt(1, nextId);
            preparedStatement.setInt(2, employeeId);
            preparedStatement.setInt(3, employeeSubject.getRegisteredSubject().getSubjectId());
            if (preparedStatement.executeUpdate() == 0) {
                nextId = 0;
            };
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
     * @see com.myschool.employee.dao.EmployeeSubjectDao#create(int, java.util.List)
     */
    @Override
    public void create(int employeeId,
            List<EmployeeSubjectDto> employeeSubjects) throws DaoException {
        int nextId = 0;
        Connection connection = null;
        PreparedStatement preparedStatement= null;

        try {
            if (employeeSubjects != null && !employeeSubjects.isEmpty()) {
                connection = databaseAgent.getConnection();
                preparedStatement = connection.prepareStatement(EmployeeSubjectDaoSql.INSERT);

                for (EmployeeSubjectDto employeeSubject : employeeSubjects) {
                    if (nextId == 0) {
                        nextId = databaseAgent.getNextId("EMPLOYEE_SUBJECT", "EMPLOYEE_SUBJECT_ID");
                    } else {
                        nextId++;
                    }
                    preparedStatement.setInt(1, nextId);
                    preparedStatement.setInt(2, employeeId);
                    preparedStatement.setInt(3, employeeSubject.getRegisteredSubject().getSubjectId());
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
     * @see com.myschool.employee.dao.EmployeeSubjectDao#update(int, com.myschool.employee.dto.EmployeeSubjectDto)
     */
    @Override
    public boolean update(int employeeSubjectId,
            EmployeeSubjectDto employeeSubject) throws DaoException {
        boolean updated = false;
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = databaseAgent.getConnection();
            String query = EmployeeSubjectDaoSql.UPDATE;
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, employeeSubject.getRegisteredSubject().getSubjectId());
            preparedStatement.setInt(2, employeeSubjectId);
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
     * @see com.myschool.employee.dao.EmployeeSubjectDao#get(int)
     */
    @Override
    public EmployeeSubjectDto get(int employeeSubjectId) throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        EmployeeSubjectDto employeeSubject = null;

        try {
            connection = databaseAgent.getConnection();
            preparedStatement = connection.prepareStatement(EmployeeSubjectDaoSql.SELECT_BY_ID);
            preparedStatement.setInt(1, employeeSubjectId);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                employeeSubject = EmployeeSubjectDataAssembler.create(resultSet);
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
        return employeeSubject;
    }

    /* (non-Javadoc)
     * @see com.myschool.employee.dao.EmployeeSubjectDao#delete(int)
     */
    @Override
    public boolean delete(int employeeSubjectId) throws DaoException {
        boolean deleted = false;
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = databaseAgent.getConnection();
            preparedStatement = connection.prepareStatement(EmployeeSubjectDaoSql.DELETE);
            preparedStatement.setInt(1, employeeSubjectId);
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
     * @see com.myschool.employee.dao.EmployeeSubjectDao#getByEmployee(java.lang.String)
     */
    @Override
    public List<EmployeeSubjectDto> getByEmployee(String employeeNumber) throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        List<EmployeeSubjectDto> employeeSubjects = null;

        try {
            connection = databaseAgent.getConnection();
            String query = EmployeeSubjectDaoSql.SELECT_BY_EMPLOYEE_NUMBER;
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, employeeNumber);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                if (employeeSubjects == null) {
                    employeeSubjects = new ArrayList<EmployeeSubjectDto>();
                }
                employeeSubjects.add(EmployeeSubjectDataAssembler.create(resultSet));
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
        return employeeSubjects;
    }

    /* (non-Javadoc)
     * @see com.myschool.employee.dao.EmployeeSubjectDao#update(java.util.List)
     */
    @Override
    public void update(List<EmployeeSubjectDto> employeeSubjects) throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement= null;

        try {
            if (employeeSubjects != null && !employeeSubjects.isEmpty()) {
                connection = databaseAgent.getConnection();
                preparedStatement = connection.prepareStatement(EmployeeSubjectDaoSql.UPDATE);

                for (EmployeeSubjectDto employeeSubject : employeeSubjects) {
                    preparedStatement.setInt(1, employeeSubject.getRegisteredSubject().getSubjectId());
                    preparedStatement.setInt(2, employeeSubject.getEmployeeSubjectId());
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

    @Override
    public EmployeeSubjectDto get(int employeeId, int registeredSubjectId) throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        EmployeeSubjectDto employeeSubject = null;

        try {
            connection = databaseAgent.getConnection();
            preparedStatement = connection.prepareStatement(EmployeeSubjectDaoSql.SELECT_BY_EMPLOYEE_SUBJECT);
            preparedStatement.setInt(1, employeeId);
            preparedStatement.setInt(2, registeredSubjectId);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                employeeSubject = EmployeeSubjectDataAssembler.create(resultSet);
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
        return employeeSubject;
    }

}
