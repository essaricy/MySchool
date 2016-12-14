package com.myschool.notification.dto;

import java.io.Serializable;
import java.util.List;

/**
 * The Class NotificationConfig.
 */
public class NotificationConfig implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The notifications. */
    private List<Notification> notifications;

    /**
     * Gets the notifications.
     *
     * @return the notifications
     */
    public List<Notification> getNotifications() {
        return notifications;
    }

    /**
     * Sets the notifications.
     *
     * @param notifications the notifications to set
     */
    public void setNotifications(List<Notification> notifications) {
        this.notifications = notifications;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        final int maxLen = 10;
        StringBuilder builder = new StringBuilder();
        builder.append("NotificationConfig [notifications=")
                .append(notifications != null ? notifications.subList(0,
                        Math.min(notifications.size(), maxLen)) : null)
                .append("]");
        return builder.toString();
    }

}
