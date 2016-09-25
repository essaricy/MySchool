package com.myschool.captcha.dto;

/**
 * The Class CaptchaVerificationResult.
 */
public class CaptchaVerificationResult {

    /** The success. */
    private boolean success;

    /** The timestamp. */
    private String timestamp;

    /** The error code. */
    private String errorCode;

    /** The error message. */
    private String errorMessage;

    /**
     * Checks if is success.
     *
     * @return the success
     */
    public boolean isSuccess() {
        return success;
    }

    /**
     * Sets the success.
     *
     * @param success the success to set
     */
    public void setSuccess(boolean success) {
        this.success = success;
    }

    /**
     * Gets the timestamp.
     *
     * @return the timestamp
     */
    public String getTimestamp() {
        return timestamp;
    }

    /**
     * Sets the timestamp.
     *
     * @param timestamp the timestamp to set
     */
    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    /**
     * Gets the error code.
     *
     * @return the errorCode
     */
    public String getErrorCode() {
        return errorCode;
    }

    /**
     * Sets the error code.
     *
     * @param errorCode the errorCode to set
     */
    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    /**
     * Gets the error message.
     *
     * @return the errorMessage
     */
    public String getErrorMessage() {
        return errorMessage;
    }

    /**
     * Sets the error message.
     *
     * @param errorMessage the errorMessage to set
     */
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("CaptchaVerificationResult [success=").append(success)
                .append(", timestamp=").append(timestamp).append(", errorCode=")
                .append(errorCode).append(", errorMessage=")
                .append(errorMessage).append("]");
        return builder.toString();
    }

}
