package com.myschool.notification.dto;

import java.io.Serializable;

/**
 * The Class NotificationCriteriaDto.
 */
public class NotificationCriteriaDto implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The end point. */
    private String endPoint;

    /** The mode. */
    private String mode;

    /** The type. */
    private String type;

    /** The status. */
    private String status;

    /** The request date min. */
    private String requestDateMin;

    /** The request date max. */
    private String requestDateMax;

    /**
     * Gets the end point.
     * 
     * @return the end point
     */
    public String getEndPoint() {
        return endPoint;
    }

    /**
     * Sets the end point.
     * 
     * @param endPoint the new end point
     */
    public void setEndPoint(String endPoint) {
        this.endPoint = endPoint;
    }

    /**
     * Gets the mode.
     * 
     * @return the mode
     */
    public String getMode() {
        return mode;
    }

    /**
     * Sets the mode.
     * 
     * @param mode the new mode
     */
    public void setMode(String mode) {
        this.mode = mode;
    }

    /**
     * Gets the type.
     * 
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * Sets the type.
     * 
     * @param type the new type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Gets the status.
     * 
     * @return the status
     */
    public String getStatus() {
        return status;
    }

    /**
     * Sets the status.
     * 
     * @param status the new status
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * Gets the request date min.
     * 
     * @return the request date min
     */
    public String getRequestDateMin() {
        return requestDateMin;
    }

    /**
     * Sets the request date min.
     * 
     * @param requestDateMin the new request date min
     */
    public void setRequestDateMin(String requestDateMin) {
        this.requestDateMin = requestDateMin;
    }

    /**
     * Gets the request date max.
     * 
     * @return the request date max
     */
    public String getRequestDateMax() {
        return requestDateMax;
    }

    /**
     * Sets the request date max.
     * 
     * @param requestDateMax the new request date max
     */
    public void setRequestDateMax(String requestDateMax) {
        this.requestDateMax = requestDateMax;
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
        retValue.append("NotificationCriteriaDto ( ")
            .append(super.toString()).append(SEPARATOR)
            .append("endPoint = ").append(this.endPoint).append(SEPARATOR)
            .append("mode = ").append(this.mode).append(SEPARATOR)
            .append("type = ").append(this.type).append(SEPARATOR)
            .append("status = ").append(this.status).append(SEPARATOR)
            .append("requestDateMin = ").append(this.requestDateMin).append(SEPARATOR)
            .append("requestDateMax = ").append(this.requestDateMax).append(SEPARATOR)
            .append(" )\n");
        return retValue.toString();
    }

}
