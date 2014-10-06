package com.myschool.notification.dto;

import java.io.File;
import java.io.Serializable;

import com.myschool.notification.constants.NotificationEndPoint;
import com.myschool.notification.constants.NotificationMode;
import com.myschool.notification.constants.NotificationType;

/**
 * The Class NotificationTemplateDto.
 */
public class NotificationTemplateDto implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The notification end point. */
    private NotificationEndPoint notificationEndPoint;

    /** The notification mode. */
    private NotificationMode notificationMode;

    /** The notification type. */
    private NotificationType notificationType;

    /** The template file. */
    private File templateFile;

    /** The test file. */
    private File testFile;

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
     * Gets the template file.
     *
     * @return the template file
     */
    public File getTemplateFile() {
        return templateFile;
    }

    /**
     * Sets the template file.
     *
     * @param templateFile the new template file
     */
    public void setTemplateFile(File templateFile) {
        this.templateFile = templateFile;
    }

    /**
     * Gets the test file.
     *
     * @return the test file
     */
    public File getTestFile() {
        return testFile;
    }

    /**
     * Sets the test file.
     *
     * @param testFile the new test file
     */
    public void setTestFile(File testFile) {
        this.testFile = testFile;
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
        retValue.append("NotificationTemplateDto ( ")
            .append(super.toString()).append(SEPARATOR)
            .append("notificationEndPoint = ").append(this.notificationEndPoint).append(SEPARATOR)
            .append("notificationMode = ").append(this.notificationMode).append(SEPARATOR)
            .append("notificationType = ").append(this.notificationType).append(SEPARATOR)
            .append("templateFile = ").append(this.templateFile).append(SEPARATOR)
            .append("testFile = ").append(this.testFile).append(SEPARATOR)
            .append(" )\n");
        return retValue.toString();
    }

}
