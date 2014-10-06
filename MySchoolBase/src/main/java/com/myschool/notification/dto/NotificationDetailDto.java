package com.myschool.notification.dto;

import java.io.Serializable;

import com.myschool.notification.constants.NotificationStatus;

/**
 * The Class NotificationDto.
 */
public class NotificationDetailDto implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The notification id. */
    private int notificationId;

    /** The notification end point. */
    private int notificationDetailSendTo;


    /** The notification status. */
    private NotificationStatus notificationStatus;


    /**
     * Gets the notification id.
     *
     * @return the notification id
     */
    public int getNotificationId() {
        return notificationId;
    }

    /**
     * Sets the notification id.
     *
     * @param notificationId the new notification id
     */
    public void setNotificationId(int notificationId) {
        this.notificationId = notificationId;
    }

    /**
     * Gets the notification end point.
     *
     * @return the notification end point
     */
    public int getNotificationDetailSendTo() {
        return notificationDetailSendTo;
    }

    /**
     * Sets the notification end point.
     *
     * @param notificationEndPoint the new notification end point
     */
    public void setNotificationDetailSendTo(int notificationDetailSendTo) {
        this.notificationDetailSendTo = notificationDetailSendTo;
    }

    /**
     * Gets the notification status.
     *
     * @return the notification status
     */
    public NotificationStatus getNotificationStatus() {
        return notificationStatus;
    }

    /**
     * Sets the notification status.
     *
     * @param notificationStatus the new notification status
     */
    public void setNotificationStatus(NotificationStatus notificationStatus) {
        this.notificationStatus = notificationStatus;
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
        retValue.append("NotificationDetailDto ( ")
            .append(super.toString()).append(SEPARATOR)
            .append("notificationDetailSendTo = ").append(this.notificationDetailSendTo).append(SEPARATOR)
            .append("notificationId = ").append(this.notificationId).append(SEPARATOR)
            .append("notificationStatus = ").append(this.notificationStatus).append(SEPARATOR)
            .append(" )\n");
        return retValue.toString();
    }

}
