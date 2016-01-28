package com.myschool.integration.common.util;

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
import com.myschool.integration.common.exception.MediaServerException;

/**
 * The Class MediaServerFTPClientProxy.
 */
@Component("MediaServerFTPClientProxy")
public class MediaServerFTPClientProxy {

    /** The Constant LOGGER. */
    private static final Log LOGGER = LogFactory.getLog(MediaServerFTPClientProxy.class);

    /** The INSTANC e_ counter. */
    private static int INSTANCE_COUNTER;

    private static Map<String, MediaServerFTPClient> MEDIA_SERVER_FTP_CLIENTS;

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
            MEDIA_SERVER_FTP_CLIENTS = new HashMap<String, MediaServerFTPClient>();
            File propertiesFile = new File("D:\\projects\\GitHub\\MySchool\\MySchoolIntegration\\target\\classes\\integration.properties");
            properties = PropertiesUtil.loadProperties(propertiesFile);
            //ftpCommandListener = new FTPCommandListener();
            LOGGER.info("Loaded properties");
        } catch (FileSystemException fileSystemException) {
            throw new ConfigurationException(" Could not load MediaServer FTP properties. " + fileSystemException.getMessage(), fileSystemException);
        }
    }

    private MediaServerFTPClient getMediaServerFTPClient(String name) {
        MediaServerFTPClient mediaServerFTPClient = null;
        if (MEDIA_SERVER_FTP_CLIENTS.containsKey(name)) {
            mediaServerFTPClient = MEDIA_SERVER_FTP_CLIENTS.get(name);
        } else {
            mediaServerFTPClient = new MediaServerFTPClient(getInstanceName(name), properties);
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
     * @throws MediaServerException the media server exception
     */
    public String getFileName(String mediaType, String remoteDirectory, String identity) throws MediaServerException {
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
     * @throws MediaServerException the media server exception
     */
    public void downloadFile(String mediaType, File srcFile,
            String remoteDirectory, String remoteFileName)
            throws MediaServerException {
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
     * @throws MediaServerException the media server exception
     */
    public String getFileContent(String mediaType, String remoteDirectory,
            String remoteFileName) throws MediaServerException {
        MediaServerFTPClient mediaServerFTPClient = getMediaServerFTPClient(mediaType);
        return mediaServerFTPClient.getFileName(remoteDirectory, remoteFileName);
    }


    /**
     * Creates the directory.
     * 
     * @param mediaType the media type
     * @param remoteDirectory the remote directory
     * @throws MediaServerException the media server exception
     */
    public void createDirectory(String mediaType, String remoteDirectory) throws MediaServerException {
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
     * @throws MediaServerException the media server exception
     */
    public boolean storeFile(String mediaType, String filePath, File file) throws MediaServerException {
        MediaServerFTPClient mediaServerFTPClient = getMediaServerFTPClient(mediaType);
        return mediaServerFTPClient.storeFile(filePath, file);
    }

    /**
     * Delete file.
     * 
     * @param mediaType the media type
     * @param remoteDirectory the remote directory
     * @param fileName the file name
     * @throws MediaServerException the media server exception
     */
    public void deleteFile(String mediaType, String remoteDirectory, String fileName) throws MediaServerException {
        MediaServerFTPClient mediaServerFTPClient = getMediaServerFTPClient(mediaType);
        mediaServerFTPClient.deleteFile(remoteDirectory, fileName);
    }

    /**
     * Delete directory.
     * 
     * @param mediaType the media type
     * @param remoteDirectory the remote directory
     * @throws MediaServerException the media server exception
     */
    public void deleteDirectory(String mediaType, String remoteDirectory) throws MediaServerException {
        MediaServerFTPClient mediaServerFTPClient = getMediaServerFTPClient(mediaType);
        mediaServerFTPClient.deleteDirectory(remoteDirectory);
    }

}
