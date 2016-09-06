package com.myschool.integration.ftp;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.myschool.common.exception.ConfigurationException;
import com.myschool.infra.remote.ftp.exception.FtpException;
import com.myschool.integration.agent.IntegrationProperties;

/**
 * The Class MediaServerFTPClientProxy.
 */
@Component("MediaServerFTPClientProxy")
public class MediaServerFTPClientProxy {

    /** The Constant LOGGER. *//*
    private static final Log LOGGER = LogFactory.getLog(MediaServerFTPClientProxy.class);*/

    /** The INSTANC e_ counter. */
    private static int INSTANCE_COUNTER;

    private static Map<String, MediaServerFTPClient> MEDIA_SERVER_FTP_CLIENTS;

    @Autowired
    private IntegrationProperties integrationProperties;

    /** The properties. */
    //private Properties properties;

    /**
     * Inits the.
     * 
     * @throws ConfigurationException the configuration exception
     */
    @PostConstruct
    private void init() throws ConfigurationException {
        MEDIA_SERVER_FTP_CLIENTS = new HashMap<String, MediaServerFTPClient>();
        /*try {
            File propertiesFile = new File("D:\\projects\\GitHub\\MySchool\\MySchoolIntegration\\target\\classes\\integration.properties");
            //properties = PropertiesUtil.loadProperties(propertiesFile);
            //ftpCommandListener = new FTPCommandListener();
            LOGGER.info("Loaded properties");
        } catch (FileSystemException fileSystemException) {
            throw new ConfigurationException(" Could not load MediaServer FTP properties. " + fileSystemException.getMessage(), fileSystemException);
        }*/
    }

    private MediaServerFTPClient getMediaServerFTPClient(String name) {
        MediaServerFTPClient mediaServerFTPClient = null;
        if (MEDIA_SERVER_FTP_CLIENTS.containsKey(name)) {
            mediaServerFTPClient = MEDIA_SERVER_FTP_CLIENTS.get(name);
        } else {
            mediaServerFTPClient = new MediaServerFTPClient(getInstanceName(name), integrationProperties.getProperties());
            MEDIA_SERVER_FTP_CLIENTS.put(name, mediaServerFTPClient);
        }
        return mediaServerFTPClient;
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
    public String getFileName(String mediaType, String remoteDirectory,
            String identity) throws FtpException {
        MediaServerFTPClient mediaServerFTPClient = getMediaServerFTPClient(mediaType);
        return mediaServerFTPClient.getFileName(remoteDirectory, identity);
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
            String remoteDirectory, String remoteFileName) throws FtpException {
        MediaServerFTPClient mediaServerFTPClient = getMediaServerFTPClient(mediaType);
        mediaServerFTPClient.downloadFile(srcFile, remoteDirectory, remoteFileName);
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
            String remoteFileName) throws FtpException {
        MediaServerFTPClient mediaServerFTPClient = getMediaServerFTPClient(mediaType);
        return mediaServerFTPClient.getFileContent(remoteDirectory, remoteFileName);
    }


    /**
     * Creates the directory.
     * 
     * @param mediaType the media type
     * @param remoteDirectory the remote directory
     */
    public void createDirectory(String mediaType, String remoteDirectory)
            throws FtpException {
        MediaServerFTPClient mediaServerFTPClient = getMediaServerFTPClient(mediaType);
        mediaServerFTPClient.createDirectory(remoteDirectory);
    }

    /**
     * Store file.
     * 
     * @param mediaType the media type
     * @param filePath the file path
     * @param file the file
     * @return true, if successful
     */
    public boolean storeFile(String mediaType, String filePath, File file)
            throws FtpException {
        MediaServerFTPClient mediaServerFTPClient = getMediaServerFTPClient(mediaType);
        return mediaServerFTPClient.storeFile(filePath, file);
    }

    /**
     * Delete file.
     * 
     * @param mediaType the media type
     * @param remoteDirectory the remote directory
     * @param fileName the file name
     */
    public void deleteFile(String mediaType, String remoteDirectory,
            String fileName) throws FtpException {
        MediaServerFTPClient mediaServerFTPClient = getMediaServerFTPClient(mediaType);
        mediaServerFTPClient.deleteFile(remoteDirectory, fileName);
    }

    /**
     * Delete directory.
     * 
     * @param mediaType the media type
     * @param remoteDirectory the remote directory
     */
    public void deleteDirectory(String mediaType, String remoteDirectory)
            throws FtpException {
        MediaServerFTPClient mediaServerFTPClient = getMediaServerFTPClient(mediaType);
        mediaServerFTPClient.deleteDirectory(remoteDirectory);
    }

}
