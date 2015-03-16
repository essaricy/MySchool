package com.myschool.exim.assembler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import org.json.JSONObject;

import com.myschool.exim.constants.UploadStatus;
import com.myschool.exim.dto.UploadFileTrackerDto;
import com.myschool.exim.dto.UploadRecordTrackerDto;
import com.myschool.exim.dto.UploadTrackerDto;

/**
 * The Class UploadTrackerDataAssembler.
 */
public class UploadTrackerDataAssembler {

    /**
     * Creates the upload tracker.
     * 
     * @param resultSet the result set
     * @return the upload tracker dto
     * @throws SQLException the sQL exception
     */
    public static UploadTrackerDto createUploadTracker(ResultSet resultSet) throws SQLException {
        UploadTrackerDto uploadTracker = new UploadTrackerDto();
        uploadTracker.setTrackerId(resultSet.getInt("TRACKER_ID"));
        uploadTracker.setUploadStatus(UploadStatus.getUploadStatus(resultSet.getInt("UPLOAD_STATUS_ID")));
        uploadTracker.setUploadTimestamp(resultSet.getTimestamp("UPLOAD_TIMESTAMP"));
        uploadTracker.setProcessedFiles(resultSet.getInt("PROCESSED_FILES"));
        uploadTracker.setTotalFiles(resultSet.getInt("TOTAL_FILES"));
        uploadTracker.setUploadedBy(resultSet.getInt("UPLOADED_BY"));
        return uploadTracker;
    }

    /**
     * Creates the upload file tracker.
     * 
     * @param resultSet the result set
     * @return the upload file tracker dto
     * @throws SQLException the sQL exception
     */
    public static UploadFileTrackerDto createUploadFileTracker(
            ResultSet resultSet) throws SQLException {
        UploadFileTrackerDto uploadFileTracker = new UploadFileTrackerDto();
        uploadFileTracker.setUploadFileId(resultSet.getInt("UPLOAD_FILE_ID"));
        uploadFileTracker.setUploadType(resultSet.getString("UPLOAD_TYPE"));
        uploadFileTracker.setFileName(resultSet.getString("FILE_NAME"));
        uploadFileTracker.setUploadStatus(UploadStatus.getUploadStatus(resultSet.getInt("UPLOAD_STATUS_ID")));
        uploadFileTracker.setUploadStartTime(resultSet.getTimestamp("UPLOAD_START_TIMESTAMP"));
        uploadFileTracker.setUploadEndTime(resultSet.getTimestamp("UPLOAD_END_TIMESTAMP"));
        uploadFileTracker.setProcessedRecords(resultSet.getInt("PROCESSED_RECORDS"));
        uploadFileTracker.setTotalRecords(resultSet.getInt("TOTAL_RECORDS"));
        uploadFileTracker.setRemarks(resultSet.getString("REMARKS"));
        return uploadFileTracker;
    }

    /**
     * Creates the upload record tracker.
     * 
     * @param resultSet the result set
     * @return the upload record tracker dto
     * @throws SQLException the sQL exception
     */
    public static UploadRecordTrackerDto createUploadRecordTracker(
            ResultSet resultSet) throws SQLException {
        UploadRecordTrackerDto uploadRecordTracker = new UploadRecordTrackerDto();
        uploadRecordTracker.setUploadRecordId(resultSet.getInt("UPLOAD_RECORD_ID"));
        uploadRecordTracker.setRecordNumber(resultSet.getInt("RECORD_NUMBER"));
        uploadRecordTracker.setRecordData(resultSet.getString("RECORD_DATA"));
        uploadRecordTracker.setUploadStatus(UploadStatus.getUploadStatus(resultSet.getInt("UPLOAD_STATUS_ID")));
        uploadRecordTracker.setRemarks(resultSet.getString("REMARKS"));
        return uploadRecordTracker;
    }

    /**
     * Gets the upload tracker.
     * 
     * @param uploadTracker the upload tracker
     * @return the upload tracker
     */
    public static JSONObject getUploadTracker(UploadTrackerDto uploadTracker) {
        JSONObject jsonObject = null;
        if (uploadTracker != null) {
            jsonObject = new JSONObject();
            jsonObject.put("ProcessedFiles", uploadTracker.getProcessedFiles());
            jsonObject.put("TotalFiles", uploadTracker.getTotalFiles());
            jsonObject.put("TrackerId", uploadTracker.getTrackerId());
            jsonObject.put("UploadedBy", uploadTracker.getUploadedBy());
            jsonObject.put("UploadStatus", uploadTracker.getUploadStatus().toString());
            Timestamp uploadTimestamp = uploadTracker.getUploadTimestamp();
            if (uploadTimestamp == null) {
                jsonObject.put("UploadTimestamp", null);
            } else {
                jsonObject.put("UploadTimestamp", uploadTimestamp.toString());
            }
        }
        return jsonObject;
    }

    /**
     * Gets the upload file tracker.
     * 
     * @param uploadFileTracker the upload file tracker
     * @return the upload file tracker
     */
    public static JSONObject getUploadFileTracker(
            UploadFileTrackerDto uploadFileTracker) {
        JSONObject jsonObject = null;
        if (uploadFileTracker != null) {
            jsonObject = new JSONObject();
            jsonObject.put("ProcessedRecords", uploadFileTracker.getProcessedRecords());
            jsonObject.put("TotalRecords", uploadFileTracker.getTotalRecords());
            jsonObject.put("UploadFileId", uploadFileTracker.getUploadFileId());
            jsonObject.put("FileName", uploadFileTracker.getFileName());
            jsonObject.put("Remarks", uploadFileTracker.getRemarks());
            Timestamp uploadStartTime = uploadFileTracker.getUploadStartTime();
            if (uploadStartTime == null) {
                jsonObject.put("UploadStartTime", null);
            } else {
                jsonObject.put("UploadStartTime", uploadStartTime.toString());
            }
            Timestamp uploadEndTime = uploadFileTracker.getUploadEndTime();
            if (uploadEndTime == null) {
                jsonObject.put("UploadEndTime", null);
            } else {
                jsonObject.put("UploadEndTime", uploadEndTime.toString());
            }
            jsonObject.put("UploadStatus", uploadFileTracker.getUploadStatus().toString());
            jsonObject.put("UploadType", uploadFileTracker.getUploadType());
        }
        return jsonObject;
    }

    /**
     * Gets the upload record tracker.
     * 
     * @param uploadRecordTracker the upload record tracker
     * @return the upload record tracker
     */
    public static JSONObject getUploadRecordTracker(
            UploadRecordTrackerDto uploadRecordTracker) {
        JSONObject jsonObject = null;
        if (uploadRecordTracker != null) {
            jsonObject = new JSONObject();
            jsonObject.put("UploadRecordId", uploadRecordTracker.getUploadRecordId());
            jsonObject.put("RecordNumber", uploadRecordTracker.getRecordNumber());
            jsonObject.put("UploadStatus", uploadRecordTracker.getUploadStatus().toString());
            String recordData = uploadRecordTracker.getRecordData();
            if (recordData == null) {
                jsonObject.put("RecordData", null);
            } else {
                jsonObject.put("RecordData", recordData.toString());
            }
            String remarks = uploadRecordTracker.getRemarks();
            if (remarks == null) {
                jsonObject.put("Remarks", null);
            } else {
                jsonObject.put("Remarks", remarks.toString());
            }
        }
        return jsonObject;
    }

}
