package com.myschool.integration.agent;

import java.io.File;
import java.util.Properties;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.myschool.common.exception.ConfigurationException;
import com.myschool.common.exception.FileSystemException;
import com.myschool.common.util.PropertiesUtil;
import com.myschool.infra.filesystem.util.FileUtil;
import com.myschool.integration.common.util.TempUtil;

/**
 * The Class IntegrationAgent.
 */
@Component
public class IntegrationAgent {

    /** The properties. */
    protected Properties properties;

    /** The temp util. */
    @Autowired
    protected TempUtil tempUtil;

    /*@Autowired
    protected MediaServerFTPClientPool mediaServerFTPClientPool;*/

    /**
     * Inits the.
     * 
     * @throws ConfigurationException the configuration exception
     */
    @PostConstruct
    private void init() throws ConfigurationException {
        loadConfiguration(new File("D:\\projects\\GitHub\\MySchool\\MySchoolIntegration\\target\\classes\\integration.properties"));
    }

    /**
     * Load configuration.
     * 
     * @param propertiesFile the properties file
     * @throws ConfigurationException the configuration exception
     */
    private void loadConfiguration(File propertiesFile) throws ConfigurationException {
        try {
            properties = PropertiesUtil.loadNestedProperties(propertiesFile);
        } catch (FileSystemException fileSystemException) {
            throw new ConfigurationException(
                    "Could not load MediaServer FTP properties. " + fileSystemException.getMessage(), fileSystemException);
        }
    }

    /**
     * Gets the property.
     * 
     * @param key the key
     * @return the property
     */
    public String getProperty(String key) {
        return properties.getProperty(key);
    }

    /**
     * Gets the file.
     * 
     * @param key the key
     * @return the file
     */
    public File getFile(String key) {
        File file = null;
        String value = properties.getProperty(key);
        if (value != null) {
            file = new File(value);
            FileUtil.createDirectory(file);
        }
        return file;
    }

}
