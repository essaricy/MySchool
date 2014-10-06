package com.myschool.exim.dao;

import java.util.List;

import com.myschool.common.exception.DaoException;
import com.myschool.exim.dto.UploadFileTrackerDto;
import com.myschool.exim.dto.UploadRecordTrackerDto;
import com.myschool.exim.dto.UploadTrackerDto;

/**
 * The Interface UploadDao.
 */
public interface UploadDao {

    /**
     * Creates the upload tracker.
     * 
     * @param userId the user id
     * @return the int
     * @throws DaoException the dao exception
     */
    int createUploadTracker(int userId) throws DaoException;

    /**
     * Update upload tracker.
     * 
     * @param trackerId the tracker id
     * @param uploadTracker the upload tracker
     * @return true, if successful
     * @throws DaoException the dao exception
     */
    boolean updateUploadTracker(int trackerId, UploadTrackerDto uploadTracker)
            throws DaoException;

    /**
     * Gets the upload tracker.
     * 
     * @param trackerId the tracker id
     * @return the upload tracker
     * @throws DaoException the dao exception
     */
    UploadTrackerDto getUploadTracker(int trackerId) throws DaoException;

    /**
     * Gets the upload trackers.
     * 
     * @return the upload trackers
     * @throws DaoException the dao exception
     */
    List<UploadTrackerDto> getUploadTrackers() throws DaoException;

    /**
     * Gets the upload trackers.
     * 
     * @param userId the user id
     * @return the upload trackers
     * @throws DaoException the dao exception
     */
    List<UploadTrackerDto> getUploadTrackers(int userId) throws DaoException;

    /**
     * Creates the upload file tracker.
     * 
     * @param trackerId the tracker id
     * @param uploadFileTracker the upload file tracker
     * @return the int
     * @throws DaoException the dao exception
     */
    int createUploadFileTracker(int trackerId,
            UploadFileTrackerDto uploadFileTracker) throws DaoException;

    /**
     * Update upload file tracker.
     * 
     * @param fileTrackerId the file tracker id
     * @param uploadFileTracker the upload file tracker
     * @return true, if successful
     * @throws DaoException the dao exception
     */
    boolean updateUploadFileTracker(int fileTrackerId,
            UploadFileTrackerDto uploadFileTracker) throws DaoException;

    /**
     * Gets the upload file tracker.
     * 
     * @param fileTrackerId the file tracker id
     * @return the upload file tracker
     * @throws DaoException the dao exception
     */
    UploadFileTrackerDto getUploadFileTracker(int fileTrackerId)
            throws DaoException;

    /**
     * Creates the upload record tracker.
     * 
     * @param fileTrackerId the file tracker id
     * @param uploadRecordTracker the upload record tracker
     * @return the int
     * @throws DaoException the dao exception
     */
    int createUploadRecordTracker(int fileTrackerId,
            UploadRecordTrackerDto uploadRecordTracker) throws DaoException;

    /**
     * Update upload record tracker.
     * 
     * @param recordTrackerId the record tracker id
     * @param uploadRecordTracker the upload record tracker
     * @return true, if successful
     * @throws DaoException the dao exception
     */
    boolean updateUploadRecordTracker(int recordTrackerId,
            UploadRecordTrackerDto uploadRecordTracker) throws DaoException;

    /**
     * Gets the upload record tracker.
     * 
     * @param recordTrackerId the record tracker id
     * @return the upload record tracker
     * @throws DaoException the dao exception
     */
    UploadRecordTrackerDto getUploadRecordTracker(int recordTrackerId)
            throws DaoException;

    /**
     * Gets the upload file tracker by tracker.
     * 
     * @param trackerId the tracker id
     * @return the upload file tracker by tracker
     * @throws DaoException the dao exception
     */
    List<UploadFileTrackerDto> getUploadFileTrackerByTracker(int trackerId)
            throws DaoException;

    /**
     * Gets the upload record tracker by file tracker.
     * 
     * @param fileTrackerId the file tracker id
     * @return the upload record tracker by file tracker
     * @throws DaoException the dao exception
     */
    List<UploadRecordTrackerDto> getUploadRecordTrackerByFileTracker(
            int fileTrackerId) throws DaoException;

}
