package com.myschool.notification.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.myschool.application.constants.Device;
import com.myschool.application.service.Servicable;
import com.myschool.common.exception.ServiceException;
import com.myschool.notification.constants.NotificationEndPoint;
import com.myschool.notification.constants.NotificationStatus;
import com.myschool.notification.dto.NotificationCriteriaDto;
import com.myschool.notification.dto.NotificationDetailDto;
import com.myschool.notification.dto.NotificationDto;

/**
 * The Interface NotificationService.
 */
@Service
public interface NotificationService extends Servicable<NotificationDto> {

    /**
     * Gets the all.
     *
     * @param notificationEndPoints the notification end points
     * @return the all
     * @throws ServiceException the service exception
     */
    List<NotificationDto> getAll(
            List<NotificationEndPoint> notificationEndPoints) throws ServiceException;

    /**
     * Gets the template text.
     * 
     * @param templateId the template id
     * @return the template text
     * @throws ServiceException the service exception
     */
    String getTemplateText(int templateId) throws ServiceException;

    /**
     * Gets the notification header data.
     * 
     * @param notificationStatus the notification status
     * @return the notification header data
     * @throws ServiceException the service exception
     */
    List<NotificationDto> getNotificationHeaderData(NotificationStatus[] notificationStatus)throws ServiceException;

    /**
     * Gets the notification detail data.
     * 
     * @param headerId the header id
     * @param notificationStatus the notification status
     * @return the notification detail data
     * @throws ServiceException the service exception
     */
    List<NotificationDetailDto> getNotificationDetailData(int headerId, NotificationStatus[] notificationStatus)throws ServiceException;

    /**
     * Update notification header status.
     * 
     * @param headerId the header id
     * @param status the status
     * @return true, if successful
     * @throws ServiceException the service exception
     */
    boolean updateNotificationHeaderStatus(int headerId, String status) throws ServiceException;

    /**
     * Update notification detail status.
     * 
     * @param detailId the detail id
     * @param status the status
     * @return true, if successful
     * @throws ServiceException the service exception
     */
    boolean updateNotificationDetailStatus(int detailId, String status) throws ServiceException;

    /**
     * Gets the text in device.
     * 
     * @param device the device
     * @param templateId the template id
     * @return the text in device
     * @throws ServiceException the service exception
     */
    String getTextInDevice(Device device, int templateId) throws ServiceException;

    /**
     * Gets the all.
     * 
     * @param notificationCriteria the notification criteria
     * @return the all
     * @throws ServiceException the service exception
     */
    List<NotificationDto> getAll(NotificationCriteriaDto notificationCriteria) throws ServiceException;

}
