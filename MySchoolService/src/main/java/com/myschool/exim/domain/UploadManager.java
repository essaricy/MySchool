package com.myschool.exim.domain;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.myschool.common.exception.DaoException;
import com.myschool.common.exception.DataException;
import com.myschool.common.exception.FileSystemException;
import com.myschool.common.exception.InsufficientInputException;
import com.myschool.common.util.PropertiesUtil;
import com.myschool.common.util.StringUtil;
import com.myschool.exim.constants.EximPolicy;
import com.myschool.exim.dao.UploadDao;
import com.myschool.exim.dto.UploadFileTrackerDto;
import com.myschool.exim.dto.UploadRecordTrackerDto;
import com.myschool.exim.dto.UploadTrackerDto;
import com.myschool.file.constant.FileExtension;
import com.myschool.file.util.FileUtil;
import com.myschool.infra.application.constants.CommandName;
import com.myschool.infra.application.constants.ExternalizeAction;
import com.myschool.infra.application.constants.ExternalizeDataFormat;
import com.myschool.infra.filesystem.agent.ImportTemplatesFileSystem;
import com.myschool.infra.filesystem.agent.TempFileSystem;
import com.myschool.infra.middleware.agent.MiddlewareStandaloneBridge;
import com.myschool.infra.middleware.exception.MessageException;
import com.myschool.user.constants.UserType;

/**
 * The Class UploadManager.
 */
@Component
public class UploadManager {

    /** The upload dao. */
    @Autowired
    private UploadDao uploadDao;

    /** The import templates file system. */
    @Autowired
    private ImportTemplatesFileSystem importTemplatesFileSystem;

    /** The temp file system. */
    @Autowired
    private TempFileSystem tempFileSystem;

    /** The middleware standalone bridge. */
    @Autowired
    private MiddlewareStandaloneBridge middlewareStandaloneBridge;

    /**
     * Creates the upload tracker.
     * 
     * @param userId the user id
     * @return the int
     * @throws DataException the data exception
     */
    public int createUploadTracker(int userId) throws DataException {
        int trackerId = 0;
        FileWriter fileWriter = null;
        try {
            // Create an upload tracker in the database
            trackerId = uploadDao.createUploadTracker(userId);
            // Create an upload point in the file system
            File uploadTrackerFile = FileUtil.createDirectory(
                    tempFileSystem.getUploadDirectory(), String.valueOf(trackerId));
            // create an empty properties file.
            Properties properties = new Properties();
            File propertiesFile = new File(uploadTrackerFile, String.valueOf(trackerId)
                    + FileUtil.FILE_EXTENSION_SEPARATOR
                    + FileExtension.PROPERTIES.getFileExtension());
            fileWriter = new FileWriter(propertiesFile);
            properties.store(fileWriter, null);
        } catch (DaoException daoException) {
            throw new DataException(daoException.getMessage(), daoException);
        } catch (FileSystemException fileSystemException) {
            throw new DataException(fileSystemException.getMessage(), fileSystemException);
        } catch (IOException ioException) {
            throw new DataException(ioException.getMessage(), ioException);
        } finally {
            try {
                if (fileWriter != null) {
                    fileWriter.close();
                }
            } catch (IOException ioException) {
                throw new DataException(ioException.getMessage(), ioException);
            }
        }
        return trackerId;
    }

    /**
     * Update upload tracker.
     * 
     * @param trackerId the tracker id
     * @param uploadTrackerDto the upload tracker dto
     * @return true, if successful
     * @throws DataException the data exception
     */
    public boolean updateUploadTracker(int trackerId,
            UploadTrackerDto uploadTrackerDto) throws DataException {
        try {
            return uploadDao.updateUploadTracker(trackerId, uploadTrackerDto);
        } catch (DaoException daoException) {
            throw new DataException(daoException.getMessage(), daoException);
        }
    }

    /**
     * Gets the upload tracker.
     * 
     * @param trackerId the tracker id
     * @return the upload tracker
     * @throws DataException the data exception
     */
    public UploadTrackerDto getUploadTracker(int trackerId) throws DataException {
        try {
            return uploadDao.getUploadTracker(trackerId);
        } catch (DaoException daoException) {
            throw new DataException(daoException.getMessage(), daoException);
        }
    }

    /**
     * Gets the upload trackers.
     * 
     * @return the upload trackers
     * @throws DataException the data exception
     */
    public List<UploadTrackerDto> getUploadTrackers() throws DataException {
        try {
            return uploadDao.getUploadTrackers();
        } catch (DaoException daoException) {
            throw new DataException(daoException.getMessage(), daoException);
        }
    }

    /**
     * Gets the upload trackers.
     * 
     * @param userId the user id
     * @return the upload trackers
     * @throws DataException the data exception
     */
    public List<UploadTrackerDto> getUploadTrackers(int userId) throws DataException {
        try {
            return uploadDao.getUploadTrackers(userId);
        } catch (DaoException daoException) {
            throw new DataException(daoException.getMessage(), daoException);
        }
    }

    /**
     * Creates the upload file tracker.
     * 
     * @param trackerId the tracker id
     * @param uploadFileTracker the upload file tracker
     * @return the int
     * @throws DataException the data exception
     */
    public int createUploadFileTracker(int trackerId,
            UploadFileTrackerDto uploadFileTracker) throws DataException {
        int fileTrackerId = 0;
        FileWriter fileWriter = null;
        try {
            String uploadType = uploadFileTracker.getUploadType();
            System.out.println("uploadType " + uploadType);
            EximPolicy policy = EximPolicy.getPolicy(uploadType);
            System.out.println("policy " + policy);
            if (policy == null) {
                throw new DataException("Unknown File type " + uploadType);
            }
            fileTrackerId = uploadDao.createUploadFileTracker(trackerId, uploadFileTracker);
            // put the file in respective upload tracker directory.
            File trackerDirectory = tempFileSystem.getUploadFile(String.valueOf(trackerId));
            /*File uploadFile = uploadFileTracker.getUploadFile();
            FileUtil.moveFile(uploadFile, new File(trackerDirectory, uploadFile.getName()));*/
            // add property to the tracker's properties file.
            File propertiesFile = new File(trackerDirectory, String.valueOf(trackerId)
                    + FileUtil.FILE_EXTENSION_SEPARATOR
                    + FileExtension.PROPERTIES.getFileExtension());
            Properties properties = PropertiesUtil.loadProperties(propertiesFile);
            properties.put(uploadType, uploadFileTracker.getFileName());
            fileWriter = new FileWriter(propertiesFile);
            properties.store(fileWriter, null);
        } catch (DaoException daoException) {
            throw new DataException(daoException.getMessage(), daoException);
        } catch (FileSystemException fileSystemException) {
            throw new DataException(fileSystemException.getMessage(), fileSystemException);
        } catch (IOException ioException) {
            throw new DataException(ioException.getMessage(), ioException);
        } finally {
            try {
                if (fileWriter != null) {
                    fileWriter.close();
                }
            } catch (IOException ioException) {
                throw new DataException(ioException.getMessage(), ioException);
            }
        }
        return fileTrackerId;
    }

    /**
     * Update upload file tracker.
     * 
     * @param fileTrackerId the file tracker id
     * @param uploadFileTrackerDto the upload file tracker dto
     * @return true, if successful
     * @throws DataException the data exception
     */
    public boolean updateUploadFileTracker(int fileTrackerId,
            UploadFileTrackerDto uploadFileTrackerDto) throws DataException {
        try {
            return uploadDao.updateUploadFileTracker(fileTrackerId, uploadFileTrackerDto);
        } catch (DaoException daoException) {
            throw new DataException(daoException.getMessage(), daoException);
        }
    }

    /**
     * Gets the upload file tracker.
     * 
     * @param fileTrackerId the file tracker id
     * @return the upload file tracker
     * @throws DataException the data exception
     */
    public UploadFileTrackerDto getUploadFileTracker(int fileTrackerId) throws DataException {
        try {
            return uploadDao.getUploadFileTracker(fileTrackerId);
        } catch (DaoException daoException) {
            throw new DataException(daoException.getMessage(), daoException);
        }
    }

    /**
     * Creates the upload record tracker.
     * 
     * @param fileTrackerId the file tracker id
     * @param uploadRecordTrackerDto the upload record tracker dto
     * @return the int
     * @throws DataException the data exception
     */
    public int createUploadRecordTracker(int fileTrackerId,
            UploadRecordTrackerDto uploadRecordTrackerDto) throws DataException {
        try {
            return uploadDao.createUploadRecordTracker(fileTrackerId, uploadRecordTrackerDto);
        } catch (DaoException daoException) {
            throw new DataException(daoException.getMessage(), daoException);
        }
    }

    /**
     * Update upload record tracker.
     * 
     * @param recordTrackerId the record tracker id
     * @param uploadRecordTrackerDto the upload record tracker dto
     * @return true, if successful
     * @throws DataException the data exception
     */
    public boolean updateUploadRecordTracker(int recordTrackerId,
            UploadRecordTrackerDto uploadRecordTrackerDto) throws DataException {
        try {
            return uploadDao.updateUploadRecordTracker(recordTrackerId, uploadRecordTrackerDto);
        } catch (DaoException daoException) {
            throw new DataException(daoException.getMessage(), daoException);
        }
    }

    /**
     * Gets the upload record tracker.
     * 
     * @param recordTrackerId the record tracker id
     * @return the upload record tracker
     * @throws DataException the data exception
     */
    public UploadRecordTrackerDto getUploadRecordTracker(int recordTrackerId) throws DataException {
        try {
            return uploadDao.getUploadRecordTracker(recordTrackerId);
        } catch (DaoException daoException) {
            throw new DataException(daoException.getMessage(), daoException);
        }
    }

    /**
     * Gets the upload file tracker by tracker.
     * 
     * @param trackerId the tracker id
     * @return the upload file tracker by tracker
     * @throws DataException the data exception
     */
    public List<UploadFileTrackerDto> getUploadFileTrackerByTracker(
            int trackerId) throws DataException {
        try {
            return uploadDao.getUploadFileTrackerByTracker(trackerId);
        } catch (DaoException daoException) {
            throw new DataException(daoException.getMessage(), daoException);
        }
    }

    /**
     * Gets the upload record tracker by file tracker.
     * 
     * @param fileTrackerId the file tracker id
     * @return the upload record tracker by file tracker
     * @throws DataException the data exception
     */
    public List<UploadRecordTrackerDto> getUploadRecordTrackerByFileTracker(
            int fileTrackerId) throws DataException {
        try {
            return uploadDao.getUploadRecordTrackerByFileTracker(fileTrackerId);
        } catch (DaoException daoException) {
            throw new DataException(daoException.getMessage(), daoException);
        }
    }

    /**
     * Start upload data process.
     * 
     * @param trackerId the tracker id
     * @throws DataException the data exception
     */
    public void startUploadDataProcess(int trackerId) throws DataException {
        try {
            File trackerDirectory = tempFileSystem.getUploadFile(String.valueOf(trackerId));
            // Prepare data required to run externalize data program.
            File propertiesFile = new File(trackerDirectory,
                    trackerId + FileUtil.FILE_EXTENSION_SEPARATOR + FileExtension.PROPERTIES.getFileExtension());
            Map<String, Object> commandArguements = new HashMap<String, Object>();
            commandArguements.put(MiddlewareStandaloneBridge.PARAM_NAME_ACTION, ExternalizeAction.LOAD);
            commandArguements.put(MiddlewareStandaloneBridge.PARAM_NAME_EXT_DIR, trackerDirectory.getAbsolutePath());
            commandArguements.put(MiddlewareStandaloneBridge.PARAM_NAME_EXT_CFG, propertiesFile.getAbsolutePath());
            commandArguements.put(MiddlewareStandaloneBridge.PARAM_NAME_DATA_FORMAT, ExternalizeDataFormat.EXCEL);
            commandArguements.put(MiddlewareStandaloneBridge.OPTION_TRACKER_ID, trackerId);
            middlewareStandaloneBridge.sendStandAloneCommand(
                    CommandName.EXTERNALIZE_DATA, commandArguements);
        } catch (FileSystemException fileSystemException) {
            throw new DataException(fileSystemException.getMessage(), fileSystemException);
        } catch (MessageException messageException) {
            throw new DataException(messageException.getMessage(), messageException);
        }
    }

    /**
     * Gets the template.
     * 
     * @param importKey the import key
     * @return the template
     * @throws DataException the data exception
     */
    public File getTemplate(String importKey) throws DataException {
        File templateFile = null;
        try {
            if (StringUtil.isNullOrBlank(importKey)) {
                throw new DataException("Import key is not specified.");
            }
            EximPolicy policy = EximPolicy.getPolicy(importKey.trim());
            templateFile = importTemplatesFileSystem.getImportTemplate(policy);
        } catch (FileSystemException fileSystemException) {
            throw new DataException(fileSystemException.getMessage(), fileSystemException);
        }
        return templateFile;
    }

    /**
     * @param module the module
     * @return the temp image
     * @throws DataException the data exception
     */
    public File createTempImage(String module, File tempFile) throws DataException {
        File returnFile = null;
        try {
            if (StringUtil.isNullOrBlank(module.trim())) {
                throw new InsufficientInputException("Specify the upload name.");
            }
            UserType userType = UserType.get(module);
            if (userType == null) {
                throw new InsufficientInputException("Specify the upload name.");
            }
            if (!FileExtension.isImage(FileUtil.getExtension(tempFile.getName()))) {
                throw new DataException(tempFile + " files are not accepted for upload.");
            }
            // create a temp file and get the file name.
            if (userType == UserType.EMPLOYEE) {
                returnFile = tempFileSystem.createEmployeeImage(tempFile);
            } else if (userType == UserType.STUDENT) {
                returnFile = tempFileSystem.createStudentImage(tempFile);
            }
        } catch (FileSystemException fileSystemException) {
            throw new DataException(fileSystemException.getMessage(), fileSystemException);
        }
        return returnFile;
    }

}
