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
import com.myschool.common.util.ConversionUtil;
import com.myschool.infra.database.agent.DatabaseAgent;
import com.myschool.student.assembler.StudentDocumentDataAssembler;
import com.myschool.student.dto.StudentDocument;

/**
 * The Class StudentDocumentDaoImpl.
 */
@Repository
public class StudentDocumentDaoImpl implements StudentDocumentDao {

    /** The database agent. */
    @Autowired
    private DatabaseAgent databaseAgent;

    /* (non-Javadoc)
     * @see com.myschool.student.dao.StudentDocumentDao#get(int)
     */
    @Override
    public StudentDocument get(int studentDocumentId) throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        StudentDocument studentDocument = null;

        try {
            connection = databaseAgent.getConnection();
            preparedStatement = connection.prepareStatement(StudentDocumentDaoSql.SELECT_BY_ID);
            preparedStatement.setInt(1, studentDocumentId);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                studentDocument = StudentDocumentDataAssembler.create(resultSet);
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
        return studentDocument;
    }

    /* (non-Javadoc)
     * @see com.myschool.student.dao.StudentDocumentDao#get(int, int)
     */
    @Override
    public StudentDocument get(int studentId, int documentId) throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        StudentDocument studentDocument = null;

        try {
            connection = databaseAgent.getConnection();
            preparedStatement = connection.prepareStatement(StudentDocumentDaoSql.SELECT_BY_DOCMENT);
            preparedStatement.setInt(1, studentId);
            preparedStatement.setInt(2, documentId);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                studentDocument = StudentDocumentDataAssembler.create(resultSet);
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
        return studentDocument;
    }

    /* (non-Javadoc)
     * @see com.myschool.student.dao.StudentDocumentDao#getByStudent(java.lang.String)
     */
    public List<StudentDocument> getByStudent(String admissionNumber) throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        List<StudentDocument> studentDocuments = null;

        try {
            connection = databaseAgent.getConnection();
            preparedStatement = connection.prepareStatement(
                    StudentDocumentDaoSql.SELECT_BY_ADMISSION_NUMBER);
            preparedStatement.setString(1, admissionNumber);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                if (studentDocuments == null) {
                    studentDocuments = new ArrayList<StudentDocument>();
                }
                studentDocuments.add(StudentDocumentDataAssembler.create(resultSet));
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
        return studentDocuments;
    }

    /* (non-Javadoc)
     * @see com.myschool.student.dao.StudentDocumentDao#create(int, com.myschool.student.dto.StudentDocument)
     */
    @Override
    public int create(int studentId, StudentDocument studentDocument) throws DaoException {
        int nextId = 0;
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = databaseAgent.getConnection();
            nextId = databaseAgent.getNextId("STUDENT_DOCUMENT", "STUDENT_DOCUMENT_ID");
            preparedStatement = connection.prepareStatement(StudentDocumentDaoSql.INSERT);
            preparedStatement.setInt(1, nextId);
            preparedStatement.setInt(2, studentId);
            preparedStatement.setInt(3, studentDocument.getDocument().getDocumentId());
            preparedStatement.setString(4, studentDocument.getDocumentNumber());
            preparedStatement.setDate(5, ConversionUtil.fromApplicationDateToStorageDate(
                    studentDocument.getDocumentExpiryDate()));
            preparedStatement.setString(6, studentDocument.getDocumentIssuedBy());
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
     * @see com.myschool.student.dao.StudentDocumentDao#update(int, com.myschool.student.dto.StudentDocument)
     */
    @Override
    public boolean update(int studentDocumentId, StudentDocument studentDocument)
            throws DaoException {
        boolean updated = false;
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = databaseAgent.getConnection();
            preparedStatement = connection.prepareStatement(StudentDocumentDaoSql.UPDATE);
            preparedStatement.setInt(1, studentDocument.getDocument().getDocumentId());
            preparedStatement.setString(2, studentDocument.getDocumentNumber());
            preparedStatement.setDate(3, ConversionUtil.fromApplicationDateToStorageDate(
                    studentDocument.getDocumentExpiryDate()));
            preparedStatement.setString(4, studentDocument.getDocumentIssuedBy());
            preparedStatement.setInt(5, studentDocumentId);
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
     * @see com.myschool.student.dao.StudentDocumentDao#create(int, java.util.List)
     */
    @Override
    public void create(int studentId, List<StudentDocument> studentDocuments) throws DaoException {
        int nextId = 0;
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            if (studentDocuments != null && !studentDocuments.isEmpty()) {
                connection = databaseAgent.getConnection();
                preparedStatement = connection.prepareStatement(StudentDocumentDaoSql.INSERT);

                for (StudentDocument studentDocument : studentDocuments) {
                    if (nextId == 0) {
                        nextId = databaseAgent.getNextId("STUDENT_DOCUMENT", "STUDENT_DOCUMENT_ID");
                    } else {
                        nextId++;
                    }
                    preparedStatement.setInt(1, nextId);
                    preparedStatement.setInt(2, studentId);
                    preparedStatement.setInt(3, studentDocument.getDocument().getDocumentId());
                    preparedStatement.setString(4, studentDocument.getDocumentNumber());
                    preparedStatement.setDate(5, ConversionUtil.fromApplicationDateToStorageDate(
                            studentDocument.getDocumentExpiryDate()));
                    preparedStatement.setString(6, studentDocument.getDocumentIssuedBy());
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
     * @see com.myschool.student.dao.StudentDocumentDao#delete(int)
     */
    @Override
    public boolean delete(int studentDocumentId) throws DaoException {
        boolean deleted = false;
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = databaseAgent.getConnection();
            preparedStatement = connection.prepareStatement(StudentDocumentDaoSql.DELETE);
            preparedStatement.setInt(1, studentDocumentId);
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

}
