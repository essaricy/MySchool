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
import com.myschool.employee.assembler.EmployeeDocumentDataAssembler;
import com.myschool.employee.dto.EmployeeDocument;
import com.myschool.infra.database.agent.DatabaseAgent;

/**
 * The Class EmployeeDocumentDaoImpl.
 */
@Repository
public class EmployeeDocumentDaoImpl implements EmployeeDocumentDao {

    /** The database agent. */
    @Autowired
    private DatabaseAgent databaseAgent;

    /* (non-Javadoc)
     * @see com.myschool.employee.dao.EmployeeDocumentDao#get(int)
     */
    @Override
    public EmployeeDocument get(int employeeDocumentId) throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        EmployeeDocument employeeDocument = null;

        try {
            connection = databaseAgent.getConnection();
            preparedStatement = connection.prepareStatement(EmployeeDocumentDaoSql.SELECT_BY_ID);
            preparedStatement.setInt(1, employeeDocumentId);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                employeeDocument = EmployeeDocumentDataAssembler.create(resultSet);
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
        return employeeDocument;
    }

    /* (non-Javadoc)
     * @see com.myschool.employee.dao.EmployeeDocumentDao#get(int, int)
     */
    @Override
    public EmployeeDocument get(int employeeId, int documentId)
            throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        EmployeeDocument employeeDocument = null;

        try {
            connection = databaseAgent.getConnection();
            preparedStatement = connection.prepareStatement(EmployeeDocumentDaoSql.SELECT_BY_DOCMENT);
            preparedStatement.setInt(1, employeeId);
            preparedStatement.setInt(2, documentId);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                employeeDocument = EmployeeDocumentDataAssembler.create(resultSet);
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
        return employeeDocument;
    }

    /* (non-Javadoc)
     * @see com.myschool.employee.dao.EmployeeDocumentDao#getByEmployee(int)
     */
    @Override
    public List<EmployeeDocument> getByEmployee(int employeeId)
            throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        List<EmployeeDocument> employeeDocuments = null;

        try {
            connection = databaseAgent.getConnection();
            preparedStatement = connection.prepareStatement(
                    EmployeeDocumentDaoSql.SELECT_BY_EMPLOYEE_ID);
            preparedStatement.setInt(1, employeeId);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                if (employeeDocuments == null) {
                    employeeDocuments = new ArrayList<EmployeeDocument>();
                }
                employeeDocuments.add(EmployeeDocumentDataAssembler.create(resultSet));
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
        return employeeDocuments;
    }

    /* (non-Javadoc)
     * @see com.myschool.employee.dao.EmployeeDocumentDao#create(int, com.myschool.employee.dto.EmployeeDocument)
     */
    @Override
    public int create(int employeeId, EmployeeDocument employeeDocument)
            throws DaoException {
        int nextId = 0;
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = databaseAgent.getConnection();
            nextId = databaseAgent.getNextId("EMPLOYEE_DOCUMENT", "EMPLOYEE_DOCUMENT_ID");
            preparedStatement = connection.prepareStatement(EmployeeDocumentDaoSql.INSERT);
            preparedStatement.setInt(1, nextId);
            preparedStatement.setInt(2, employeeId);
            preparedStatement.setInt(3, employeeDocument.getDocument().getDocumentId());
            preparedStatement.setString(4, employeeDocument.getDocumentNumber());
            preparedStatement.setDate(5, ConversionUtil.fromApplicationDateToStorageDate(
                    employeeDocument.getDocumentExpiryDate()));
            preparedStatement.setString(6, employeeDocument.getDocumentIssuedBy());
            if (preparedStatement.executeUpdate() == 0) {
                nextId = 0;
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
        return nextId;
    }

    /* (non-Javadoc)
     * @see com.myschool.employee.dao.EmployeeDocumentDao#create(int, java.util.List)
     */
    @Override
    public void create(int employeeId, List<EmployeeDocument> employeeDocuments)
            throws DaoException {
        int nextId = 0;
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            if (employeeDocuments != null && !employeeDocuments.isEmpty()) {
                connection = databaseAgent.getConnection();
                preparedStatement = connection.prepareStatement(EmployeeDocumentDaoSql.INSERT);

                for (EmployeeDocument employeeDocument : employeeDocuments) {
                    if (nextId == 0) {
                        nextId = databaseAgent.getNextId("EMPLOYEE_DOCUMENT", "EMPLOYEE_DOCUMENT_ID");
                    } else {
                        nextId++;
                    }
                    preparedStatement.setInt(1, nextId);
                    preparedStatement.setInt(2, employeeId);
                    preparedStatement.setInt(3, employeeDocument.getDocument().getDocumentId());
                    preparedStatement.setString(4, employeeDocument.getDocumentNumber());
                    preparedStatement.setDate(5, ConversionUtil.fromApplicationDateToStorageDate(
                            employeeDocument.getDocumentExpiryDate()));
                    preparedStatement.setString(6, employeeDocument.getDocumentIssuedBy());
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
     * @see com.myschool.employee.dao.EmployeeDocumentDao#update(int, com.myschool.employee.dto.EmployeeDocument)
     */
    @Override
    public boolean update(int employeeDocumentId,
            EmployeeDocument employeeDocument) throws DaoException {
        boolean updated = false;
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = databaseAgent.getConnection();
            preparedStatement = connection.prepareStatement(EmployeeDocumentDaoSql.UPDATE);
            preparedStatement.setInt(1, employeeDocument.getDocument().getDocumentId());
            preparedStatement.setString(2, employeeDocument.getDocumentNumber());
            preparedStatement.setDate(3, ConversionUtil.fromApplicationDateToStorageDate(
                    employeeDocument.getDocumentExpiryDate()));
            preparedStatement.setString(4, employeeDocument.getDocumentIssuedBy());
            preparedStatement.setInt(5, employeeDocumentId);
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
     * @see com.myschool.employee.dao.EmployeeDocumentDao#delete(int)
     */
    @Override
    public boolean delete(int employeeDocumentId) throws DaoException {
        boolean deleted = false;
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = databaseAgent.getConnection();
            preparedStatement = connection.prepareStatement(EmployeeDocumentDaoSql.DELETE);
            preparedStatement.setInt(1, employeeDocumentId);
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
     * @see com.myschool.employee.dao.EmployeeDocumentDao#getByEmployee(java.lang.String)
     */
    @Override
    public List<EmployeeDocument> getByEmployee(String employeeNumber) throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        List<EmployeeDocument> employeeDocuments = null;

        try {
            connection = databaseAgent.getConnection();
            preparedStatement = connection.prepareStatement(
                    EmployeeDocumentDaoSql.SELECT_BY_EMPLOYEE_NUMBER);
            preparedStatement.setString(1, employeeNumber);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                if (employeeDocuments == null) {
                    employeeDocuments = new ArrayList<EmployeeDocument>();
                }
                employeeDocuments.add(EmployeeDocumentDataAssembler.create(resultSet));
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
        return employeeDocuments;
    }

    /* (non-Javadoc)
     * @see com.myschool.employee.dao.EmployeeDocumentDao#update(java.util.List)
     */
    @Override
    public void update(List<EmployeeDocument> employeeDocuments) throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            if (employeeDocuments != null && !employeeDocuments.isEmpty()) {
                connection = databaseAgent.getConnection();
                preparedStatement = connection.prepareStatement(EmployeeDocumentDaoSql.UPDATE);

                for (EmployeeDocument employeeDocument : employeeDocuments) {
                    if (employeeDocument == null || employeeDocument.getEmployeeDocumentId() == 0) {
                        continue;
                    }
                    preparedStatement.setInt(1, employeeDocument.getDocument().getDocumentId());
                    preparedStatement.setString(2, employeeDocument.getDocumentNumber());
                    preparedStatement.setDate(3, ConversionUtil.fromApplicationDateToStorageDate(
                            employeeDocument.getDocumentExpiryDate()));
                    preparedStatement.setString(4, employeeDocument.getDocumentIssuedBy());
                    preparedStatement.setInt(5, employeeDocument.getEmployeeDocumentId());
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
