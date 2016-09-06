package com.myschool.routing.common.util;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.annotation.PostConstruct;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import com.myschool.common.exception.ConfigurationException;
import com.myschool.common.exception.FileSystemException;
import com.myschool.common.util.PropertiesUtil;
import com.myschool.routing.exception.IntegrationServerException;

@Component("IntegrationServerFTPClientProxy")
public class IntegrationServerFTPClientProxy {

    /** The Constant LOGGER. */
    private static final Log LOGGER = LogFactory.getLog(IntegrationServerFTPClientProxy.class);

    /** The INSTANC e_ counter. */
    private static int INSTANCE_COUNTER;

    private static Map<String, IntegrationServerFTPClient> MEDIA_SERVER_FTP_CLIENTS;

    /** The properties. */
    private Properties properties;

    /**
     * Inits the.
     * 
     * @throws ConfigurationException the configuration exception
     */
    @PostConstruct
    private void init() throws ConfigurationException {
        try {
            MEDIA_SERVER_FTP_CLIENTS = new HashMap<String, IntegrationServerFTPClient>();
            File propertiesFile = new File("D:\\projects\\GitHub\\MySchool\\MySchoolDataRouting\\src\\main\\resources\\config\\camel\\application-routes.properties");
            properties = PropertiesUtil.loadProperties(propertiesFile);
            //ftpCommandListener = new FTPCommandListener();
            LOGGER.info("Loaded properties");
        } catch (FileSystemException fileSystemException) {
            throw new ConfigurationException(" Could not load MediaServer FTP properties. " + fileSystemException.getMessage(), fileSystemException);
        }
    }

    private IntegrationServerFTPClient getIntegrationServerFTPClient(String name) {
        IntegrationServerFTPClient integrationServerFTPClient = null;
        if (MEDIA_SERVER_FTP_CLIENTS.containsKey(name)) {
            integrationServerFTPClient = MEDIA_SERVER_FTP_CLIENTS.get(name);
        } else {
            integrationServerFTPClient = new IntegrationServerFTPClient(getInstanceName(name), properties);
            MEDIA_SERVER_FTP_CLIENTS.put(name, integrationServerFTPClient);
        }
        return integrationServerFTPClient;
    }

    /**
     * Gets the instance name.
     * 
     * @param name the name
     * @param op the op
     * @return the instance name
     */
    private synchronized String getInstanceName(String name) {
        if (name == null) {
            return "MSFTPC_" + (++INSTANCE_COUNTER);
        } else {
            return name + "_" + (++INSTANCE_COUNTER);
        }
    }

    /**
     * Gets the file name.
     * 
     * @param mediaType the media type
     * @param remoteDirectory the remote directory
     * @param identity the identity
     * @return the file name
     */
    public String getFileName(String mediaType, String remoteDirectory, String identity) throws IntegrationServerException {
        IntegrationServerFTPClient integrationServerFTPClient = getIntegrationServerFTPClient(mediaType);
        return integrationServerFTPClient.getFileName(remoteDirectory, identity);
    }

    /**
     * Download file.
     * 
     * @param mediaType the media type
     * @param srcFile the src file
     * @param remoteDirectory the remote directory
     * @param remoteFileName the remote file name
     */
    public void downloadFile(String mediaType, File srcFile,
            String remoteDirectory, String remoteFileName)
            throws IntegrationServerException {
        IntegrationServerFTPClient integrationServerFTPClient = getIntegrationServerFTPClient(mediaType);
        integrationServerFTPClient.downloadFile(srcFile, remoteDirectory, remoteFileName);
    }

    /**
     * Gets the file content.
     * 
     * @param mediaType the media type
     * @param remoteDirectory the remote directory
     * @param remoteFileName the remote file name
     * @return the file content
     */
    public String getFileContent(String mediaType, String remoteDirectory,
            String remoteFileName) throws IntegrationServerException {
        IntegrationServerFTPClient integrationServerFTPClient = getIntegrationServerFTPClient(mediaType);
        return integrationServerFTPClient.getFileName(remoteDirectory, remoteFileName);
    }


    /**
     * Creates the directory.
     * 
     * @param mediaType the media type
     * @param remoteDirectory the remote directory
     */
    public void createDirectory(String mediaType, String remoteDirectory) throws IntegrationServerException {
        IntegrationServerFTPClient integrationServerFTPClient = getIntegrationServerFTPClient(mediaType);
        integrationServerFTPClient.createDirectory(remoteDirectory);
    }

    /**
     * Store file.
     * 
     * @param mediaType the media type
     * @param filePath the file path
     * @param file the file
     * @return true, if successful
     */
    public boolean storeFile(String mediaType, String filePath, File file) throws IntegrationServerException {
        IntegrationServerFTPClient integrationServerFTPClient = getIntegrationServerFTPClient(mediaType);
        return integrationServerFTPClient.storeFile(filePath, file);
    }

    /**
     * Delete file.
     * 
     * @param mediaType the media type
     * @param remoteDirectory the remote directory
     * @param fileName the file name
     */
    public void deleteFile(String mediaType, String remoteDirectory, String fileName) throws IntegrationServerException {
        IntegrationServerFTPClient integrationServerFTPClient = getIntegrationServerFTPClient(mediaType);
        integrationServerFTPClient.deleteFile(remoteDirectory, fileName);
    }

    /**
     * Delete directory.
     * 
     * @param mediaType the media type
     * @param remoteDirectory the remote directory
     */
    public void deleteDirectory(String mediaType, String remoteDirectory) throws IntegrationServerException {
        IntegrationServerFTPClient integrationServerFTPClient = getIntegrationServerFTPClient(mediaType);
        integrationServerFTPClient.deleteDirectory(remoteDirectory);
    }

}
