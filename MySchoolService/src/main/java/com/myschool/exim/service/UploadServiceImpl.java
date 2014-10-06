package com.myschool.exim.service;

import java.io.File;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myschool.common.exception.DataException;
import com.myschool.common.exception.ServiceException;
import com.myschool.exim.domain.UploadManager;
import com.myschool.exim.dto.UploadFileTrackerDto;
import com.myschool.exim.dto.UploadRecordTrackerDto;
import com.myschool.exim.dto.UploadTrackerDto;

/**
 * The Class UploadServiceImpl.
 */
@Service
public class UploadServiceImpl implements UploadService {

    /** The upload manager. */
    @Autowired
    private UploadManager uploadManager;

    /* (non-Javadoc)
     * @see com.myschool.exim.service.UploadService#createUploadTracker(int)
     */
    @Override
    public int createUploadTracker(int userId) throws ServiceException {
        try {
            return uploadManager.createUploadTracker(userId);
        } catch (DataException dataException) {
            throw new ServiceException(dataException.getMessage(), dataException);
        }
    }

    /* (non-Javadoc)
     * @see com.myschool.exim.service.UploadService#updateUploadTracker(int, com.myschool.exim.dto.UploadTrackerDto)
     */
    @Override
    public boolean updateUploadTracker(int trackerId,
            UploadTrackerDto uploadTrackerDto) throws ServiceException {
        try {
            return uploadManager.updateUploadTracker(trackerId, uploadTrackerDto);
        } catch (DataException dataException) {
            throw new ServiceException(dataException.getMessage(), dataException);
        }
    }

    /* (non-Javadoc)
     * @see com.myschool.exim.service.UploadService#getUploadTracker(int)
     */
    @Override
    public UploadTrackerDto getUploadTracker(int trackerId)
            throws ServiceException {
        try {
            return uploadManager.getUploadTracker(trackerId);
        } catch (DataException dataException) {
            throw new ServiceException(dataException.getMessage(), dataException);
        }
    }

    /* (non-Javadoc)
     * @see com.myschool.exim.service.UploadService#getUploadTrackers()
     */
    @Override
    public List<UploadTrackerDto> getUploadTrackers()
            throws ServiceException {
        try {
            return uploadManager.getUploadTrackers();
        } catch (DataException dataException) {
            throw new ServiceException(dataException.getMessage(), dataException);
        }
    }

    /* (non-Javadoc)
     * @see com.myschool.exim.service.UploadService#getUploadTrackers(int)
     */
    @Override
    public List<UploadTrackerDto> getUploadTrackers(int userId)
            throws ServiceException {
        try {
            return uploadManager.getUploadTrackers(userId);
        } catch (DataException dataException) {
            throw new ServiceException(dataException.getMessage(), dataException);
        }
    }

    /* (non-Javadoc)
     * @see com.myschool.exim.service.UploadService#createUploadFileTracker(int, com.myschool.exim.dto.UploadFileTrackerDto)
     */
    @Override
    public int createUploadFileTracker(int trackerId,
            UploadFileTrackerDto uploadFileTrackerDto) throws ServiceException {
        try {
            return uploadManager.createUploadFileTracker(trackerId, uploadFileTrackerDto);
        } catch (DataException dataException) {
            throw new ServiceException(dataException.getMessage(), dataException);
        }
    }

    /* (non-Javadoc)
     * @see com.myschool.exim.service.UploadService#updateUploadFileTracker(int, com.myschool.exim.dto.UploadFileTrackerDto)
     */
    @Override
    public boolean updateUploadFileTracker(int fileTrackerId,
            UploadFileTrackerDto uploadFileTrackerDto) throws ServiceException {
        try {
            return uploadManager.updateUploadFileTracker(fileTrackerId, uploadFileTrackerDto);
        } catch (DataException dataException) {
            throw new ServiceException(dataException.getMessage(), dataException);
        }
    }

    /* (non-Javadoc)
     * @see com.myschool.exim.service.UploadService#getUploadFileTracker(int)
     */
    @Override
    public UploadFileTrackerDto getUploadFileTracker(int fileTrackerId)
            throws ServiceException {
        try {
            return uploadManager.getUploadFileTracker(fileTrackerId);
        } catch (DataException dataException) {
            throw new ServiceException(dataException.getMessage(), dataException);
        }
    }

    /* (non-Javadoc)
     * @see com.myschool.exim.service.UploadService#createUploadRecordTracker(int, com.myschool.exim.dto.UploadRecordTrackerDto)
     */
    @Override
    public int createUploadRecordTracker(int fileTrackerId,
            UploadRecordTrackerDto uploadRecordTrackerDto)
            throws ServiceException {
        try {
            return uploadManager.createUploadRecordTracker(fileTrackerId, uploadRecordTrackerDto);
        } catch (DataException dataException) {
            throw new ServiceException(dataException.getMessage(), dataException);
        }
    }

    /* (non-Javadoc)
     * @see com.myschool.exim.service.UploadService#updateUploadRecordTracker(int, com.myschool.exim.dto.UploadRecordTrackerDto)
     */
    @Override
    public boolean updateUploadRecordTracker(int recordTrackerId,
            UploadRecordTrackerDto uploadRecordTrackerDto)
            throws ServiceException {
        try {
            return uploadManager.updateUploadRecordTracker(recordTrackerId, uploadRecordTrackerDto);
        } catch (DataException dataException) {
            throw new ServiceException(dataException.getMessage(), dataException);
        }
    }

    /* (non-Javadoc)
     * @see com.myschool.exim.service.UploadService#getUploadRecordTracker(int)
     */
    @Override
    public UploadRecordTrackerDto getUploadRecordTracker(int recordTrackerId)
            throws ServiceException {
        try {
            return uploadManager.getUploadRecordTracker(recordTrackerId);
        } catch (DataException dataException) {
            throw new ServiceException(dataException.getMessage(), dataException);
        }
    }

    /* (non-Javadoc)
     * @see com.myschool.exim.service.UploadService#getUploadFileTrackerByTracker(int)
     */
    @Override
    public List<UploadFileTrackerDto> getUploadFileTrackerByTracker(
            int trackerId) throws ServiceException {
        try {
            return uploadManager.getUploadFileTrackerByTracker(trackerId);
        } catch (DataException dataException) {
            throw new ServiceException(dataException.getMessage(), dataException);
        }
    }

    /* (non-Javadoc)
     * @see com.myschool.exim.service.UploadService#getUploadRecordTrackerByFileTracker(int)
     */
    @Override
    public List<UploadRecordTrackerDto> getUploadRecordTrackerByFileTracker(
            int fileTrackerId) throws ServiceException {
        try {
            return uploadManager.getUploadRecordTrackerByFileTracker(fileTrackerId);
        } catch (DataException dataException) {
            throw new ServiceException(dataException.getMessage(), dataException);
        }
    }

    /* (non-Javadoc)
     * @see com.myschool.exim.service.UploadService#startUploadDataProcess(int)
     */
    @Override
    public void startUploadDataProcess(int trackerId) throws ServiceException {
        try {
            uploadManager.startUploadDataProcess(trackerId);
        } catch (DataException dataException) {
            throw new ServiceException(dataException.getMessage(), dataException);
        }
    }

    /* (non-Javadoc)
     * @see com.myschool.exim.service.UploadService#getTemplate(java.lang.String)
     */
    @Override
    public File getTemplate(String importKey) throws ServiceException {
        try {
            return uploadManager.getTemplate(importKey);
        } catch (DataException dataException) {
            throw new ServiceException(dataException.getMessage(), dataException);
        }
    }

    @Override
    public File createTempImage(String module, File tempFile) throws ServiceException {
        try {
            return uploadManager.createTempImage(module, tempFile);
        } catch (DataException dataException) {
            throw new ServiceException(dataException.getMessage(), dataException);
        }
    }

}
