package com.myschool.exim.service;

import java.io.File;
import java.util.List;

import com.myschool.common.exception.ServiceException;
import com.myschool.exim.dto.UploadFileTrackerDto;
import com.myschool.exim.dto.UploadRecordTrackerDto;
import com.myschool.exim.dto.UploadTrackerDto;

/**
 * The Interface UploadService.
 */
public interface UploadService {

    /**
     * Creates the upload tracker.
     * 
     * @param userId the user id
     * @return the int
     * @throws ServiceException the service exception
     */
    int createUploadTracker(int userId) throws ServiceException;

    /**
     * Update upload tracker.
     * 
     * @param trackerId the tracker id
     * @param uploadTrackerDto the upload tracker dto
     * @return true, if successful
     * @throws ServiceException the service exception
     */
    boolean updateUploadTracker(int trackerId, UploadTrackerDto uploadTrackerDto)
            throws ServiceException;

    /**
     * Gets the upload tracker.
     * 
     * @param trackerId the tracker id
     * @return the upload tracker
     * @throws ServiceException the service exception
     */
    UploadTrackerDto getUploadTracker(int trackerId) throws ServiceException;

    /**
     * Gets the upload trackers.
     * 
     * @return the upload trackers
     * @throws ServiceException the service exception
     */
    List<UploadTrackerDto> getUploadTrackers() throws ServiceException;

    /**
     * Gets the upload trackers.
     * 
     * @param userId the user id
     * @return the upload trackers
     * @throws ServiceException the service exception
     */
    List<UploadTrackerDto> getUploadTrackers(int userId) throws ServiceException;

    /**
     * Creates the upload file tracker.
     * 
     * @param trackerId the tracker id
     * @param uploadFileTrackerDto the upload file tracker dto
     * @return the int
     * @throws ServiceException the service exception
     */
    int createUploadFileTracker(int trackerId,
            UploadFileTrackerDto uploadFileTrackerDto) throws ServiceException;

    /**
     * Update upload tracker.
     * 
     * @param fileTrackerId the file tracker id
     * @param uploadFileTrackerDto the upload file tracker dto
     * @return true, if successful
     * @throws ServiceException the service exception
     */
    boolean updateUploadFileTracker(int fileTrackerId,
            UploadFileTrackerDto uploadFileTrackerDto) throws ServiceException;

    /**
     * Gets the upload file tracker.
     * 
     * @param fileTrackerId the file tracker id
     * @return the upload file tracker
     * @throws ServiceException the service exception
     */
    UploadFileTrackerDto getUploadFileTracker(int fileTrackerId)
            throws ServiceException;

    /**
     * Gets the upload file tracker by tracker.
     * 
     * @param trackerId the tracker id
     * @return the upload file tracker by tracker
     * @throws ServiceException the service exception
     */
    List<UploadFileTrackerDto> getUploadFileTrackerByTracker(int trackerId)
            throws ServiceException;

    /**
     * Creates the upload record tracker.
     * 
     * @param fileTrackerId the file tracker id
     * @param uploadRecordTrackerDto the upload record tracker dto
     * @return the int
     * @throws ServiceException the service exception
     */
    int createUploadRecordTracker(int fileTrackerId,
            UploadRecordTrackerDto uploadRecordTrackerDto)
            throws ServiceException;

    /**
     * Update upload record tracker.
     * 
     * @param recordTrackerId the record tracker id
     * @param uploadRecordTrackerDto the upload record tracker dto
     * @return true, if successful
     * @throws ServiceException the service exception
     */
    boolean updateUploadRecordTracker(int recordTrackerId,
            UploadRecordTrackerDto uploadRecordTrackerDto)
            throws ServiceException;

    /**
     * Gets the upload record tracker.
     * 
     * @param recordTrackerId the record tracker id
     * @return the upload record tracker
     * @throws ServiceException the service exception
     */
    UploadRecordTrackerDto getUploadRecordTracker(int recordTrackerId)
            throws ServiceException;

    /**
     * Gets the upload record tracker by file tracker.
     * 
     * @param fileTrackerId the file tracker id
     * @return the upload record tracker by file tracker
     * @throws ServiceException the service exception
     */
    List<UploadRecordTrackerDto> getUploadRecordTrackerByFileTracker(int fileTrackerId)
            throws ServiceException;

    /**
     * Start upload data process.
     * 
     * @param trackerId the tracker id
     * @throws ServiceException the service exception
     */
    void startUploadDataProcess(int trackerId) throws ServiceException;

    /**
     * Gets the template.
     * 
     * @param importKey the import key
     * @return the template
     * @throws ServiceException the service exception
     */
    File getTemplate(String importKey) throws ServiceException;

    /**
     * Creates the temp image.
     * 
     * @param module the module
     * @param tempFile the temp file
     * @return the file
     * @throws ServiceException the service exception
     */
    File createTempImage(String module, File tempFile) throws ServiceException;

}
