package com.myschool.notification.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myschool.application.constants.Device;
import com.myschool.common.exception.DataException;
import com.myschool.common.exception.ServiceException;
import com.myschool.notification.constants.NotificationEndPoint;
import com.myschool.notification.constants.NotificationStatus;
import com.myschool.notification.domain.NotificationManager;
import com.myschool.notification.dto.NotificationCriteriaDto;
import com.myschool.notification.dto.NotificationDetailDto;
import com.myschool.notification.dto.NotificationDto;

/**
 * The Class NotificationServiceImpl.
 */
@Service
public class NotificationServiceImpl implements NotificationService {

    /** The notification manager. */
    @Autowired
    private NotificationManager notificationManager;

    /* (non-Javadoc)
     * @see com.myschool.application.service.Servicable#create(java.lang.Object)
     */
    @Override
    public boolean create(NotificationDto notification) throws ServiceException {
        boolean created = false;
        try {
            created = notificationManager.create(notification);
        } catch (DataException dataException) {
            throw new ServiceException(dataException.getMessage(), dataException);
        }
        return created;
    }

    /* (non-Javadoc)
     * @see com.myschool.application.service.Servicable#getAll()
     */
    @Override
    public List<NotificationDto> getAll() throws ServiceException {
        return getAll((List<NotificationEndPoint>) null);
    }

    /* (non-Javadoc)
     * @see com.myschool.application.service.Servicable#get(int)
     */
    @Override
    public NotificationDto get(int notificationId) throws ServiceException {
        NotificationDto notification = null;
        try {
            notification = notificationManager.get(notificationId);
        } catch (DataException dataException) {
            throw new ServiceException(dataException.getMessage(),
                    dataException);
        }
        return notification;
    }

    /* (non-Javadoc)
     * @see com.myschool.application.service.Servicable#update(int, java.lang.Object)
     */
    @Override
    public boolean update(int notificationId, NotificationDto dto) throws ServiceException {
        return false;
    }

    /* (non-Javadoc)
     * @see com.myschool.application.service.Servicable#delete(int)
     */
    @Override
    public boolean delete(int notificationId) throws ServiceException {
        return false;
    }

    /* (non-Javadoc)
     * @see com.myschool.notification.service.NotificationService#getAll(java.util.List)
     */
    @Override
    public List<NotificationDto> getAll(
            List<NotificationEndPoint> notificationEndPoints) throws ServiceException {
        List<NotificationDto> notifications = null;
        try {
            notifications = notificationManager.getAll(notificationEndPoints);
        } catch (DataException dataException) {
            throw new ServiceException(dataException.getMessage(),
                    dataException);
        }
        return notifications;
    }

    /* (non-Javadoc)
     * @see com.myschool.notification.service.NotificationService#getTemplateText(int)
     */
    @Override
    public String getTemplateText(int templateId) throws ServiceException {
        String templateText = null;
        try {
            templateText = notificationManager.getTemplateText(templateId);
        } catch (DataException dataException) {
            throw new ServiceException(dataException.getMessage(),
                    dataException);
        }
        return templateText;
    }

    /* (non-Javadoc)
     * @see com.myschool.notification.service.NotificationService#getNotificationHeaderData(com.myschool.notification.dto.NotificationStatus[])
     */
    @Override
    public List<NotificationDto> getNotificationHeaderData(NotificationStatus[] notificationStatus)
             throws ServiceException {
    	 List<NotificationDto> notifications = null;
         try {
             notifications = notificationManager.getNotificationHeaderData(notificationStatus);
         } catch (DataException dataException) {
             throw new ServiceException(dataException.getMessage(),
                     dataException);
         }
         return notifications;
    }
    
	/* (non-Javadoc)
	 * @see com.myschool.notification.service.NotificationService#getNotificationDetailData(int, com.myschool.notification.dto.NotificationStatus[])
	 */
	@Override
	public List<NotificationDetailDto> getNotificationDetailData(int headerId, NotificationStatus[] notificationStatus)
			throws ServiceException {
		List<NotificationDetailDto> notificationDetail = null;
        try {
        	notificationDetail = notificationManager.getNotificationDetailData(headerId, notificationStatus);
        } catch (DataException dataException) {
            throw new ServiceException(dataException.getMessage(),
                    dataException);
        }
        return notificationDetail;
	}

	/* (non-Javadoc)
	 * @see com.myschool.notification.service.NotificationService#updateNotificationHeaderStatus(int, java.lang.String)
	 */
	@Override
	public boolean updateNotificationHeaderStatus(int headerId, String status)
	throws ServiceException {
		boolean result = false;
		try {
			result = notificationManager.updateNotificationHeaderStatus(headerId, status);
		} catch (DataException dataException) {
			throw new ServiceException(dataException.getMessage(),
					dataException);
		}
		return result;
	}
	
	/* (non-Javadoc)
	 * @see com.myschool.notification.service.NotificationService#updateNotificationDetailStatus(int, java.lang.String)
	 */
	@Override
	public boolean updateNotificationDetailStatus(int detailId, String status)
			throws ServiceException {
		boolean result = false;
		try {
			result = notificationManager.updateNotificationDetailStatus(detailId, status);
        } catch (DataException dataException) {
            throw new ServiceException(dataException.getMessage(),
                    dataException);
        }
        return result;
	}

    /* (non-Javadoc)
     * @see com.myschool.notification.service.NotificationService#getTextInDevice(com.myschool.application.constants.Device, int)
     */
    @Override
    public String getTextInDevice(Device device, int templateId) throws ServiceException {
        String templateText = null;
        try {
            templateText = notificationManager.getTextInDevice(device, templateId);
        } catch (DataException dataException) {
            throw new ServiceException(dataException.getMessage(), dataException);
        }
        return templateText;
    }

    /* (non-Javadoc)
     * @see com.myschool.notification.service.NotificationService#getAll(com.myschool.notification.dto.NotificationCriteriaDto)
     */
    @Override
    public List<NotificationDto> getAll(
            NotificationCriteriaDto notificationCriteria) throws ServiceException {
        List<NotificationDto> notifications = null;
        try {
            notifications = notificationManager.getAll(notificationCriteria);
        } catch (DataException dataException) {
            throw new ServiceException(dataException.getMessage(),
                    dataException);
        }
        return notifications;
    }

}
