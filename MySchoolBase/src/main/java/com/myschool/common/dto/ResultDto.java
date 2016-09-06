package com.myschool.common.dto;

import java.io.Serializable;

/**
 * The Class ResultDto.
 */
public class ResultDto implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The Constant SUCCESS. */
    public static final boolean SUCCESS = true;

    /** The Constant FAILURE. */
    public static final boolean FAILURE = false;

    /** The successful. */
    private boolean successful;

    /** The status message. */
    private String statusMessage;

    /** The reference number. */
    private String referenceNumber;

    private Object reference;

    /**
     * Checks if is successful.
     *
     * @return true, if is successful
     */
    public boolean isSuccessful() {
        return successful;
    }

    /**
     * Sets the successful.
     *
     * @param successful the new successful
     */
    public void setSuccessful(boolean successful) {
        this.successful = successful;
    }

    /**
     * Gets the status message.
     *
     * @return the status message
     */
    public String getStatusMessage() {
        return statusMessage;
    }

    /**
     * Sets the status message.
     *
     * @param statusMessage the new status message
     */
    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }

    /**
     * Gets the reference number.
     * 
     * @return the reference number
     */
    public String getReferenceNumber() {
        return referenceNumber;
    }

    /**
     * Sets the reference number.
     * 
     * @param referenceNumber the new reference number
     */
    public void setReferenceNumber(String referenceNumber) {
        this.referenceNumber = referenceNumber;
    }

    /**
     * @return the reference
     */
    public Object getReference() {
        return reference;
    }

    /**
     * @param reference the reference to set
     */
    public void setReference(Object reference) {
        this.reference = reference;
    }

    /**
     * To XML string.
     *
     * @return the string
     */
    public String toXml() {
        StringBuilder builder = new StringBuilder();
        builder.append("<result>");
        builder.append("<successful>").append(successful).append("</successful>");
        builder.append("<statusMessage>").append(statusMessage).append("</statusMessage>");
        builder.append("<referenceNumber>").append(referenceNumber).append("</referenceNumber>");
        builder.append("</result>");
        return builder.toString();
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("ResultDto [successful=").append(successful)
                .append(", statusMessage=").append(statusMessage)
                .append(", referenceNumber=").append(referenceNumber)
                .append(", reference=").append(reference).append("]");
        return builder.toString();
    }

}
