package com.myschool.exim.dto;

import java.io.Serializable;
import java.sql.Timestamp;

import com.myschool.exim.constants.UploadStatus;

/**
 * The Class UploadTrackerDto.
 */
public class UploadTrackerDto implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The tracker id. */
    private int trackerId;

    /** The upload status. */
    private UploadStatus uploadStatus;

    /** The upload timestamp. */
    private Timestamp uploadTimestamp;

    /** The processed files. */
    private int processedFiles;

    /** The total files. */
    private int totalFiles;

    /** The uploaded by. */
    private int uploadedBy;

    /**
     * Gets the tracker id.
     * 
     * @return the tracker id
     */
    public int getTrackerId() {
        return trackerId;
    }

    /**
     * Sets the tracker id.
     * 
     * @param trackerId the new tracker id
     */
    public void setTrackerId(int trackerId) {
        this.trackerId = trackerId;
    }

    /**
     * Gets the upload status.
     * 
     * @return the upload status
     */
    public UploadStatus getUploadStatus() {
        return uploadStatus;
    }

    /**
     * Sets the upload status.
     * 
     * @param uploadStatus the new upload status
     */
    public void setUploadStatus(UploadStatus uploadStatus) {
        this.uploadStatus = uploadStatus;
    }

    /**
     * Gets the upload timestamp.
     * 
     * @return the upload timestamp
     */
    public Timestamp getUploadTimestamp() {
        return uploadTimestamp;
    }

    /**
     * Sets the upload timestamp.
     * 
     * @param uploadTimestamp the new upload timestamp
     */
    public void setUploadTimestamp(Timestamp uploadTimestamp) {
        this.uploadTimestamp = uploadTimestamp;
    }

    /**
     * Gets the processed files.
     * 
     * @return the processed files
     */
    public int getProcessedFiles() {
        return processedFiles;
    }

    /**
     * Sets the processed files.
     * 
     * @param processedFiles the new processed files
     */
    public void setProcessedFiles(int processedFiles) {
        this.processedFiles = processedFiles;
    }

    /**
     * Gets the total files.
     * 
     * @return the total files
     */
    public int getTotalFiles() {
        return totalFiles;
    }

    /**
     * Sets the total files.
     * 
     * @param totalFiles the new total files
     */
    public void setTotalFiles(int totalFiles) {
        this.totalFiles = totalFiles;
    }

    /**
     * Gets the uploaded by.
     * 
     * @return the uploaded by
     */
    public int getUploadedBy() {
        return uploadedBy;
    }

    /**
     * Sets the uploaded by.
     * 
     * @param uploadedBy the new uploaded by
     */
    public void setUploadedBy(int uploadedBy) {
        this.uploadedBy = uploadedBy;
    }

    /**
     * Constructs a <code>String</code> with all attributes
     * in name = value format.
     *
     * @return a <code>String</code> representation 
     * of this object.
     */
    public String toString() {
        final String SEPARATOR = ", ";
        StringBuilder retValue = new StringBuilder();
        retValue.append("UploadTrackerDto ( ")
            .append(super.toString()).append(SEPARATOR)
            .append("trackerId = ").append(this.trackerId).append(SEPARATOR)
            .append("uploadStatus = ").append(this.uploadStatus).append(SEPARATOR)
            .append("uploadTimestamp = ").append(this.uploadTimestamp).append(SEPARATOR)
            .append("processedFiles = ").append(this.processedFiles).append(SEPARATOR)
            .append("totalFiles = ").append(this.totalFiles).append(SEPARATOR)
            .append("uploadedBy = ").append(this.uploadedBy).append(SEPARATOR)
            .append(" )\n");
        return retValue.toString();
    }

}
