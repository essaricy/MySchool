package com.myschool.integration.common.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.MessageFormat;
import java.util.List;
import java.util.Properties;

import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

import com.myschool.common.util.PropertiesUtil;
import com.myschool.infra.filesystem.util.FileUtil;
import com.myschool.integration.common.constant.IntegrationConstant;
import com.myschool.integration.common.constant.IntegrationPropertiesConstant;
import com.myschool.integration.common.exception.MediaServerException;

/**
 * The Class MediaServerFTPClient.
 */
public class MediaServerFTPClient {

    /** The Constant LOGGER. */
    private static final Log LOGGER = LogFactory.getLog(MediaServerFTPClient.class);

    /** The name. */
    private String name;

    /** The ftp client. */
    private FTPClient ftpClient;

    /** The interrupted. */
    private boolean interrupted;

    /** The properties. */
    private Properties properties;

    private FTPCommandListener ftpCommandListener;

    /**
     * Instantiates a new media server ftp client.
     * 
     * @param name the name
     * @param properties the properties
     */
    public MediaServerFTPClient(String name, Properties properties) {
        this.name=name;
        this.properties=properties;
        LOGGER.info("Instantiated MediaServerFTPClient: " + name);
        ftpCommandListener = new FTPCommandListener();
    }

    private FTPClient getFTPClientUntilSuccess(boolean forceConnect) {
        if (forceConnect) {
            disconnect();
        }
        return getFTPClientUntilSuccess();
    }

    /**
     * Gets the fTP client until success.
     * 
     * @return the fTP client until success
     */
    private FTPClient getFTPClientUntilSuccess() {
        long attempt = 1;
        long delay = 0;

        long retryMinDelay = PropertiesUtil.getLong(properties, IntegrationPropertiesConstant.MEDIA_SERVER_RETRY_MIN_DELAY);
        long retryMaxDelay = PropertiesUtil.getLong(properties, IntegrationPropertiesConstant.MEDIA_SERVER_RETRY_MAX_DELAY);

        while(true) {
            try {
                if (ftpClient == null) {
                    ftpClient = getFTPClient();
                } else if (!ftpClient.isConnected()) {
                    ftpClient = getFTPClient();
                }

                if (ftpClient != null && ftpClient.isConnected()) {
                    //ftpClient.enterLocalPassiveMode();
                    break;
                }
                delay = (retryMinDelay * attempt < retryMaxDelay)? retryMinDelay * (attempt++) : retryMaxDelay;
                LOGGER.info(name + " Obtaining FTP connection, attempt " + attempt + " with delay of " + delay);
                Thread.sleep(delay);
            } catch (InterruptedException interruptedException) {
                LOGGER.error(name + " Failed to obtain connection. " + interruptedException.getMessage(), interruptedException);
            }
        }
        return ftpClient;
    }

    /**
     * Gets the fTP client.
     * 
     * @return the fTP client
     */
    private FTPClient getFTPClient() {
        FTPClient ftp = null;

        try {
            String ftpHost = properties.getProperty(IntegrationPropertiesConstant.MEDIA_SERVER_HOST);
            int ftpPort = PropertiesUtil.getInt(properties, IntegrationPropertiesConstant.MEDIA_SERVER_FTP_PORT);
            String ftpUser = properties.getProperty(IntegrationPropertiesConstant.MEDIA_SERVER_FTP_USER);
            String ftpPwd = properties.getProperty(IntegrationPropertiesConstant.MEDIA_SERVER_FTP_PWD);

            ftp = new FTPClient();
            ftp.setControlKeepAliveTimeout(-1);
            ftp.setControlKeepAliveReplyTimeout(-1);
            ftp.addProtocolCommandListener(ftpCommandListener);

            int reply;
            ftp.connect(ftpHost, ftpPort);
            LOGGER.info(name + " Connected to " + ftpHost + " on " + ftpPort);

            // After connection attempt, you should check the reply code to verify success.
            reply = ftp.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                ftp.disconnect();
                LOGGER.warn(name + " FTP server refused connection. reply=" + reply);
                return null;
            }
            if (!ftp.login(ftpUser, ftpPwd)) {
                LOGGER.warn(name + " FTP server login failed. reply=" + reply);
                ftp.logout();
                ftp.disconnect();
                return null;
            }
            //LOGGER.debug("Remote system is " + ftp.getSystemType());
            ftp.setBufferSize(3 * 1024*1024);
            ftp.setFileType(FTP.BINARY_FILE_TYPE);
            // Use passive mode as default because most of us are behind firewalls these days.
            ftp.enterLocalPassiveMode();
        } catch (Exception exception) {
            if (ftp.isConnected()) {
                try {
                    ftp.disconnect();
                } catch (IOException f) {
                    // do nothing
                }
            }
            LOGGER.warn(name + " Could not connect to server. " + exception.getMessage());
            return null;
        }
        return ftp;
    }

    /**
     * Gets the file name.
     * 
     * @param directory the directory
     * @param identity the identity
     * @return the file name
     * @throws MediaServerException the media server exception
     */
    public String getFileName(String directory, String identity) throws MediaServerException {
        FTPClient ftpClient = null;

        try {
            LOGGER.debug("getFileName(" + directory + "/" + identity + ")");
            if (directory != null && identity != null) {
                ftpClient = getFTPClientUntilSuccess(true);
                //ftpClient = getFTPClientUntilSuccess();
                FTPFile[] ftpFiles = ftpClient.listFiles(directory);
                //LOGGER.debug("ftpFiles=" + ftpFiles);
                //LOGGER.debug("# of FTP Files=" + ((ftpFiles == null) ? null : ftpFiles.length));
                if (ftpFiles == null || ftpFiles.length == 0) {
                    //LOGGER.debug("No Files found....");
                    return null;
                }
                //LOGGER.debug("Files found=" + ftpFiles.length);
                // Step 1: try to find the file name with exact match
                for (FTPFile ftpFile : ftpFiles) {
                    if (ftpFile.isFile()) {
                        String name = ftpFile.getName();
                        //LOGGER.debug("getFileName, 1 listed name=" + name);
                        if (name.equals(identity)) {
                            //LOGGER.debug("getFileName, identity=" + identity);
                            return name;
                        }
                    }
                }
                // Step 2: If not found still, try to find by name, excluding extension
                for (FTPFile ftpFile : ftpFiles) {
                    if (ftpFile.isFile()) {
                        String name = ftpFile.getName();
                        if (name.indexOf(".") != -1) {
                            String listFileName = FileUtil.getFileName(name);
                            if (listFileName != null && listFileName.equals(identity)) {
                                //LOGGER.debug("getFileName, 2 listed name=" + name);
                                return name;
                            }
                        }
                    }
                }
            }
        } catch (IOException ioException) {
            throw new MediaServerException(ioException.getMessage());
        }
        return null;
    }

    /**
     * Gets the file content.
     * 
     * @param remoteFileName the remote file name
     * @return the file content
     * @throws MediaServerException the media server exception
     */
    public String getFileContent(String remoteFileName) throws MediaServerException {
        FTPClient ftp = null;
        StringBuffer fileContent = null;
        InputStream inputStream = null;

        if (remoteFileName != null) {
            int attempt = 1;
            long startTime = 0;
            long endTime = 0;
            while (true) {
                startTime=0;
                try {
                    ftp = getFTPClientUntilSuccess();
                    startTime = System.currentTimeMillis();
                    inputStream = ftp.retrieveFileStream(remoteFileName);
                    int replyCode = ftp.getReplyCode();
                    endTime = System.currentTimeMillis();
                    if (replyCode == FtpServerReturnCodes.CODE_550_REQUESTED_ACTION_NOT_TAKEN_FILE_NOT_FOUND) {
                        LOGGER.info(MessageFormat.format(
                                IntegrationConstant.OP_SUCCESS, name,
                                IntegrationConstant.OP_READ,
                                IntegrationConstant.NOT_EXISTS, attempt,
                                (endTime - startTime), remoteFileName));
                        break;
                    } else {
                        if (inputStream == null) {
                            LOGGER.info(MessageFormat.format(
                                    IntegrationConstant.OP_FAILED, name,
                                    IntegrationConstant.OP_READ,
                                    IntegrationConstant.NOT_EXISTS, attempt,
                                    (endTime - startTime), remoteFileName));
                            break;
                        }
                    }
                    List<String> lines = IOUtils.readLines(inputStream);
                    if (lines != null) {
                        fileContent = new StringBuffer();
                        for (String line : lines) {
                            fileContent.append(line).append("\n");
                        }
                    }
                    break;
                } catch (Exception exception) {
                    if (startTime == 0) {
                        startTime = endTime;
                    }
                    String logMessage = MessageFormat.format(
                            IntegrationConstant.OP_FAILED, name,
                            IntegrationConstant.OP_READ,
                            IntegrationConstant.STATUS_ERRORED, attempt,
                            (endTime - startTime), remoteFileName,
                            ftp.getReplyString());
                    handleException(ftp, exception, logMessage);
                }
                attempt++;
                if (attempt > 10) {
                    IOUtils.closeQuietly(inputStream);
                    String logMessage = MessageFormat.format(
                            IntegrationConstant.OP_MAX_ATTEMPTS, name,
                            IntegrationConstant.OP_READ, remoteFileName);
                    throw new MediaServerException(logMessage);
                }
            }
            IOUtils.closeQuietly(inputStream);
        }
        return (fileContent == null)? null : fileContent.toString();
    }

    public String getFileContent(String directory, String fileName) throws MediaServerException {
        if (directory != null && fileName != null) {
            String fileToDelete = null;
            if (directory.endsWith("/")) {
                fileToDelete = directory + fileName;
            } else {
                fileToDelete = directory + "/" + fileName;
            }
            return getFileContent(fileToDelete);
        }
        return null;
    }

    public void downloadFile(File localFile, String remoteFileName) throws MediaServerException {
        FTPClient ftp = null;
        InputStream inputStream = null;
        FileOutputStream fileOutputStream = null;

        if (localFile != null && remoteFileName != null) {
            int attempt = 1;
            long startTime = 0;
            long endTime = 0;
            while (true) {
                startTime=0;
                try {
                    ftp = getFTPClientUntilSuccess();
                    startTime = System.currentTimeMillis();
                    inputStream = ftp.retrieveFileStream(remoteFileName);
                    int replyCode = ftp.getReplyCode();
                    endTime = System.currentTimeMillis();
                    if (replyCode == FtpServerReturnCodes.CODE_550_REQUESTED_ACTION_NOT_TAKEN_FILE_NOT_FOUND) {
                        LOGGER.info(MessageFormat.format(
                                IntegrationConstant.OP_SUCCESS, name,
                                IntegrationConstant.OP_DOWNLOAD,
                                IntegrationConstant.NOT_EXISTS, attempt,
                                (endTime - startTime), remoteFileName));
                        break;
                    } else {
                        if (inputStream == null) {
                            LOGGER.info(MessageFormat.format(
                                    IntegrationConstant.OP_FAILED, name,
                                    IntegrationConstant.OP_DOWNLOAD,
                                    IntegrationConstant.NOT_EXISTS, attempt,
                                    (endTime - startTime), remoteFileName,
                                    ftp.getReplyString()));
                            break;
                        }
                    }
                    fileOutputStream = new FileOutputStream(localFile);
                    IOUtils.copy(inputStream, fileOutputStream);
                    break;
                } catch (Exception exception) {
                    if (startTime == 0) {
                        startTime = endTime;
                    }
                    String logMessage = MessageFormat.format(
                            IntegrationConstant.OP_FAILED, name,
                            IntegrationConstant.OP_DOWNLOAD,
                            IntegrationConstant.STATUS_ERRORED, attempt,
                            (endTime - startTime), remoteFileName,
                            ftp.getReplyString());
                    handleException(ftp, exception, logMessage);
                }
                attempt++;
                if (attempt > 10) {
                    IOUtils.closeQuietly(inputStream);
                    IOUtils.closeQuietly(fileOutputStream);
                    String logMessage = MessageFormat.format(
                            IntegrationConstant.OP_MAX_ATTEMPTS, name,
                            IntegrationConstant.OP_DOWNLOAD, remoteFileName);
                    throw new MediaServerException(logMessage);
                }
            }
            IOUtils.closeQuietly(inputStream);
            IOUtils.closeQuietly(fileOutputStream);
        }
    }

    public void downloadFile(File localFile, String remoteDir, String fileName)
            throws MediaServerException {
        if (localFile != null && remoteDir!= null && fileName != null) {
            String fileToDownload = null;
            if (remoteDir.endsWith("/")) {
                fileToDownload = remoteDir + fileName;
            } else {
                fileToDownload = remoteDir + "/" + fileName;
            }
            downloadFile(localFile, fileToDownload);
        }
    }

    /**
     * Creates the directory.
     * 
     * @param remoteDirectory the remote directory
     * @throws MediaServerException the media server exception
     */
    public void createDirectory(String remoteDirectory) throws MediaServerException {
        FTPClient ftp = null;

        int attempt = 1;
        boolean created;
        long startTime;
        while (true) {
            startTime=0;
            try {
                ftp = getFTPClientUntilSuccess();
                startTime = System.currentTimeMillis();
                created = ftp.makeDirectory(remoteDirectory);
                ftp.noop();
                long endTime = System.currentTimeMillis();
                if (created) {
                    LOGGER.info(MessageFormat.format(
                            IntegrationConstant.OP_SUCCESS, name,
                            IntegrationConstant.OP_MKDIR,
                            IntegrationConstant.STATUS_OK, attempt,
                            (endTime - startTime), remoteDirectory));
                    break;
                } else {
                    int replyCode = ftp.getReplyCode();
                    if (replyCode == FtpServerReturnCodes.CODE_200_COMMAND_OKAY) {
                        LOGGER.info(MessageFormat.format(
                                IntegrationConstant.OP_SUCCESS, name,
                                IntegrationConstant.OP_MKDIR,
                                IntegrationConstant.STATUS_EXISTS, attempt,
                                (endTime - startTime), remoteDirectory));
                        break;
                    } else {
                        LOGGER.warn(MessageFormat.format(
                                IntegrationConstant.OP_FAILED, name,
                                IntegrationConstant.OP_MKDIR,
                                IntegrationConstant.STATUS_FAILED, attempt,
                                (endTime - startTime), remoteDirectory,
                                ftp.getReplyString()));
                    }
                }
            } catch (Exception exception) {
                long endTime = System.currentTimeMillis();
                if (startTime == 0) {
                    startTime = endTime;
                }
                String logMessage = MessageFormat.format(
                        IntegrationConstant.OP_FAILED, name,
                        IntegrationConstant.OP_MKDIR,
                        IntegrationConstant.STATUS_ERRORED, attempt,
                        (endTime - startTime), remoteDirectory,
                        ftp.getReplyString());
                handleException(ftp, exception, logMessage);
            } finally {
                attempt++;
            }
        }
    }

    /**
     * Store file.
     * 
     * @param remoteFileName the remote file name
     * @param file the file
     * @return true, if successful
     * @throws MediaServerException the media server exception
     */
    public boolean storeFile(String remoteFileName, File file)
            throws MediaServerException {
        boolean stored = false;
        FTPClient ftp = null;
        FileInputStream fileInputStream = null;

        int attempt = 1;
        try {
            fileInputStream = new FileInputStream(file);
            long startTime;
            while (true) {
                startTime=0;
                try {
                    ftp = getFTPClientUntilSuccess();
                    startTime = System.currentTimeMillis();
                    if (interrupted) {
                        LOGGER.info(name + " File " + remoteFileName + " has been interrupted during STORE. Will delete and STORE again.");
                        deleteFile(remoteFileName);
                        interrupted = false;
                        fileInputStream = new FileInputStream(file);
                    }
                    stored = ftp.storeFile(remoteFileName, fileInputStream);
                    int replyCode = ftp.getReplyCode();
                    ftp.noop();
                    long endTime = System.currentTimeMillis();

                    if (stored) {
                        LOGGER.info(MessageFormat.format(
                                IntegrationConstant.OP_SUCCESS, name,
                                IntegrationConstant.OP_STORE,
                                IntegrationConstant.STATUS_OK, attempt,
                                (endTime - startTime), remoteFileName));
                        break;
                    } else if (replyCode == FtpServerReturnCodes.CODE_200_COMMAND_OKAY) {
                        LOGGER.info(MessageFormat.format(
                                IntegrationConstant.OP_SUCCESS, name,
                                IntegrationConstant.OP_STORE,
                                IntegrationConstant.STATUS_OK, attempt,
                                (endTime - startTime), remoteFileName));
                        break;
                    } else {
                        LOGGER.warn(MessageFormat.format(
                                IntegrationConstant.OP_FAILED, name,
                                IntegrationConstant.OP_STORE,
                                IntegrationConstant.STATUS_FAILED, attempt,
                                (endTime - startTime), remoteFileName,
                                ftp.getReplyString()));
                    }
                } catch (Exception exception) {
                    LOGGER.fatal(exception);
                    long endTime = System.currentTimeMillis();
                    if (startTime == 0) {
                        startTime = endTime;
                    }
                    String logMessage = MessageFormat.format(
                            IntegrationConstant.OP_FAILED, name,
                            IntegrationConstant.OP_STORE,
                            IntegrationConstant.STATUS_ERRORED, attempt,
                            (endTime - startTime), remoteFileName,
                            ftp.getReplyString());
                    handleException(ftp, exception, logMessage);
                    interrupted = true;
                } finally {
                    if (interrupted) {
                        IOUtils.closeQuietly(fileInputStream);
                    }
                    attempt++;
                    if (attempt > 10) {
                        IOUtils.closeQuietly(fileInputStream);
                        String logMessage = MessageFormat.format(
                                IntegrationConstant.OP_MAX_ATTEMPTS, name,
                                IntegrationConstant.OP_STORE, remoteFileName);
                        throw new MediaServerException(logMessage);
                    }
                }
            }
            IOUtils.closeQuietly(fileInputStream);
        } catch (FileNotFoundException fileNotFoundException) {
            throw new MediaServerException("Missing File: " + file);
        } finally {
            IOUtils.closeQuietly(fileInputStream);
        }
        return stored;
    }

    /**
     * Delete directory.
     * 
     * @param remoteDirectory the remote directory
     * @throws MediaServerException the media server exception
     */
    public void deleteDirectory(String remoteDirectory) throws MediaServerException {
        FTPClient ftp = null;

        int attempt = 1;
        if (remoteDirectory != null) {
            long startTime = 0;
            while (true) {
                try {
                    ftp = getFTPClientUntilSuccess();
                    startTime = System.currentTimeMillis();
                    boolean removeDirectory = ftp.removeDirectory(remoteDirectory);
                    ftp.noop();
                    long endTime = System.currentTimeMillis();
                    if (removeDirectory) {
                        LOGGER.info(MessageFormat.format(
                                IntegrationConstant.OP_SUCCESS, name,
                                IntegrationConstant.OP_REMDIR,
                                IntegrationConstant.STATUS_OK, attempt,
                                (endTime - startTime), remoteDirectory));
                        break;
                    } else {
                        int replyCode = ftp.getReplyCode();
                        if (replyCode == FtpServerReturnCodes.CODE_200_COMMAND_OKAY) {
                            LOGGER.info(MessageFormat.format(
                                    IntegrationConstant.OP_FAILED, name,
                                    IntegrationConstant.OP_REMDIR,
                                    IntegrationConstant.NOT_EXISTS, attempt,
                                    (endTime - startTime), remoteDirectory,
                                    ftp.getReplyString()));
                            break;
                        } else {
                            LOGGER.warn(MessageFormat.format(
                                    IntegrationConstant.OP_FAILED, name,
                                    IntegrationConstant.OP_REMDIR,
                                    IntegrationConstant.STATUS_FAILED, attempt,
                                    (endTime - startTime), remoteDirectory,
                                    ftp.getReplyString()));
                        }
                    }
                } catch (Exception exception) {
                    long endTime = System.currentTimeMillis();
                    if (startTime == 0) {
                        startTime = endTime;
                    }
                    String logMessage = MessageFormat.format(
                            IntegrationConstant.OP_FAILED, name,
                            IntegrationConstant.OP_REMDIR,
                            IntegrationConstant.STATUS_ERRORED, attempt,
                            (endTime - startTime), remoteDirectory,
                            ftp.getReplyString());
                    handleException(ftp, exception, logMessage);
                } finally {
                    attempt++;
                }
            }
        }
    }

    /**
     * Delete file.
     * 
     * @param remoteFileName the remote file name
     * @throws MediaServerException the media server exception
     */
    public void deleteFile(String remoteFileName) throws MediaServerException {
        FTPClient ftp = null;

        int attempt = 1;
        if (remoteFileName != null) {
            long startTime = 0;
            while (true) {
                try {
                    ftp = getFTPClientUntilSuccess();
                    startTime = System.currentTimeMillis();
                    boolean deleteFile = ftp.deleteFile(remoteFileName);
                    ftp.noop();
                    long endTime = System.currentTimeMillis();
                    if (deleteFile) {
                        LOGGER.info(MessageFormat.format(
                                IntegrationConstant.OP_SUCCESS, name,
                                IntegrationConstant.OP_DELETE,
                                IntegrationConstant.STATUS_OK, attempt,
                                (endTime - startTime), remoteFileName));
                        break;
                    } else {
                        LOGGER.warn(MessageFormat.format(
                                IntegrationConstant.OP_FAILED, name,
                                IntegrationConstant.OP_DELETE,
                                IntegrationConstant.STATUS_FAILED, attempt,
                                (endTime - startTime), remoteFileName,
                                ftp.getReplyString()));
                    }
                } catch (Exception exception) {
                    long endTime = System.currentTimeMillis();
                    if (startTime == 0) {
                        startTime = endTime;
                    }
                    String logMessage = MessageFormat.format(
                            IntegrationConstant.OP_FAILED, name,
                            IntegrationConstant.OP_DELETE,
                            IntegrationConstant.STATUS_ERRORED, attempt,
                            (endTime - startTime), remoteFileName,
                            ftp.getReplyString());
                    handleException(ftp, exception, logMessage);
                } finally {
                    attempt++;
                }
            }
        }
    }

    /**
     * Delete file.
     * 
     * @param directory the directory
     * @param fileName the file name
     * @throws MediaServerException the media server exception
     */
    public void deleteFile(String directory, String fileName) throws MediaServerException {
        if (directory != null && fileName != null) {
            String fileToDelete = null;
            if (directory.endsWith("/")) {
                fileToDelete = directory + fileName;
            } else {
                fileToDelete = directory + "/" + fileName;
            }
            deleteFile(fileToDelete);
        }
    }

    /**
     * Disconnect.
     */
    public void disconnect() {
        disconnect(ftpClient);
    }

    /**
     * Disconnect.
     * 
     * @param ftp the ftp
     */
    public void disconnect(FTPClient ftp) {
        if (ftp != null && ftp.isConnected()) {
            try {
                ftp.logout();
            } catch (Exception exception) {
                LOGGER.error(exception.getMessage());
            }
            try {
                ftp.disconnect();
            } catch (Exception exception) {
                LOGGER.error(exception.getMessage());
            }
            LOGGER.info(name + " Disconnected");
        }
    }

    /**
     * Handle exception.
     * 
     * @param ftp the ftp
     * @param exception the exception
     * @param logMessage the log message
     */
    private void handleException(FTPClient ftp, Exception exception, String logMessage) {
        LOGGER.warn(name + " " + logMessage);
        if (exception != null) {
            LOGGER.warn(name + " Handling exception: " + exception.getMessage());
            String message = exception.getMessage();
            if (message.contains("Connection closed without indication")
                    || message.contains("Software caused connection abort")) {
                disconnect(ftp);
            }
        }
    }

}
