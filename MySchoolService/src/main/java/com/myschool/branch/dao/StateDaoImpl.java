package com.myschool.branch.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.myschool.branch.assembler.StateDataAssembler;
import com.myschool.branch.dto.StateDto;
import com.myschool.common.exception.ConnectionException;
import com.myschool.common.exception.DaoException;
import com.myschool.infra.database.agent.DatabaseAgent;

/**
 * The Class StateDaoImpl.
 */
@Repository
public class StateDaoImpl implements StateDao {

    /** The database agent. */
    @Autowired
    private DatabaseAgent databaseAgent;

    /* (non-Javadoc)
     * @see com.myschool.branch.dao.StateDao#create(java.lang.String)
     */
    @Override
    public int create(String stateName) throws DaoException {
        boolean stateCreated = false;
        int stateId = 0;
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = databaseAgent.getConnection();
            stateId = databaseAgent.getNextId("REF_STATE", "STATE_ID");
            preparedStatement = connection.prepareStatement(StateDaoSql.INSERT);
            preparedStatement.setInt(1, stateId);
            preparedStatement.setString(2, stateName);
            stateCreated = (preparedStatement.executeUpdate() > 0) ? true : false;
            if (!stateCreated) {
                stateId = 0;
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
        return stateId;
    }

    /* (non-Javadoc)
     * @see com.myschool.branch.dao.StateDao#get(java.lang.String)
     */
    @Override
    public StateDto get(String stateName) throws DaoException {
        StateDto state = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = databaseAgent.getConnection();
            preparedStatement = connection.prepareStatement(StateDaoSql.SELECT_BY_STATE);
            preparedStatement.setString(1, stateName);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                state = StateDataAssembler.create(resultSet);
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
        return state;
    }

    /* (non-Javadoc)
     * @see com.myschool.branch.dao.StateDao#getAll()
     */
    @Override
    public List<StateDto> getAll() throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        StateDto state = null;
        List<StateDto> states = null;

        try {
            connection = databaseAgent.getConnection();
            preparedStatement = connection.prepareStatement(StateDaoSql.SELECT_ALL);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                state = StateDataAssembler.create(resultSet);
                if (states == null) {
                    states = new ArrayList<StateDto>();
                }
                states.add(state);
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
        return states;
    }

}
