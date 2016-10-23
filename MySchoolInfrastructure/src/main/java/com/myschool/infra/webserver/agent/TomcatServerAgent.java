package com.myschool.infra.webserver.agent;

import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.springframework.stereotype.Component;

import com.myschool.common.exception.AgentException;
import com.myschool.infra.database.constants.DataSourceConstants;

/**
 * The Class TomcatServerAgent.
 */
@Component
public class TomcatServerAgent extends WebServerAgent {

    /* (non-Javadoc)
     * @see com.myschool.infra.webserver.agent.WebServerAgent#getDataSource(java.util.Properties)
     */
    @Override
    public DataSource getDataSource(Properties dataSourceProperties) throws AgentException {
        try {
            String dataSourceName = (String) dataSourceProperties.get(DataSourceConstants.JDBC_DATASOURCE_NAME);
            Context context = new InitialContext();
            return (DataSource) context.lookup("java:comp/env/jdbc/" + dataSourceName);
        } catch (NamingException namingException) {
            throw new AgentException(namingException.getMessage(), namingException);
        }
    }

    /* (non-Javadoc)
     * @see com.myschool.infra.webserver.agent.WebServerAgent#isWebServer()
     */
    @Override
    public boolean isWebServer() throws AgentException {
        return true;
    }

}
