package com.myschool.notification.dto;

import java.io.Serializable;
import java.util.List;

import com.myschool.notification.constants.NotificationEndPoint;
import com.myschool.notification.constants.NotificationMode;
import com.myschool.notification.constants.NotificationStatus;
import com.myschool.notification.constants.NotificationType;

/**
 * The Class NotificationDto.
 */
public class NotificationDto implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The notification id. */
    private int notificationId;

    /** The notification end point. */
    private NotificationEndPoint notificationEndPoint;

    /** The notification mode. */
    private NotificationMode notificationMode;

    /** The notification type. */
    private NotificationType notificationType;

    /** The notification status. */
    private NotificationStatus notificationStatus;

    /** The notifying ids. */
    private List<Integer> notifyingIds;

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
    public NotificationEndPoint getNotificationEndPoint() {
        return notificationEndPoint;
    }

    /**
     * Sets the notification end point.
     *
     * @param notificationEndPoint the new notification end point
     */
    public void setNotificationEndPoint(NotificationEndPoint notificationEndPoint) {
        this.notificationEndPoint = notificationEndPoint;
    }

    /**
     * Gets the notification mode.
     *
     * @return the notification mode
     */
    public NotificationMode getNotificationMode() {
        return notificationMode;
    }

    /**
     * Sets the notification mode.
     *
     * @param notificationMode the new notification mode
     */
    public void setNotificationMode(NotificationMode notificationMode) {
        this.notificationMode = notificationMode;
    }

    /**
     * Gets the notification type.
     *
     * @return the notification type
     */
    public NotificationType getNotificationType() {
        return notificationType;
    }

    /**
     * Sets the notification type.
     *
     * @param notificationType the new notification type
     */
    public void setNotificationType(NotificationType notificationType) {
        this.notificationType = notificationType;
    }

    /**
     * Gets the notifying ids.
     *
     * @return the notifying ids
     */
    public List<Integer> getNotifyingIds() {
        return notifyingIds;
    }

    /**
     * Sets the notifying ids.
     *
     * @param notifyingIds the new notifying ids
     */
    public void setNotifyingIds(List<Integer> notifyingIds) {
        this.notifyingIds = notifyingIds;
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
        retValue.append("NotificationDto ( ")
            .append(super.toString()).append(SEPARATOR)
            .append("notificationEndPoint = ").append(this.notificationEndPoint).append(SEPARATOR)
            .append("notificationId = ").append(this.notificationId).append(SEPARATOR)
            .append("notificationMode = ").append(this.notificationMode).append(SEPARATOR)
            .append("notificationStatus = ").append(this.notificationStatus).append(SEPARATOR)
            .append("notificationType = ").append(this.notificationType).append(SEPARATOR)
            .append("notifyingIds = ").append(this.notifyingIds).append(SEPARATOR)
            .append(" )\n");
        return retValue.toString();
    }

}
