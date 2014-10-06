package com.myschool.exim.dao;

/**
 * The Class UploadDaoSql.
 */
public class UploadDaoSql {

    /**
     * Builds the insert upload tracker sql.
     * 
     * @return the string
     */
    public static String buildInsertUploadTrackerSql() {
        StringBuilder builder = new StringBuilder();
        builder.append("INSERT INTO ");
        builder.append("UPLOAD_TRACKER (");
        builder.append("TRACKER_ID, ");
        builder.append("UPLOAD_STATUS_ID, ");
        builder.append("UPLOADED_BY");
        builder.append(") VALUES (?, ?, ?)");
        return builder.toString();
    }

    /**
     * Builds the update upload tracker sql.
     * 
     * @return the string
     */
    public static String buildUpdateUploadTrackerSql() {
        StringBuilder builder = new StringBuilder();
        builder.append("UPDATE UPLOAD_TRACKER ");
        builder.append("SET UPLOAD_STATUS_ID = ?, ");
        builder.append("PROCESSED_FILES = ?, ");
        builder.append("TOTAL_FILES = ? ");
        builder.append("WHERE TRACKER_ID = ? ");
        return builder.toString();
    }

    /**
     * Builds the select upload tracker sql.
     * 
     * @param trackerId the tracker id
     * @return the string
     */
    public static String buildSelectUploadTrackerSql(int trackerId) {
        StringBuilder builder = new StringBuilder();
        builder.append("SELECT ");
        builder.append("TRACKER_ID, ");
        builder.append("UPLOAD_STATUS_ID, ");
        builder.append("UPLOAD_TIMESTAMP, ");
        builder.append("PROCESSED_FILES, ");
        builder.append("TOTAL_FILES, ");
        builder.append("UPLOADED_BY ");
        builder.append("FROM ");
        builder.append("UPLOAD_TRACKER ");
        builder.append("WHERE ");
        builder.append("TRACKER_ID=");
        builder.append(trackerId);
        return builder.toString();
    }

    /**
     * Builds the select upload trackers sql.
     * 
     * @return the string
     */
    public static String buildSelectUploadTrackersSql() {
        return buildSelectUploadTrackersSql(0);
    }

    /**
     * Builds the select upload trackers sql.
     * 
     * @param userId the user id
     * @return the string
     */
    public static String buildSelectUploadTrackersSql(int userId) {
        StringBuilder builder = new StringBuilder();
        builder.append("SELECT ");
        builder.append("TRACKER_ID, ");
        builder.append("UPLOAD_STATUS_ID, ");
        builder.append("UPLOAD_TIMESTAMP, ");
        builder.append("PROCESSED_FILES, ");
        builder.append("TOTAL_FILES, ");
        builder.append("UPLOADED_BY ");
        builder.append("FROM UPLOAD_TRACKER ");
        if (userId != 0) {
            builder.append("WHERE UPLOADED_BY=").append(userId).append(" ");
        }
        builder.append("ORDER BY UPLOAD_TIMESTAMP DESC ");
        return builder.toString();
    }

    /**
     * Builds the insert upload file tracker sql.
     * 
     * @return the string
     */
    public static String buildInsertUploadFileTrackerSql() {
        StringBuilder builder = new StringBuilder();
        builder.append("INSERT INTO ");
        builder.append("UPLOAD_FILE_TRACKER (");
        builder.append("UPLOAD_FILE_ID, ");
        builder.append("TRACKER_ID, ");
        builder.append("UPLOAD_TYPE, ");
        builder.append("FILE_NAME, ");
        builder.append("UPLOAD_STATUS_ID, ");
        builder.append("PROCESSED_RECORDS, ");
        builder.append("TOTAL_RECORDS, ");
        builder.append("REMARKS");
        builder.append(") VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
        return builder.toString();
    }

    /**
     * Builds the update upload file tracker sql.
     * 
     * @return the string
     */
    public static String buildUpdateUploadFileTrackerSql() {
        StringBuilder builder = new StringBuilder();
        builder.append("UPDATE ");
        builder.append("UPLOAD_FILE_TRACKER ");
        builder.append("SET ");
        builder.append("UPLOAD_STATUS_ID=?, ");
        builder.append("UPLOAD_START_TIMESTAMP=?, ");
        builder.append("UPLOAD_END_TIMESTAMP=?, ");
        builder.append("PROCESSED_RECORDS=?, ");
        builder.append("TOTAL_RECORDS=?, ");
        builder.append("REMARKS=? ");
        builder.append("WHERE ");
        builder.append("UPLOAD_FILE_ID=? ");
        return builder.toString();
    }

    /**
     * Builds the select upload file tracker sql.
     * 
     * @param fileTrackerId the file tracker id
     * @return the string
     */
    public static String buildSelectUploadFileTrackerSql(int fileTrackerId) {
        StringBuilder builder = new StringBuilder();
        builder.append("SELECT ");
        builder.append("UPLOAD_FILE_ID, ");
        builder.append("UPLOAD_TYPE, ");
        builder.append("FILE_NAME, ");
        builder.append("UPLOAD_STATUS_ID, ");
        builder.append("UPLOAD_START_TIMESTAMP, ");
        builder.append("UPLOAD_END_TIMESTAMP, ");
        builder.append("PROCESSED_RECORDS, ");
        builder.append("TOTAL_RECORDS, ");
        builder.append("REMARKS ");
        builder.append("FROM ");
        builder.append("UPLOAD_FILE_TRACKER ");
        builder.append("WHERE ");
        builder.append("UPLOAD_FILE_ID=");
        builder.append(fileTrackerId);
        return builder.toString();
    }

    /**
     * Builds the select upload file trackers sql.
     * 
     * @param trackerId the tracker id
     * @return the string
     */
    public static String buildSelectUploadFileTrackersSql(int trackerId) {
        StringBuilder builder = new StringBuilder();
        builder.append("SELECT ");
        builder.append("UPLOAD_FILE_ID, ");
        builder.append("UPLOAD_TYPE, ");
        builder.append("FILE_NAME, ");
        builder.append("UPLOAD_STATUS_ID, ");
        builder.append("UPLOAD_START_TIMESTAMP, ");
        builder.append("UPLOAD_END_TIMESTAMP, ");
        builder.append("PROCESSED_RECORDS, ");
        builder.append("TOTAL_RECORDS, ");
        builder.append("REMARKS ");
        builder.append("FROM ");
        builder.append("UPLOAD_FILE_TRACKER ");
        builder.append("WHERE ");
        builder.append("TRACKER_ID=");
        builder.append(trackerId);
        builder.append(" ORDER BY UPLOAD_FILE_ID ASC ");
        return builder.toString();
    }

    /**
     * Builds the insert upload record tracker sql.
     * 
     * @return the string
     */
    public static String buildInsertUploadRecordTrackerSql() {
        StringBuilder builder = new StringBuilder();
        builder.append("INSERT INTO ");
        builder.append("UPLOAD_RECORD_TRACKER (");
        builder.append("UPLOAD_RECORD_ID, ");
        builder.append("UPLOAD_FILE_ID, ");
        builder.append("RECORD_NUMBER, ");
        builder.append("RECORD_DATA, ");
        builder.append("UPLOAD_STATUS_ID, " );
        builder.append("REMARKS");
        builder.append(") VALUES (?, ?, ?, ?, ?, ?)");
        return builder.toString();
    }

    /**
     * Builds the update upload record tracker sql.
     * 
     * @return the string
     */
    public static String buildUpdateUploadRecordTrackerSql() {
        StringBuilder builder = new StringBuilder();
        builder.append("UPDATE ");
        builder.append("UPLOAD_RECORD_TRACKER ");
        builder.append("SET ");
        builder.append("UPLOAD_STATUS_ID=?, ");
        builder.append("REMARKS=? ");
        builder.append("WHERE ");
        builder.append("UPLOAD_RECORD_ID=? ");
        return builder.toString();
    }

    /**
     * Builds the select upload record tracker sql.
     * 
     * @param recordTrackerId the record tracker id
     * @return the string
     */
    public static String buildSelectUploadRecordTrackerSql(int recordTrackerId) {
        StringBuilder builder = new StringBuilder();
        builder.append("SELECT ");
        builder.append("UPLOAD_RECORD_ID, ");
        builder.append("RECORD_NUMBER, ");
        builder.append("RECORD_DATA, ");
        builder.append("UPLOAD_STATUS_ID, ");
        builder.append("REMARKS ");
        builder.append("FROM ");
        builder.append("UPLOAD_RECORD_TRACKER ");
        builder.append("WHERE ");
        builder.append("UPLOAD_RECORD_ID=");
        builder.append(recordTrackerId);
        return builder.toString();
    }

    /**
     * Builds the select upload record trackers sql.
     * 
     * @param fileTrackerId the file tracker id
     * @return the string
     */
    public static String buildSelectUploadRecordTrackersSql(int fileTrackerId) {
        StringBuilder builder = new StringBuilder();
        builder.append("SELECT ");
        builder.append("UPLOAD_RECORD_ID, ");
        builder.append("RECORD_NUMBER, ");
        builder.append("RECORD_DATA, ");
        builder.append("UPLOAD_STATUS_ID, ");
        builder.append("REMARKS ");
        builder.append("FROM ");
        builder.append("UPLOAD_RECORD_TRACKER ");
        builder.append("WHERE ");
        builder.append("UPLOAD_FILE_ID=");
        builder.append(fileTrackerId);
        builder.append("ORDER BY RECORD_NUMBER ASC ");
        return builder.toString();
    }

}
