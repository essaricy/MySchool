package com.myschool.branch.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.myschool.branch.assembler.DivisionDataAssembler;
import com.myschool.branch.dto.DivisionDto;
import com.myschool.common.exception.ConnectionException;
import com.myschool.common.exception.DaoException;
import com.myschool.infra.database.agent.DatabaseAgent;

/**
 * The Class DivisionDaoImpl.
 */
@Repository
public class DivisionDaoImpl implements DivisionDao {

    /** The database agent. */
    @Autowired
    private DatabaseAgent databaseAgent;

    /* (non-Javadoc)
     * @see com.myschool.branch.dao.DivisionDao#create(com.myschool.branch.dto.DivisionDto)
     */
    public int create(DivisionDto divisionDto) throws DaoException {
        int nextId = 0;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            nextId = databaseAgent.getNextId("REF_DIVISION", "DIVISION_ID");
            connection = databaseAgent.getConnection();
            preparedStatement = connection.prepareStatement(DivisionDaoSql.INSERT);
            preparedStatement.setInt(1, nextId);
            preparedStatement.setString(2, divisionDto.getDivisionCode());
            preparedStatement.setString(3, divisionDto.getDescription());
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
     * @see com.myschool.branch.dao.DivisionDao#getAll()
     */
    public List<DivisionDto> getAll() throws DaoException {
        List<DivisionDto> divisions = null;

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        DivisionDto division = null;

        try {
            connection = databaseAgent.getConnection();
            preparedStatement = connection.prepareStatement(DivisionDaoSql.SELECT_ALL);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
            	division = DivisionDataAssembler.create(resultSet);
                if (divisions == null) {
                    divisions = new ArrayList<DivisionDto>();
                }
                divisions.add(division);
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
        return divisions;
    }

    /* (non-Javadoc)
     * @see com.myschool.branch.dao.DivisionDao#get(int)
     */
    public DivisionDto get(int divisionId) throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        DivisionDto division = null;

        try {
            connection = databaseAgent.getConnection();
            preparedStatement = connection.prepareStatement(DivisionDaoSql.SELECT_BY_ID);
            preparedStatement.setInt(1,divisionId);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                division = DivisionDataAssembler.create(resultSet);
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
        return division;
    }

    /* (non-Javadoc)
     * @see com.myschool.branch.dao.DivisionDao#update(int, com.myschool.branch.dto.DivisionDto)
     */
    public boolean update(int divisionId, DivisionDto divisionDto)
            throws DaoException {
        boolean updated = false;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = databaseAgent.getConnection();
            preparedStatement = connection.prepareStatement(DivisionDaoSql.UPDATE);
            preparedStatement.setString(1, divisionDto.getDivisionCode());
            preparedStatement.setString(2, divisionDto.getDescription());
            preparedStatement.setInt(3, divisionId);
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
     * @see com.myschool.branch.dao.DivisionDao#delete(int)
     */
    public boolean delete(int divisionId) throws DaoException {
        boolean deleted = false;
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = databaseAgent.getConnection();
            preparedStatement = connection.prepareStatement(DivisionDaoSql.DELETE);
            preparedStatement.setInt(1, divisionId);
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
     * @see com.myschool.branch.dao.DivisionDao#get(java.lang.String)
     */
    @Override
    public DivisionDto get(String divisionCode) throws DaoException {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        DivisionDto division = null;

        try {
            connection = databaseAgent.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(DivisionDaoSql.SELECT_BY_CODE);
            if (resultSet.next()) {
                division = DivisionDataAssembler.create(resultSet);
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
        return division;
    }

}
