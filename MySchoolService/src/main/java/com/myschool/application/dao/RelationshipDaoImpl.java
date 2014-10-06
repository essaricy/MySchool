package com.myschool.application.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.myschool.application.assembler.RelationshipDataAssembler;
import com.myschool.common.dto.Relationship;
import com.myschool.common.exception.ConnectionException;
import com.myschool.common.exception.DaoException;
import com.myschool.infra.database.agent.DatabaseAgent;

/**
 * The Class RelationshipDaoImpl.
 */
@Repository
public class RelationshipDaoImpl implements RelationshipDao {

    /** The database agent. */
    @Autowired
    private DatabaseAgent databaseAgent;

    /* (non-Javadoc)
     * @see com.myschool.application.dao.RelationshipDao#getAll()
     */
    @Override
    public List<Relationship> getAll() throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        List<Relationship> relationships = null;

        try {
            connection = databaseAgent.getConnection();
            preparedStatement = connection.prepareStatement(RelationshipDaoSql.SELECT_ALL);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                if (relationships == null) {
                    relationships = new ArrayList<Relationship>();
                }
                relationships.add(RelationshipDataAssembler.create(resultSet));
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
        return relationships;
    }

    /* (non-Javadoc)
     * @see com.myschool.application.dao.RelationshipDao#get(java.lang.String)
     */
    @Override
    public Relationship get(String relationshipCode) throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        Relationship relationship = null;

        try {
            connection = databaseAgent.getConnection();
            preparedStatement = connection.prepareStatement(RelationshipDaoSql.SELECT_BY_CODE);
            preparedStatement.setString(1, relationshipCode);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                relationship = RelationshipDataAssembler.create(resultSet);
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
        return relationship;
    }

    /* (non-Javadoc)
     * @see com.myschool.application.dao.RelationshipDao#getByName(java.lang.String)
     */
    @Override
    public Relationship getByName(String relationshipName) throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        Relationship relationship = null;

        try {
            connection = databaseAgent.getConnection();
            preparedStatement = connection.prepareStatement(RelationshipDaoSql.SELECT_BY_NAME);
            preparedStatement.setString(1, relationshipName);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                relationship = RelationshipDataAssembler.create(resultSet);
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
        return relationship;
    }

    /* (non-Javadoc)
     * @see com.myschool.application.dao.RelationshipDao#create(com.myschool.common.dto.Relationship)
     */
    @Override
    public boolean create(Relationship relationship) throws DaoException {
        boolean created = false;
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = databaseAgent.getConnection();
            preparedStatement = connection.prepareStatement(RelationshipDaoSql.INSERT);
            preparedStatement.setString(1, relationship.getCode().toUpperCase());
            preparedStatement.setString(2, relationship.getName());
            created = preparedStatement.executeUpdate() > 0;
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
        return created;
    }

    /* (non-Javadoc)
     * @see com.myschool.application.dao.RelationshipDao#update(java.lang.String, java.lang.String)
     */
    @Override
    public boolean update(String relationshipCode, String relationshipName) throws DaoException {
        boolean updated = false;
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = databaseAgent.getConnection();
            preparedStatement = connection.prepareStatement(RelationshipDaoSql.UPDATE);
            preparedStatement.setString(1, relationshipName);
            preparedStatement.setString(2, relationshipCode);
            updated = preparedStatement.executeUpdate() > 0;
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
     * @see com.myschool.application.dao.RelationshipDao#delete(java.lang.String)
     */
    @Override
    public boolean delete(String relationshipCode) throws DaoException {
        boolean deleted = false;
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = databaseAgent.getConnection();
            preparedStatement = connection.prepareStatement(RelationshipDaoSql.DELETE);
            preparedStatement.setString(1, relationshipCode);
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

}
