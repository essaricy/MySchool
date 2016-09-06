package com.myschool.infra.filesystem.agent;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.myschool.common.exception.FileSystemException;
import com.myschool.filesystem.dto.DirectoryDto;
import com.myschool.infra.filesystem.reader.NotificationConfigReader;
import com.myschool.notification.constants.NotificationEndPoint;
import com.myschool.notification.constants.NotificationMode;
import com.myschool.notification.constants.NotificationType;
import com.myschool.notification.dto.NotificationDto;
import com.myschool.notification.dto.NotificationTemplateDto;

/**
 * The Class NotificationTemplatesFileSystem.
 */
@Component
public class NotificationTemplatesFileSystem extends AbstractSubFileSystem {

    /** The notification config reader. */
    @Autowired
    private NotificationConfigReader notificationConfigReader;

    /** The notification templates. */
    private List<NotificationTemplateDto> notificationTemplates;

    /**
     * Load.
     *
     * @param configFile the config file
     * @param mainNotificationDirectory the main notification directory
     * @param testNotificationDirectory the test notification directory
     * @throws FileSystemException the file system exception
     */
    public void load(File configFile, DirectoryDto mainNotificationDirectory,
            DirectoryDto testNotificationDirectory)
            throws FileSystemException {
        notificationTemplates = notificationConfigReader.getNotificationTemplates(
                configFile, mainNotificationDirectory, testNotificationDirectory);
    }

    /**
     * Gets the notification templates.
     *
     * @return the notification templates
     */
    public List<NotificationTemplateDto> getNotificationTemplates() {
        return notificationTemplates;
    }

    /**
     * Gets the notification templates by end point.
     *
     * @param notificationEndPoint the notification end point
     * @return the notification templates by end point
     */
    public List<NotificationTemplateDto> getNotificationTemplatesByEndPoint(NotificationEndPoint notificationEndPoint) {
        List<NotificationTemplateDto> notificationTemplatesByEndPoint = new ArrayList<NotificationTemplateDto>();
        if (notificationEndPoint != null) {
            for (NotificationTemplateDto notificationTemplate : notificationTemplates) {
                if (notificationEndPoint == notificationTemplate.getNotificationEndPoint()) {
                    notificationTemplatesByEndPoint.add(notificationTemplate);
                }
            }
        }
        return notificationTemplatesByEndPoint;
    }

    /**
     * Gets the notification templates by end point.
     *
     * @param notificationEndPoints the notification end points
     * @return the notification templates by end point
     */
    public List<NotificationTemplateDto> getNotificationTemplatesByEndPoint(List<NotificationEndPoint> notificationEndPoints) {
        List<NotificationTemplateDto> notificationTemplatesByEndPoint = new ArrayList<NotificationTemplateDto>();
        if (notificationEndPoints != null && !notificationEndPoints.isEmpty()) {
            for (NotificationEndPoint notificationEndPoint : notificationEndPoints) {
                notificationTemplatesByEndPoint.addAll(getNotificationTemplatesByEndPoint(notificationEndPoint));
            }
        }
        return notificationTemplatesByEndPoint;
    }

    /**
     * Gets the notification templates by mode.
     *
     * @param notificationMode the notification mode
     * @return the notification templates by mode
     */
    public List<NotificationTemplateDto> getNotificationTemplatesByMode(NotificationMode notificationMode) {
        List<NotificationTemplateDto> notificationTemplatesByMode = new ArrayList<NotificationTemplateDto>();
        if (notificationMode != null) {
            for (NotificationTemplateDto notificationTemplate : notificationTemplates) {
                if (notificationMode == notificationTemplate.getNotificationMode()) {
                    notificationTemplatesByMode.add(notificationTemplate);
                }
            }
        }
        return notificationTemplatesByMode;
    }

    /**
     * Gets the notification templates by mode.
     *
     * @param notificationModes the notification modes
     * @return the notification templates by mode
     */
    public List<NotificationTemplateDto> getNotificationTemplatesByMode(List<NotificationMode> notificationModes) {
        List<NotificationTemplateDto> notificationTemplatesByMode = new ArrayList<NotificationTemplateDto>();
        if (notificationModes != null && !notificationModes.isEmpty()) {
            for (NotificationMode notificationMode : notificationModes) {
                notificationTemplatesByMode.addAll(getNotificationTemplatesByMode(notificationMode));
            }
        }
        return notificationTemplatesByMode;
    }

    /**
     * Gets the notification templates by type.
     *
     * @param notificationType the notification type
     * @return the notification templates by type
     */
    public List<NotificationTemplateDto> getNotificationTemplatesByType(NotificationType notificationType) {
        List<NotificationTemplateDto> notificationTemplatesByType = new ArrayList<NotificationTemplateDto>();
        if (notificationType != null) {
            for (NotificationTemplateDto notificationTemplate : notificationTemplates) {
                if (notificationType == notificationTemplate.getNotificationType()) {
                    notificationTemplatesByType.add(notificationTemplate);
                }
            }
        }
        return notificationTemplatesByType;
    }

    /**
     * Gets the notification templates by type.
     *
     * @param notificationTypes the notification types
     * @return the notification templates by type
     */
    public List<NotificationTemplateDto> getNotificationTemplatesByType(List<NotificationType> notificationTypes) {
        List<NotificationTemplateDto> notificationTemplatesByType = new ArrayList<NotificationTemplateDto>();
        if (notificationTypes != null && !notificationTypes.isEmpty()) {
            for (NotificationType notificationType : notificationTypes) {
                notificationTemplatesByType.addAll(getNotificationTemplatesByType(notificationType));
            }
        }
        return notificationTemplatesByType;
    }

    /**
     * Gets the notification template.
     * 
     * @param notificationEndPoint the notification end point
     * @param notificationMode the notification mode
     * @param notificationType the notification type
     * @return the notification template
     */
    public NotificationTemplateDto getNotificationTemplate(
            NotificationEndPoint notificationEndPoint,
            NotificationMode notificationMode, NotificationType notificationType) {
        NotificationTemplateDto gotNotificationTemplate = null;
        if (notificationEndPoint != null && notificationMode != null && notificationType != null) {
            for (NotificationTemplateDto notificationTemplate : notificationTemplates) {
                if (notificationEndPoint == notificationTemplate.getNotificationEndPoint()
                        && notificationMode == notificationTemplate.getNotificationMode()
                        && notificationType == notificationTemplate.getNotificationType()) {
                    gotNotificationTemplate = notificationTemplate;
                    break;
                }
            }
        }
        return gotNotificationTemplate;
    }

    /**
     * Gets the notification template.
     * 
     * @param notificationDto the notification dto
     * @return the notification template
     */
    public NotificationTemplateDto getNotificationTemplate(NotificationDto notificationDto) {
        return getNotificationTemplate(
                notificationDto.getNotificationEndPoint(),
                notificationDto.getNotificationMode(),
                notificationDto.getNotificationType());
    }

}
