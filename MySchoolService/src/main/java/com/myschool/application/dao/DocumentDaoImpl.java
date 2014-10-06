package com.myschool.application.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.myschool.application.assembler.DocumentDataAssembler;
import com.myschool.common.dto.DocumentDto;
import com.myschool.common.dto.DocumentSearchCriteria;
import com.myschool.common.exception.ConnectionException;
import com.myschool.common.exception.DaoException;
import com.myschool.infra.database.agent.DatabaseAgent;
import com.myschool.user.constants.UserType;


/**
 * The Class DocumentDaoImpl.
 */
@Repository
public class DocumentDaoImpl implements DocumentDao {

    /** The database agent. */
    @Autowired
    private DatabaseAgent databaseAgent;

    /* (non-Javadoc)
     * @see com.myschool.application.dao.DocumentDao#getAll()
     */
    @Override
    public List<DocumentDto> getAll() throws DaoException {
        return getAll((UserType) null);
    }

    /* (non-Javadoc)
     * @see com.myschool.application.dao.DocumentDao#getAll(com.myschool.user.constants.UserType)
     */
    @Override
    public List<DocumentDto> getAll(UserType userType) throws DaoException {

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        DocumentDto document = null;
        List<DocumentDto> documents = null;

        try {
            connection = databaseAgent.getConnection();
            String query = DocumentDaoSql.buildSelectQuery(userType);
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                document = DocumentDataAssembler.create(resultSet);
                if (documents == null) {
                    documents = new ArrayList<DocumentDto>();
                }
                documents.add(document);
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
        return documents;
    }

    /* (non-Javadoc)
     * @see com.myschool.application.dao.DocumentDao#get(int)
     */
    @Override
    public DocumentDto get(int documentId) throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        DocumentDto document = null;

        try {
            connection = databaseAgent.getConnection();
            preparedStatement = connection.prepareStatement(DocumentDaoSql.SELECT_BY_ID);
            preparedStatement.setInt(1, documentId);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                document = DocumentDataAssembler.create(resultSet);
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
        return document;
    }

    /* (non-Javadoc)
     * @see com.myschool.application.dao.DocumentDao#get(java.lang.String)
     */
    @Override
    public DocumentDto get(String documentName) throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        DocumentDto document = null;

        try {
            connection = databaseAgent.getConnection();
            preparedStatement = connection.prepareStatement(DocumentDaoSql.SELECT_BY_NAME);
            preparedStatement.setString(1, documentName);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                document = DocumentDataAssembler.create(resultSet);
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
        return document;
    }

    /* (non-Javadoc)
     * @see com.myschool.application.dao.DocumentDao#create(com.myschool.common.dto.DocumentDto)
     */
    @Override
    public int create(DocumentDto document) throws DaoException {
        int nextId = 0;
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = databaseAgent.getConnection();
            preparedStatement = connection.prepareStatement(DocumentDaoSql.INSERT);
            nextId = databaseAgent.getNextId("DOCUMENT", "DOCUMENT_ID");
            preparedStatement.setInt(1, nextId);
            preparedStatement.setString(2, document.getName());
            preparedStatement.setString(3, document.getDescription());
            preparedStatement.setString(4, document.getApplicabilityForStudent().getApplicabilityCode());
            preparedStatement.setString(5, document.getApplicabilityForEmployee().getApplicabilityCode());
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
     * @see com.myschool.application.dao.DocumentDao#update(int, com.myschool.common.dto.DocumentDto)
     */
    @Override
    public boolean update(int documentId, DocumentDto document) throws DaoException {
        boolean updated = false;
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = databaseAgent.getConnection();
            preparedStatement = connection.prepareStatement(DocumentDaoSql.UPDATE);
            preparedStatement.setString(1, document.getName());
            preparedStatement.setString(2, document.getDescription());
            preparedStatement.setString(3, document.getApplicabilityForStudent().getApplicabilityCode());
            preparedStatement.setString(4, document.getApplicabilityForEmployee().getApplicabilityCode());
            preparedStatement.setInt(5, documentId);
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
     * @see com.myschool.application.dao.DocumentDao#delete(int)
     */
    @Override
    public boolean delete(int documentId) throws DaoException {
        boolean deleted = false;
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = databaseAgent.getConnection();
            preparedStatement = connection.prepareStatement(DocumentDaoSql.DELETE);
            preparedStatement.setInt(1, documentId);
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
     * @see com.myschool.application.dao.DocumentDao#getAll(com.myschool.common.dto.DocumentSearchCriteria)
     */
    @Override
    public List<DocumentDto> getAll(
            DocumentSearchCriteria documentSearchCriteria) throws DaoException {

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        DocumentDto document = null;
        List<DocumentDto> documents = null;

        try {
            connection = databaseAgent.getConnection();
            String query = DocumentDaoSql.buildSelectQuery(documentSearchCriteria);
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                document = DocumentDataAssembler.create(resultSet);
                if (documents == null) {
                    documents = new ArrayList<DocumentDto>();
                }
                documents.add(document);
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
        return documents;
    }

}
