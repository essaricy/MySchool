package com.myschool.clazz.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.myschool.clazz.assembler.MediumDataAssembler;
import com.myschool.clazz.dto.MediumDto;
import com.myschool.common.exception.ConnectionException;
import com.myschool.common.exception.DaoException;
import com.myschool.infra.database.agent.DatabaseAgent;

/**
 * The Class MediumDaoImpl.
 */
@Repository
public class MediumDaoImpl implements MediumDao {

    /** The database agent. */
    @Autowired
    private DatabaseAgent databaseAgent;

    /* (non-Javadoc)
     * @see com.myschool.clazz.dao.MediumDao#createMedium(com.myschool.clazz.dto.MediumDto)
     */
    @Override
    public int createMedium(MediumDto mediumDto) throws DaoException {
        int nextId = 0;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            String insertMediumSql = MediumDaoSql.buildInsertMediumSql();
            connection = databaseAgent.getConnection();
            preparedStatement = connection.prepareStatement(insertMediumSql);
            nextId = databaseAgent.getNextId("REF_MEDIUM", "MEDIUM_ID");
            preparedStatement.setInt(1, nextId);
            preparedStatement.setString(2, mediumDto.getDescription());
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
     * @see com.myschool.clazz.dao.MediumDao#deleteMedium(int)
     */
    @Override
    public boolean deleteMedium(int mediumId) throws DaoException {
        boolean deleted = false;
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = databaseAgent.getConnection();
            String query = MediumDaoSql.buildDeleteMediumSql();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, mediumId);
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
     * @see com.myschool.clazz.dao.MediumDao#get(int)
     */
    @Override
    public MediumDto get(int mediumId) throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        MediumDto medium = null;

        try {
            connection = databaseAgent.getConnection();
            String query =MediumDaoSql.buildSelectMediumsSql(true);
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, mediumId);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
            	medium = MediumDataAssembler.create(resultSet);
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
        return medium;
    }

    /* (non-Javadoc)
     * @see com.myschool.clazz.dao.MediumDao#getAll()
     */
    @Override
    public List<MediumDto> getAll() throws DaoException {

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        List<MediumDto> mediums = null;
        MediumDto medium = null;

        try {
            connection = databaseAgent.getConnection();
            String query = MediumDaoSql.buildSelectMediumsSql(false);
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
            	medium = MediumDataAssembler.create(resultSet);
                if (mediums == null) {
                	mediums = new ArrayList<MediumDto>();
                }
                mediums.add(medium);
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
        return mediums;
    }

    /* (non-Javadoc)
     * @see com.myschool.clazz.dao.MediumDao#updateMedium(int, com.myschool.clazz.dto.MediumDto)
     */
    @Override
    public boolean updateMedium(int mediumId, MediumDto mediumDto)
            throws DaoException {
        boolean updated = false;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = databaseAgent.getConnection();
            String query = MediumDaoSql.buildUpdateMediumSql();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, mediumDto.getDescription());
            preparedStatement.setInt(2, mediumId);
            updated = (preparedStatement.executeUpdate() > 0) ? true : false;
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
     * @see com.myschool.clazz.dao.MediumDao#get(java.lang.String)
     */
    @Override
    public MediumDto get(String mediumName) throws DaoException {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        MediumDto medium = null;

        try {
            String selectMediumsSql = MediumDaoSql.buildSelectMediumsSql(mediumName);
            connection = databaseAgent.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(selectMediumsSql);
            if (resultSet.next()) {
                medium = MediumDataAssembler.create(resultSet);
            }
        } catch (SQLException sqlException) {
            throw new DaoException(sqlException.getMessage(), sqlException);
        } catch (ConnectionException connectionException) {
            throw new DaoException(connectionException.getMessage(),
                    connectionException);
        } finally {
            try {
                databaseAgent.releaseResources(connection, statement, resultSet);
            } catch (ConnectionException connectionException) {
                throw new DaoException(connectionException.getMessage(),
                        connectionException);
            }
        }
        return medium;
    }

    /* (non-Javadoc)
     * @see com.myschool.clazz.dao.MediumDao#create(java.lang.String)
     */
    @Override
    public int create(String mediumName) throws DaoException {
        MediumDto medium = new MediumDto();
        medium.setDescription(mediumName);
        return createMedium(medium);
    }

}
