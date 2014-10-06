package com.myschool.exim.dto;

import java.io.File;
import java.io.Serializable;
import java.sql.Timestamp;

import com.myschool.exim.constants.UploadStatus;

/**
 * The Class UploadFileTrackerDto.
 */
public class UploadFileTrackerDto implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The upload file id. */
    private int uploadFileId;
    
    /** The upload type. */
    private String uploadType;
    
    /** The file name. */
    private String fileName;
    
    /** The upload status. */
    private UploadStatus uploadStatus;
    
    /** The upload start time. */
    private Timestamp uploadStartTime;
    
    /** The upload end time. */
    private Timestamp uploadEndTime;
    
    /** The processed records. */
    private int processedRecords;
    
    /** The total records. */
    private int totalRecords;
    
    /** The remarks. */
    private String remarks;

    /** The upload file. */
    private File uploadFile;

    /**
     * Gets the upload file id.
     * 
     * @return the upload file id
     */
    public int getUploadFileId() {
        return uploadFileId;
    }

    /**
     * Sets the upload file id.
     * 
     * @param uploadFileId the new upload file id
     */
    public void setUploadFileId(int uploadFileId) {
        this.uploadFileId = uploadFileId;
    }

    /**
     * Gets the upload type.
     * 
     * @return the upload type
     */
    public String getUploadType() {
        return uploadType;
    }

    /**
     * Sets the upload type.
     * 
     * @param uploadType the new upload type
     */
    public void setUploadType(String uploadType) {
        this.uploadType = uploadType;
    }

    /**
     * Gets the file name.
     * 
     * @return the file name
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * Sets the file name.
     * 
     * @param fileName the new file name
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
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
     * Gets the upload start time.
     * 
     * @return the upload start time
     */
    public Timestamp getUploadStartTime() {
        return uploadStartTime;
    }

    /**
     * Sets the upload start time.
     * 
     * @param uploadStartTime the new upload start time
     */
    public void setUploadStartTime(Timestamp uploadStartTime) {
        this.uploadStartTime = uploadStartTime;
    }

    /**
     * Gets the upload end time.
     * 
     * @return the upload end time
     */
    public Timestamp getUploadEndTime() {
        return uploadEndTime;
    }

    /**
     * Sets the upload end time.
     * 
     * @param uploadEndTime the new upload end time
     */
    public void setUploadEndTime(Timestamp uploadEndTime) {
        this.uploadEndTime = uploadEndTime;
    }

    /**
     * Gets the processed records.
     * 
     * @return the processed records
     */
    public int getProcessedRecords() {
        return processedRecords;
    }

    /**
     * Sets the processed records.
     * 
     * @param processedRecords the new processed records
     */
    public void setProcessedRecords(int processedRecords) {
        this.processedRecords = processedRecords;
    }

    /**
     * Gets the total records.
     * 
     * @return the total records
     */
    public int getTotalRecords() {
        return totalRecords;
    }

    /**
     * Sets the total records.
     * 
     * @param totalRecords the new total records
     */
    public void setTotalRecords(int totalRecords) {
        this.totalRecords = totalRecords;
    }

    /**
     * Gets the remarks.
     * 
     * @return the remarks
     */
    public String getRemarks() {
        return remarks;
    }

    /**
     * Sets the remarks.
     * 
     * @param remarks the new remarks
     */
    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    /**
     * Gets the upload file.
     * 
     * @return the upload file
     */
    public File getUploadFile() {
        return uploadFile;
    }

    /**
     * Sets the upload file.
     * 
     * @param uploadFile the new upload file
     */
    public void setUploadFile(File uploadFile) {
        this.uploadFile = uploadFile;
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
        retValue.append("UploadFileTrackerDto ( ")
            .append(super.toString()).append(SEPARATOR)
            .append("uploadFileId = ").append(this.uploadFileId).append(SEPARATOR)
            .append("uploadType = ").append(this.uploadType).append(SEPARATOR)
            .append("fileName = ").append(this.fileName).append(SEPARATOR)
            .append("uploadStatus = ").append(this.uploadStatus).append(SEPARATOR)
            .append("uploadStartTime = ").append(this.uploadStartTime).append(SEPARATOR)
            .append("uploadEndTime = ").append(this.uploadEndTime).append(SEPARATOR)
            .append("processedRecords = ").append(this.processedRecords).append(SEPARATOR)
            .append("totalRecords = ").append(this.totalRecords).append(SEPARATOR)
            .append("remarks = ").append(this.remarks).append(SEPARATOR)
            .append("uploadFile = ").append(this.uploadFile).append(SEPARATOR)
            .append(" )\n");
        return retValue.toString();
    }

}
