package com.myschool.exim.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.myschool.common.exception.ConnectionException;
import com.myschool.common.exception.DaoException;
import com.myschool.exim.assembler.EximDataAssembler;
import com.myschool.exim.dto.EximDto;
import com.myschool.infra.database.agent.DatabaseAgent;

/**
 * The Class EximDaoImpl.
 */
@Repository
public class EximDaoImpl implements EximDao {

    /** The database agent. */
    @Autowired
    private DatabaseAgent databaseAgent;

    /* (non-Javadoc)
     * @see com.myschool.exim.dao.EximDao#getAll()
     */
    @Override
    public List<EximDto> getAll() throws DaoException {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        List<EximDto> exims = null;
        EximDto exim = null;

        try {
            connection = databaseAgent.getConnection();
            String selectEximsSql = EximDaoSql.selectEximsSql();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(selectEximsSql);
            while (resultSet.next()) {
                exim = EximDataAssembler.createExim(resultSet);
                if (exims == null) {
                    exims = new ArrayList<EximDto>();
                }
                exims.add(exim);
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
        return exims;
    }

    /* (non-Javadoc)
     * @see com.myschool.exim.dao.EximDao#getAllImports()
     */
    @Override
    public List<EximDto> getAllImports() throws DaoException {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        List<EximDto> imports = null;
        EximDto importDto = null;

        try {
            connection = databaseAgent.getConnection();
            String selectEximsSql = EximDaoSql.selectEximsSql(true, false);
            statement = connection.createStatement();
            resultSet = statement.executeQuery(selectEximsSql);
            while (resultSet.next()) {
                importDto = EximDataAssembler.createExim(resultSet);
                if (imports == null) {
                    imports = new ArrayList<EximDto>();
                }
                imports.add(importDto);
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
        return imports;
    }

    /* (non-Javadoc)
     * @see com.myschool.exim.dao.EximDao#getAllExports()
     */
    @Override
    public List<EximDto> getAllExports() throws DaoException {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        List<EximDto> exports = null;
        EximDto exportDto = null;

        try {
            connection = databaseAgent.getConnection();
            String selectEximsSql = EximDaoSql.selectEximsSql(false, true);
            statement = connection.createStatement();
            resultSet = statement.executeQuery(selectEximsSql);
            while (resultSet.next()) {
                exportDto = EximDataAssembler.createExim(resultSet);
                if (exports == null) {
                    exports = new ArrayList<EximDto>();
                }
                exports.add(exportDto);
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
        return exports;
    }

}
