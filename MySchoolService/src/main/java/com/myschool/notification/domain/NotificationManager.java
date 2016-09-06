/*
 * 
 */
package com.myschool.notification.domain;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.xml.transform.TransformerException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.myschool.application.constants.Device;
import com.myschool.common.exception.DaoException;
import com.myschool.common.exception.DataException;
import com.myschool.common.exception.FileSystemException;
import com.myschool.common.util.XslUtil;
import com.myschool.file.constant.FileExtension;
import com.myschool.infra.filesystem.agent.DeviceTemplatesFileSystem;
import com.myschool.infra.filesystem.agent.NotificationTemplatesFileSystem;
import com.myschool.infra.filesystem.agent.TempFileSystem;
import com.myschool.infra.filesystem.util.FileUtil;
import com.myschool.notification.assembler.NotificationDataAssembler;
import com.myschool.notification.constants.NotificationEndPoint;
import com.myschool.notification.constants.NotificationMode;
import com.myschool.notification.constants.NotificationStatus;
import com.myschool.notification.constants.NotificationType;
import com.myschool.notification.dao.NotificationDao;
import com.myschool.notification.dto.NotificationCriteriaDto;
import com.myschool.notification.dto.NotificationDetailDto;
import com.myschool.notification.dto.NotificationDto;
import com.myschool.notification.dto.NotificationTemplateDto;

/**
 * The Class NotificationManager.
 */
@Component
public class NotificationManager {

    /** The notification templates file system. */
    @Autowired
    private NotificationTemplatesFileSystem notificationTemplatesFileSystem;

    /** The device templates file system. */
    @Autowired
    private DeviceTemplatesFileSystem deviceTemplatesFileSystem;

    /** The temp file system. */
    @Autowired
    private TempFileSystem tempFileSystem;

    /** The notification dao. */
    @Autowired
    private NotificationDao notificationDao;

    /**
     * Gets the all.
     *
     * @param notificationEndPoints the notification end points
     * @return the all
     * @throws DataException the data exception
     */
    public List<NotificationDto> getAll(
            List<NotificationEndPoint> notificationEndPoints) throws DataException {
        List<NotificationDto> finalNotifications = null;
        try {
            List<NotificationDto> notifications = notificationDao.getAll(notificationEndPoints);
            finalNotifications = filterAvailableNotifications(notifications);
        } catch (DaoException daoException) {
            throw new DataException(daoException.getMessage(), daoException);
        }
        return finalNotifications;
    }

    /**
     * Filter available notifications.
     * 
     * @param notifications the notifications
     * @return the list
     */
    private List<NotificationDto> filterAvailableNotifications(
            List<NotificationDto> notifications) {
        List<NotificationDto> finalNotifications = null;
        List<NotificationTemplateDto> notificationTemplates = notificationTemplatesFileSystem.getNotificationTemplates();
        if (notifications != null && !notifications.isEmpty()
                && notificationTemplates != null && !notificationTemplates.isEmpty()) {
            finalNotifications = new ArrayList<NotificationDto>();
            for (NotificationDto notificationDto : notifications) {
                for (NotificationTemplateDto notificationTemplateDto : notificationTemplates) {
                    if (notificationDto.getNotificationEndPoint() == notificationTemplateDto.getNotificationEndPoint()
                            && notificationDto.getNotificationMode() == notificationTemplateDto.getNotificationMode()
                            && notificationDto.getNotificationType() == notificationTemplateDto.getNotificationType()
                            && notificationTemplateDto.getTestFile() != null) {
                        finalNotifications.add(notificationDto);
                    }
                }
            }
        }
        return finalNotifications;
    }

    /**
     * Gets the.
     *
     * @param notificationId the notification id
     * @return the notification dto
     * @throws DataException the data exception
     */
    public NotificationDto get(int notificationId) throws DataException {
        NotificationDto notification = null;
        try {
            notification = notificationDao.get(notificationId);
        } catch (DaoException daoException) {
            throw new DataException(daoException.getMessage(), daoException);
        }
        return notification;
    }

    /**
     * Gets the template text.
     * 
     * @param templateId the template id
     * @return the template text
     * @throws DataException the data exception
     */
    public String getTemplateText(int templateId) throws DataException {
        String templateText = "This Notification Template is currently not available";
        NotificationTemplateDto notificationTemplate = null;

        try {
            NotificationDto notificationDto = get(templateId);
            if (notificationDto != null) {
                notificationTemplate = notificationTemplatesFileSystem.getNotificationTemplate(notificationDto);
                if (notificationTemplate != null) {
                    StringBuffer fileName = new StringBuffer();
                    fileName.append(notificationTemplate.getNotificationEndPoint());
                    fileName.append("_");
                    fileName.append(notificationTemplate.getNotificationMode());
                    fileName.append("_");
                    fileName.append(notificationTemplate.getNotificationType());
                    fileName.append(".").append(FileExtension.HTML.getFileExtension());
                    File outputFile = tempFileSystem.createNotificationFile(fileName.toString());
                    XslUtil.transform(notificationTemplate.getTemplateFile(), notificationTemplate.getTestFile(), outputFile);
                    templateText = FileUtil.getFileContent(outputFile);
                }
            }
        } catch (FileSystemException fileSystemException) {
            throw new DataException(fileSystemException.getMessage(), fileSystemException);
        } catch (TransformerException transformerException) {
            throw new DataException(transformerException.getMessage(), transformerException);
        }
        return templateText;
    }

    /**
     * Creates the.
     *
     * @param notification the notification
     * @return true, if successful
     * @throws DataException the data exception
     */
    public boolean create(NotificationDto notification) throws DataException {
        boolean successful;
        try {
            int headerId = notificationDao.createNotificationHeader(notification);
            if (headerId <= 0) {
                throw new DataException("Unable to create notification header. Please contact support for assistance.");
            }
            successful = notificationDao.createNotificationDetail(headerId, notification.getNotifyingIds());
            if (!successful) {
                throw new DataException("Unable to create notification details. Please contact support for assistance.");
                // rollback is required at this point.
            }
        } catch (DaoException daoException) {
            throw new DataException(daoException.getMessage(), daoException);
        }
        return successful;
    }
    
    /**
     * Gets the all.
     * 
     * @param notificationStatus the notification status
     * @return the all
     * @throws DataException the data exception
     */
    public List<NotificationDto> getNotificationHeaderData(NotificationStatus[] notificationStatus) throws DataException {
        List<NotificationDto> notifications = null;
        try {
            notifications = notificationDao.getNotificationHeaderData(notificationStatus);
        } catch (DaoException daoException) {
            throw new DataException(daoException.getMessage(), daoException);
        }
        return notifications;
    }

	/**
     * Gets the notification detail data.
     * 
     * @param headerId the header id
     * @param notificationStatus the notification status
     * @return the notification detail data
     * @throws DataException the data exception
     */
    public List<NotificationDetailDto> getNotificationDetailData(int headerId,
            NotificationStatus[] notificationStatus) throws DataException {
        List<NotificationDetailDto> notificationDetail = null;
        try {
            notificationDetail = notificationDao.getNotificationDetailData(
                    headerId, notificationStatus);
        } catch (DaoException daoException) {
            throw new DataException(daoException.getMessage(), daoException);
        }
        return notificationDetail;
    }

	/**
     * Update notification header status.
     * 
     * @param headerId the header id
     * @param status the status
     * @return true, if successful
     * @throws DataException the data exception
     */
    public boolean updateNotificationHeaderStatus(int headerId, String status)
            throws DataException {
        boolean result = false;
        try {
            result = notificationDao.updateNotificationHeaderStatus(headerId,
                    status);
        } catch (DaoException daoException) {
            throw new DataException(daoException.getMessage(), daoException);
        }
        return result;
    }

	/**
     * Update notification detail status.
     * 
     * @param detailId the detail id
     * @param status the status
     * @return true, if successful
     * @throws DataException the data exception
     */
    public boolean updateNotificationDetailStatus(int detailId, String status)
            throws DataException {
        boolean result = false;
        try {
            result = notificationDao.updateNotificationDetailStatus(detailId, status);
        } catch (DaoException daoException) {
            throw new DataException(daoException.getMessage(), daoException);
        }
        return result;
    }

    /**
     * Creates the notification.
     * 
     * @param notificationEndPoint the notification end point
     * @param notificationType the notification type
     * @param mailNotifyingIds the mail notifying ids
     * @param smsNotifyingIds the sms notifying ids
     * @throws DataException the data exception
     */
    public void createNotification(NotificationEndPoint notificationEndPoint,
            NotificationType notificationType, List<Integer> mailNotifyingIds,
            List<Integer> smsNotifyingIds) throws DataException {
        try {
            if (mailNotifyingIds != null && !mailNotifyingIds.isEmpty()) {
                // Create async notification that says the student has been created.
                NotificationDto notification = NotificationDataAssembler.createNotification(
                        notificationEndPoint, NotificationMode.EMAIL, notificationType, mailNotifyingIds);
                int notificationHeaderId = notificationDao.createNotificationHeader(notification);
                notificationDao.createNotificationDetail(notificationHeaderId, mailNotifyingIds);
                mailNotifyingIds.clear();
            }
            if (smsNotifyingIds != null && !smsNotifyingIds.isEmpty()) {
                // Create async notification that says the student has been created.
                NotificationDto notification = NotificationDataAssembler.createNotification(
                        notificationEndPoint, NotificationMode.SMS, notificationType, smsNotifyingIds);
                int notificationHeaderId = notificationDao.createNotificationHeader(notification);
                notificationDao.createNotificationDetail(notificationHeaderId, smsNotifyingIds);
                smsNotifyingIds.clear();
            }
        } catch (DaoException daoException) {
            throw new DataException(daoException.getMessage(), daoException);
        }
    }

    /**
     * Gets the text in device.
     * 
     * @param device the device
     * @param templateId the template id
     * @return the text in device
     * @throws DataException the data exception
     */
    public String getTextInDevice(Device device, int templateId) throws DataException {
        String templateText = null;
        try {
            File deviceTemplateFile = deviceTemplatesFileSystem.getDeviceTemplate(device);
            if (deviceTemplateFile != null) {
                NotificationDto notificationDto = get(templateId);
                if (notificationDto != null) {
                    templateText = getTemplateText(templateId);
                    if (templateText != null) {
                        StringBuffer fileName = new StringBuffer();
                        fileName.append(device);
                        fileName.append("_");
                        fileName.append(notificationDto.getNotificationEndPoint());
                        fileName.append("_");
                        fileName.append(notificationDto.getNotificationMode());
                        fileName.append("_");
                        fileName.append(notificationDto.getNotificationType());
                        fileName.append(".").append(FileExtension.HTML.getFileExtension());
                        File outputFile = tempFileSystem.createNotificationFile(fileName.toString());
                        File inputFile = tempFileSystem.createNotificationFile(
                                "input_" + fileName .toString() .replaceAll(
                                        FileExtension.HTML.getFileExtension(),
                                        FileExtension.XML.getFileExtension()));
                        FileUtil.writeToFile(inputFile, getInLineContent(templateText).getBytes());
                        XslUtil.transform(deviceTemplateFile, inputFile, outputFile);
                        templateText = FileUtil.getFileContent(outputFile);
                    }
                }
            }
        } catch (FileSystemException fileSystemException) {
            throw new DataException(fileSystemException.getMessage(), fileSystemException);
        } catch (TransformerException transformerException) {
            throw new DataException(transformerException.getMessage(), transformerException);
        }
        return templateText;
    }

    /**
     * Gets the in line content.
     * 
     * @param templateText the template text
     * @return the in line content
     */
    private String getInLineContent(String templateText) {
        // TODO move this method.
        StringBuffer buffer = new StringBuffer();
        buffer.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        buffer.append("<Content>");
        buffer.append(templateText);
        buffer.append("</Content>");
        return buffer.toString().replaceAll("<br>", "<br/>").replaceAll("<pre>", "").replaceAll("</pre>", "");
    }

    /**
     * Gets the all.
     * 
     * @param notificationCriteria the notification criteria
     * @return the all
     * @throws DataException the data exception
     */
    public List<NotificationDto> getAll(
            NotificationCriteriaDto notificationCriteria) throws DataException {
        List<NotificationDto> finalNotifications = null;
        try {
            List<NotificationDto> notifications = notificationDao.getAll(notificationCriteria);
            finalNotifications = filterAvailableNotifications(notifications);
        } catch (DaoException daoException) {
            throw new DataException(daoException.getMessage(), daoException);
        }
        return finalNotifications;
    }

}
