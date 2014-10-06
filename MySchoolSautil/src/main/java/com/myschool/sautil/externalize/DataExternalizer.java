package com.myschool.sautil.externalize;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.myschool.common.exception.ApplicationException;
import com.myschool.common.exception.FileSystemException;
import com.myschool.common.exception.ServiceException;
import com.myschool.common.util.DateUtil;
import com.myschool.common.util.PropertiesUtil;
import com.myschool.common.util.StringUtil;
import com.myschool.exim.constants.EximPolicy;
import com.myschool.exim.constants.UploadStatus;
import com.myschool.exim.dto.ExportStatusDto;
import com.myschool.exim.dto.ImportRecordStatusDto;
import com.myschool.exim.dto.ImportStatusDto;
import com.myschool.exim.dto.UploadFileTrackerDto;
import com.myschool.exim.dto.UploadTrackerDto;
import com.myschool.exim.exception.EximException;
import com.myschool.exim.service.EximService;
import com.myschool.exim.service.UploadService;
import com.myschool.infra.database.factory.DatabaseAgentFactory;

/**
 * The Class DataExternalizer.
 */
@Component
public class DataExternalizer implements Externalize {

    /** The Constant MULTI_FILE_DELIMITER. */
    private static final String MULTI_FILE_DELIMITER = ",";

    /** The Constant LOGGER. */
    private static final Logger LOGGER = Logger.getLogger(DataExternalizer.class);

    /** The exim service. */
    @Autowired
    private EximService eximService;

    /** The database agent factory. */
    @Autowired
    private DatabaseAgentFactory databaseAgentFactory;

    /** The upload service. */
    @Autowired
    private UploadService uploadService;

    /** The upload tracker. */
    private UploadTrackerDto uploadTracker;

    /* (non-Javadoc)
     * @see com.myschool.sautil.Externalize#importData(java.io.File)
     */
    @Override
    public void importData(File fromDirectory, File mappingFile, Integer trackerId) throws ApplicationException {

        ImportStatusDto importStatus = null;
        List<ImportStatusDto> importStatusDtos = new ArrayList<ImportStatusDto>();
        try {
            int totalNumberOfFiles = getTotalNumberOfFiles(mappingFile);
            LOGGER.info("totalNumberOfFiles = " + totalNumberOfFiles);
            LOGGER.info("Starting processing of " + totalNumberOfFiles + " files.");
            if (trackerId != null) {
                // update the upload tracker status to 'Started'.  and set total number of files.
                uploadTracker = getUploadTracker(trackerId);
                uploadTracker.setTotalFiles(totalNumberOfFiles);
                uploadTracker.setUploadStatus(UploadStatus.STARTED);
                updateUploadTracker();
            }
            Properties mappingProperties = PropertiesUtil.loadProperties(mappingFile);
            List<EximPolicy> eximPolicies = EximPolicy.getEximPoliciesByRankOrder();
            for (EximPolicy eximPolicy : eximPolicies) {
                LOGGER.info("CURRENT POLICY: " + eximPolicy);
                String importFileNames = mappingProperties.getProperty(eximPolicy.toString());
                if (!StringUtil.isNullOrBlank(importFileNames)) {
                    if (importFileNames.indexOf(MULTI_FILE_DELIMITER) != -1) {
                        String[] splitStringArray = importFileNames.split(MULTI_FILE_DELIMITER);
                        for (String splitString : splitStringArray) {
                            importStatus = importData(fromDirectory, splitString, eximPolicy);
                            importStatusDtos.add(importStatus);
                        }
                    } else {
                        importStatus = importData(fromDirectory, importFileNames, eximPolicy);
                        importStatusDtos.add(importStatus);
                    }
                }
            }
            if (uploadTracker != null) {
                // update the upload tracker status to 4 'Completed'.
                uploadTracker.setUploadStatus(UploadStatus.COMPLETED);
            }
        } catch (FileSystemException fileSystemException) {
            if (uploadTracker != null) {
                // update the upload tracker status to 4 'Failed'.
                uploadTracker.setUploadStatus(UploadStatus.FAILED);
            }
            throw new ApplicationException(fileSystemException.getMessage(), fileSystemException);
        } catch (ServiceException serviceException) {
            if (uploadTracker != null) {
                // update the upload tracker status to 4 'Failed'.
                uploadTracker.setUploadStatus(UploadStatus.FAILED);
            }
            throw new ApplicationException(serviceException.getMessage(), serviceException);
        } finally {
            try {
                updateUploadTracker();
            } catch (ServiceException serviceException) {
                throw new ApplicationException(serviceException.getMessage(), serviceException);
            }
        }
        //System.out.println(importStatusDtos);
    }

    /**
     * Gets the total number of files.
     * 
     * @param mappingFile the mapping file
     * @return the total number of files
     * @throws FileSystemException the file system exception
     */
    private int getTotalNumberOfFiles(File mappingFile) throws FileSystemException {
        int totalNumberOfFiles = 0;
        Properties mappingProperties = PropertiesUtil.loadProperties(mappingFile);
        if (mappingProperties != null &&!mappingProperties.isEmpty()) {
            List<EximPolicy> eximPolicies = EximPolicy.getEximPoliciesByRankOrder();
            for (EximPolicy eximPolicy : eximPolicies) {
                String importFileNames = mappingProperties.getProperty(eximPolicy.toString());
                if (!StringUtil.isNullOrBlank(importFileNames)) {
                    if (importFileNames.indexOf(MULTI_FILE_DELIMITER) != -1) {
                        String[] splitStringArray = importFileNames.split(MULTI_FILE_DELIMITER);
                        if (splitStringArray!= null) {
                            totalNumberOfFiles += splitStringArray.length;
                        }
                    } else {
                        totalNumberOfFiles++;
                    }
                }
            }
        }
        return totalNumberOfFiles;
    }

    /**
     * Import data.
     * 
     * @param fromDirectory the from directory
     * @param importFileName the import file name
     * @param eximPolicy the exim policy
     * @return the import status dto
     * @throws ServiceException the service exception
     */
    private ImportStatusDto importData(File fromDirectory,
            String importFileName, EximPolicy eximPolicy)
            throws ServiceException {
        ImportStatusDto importStatus = null;
        try {
            LOGGER.info("Importing File: " + importFileName);
            importStatus = importFile(fromDirectory, importFileName, eximPolicy);
        } catch (EximException eximException) {
            LOGGER.error(eximException.getMessage(), eximException);
        } finally {
            if (uploadTracker != null) {
                // update the number of processed files (successfulImports + failedImports) processed files. 
                uploadTracker.setProcessedFiles(uploadTracker.getProcessedFiles() + 1);
                updateUploadTracker();
            }
        }
        return importStatus;
    }

    /**
     * Import file.
     * 
     * @param fromDirectory the from directory
     * @param fileName the file name
     * @param eximPolicy the exim policy
     * @return the import status dto
     * @throws EximException the exim exception
     */
    private ImportStatusDto importFile(File fromDirectory, String fileName,
            EximPolicy eximPolicy) throws EximException {
        long startTime = System.currentTimeMillis();
        UploadFileTrackerDto uploadFileTracker = null;
        ImportStatusDto importStatusDto = null;
        try {
            File importFile = new File(fromDirectory, fileName);
            uploadFileTracker = getFileTracker(fileName);
            importStatusDto = eximService.uploadData(eximPolicy, importFile, uploadFileTracker);
            printSummary(importStatusDto, eximPolicy);
        } catch (ServiceException serviceException) {
            throw new EximException(serviceException.getMessage(), serviceException);
        } finally {
            long duration = System.currentTimeMillis() - startTime;
            LOGGER.info("Importing '" + fileName + "' has taken " + DateUtil.getReadableDuration(duration));
        }
        return importStatusDto;
    }

    /**
     * Gets the upload tracker.
     * 
     * @param trackerId the tracker id
     * @return the upload tracker
     * @throws ServiceException the service exception
     */
    private UploadTrackerDto getUploadTracker(Integer trackerId) throws ServiceException {
        return uploadService.getUploadTracker(trackerId);
    }

    /**
     * Update upload tracker.
     * 
     * @throws ServiceException the service exception
     */
    private void updateUploadTracker() throws ServiceException {
        if (uploadTracker != null) {
            uploadService.updateUploadTracker(uploadTracker.getTrackerId(), uploadTracker);
        }
    }

    /**
     * Gets the file tracker.
     * 
     * @param fileName the file name
     * @return the file tracker
     * @throws ServiceException the service exception
     */
    private UploadFileTrackerDto getFileTracker(String fileName)
            throws ServiceException {
        if (uploadTracker != null) {
            int trackerId = uploadTracker.getTrackerId();
            List<UploadFileTrackerDto> uploadFileTrackerByTracker = 
                    uploadService.getUploadFileTrackerByTracker(trackerId);
            if (uploadFileTrackerByTracker != null
                    && !uploadFileTrackerByTracker.isEmpty()) {
                for (UploadFileTrackerDto uploadFileTracker : uploadFileTrackerByTracker) {
                    String trackerFileName = uploadFileTracker.getFileName();
                    if (trackerFileName.trim().equals(fileName)) {
                        return uploadFileTracker;
                    }
                }
            }
        }
        return null;
    }

    /**
     * Prints the summary.
     *
     * @param importStatusDto the import status dto
     * @param eximPolicy the exim policy
     */
    private void printSummary(ImportStatusDto importStatusDto, EximPolicy eximPolicy) {
        String PADDING_WIDTH = "%-30s";
        LOGGER.info("########################################################################");
        if (importStatusDto == null) {
            LOGGER.info(String.format(PADDING_WIDTH, "Could not determine the status for " + eximPolicy));
        } else {
            List<ImportRecordStatusDto> importRecordStatusList = importStatusDto.getImportRecordStatusList();
            if (importRecordStatusList != null && !importRecordStatusList.isEmpty()) {
                for (ImportRecordStatusDto importRecordStatusDto : importRecordStatusList) {
                    if (importRecordStatusDto != null) {
                        LOGGER.info(importRecordStatusDto.getStatusCode()
                                + "\t" + importRecordStatusDto.getActionCode()
                                + "\t" + importRecordStatusDto.getStatusDescription());
                    }
                }
            }
        }
        LOGGER.info("########################################################################");
    }

    /* (non-Javadoc)
     * @see com.myschool.sautil.Externalize#exportData(java.io.File)
     */
    @Override
    public ExportStatusDto exportData(File toDirectory) {
        return null;
    }

    /* (non-Javadoc)
     * @see com.myschool.sautil.externalize.Externalize#exportDataToSqlScript(java.io.File)
     */
    @Override
    public boolean exportDataToSqlScript(File exportToDirectory) throws ApplicationException {
        return false;
        /*DatabaseEximAgent databaseEximAgent = databaseAgentFactory.getDatabaseDataService(DatabaseType.POSTGRES);
        try {
            return databaseEximAgent.exportDataToFileSqlScript(exportToDirectory);
        } catch (ServiceException serviceException) {
            throw new ApplicationException(serviceException.getMessage(), serviceException);
        }*/
    }

    /* (non-Javadoc)
     * @see com.myschool.sautil.externalize.Externalize#importDataFromSqlScript(java.io.File)
     */
    @Override
    public boolean importDataFromSqlScript(File importFromDirectory)
            throws ApplicationException {
                return false;
        /*DatabaseEximAgent databaseEximAgent = databaseAgentFactory.getDatabaseDataService(DatabaseType.POSTGRES);
        try {
            return databaseEximAgent.importDataFromSqlScript(importFromDirectory);
        } catch (ServiceException serviceException) {
            throw new ApplicationException(serviceException.getMessage(), serviceException);
        }*/
    }
}
