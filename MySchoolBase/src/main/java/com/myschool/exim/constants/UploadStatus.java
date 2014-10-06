package com.myschool.exim.constants;

/**
 * The Enum UploadStatus.
 */
public enum UploadStatus {

    /** The NOT_STARTED. */
    NOT_STARTED(1),

    /** The STARTED. */
    STARTED(2),

    /** The COMPLETED. */
    COMPLETED(3),

    /** The FAILED. */
    FAILED(4);

    /** The status id. */
    private int statusId;

    /**
     * Instantiates a new upload status.
     * 
     * @param statusId the status id
     */
    private UploadStatus(int statusId) {
        this.statusId = statusId;
    }

    /**
     * Gets the status id.
     * 
     * @return the status id
     */
    public int getStatusId() {
        return statusId;
    }

    /**
     * Gets the upload status.
     * 
     * @param statusId the status id
     * @return the upload status
     */
    public static UploadStatus getUploadStatus(int statusId) {
        for (UploadStatus uploadStatus: values()) {
            if (uploadStatus.getStatusId() == statusId) {
                return uploadStatus;
            }
        }
        return null;
    }

}
