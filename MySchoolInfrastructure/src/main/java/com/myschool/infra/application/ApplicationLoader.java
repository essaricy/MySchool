package com.myschool.infra.application;

import java.io.File;
import java.io.InputStream;
import java.util.Properties;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.myschool.common.exception.AgentException;
import com.myschool.common.exception.ApplicationException;
import com.myschool.common.exception.ConfigurationException;
import com.myschool.common.exception.FileSystemException;
import com.myschool.common.util.PropertiesUtil;
import com.myschool.common.util.StringUtil;
import com.myschool.infra.application.dto.AppConfigDto;
import com.myschool.infra.application.reader.AppConfigReader;
import com.myschool.infra.filesystem.constants.FileSystemConstants;
import com.myschool.infra.filesystem.util.FileUtil;

/**
 * The Class ApplicationLoader.
 */
@Component
public class ApplicationLoader {

    /** The Constant LOGGER. */
    private static final Logger LOGGER = Logger.getLogger(ApplicationLoader.class);

    /** The Constant FILE_SYSTEM_PROPERTIES. */
    public static final String FILE_SYSTEM_PROPERTIES = "FileSystem.properties";

    /** The agents loader. */
    @Autowired
    private AgentsLoader agentsLoader;

    /** The app config reader. */
    @Autowired
    private AppConfigReader appConfigReader;

    /** The app config. */
    private AppConfigDto appConfig;

    /**
     * Load application.
     * 
     * @throws ApplicationException the application exception
     */
    @PostConstruct
    public void loadApplication() throws ApplicationException {
        try {
            LOGGER.info("Loading Application");
            Properties properties = getFileSystemProperties();
            String appConfigFilePath = properties.getProperty(FileSystemConstants.CONFIG_APPLICATION_APP_FILE);
            appConfig = appConfigReader.getAppConfig(appConfigFilePath, properties);
            agentsLoader.loadAgents(properties, appConfig.getAgentDtos());
        } catch (ConfigurationException configurationException) {
            throw new ApplicationException(configurationException.getMessage(), configurationException);
        } catch (AgentException agentException) {
            throw new ApplicationException(agentException.getMessage(), agentException);
        } catch (FileSystemException fileSystemException) {
            throw new ApplicationException(fileSystemException.getMessage(), fileSystemException);
        }
    }

    /**
     * Gets the file system properties.
     * 
     * @return the file system properties
     * @throws FileSystemException the file system exception
     */
    private Properties getFileSystemProperties() throws FileSystemException {
        Properties properties = null;
        // First look into the system properties.
        String fileSystemPropertyFileName = System.getProperty(FILE_SYSTEM_PROPERTIES);
        String message = "File system configuration file is missing/inaccessible.";
        if (StringUtil.isNullOrBlank(fileSystemPropertyFileName)) {
            LOGGER.warn(FILE_SYSTEM_PROPERTIES + " not found in the system properties. Searching in the classpath.");
            // File is not found in the system properties.
            // Search in the class path
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream(FILE_SYSTEM_PROPERTIES);
            properties = PropertiesUtil.loadNestedProperties(inputStream);
        } else {
            File fileSystemPropertiesFile = new File(fileSystemPropertyFileName);
            FileUtil.checkFile(fileSystemPropertiesFile, message, message);
            properties = PropertiesUtil.loadNestedProperties(fileSystemPropertiesFile);
        }
        return properties;
    }

}
