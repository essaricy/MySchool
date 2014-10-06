package com.myschool.exim.dto;

import java.io.Serializable;

import com.myschool.exim.constants.UploadStatus;

/**
 * The Class UploadRecordTrackerDto.
 */
public class UploadRecordTrackerDto implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The upload record id. */
    private int uploadRecordId;

    /** The record number. */
    private int recordNumber;

    /** The record data. */
    private String recordData;

    /** The upload status. */
    private UploadStatus uploadStatus;

    /** The remarks. */
    private String remarks;

    /**
     * Gets the upload record id.
     * 
     * @return the upload record id
     */
    public int getUploadRecordId() {
        return uploadRecordId;
    }

    /**
     * Sets the upload record id.
     * 
     * @param uploadRecordId the new upload record id
     */
    public void setUploadRecordId(int uploadRecordId) {
        this.uploadRecordId = uploadRecordId;
    }

    /**
     * Gets the record number.
     * 
     * @return the record number
     */
    public int getRecordNumber() {
        return recordNumber;
    }

    /**
     * Sets the record number.
     * 
     * @param recordNumber the new record number
     */
    public void setRecordNumber(int recordNumber) {
        this.recordNumber = recordNumber;
    }

    /**
     * Gets the record data.
     * 
     * @return the record data
     */
    public String getRecordData() {
        return recordData;
    }

    /**
     * Sets the record data.
     * 
     * @param recordData the new record data
     */
    public void setRecordData(String recordData) {
        this.recordData = recordData;
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
     * Constructs a <code>String</code> with all attributes
     * in name = value format.
     *
     * @return a <code>String</code> representation 
     * of this object.
     */
    public String toString() {
        final String SEPARATOR = ", ";
        StringBuilder retValue = new StringBuilder();
        retValue.append("UploadRecordTrackerDto ( ")
            .append(super.toString()).append(SEPARATOR)
            .append("uploadRecordId = ").append(this.uploadRecordId).append(SEPARATOR)
            .append("recordNumber = ").append(this.recordNumber).append(SEPARATOR)
            .append("recordData = ").append(this.recordData).append(SEPARATOR)
            .append("uploadStatus = ").append(this.uploadStatus).append(SEPARATOR)
            .append("remarks = ").append(this.remarks).append(SEPARATOR)
            .append(" )\n");
        return retValue.toString();
    }

}
