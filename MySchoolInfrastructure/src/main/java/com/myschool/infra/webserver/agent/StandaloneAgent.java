package com.myschool.infra.webserver.agent;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.stereotype.Component;

import com.myschool.common.exception.AgentException;
import com.myschool.infra.database.constants.DataSourceConstants;

/**
 * The Class StandaloneAgent.
 */
@Component
public class StandaloneAgent extends WebServerAgent {

    /* (non-Javadoc)
     * @see com.myschool.infra.webserver.agent.WebServerAgent#validate()
     */
    @Override
    public void validate() throws AgentException {
    }

    /* (non-Javadoc)
     * @see com.myschool.infra.webserver.agent.WebServerAgent#getDataSource(java.util.Properties)
     */
    @Override
    public DataSource getDataSource(Properties dataSourceProperties) throws AgentException {
        DataSource dataSource = null;
        try {
            // The program must have initialized as a stand along program.
            // Read through the DB connection file in config directory to create a data source.
            String dbUrl = dataSourceProperties.getProperty(DataSourceConstants.JDBC_DATASOURCE_URL);
            String dbUserName = dataSourceProperties.getProperty(DataSourceConstants.DATABASE_USER_NAME);
            String dbPassword = dataSourceProperties.getProperty(DataSourceConstants.DATABASE_PASSWORD);
            dataSource = new SimpleDriverDataSource(DriverManager.getDriver(dbUrl), dbUrl, dbUserName, dbPassword);
        } catch (SQLException sqlException) {
            throw new AgentException(sqlException.getMessage(), sqlException);
        }
        return dataSource;
    }

    /* (non-Javadoc)
     * @see com.myschool.infra.webserver.agent.WebServerAgent#isWebServer()
     */
    @Override
    public boolean isWebServer() throws AgentException {
        return false;
    }

}