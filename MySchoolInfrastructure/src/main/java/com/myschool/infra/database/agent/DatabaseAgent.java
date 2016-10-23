package com.myschool.infra.database.agent;

import java.io.File;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.MessageFormat;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.myschool.common.exception.AgentException;
import com.myschool.common.exception.ConfigurationException;
import com.myschool.common.exception.ConnectionException;
import com.myschool.common.exception.DaoException;
import com.myschool.common.exception.FileSystemException;
import com.myschool.common.util.PropertiesUtil;
import com.myschool.infra.agent.AbstractAgent;
import com.myschool.infra.application.Agents;
import com.myschool.infra.webserver.agent.WebServerAgent;

/**
 * The Class DatabaseAgent.
 */
@Component
public class DatabaseAgent extends AbstractAgent {

	private static final String SELECT_MAX_ID = "SELECT COALESCE(MAX({0})+1, 1) AS MAX_ID FROM {1}";

    /** The data source. */
    private static DataSource dataSource;

    /** The agents. */
    @Autowired
    private Agents agents;

    /* (non-Javadoc)
     * @see com.myschool.infra.agent.Agent#loadConfiguration(java.io.File)
     */
    @Override
    public void loadConfiguration(File configFile)
            throws ConfigurationException {
        try {
            // Determine the runtime and initiate the datasource accordingly.
            WebServerAgent webServerAgent = agents.getWebServerAgent();
            properties = PropertiesUtil.loadProperties(configFile);
            dataSource = webServerAgent.getDataSource(properties);
            if (dataSource == null) {
                throw new ConfigurationException("Datasource is unreachable.");
            }
        } catch (AgentException agentException) {
            throw new ConfigurationException(agentException.getMessage(), agentException);
        } catch (FileSystemException fileSystemException) {
            throw new ConfigurationException(fileSystemException.getMessage(), fileSystemException);
        }
    }

    /* (non-Javadoc)
     * @see com.myschool.infra.Agent#validate()
     */
    @Override
    public void validate() throws AgentException {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            connection = getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM ORGANIZATION");
        } catch (SQLException sqlException) {
            throw new AgentException("Errors occurred while validating DatabaseAgent", sqlException);
        } catch (ConnectionException connectionException) {
            throw new AgentException("Errors occurred while validating DatabaseAgent", connectionException);
        } finally {
            try {
                releaseResources(connection, statement, resultSet);
            } catch (ConnectionException connectionException) {
                throw new AgentException(connectionException.getMessage(), connectionException);
            }
        }
    }

    /**
     * Gets the connection.
     *
     * @return the connection
     * @throws ConnectionException the connection exception
     */
    public Connection getConnection() throws ConnectionException {
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            if (connection == null) {
                throw new ConnectionException("Could not create a connection.");
            }
        } catch (SQLException sqlException) {
            throw new ConnectionException(sqlException.getMessage(),
                    sqlException);
        }
        return connection;
    }

    /**
     * Release resources.
     * 
     * @param connection the connection
     * @param statement the statement
     * @param resultSet the result set
     * @throws ConnectionException the connection exception
     */
    public void releaseResources(Connection connection,
            Statement statement, ResultSet resultSet)
            throws ConnectionException {

        try {
            if (resultSet != null) {
                resultSet.close();
            }
            if (statement != null) {
                statement.close();
            }
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException sqlException) {
            throw new ConnectionException(sqlException.getMessage(),
                    sqlException);
        }
    }

    /**
     * Release resources.
     *
     * @param connection the connection
     * @param statement the statement
     * @throws ConnectionException the connection exception
     */
    public void releaseResources(Connection connection,
            Statement statement) throws ConnectionException {
        releaseResources(connection, statement, null);
    }

    /**
     * Release resources.
     *
     * @param connection the connection
     * @throws ConnectionException the connection exception
     */
    public void releaseResources(Connection connection) throws ConnectionException {
        releaseResources(connection, null, null);
    }

    /**
     * Gets the next id.
     * 
     * @param tableName the table name
     * @param columnName the column name
     * @return the next id
     * @throws DaoException the dao exception
     */
    public int getNextId(String tableName, String columnName)
            throws DaoException {
        int nextId = 0;
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            connection = getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(MessageFormat.format(SELECT_MAX_ID, columnName, tableName));

            if (resultSet.next()) {
                nextId = resultSet.getInt("MAX_ID");
            }
        } catch (SQLException sqlException) {
            throw new DaoException(sqlException.getMessage(), sqlException);
        } catch (ConnectionException connectionException) {
            throw new DaoException(connectionException.getMessage(),
                    connectionException);
        } finally {
            try {
                releaseResources(connection, statement, resultSet);
            } catch (ConnectionException connectionException) {
                throw new DaoException(connectionException.getMessage(),
                        connectionException);
            }
        }
        return nextId;
    }

}
